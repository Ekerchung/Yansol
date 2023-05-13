<template>
  <div class="manage">
    <!-- 點擊新增按鈕的彈框表單 -->
    <el-dialog
        :title="`${this.modelType === 0 ? '新增員工' : '修改員工資料'}`"
        :visible.sync="dialogFormVisible"
        width="400px"
        :before-close="handleClose">
      <el-form :model="form" :rules="rules" ref="form" label-width="50px" style="width: 350px">
        <el-form-item label="姓名" prop="name">
          <el-input v-model="form.name"  placeholder="請輸入姓名"></el-input>
        </el-form-item>
        <el-form-item label="電話" prop="phone">
          <el-input v-model="form.phone"  placeholder="請輸入電話"></el-input>
        </el-form-item>
        <el-form-item label="地址" prop="address" >
          <el-input v-model="form.address"  placeholder="請輸入地址"></el-input>
        </el-form-item>
        <el-form-item label="生日" prop="birthday" >
          <el-date-picker
              type="date"
              placeholder="選擇日期"
              v-model="form.birthday"
              style="width: 100%"
              value-format="yyyy-MM-DD"
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item label="性別">
          <template>
            <el-radio v-model="form.gender" label="0">女</el-radio>
            <el-radio v-model="form.gender" label="1">男</el-radio>
          </template>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="handleClose">取 消</el-button>
        <el-button type="primary" @click="submit">確 定</el-button>
      </div>
    </el-dialog>
    <div class="manage-header">
      <!--新增按鈕-->
      <el-button type="primary" @click="dialogFormVisible = true">+ 新增</el-button>
      <el-form :inline="true" :model="queryForm">
        <el-form-item>
          <el-input v-model="queryForm.queryKeyword" placeholder="輸入關鍵字" @keyup.enter.native="handlePage(1)"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handlePage(1)">查詢</el-button>
        </el-form-item>
      </el-form>
    </div>
    <div class="common-tabel">
      <!--    人員管理表格-->
      <el-table
          :data="employeePageData.content"
          height="90%"
          stripe
          style="width: 100%">
        <el-table-column
            prop="name"
            label="姓名"
            width="120">
        </el-table-column>
        <el-table-column
            prop= "gender"
            label="性別"
            width="120">
          <template slot-scope="scope">
            <span>{{ scope.row.gender == 1 ? '男' : '女' }}</span>
          </template>
        </el-table-column>
        <el-table-column
            prop="birthday"
            label="生日"
            width="120">
        </el-table-column>
        <el-table-column
            prop="phone"
            label="電話"
            width="180">
        </el-table-column>
        <el-table-column
            prop="address"
            label="地址"
            width="400">
        </el-table-column>
        <el-table-column
            prop="addr"
            label="操作"
            width="700">
          <template slot-scope="scope">
            <el-button size="mini" @click="handleEdit(scope.row)">編輯</el-button>
            <el-button type="danger" size="mini" @click="handleDelete(scope.row)">刪除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <!--分頁功能-->
      <div class="pager">
        <el-pagination

            background
            layout="prev, pager, next"
            :total=employeePageData.totalElements
            @current-change="handlePage">>
        </el-pagination>
      </div>
    </div>
  </div>



</template>

<script>
import {mapActions, mapMutations, mapState} from 'vuex';
export default {
  name:'WorkerManagement',
  data(){
    return{
      //彈窗開/關
      dialogFormVisible: false,
      //employee表彈窗初始資料
      form: {
        name: '',
        gender: '0',
        birthday: '',
        phone: '',
        address: ''
      },
      //驗證彈窗輸入資料
      rules: {
        name: [
          { required: true, message: '請輸入姓名', trigger: 'blur' },
        ],
      },
      modelType:0, //0：新增窗口，1：修改窗口
      //查詢功能信息
      queryForm:{
        queryKeyword:'',
      },
    }
  },

  computed: {
    ...mapState('worker',['employeePageData']),
  },
  methods:{
    ...mapActions('data',['fetchGoodData','fetchCompanyData','fetchEmployeeData','fetchOrderData','fetchReturnData']),
    ...mapActions('worker',['fetchUpdateEmployee','fetchDeleteEmployee','fetchGetEmployeePageData']),
    ...mapMutations('worker',['setEmployeePageData']),
    //點擊彈窗關閉時操作
    handleClose(){
      this.$refs.form.resetFields();
      this.dialogFormVisible = false;
    },
    //點擊編輯時操作
    handleEdit(row){
      //設定為編輯窗口
      this.modelType = 1;
      //顯示彈窗
      this.dialogFormVisible = true;
      //【重要】使用深拷貝row資料來回顯到編輯窗口，若不使用深拷貝，則編輯的資料會與表格資料連動，會有錯誤
      this.form = JSON.parse(JSON.stringify(row));
    },
    //點擊刪除時操作
    handleDelete(row) {
      this.$confirm(`是否確定要刪除【${row.name}】的資料?`, '提示', {
        confirmButtonText: '確定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        console.log(row.eid);
        this.fetchDeleteEmployee(row.eid).then(() => {
          this.$message({
            type: 'success',
            message: '刪除成功!'
          });
          //重新查詢數據庫資料，更新數據
          this.fetchEmployeeData();
        })

      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消刪除'
        });
      });
    },
    //提交用戶表單
    submit(){
      this.$refs.form.validate(valid => {
        //驗證通過，執行if內的代碼
        if(valid){
          //新增或修改員工資料
          this.fetchUpdateEmployee(this.form);
        }
        //關閉彈窗
        this.dialogFormVisible = false;
        //清空form表單的數據
        setTimeout(() => {
          //重新查詢數據庫資料，更新數據
          this.fetchEmployeeData();
          // this.$refs.form.resetFields();
          this.$refs.form.resetFields();
        }, 200);

      })
    },
    //切換分頁
    handlePage(pageNum){
      //將分頁頁碼保存到參數params中
      const params = {'pageNum': pageNum,'queryKeyWord': this.queryForm.queryKeyword};
      //請求查詢分頁信息
      this.fetchGetEmployeePageData(params);
    },
  },
  //頁面創建時獲取員工資料
  created() {
    this.fetchGetEmployeePageData({'pageNum':1});
  }
}
</script>

<style  scoped>
.manage {
  height: 100%;
}

.manage .manage-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.manage .common-tabel {
  position: relative;
  height: calc(100% - 62px);
}

.manage .common-tabel .pager {
  position: absolute;
  bottom: 40px;
  right: 20px;
}

</style>