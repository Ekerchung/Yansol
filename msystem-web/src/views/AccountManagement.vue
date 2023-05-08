<template>
  <div class="manage">
    <!-- 點擊新增按鈕的彈框表單 -->
    <el-dialog
        :title="`${this.modelType === 0 ? '新增帳號' : '修改帳號資料'}`"
        :visible.sync="dialogFormVisible"
        width="400px"
        :before-close="handleClose">
      <el-form :model="form" :rules="rules" ref="form" label-width="100px" style="width: 350px">
        <el-form-item label="帳號" prop="username">
          <el-input v-model="form.username"  placeholder="請輸入帳號"></el-input>
        </el-form-item>
        <el-form-item label="密碼" prop="password">
          <el-input v-model="form.password" type="password"  placeholder="請輸入密碼"></el-input>
        </el-form-item>
        <el-form-item label="確認密碼" prop="checkpass" >
          <el-input v-model="form.checkpass" type="password"  placeholder="請確認密碼"></el-input>
        </el-form-item>
        <el-form-item label="權限" prop="role" >
          <el-input v-model="form.role"  placeholder="1：管理員，2：一般用戶"></el-input>
        </el-form-item>
        <el-form-item label="帳號所有人" prop="eId" >
          <el-select v-model="form.employee.eid" style="width: 250px" placeholder="請選擇員工">
            <el-option v-for="emp in employeeData" :label= "`${emp.eid}_${emp.name}`"  :value=emp.eid :key="emp.eid"></el-option>
          </el-select>
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
          <el-input v-model="queryForm.queryKeyword" placeholder="輸入帳號"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handlePage(1)">查詢</el-button>
        </el-form-item>
      </el-form>
    </div>
    <div class="common-tabel">
      <!--    人員管理表格-->
      <el-table
          :data="accountPageData.content"
          height="90%"
          stripe
          style="width: 100%">
        <el-table-column
            prop="username"
            label="帳號"
            width="180">
        </el-table-column>
        <el-table-column
            prop= "password"
            label="密碼"
            width="180">
        </el-table-column>
        <el-table-column
            prop="employee.name"
            label="帳號持有人"
            width="180">
        </el-table-column>
        <el-table-column
            prop="lastLogin"
            label="上次登陸時間"
            width="240">
        </el-table-column>
        <el-table-column
            prop="currentLogin"
            label="當次登陸時間"
            width="240">
        </el-table-column>
        <el-table-column
            prop="role"
            label="權限"
            width="240">
          <template slot-scope="scope">
            <span>{{ scope.row.role == 1 ? '管理員' : '一般使用者' }}</span>
          </template>
        </el-table-column>
        <el-table-column
            prop="addr"
            label="操作"
            width="300">
          <template slot-scope="scope">
            <el-button size="mini" @click="handleEdit(scope.row)">編輯</el-button>
            <el-button type="danger" size="mini" @click="handleDelete(scope.row)">刪除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <!--分頁功能-->
      <div class="pager">
        <el-pagination
            :hide-on-single-page="true"
            :current-page.sync="currentPage"
            :page-size="pageSize"
            :total=accountPageData.totalElements
            background
            layout="prev, pager, next"
            @current-change="handlePage">>
        </el-pagination>
      </div>
    </div>
  </div>
</template>


<script>
import {mapActions, mapMutations, mapState} from 'vuex';
export default {
  name:'AccountManagement',
  data(){
    let validatePass = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('請輸入密碼'));
      } else {
        if (this.form.checkpass !== '') {
          this.$refs.form.validateField('checkPass');
        }
        callback();
      }
    };
    let validatePass2 = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('請再次輸入密碼'));
      } else if (value !== this.form.password) {
        callback(new Error('兩次輸入密碼不一致!'));
      } else {
        callback();
      }
    };
    return{
      currentPage: 1,
      pageSize: 10,
      //彈窗開/關
      dialogFormVisible: false,
      //employee表彈窗初始資料
      form: {
        username: '',
        password: '',
        checkpass: '',
        role: '',
        eId: '',
        employee:{
          eid:''
        }
      },
      //驗證彈窗輸入資料
      rules: {
        username: [
          { required: true, message: '請輸入帳號', trigger: 'blur' },
        ],
        password: [
          { required: true, validator: validatePass, trigger: 'blur' },
        ],
        checkpass: [
          { required: true, validator: validatePass2, trigger: 'blur' },
        ],
        role: [
          { required: true, message: '請輸入權限', trigger: 'blur' },
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
    ...mapState('account',['accountPageData']),
    ...mapState('data',['employeeData']),
  },
  methods:{
    ...mapActions('data',['fetchGoodData','fetchCompanyData','fetchEmployeeData','fetchOrderData','fetchReturnData']),
    ...mapActions('account',['fetchUpdateAccount','fetchDeleteAccount','fetchGetAccountPageData']),
    ...mapMutations('account',['setAccountPageData']),
    //點擊彈窗關閉時操作
    handleClose(){
      this.$refs.form.resetFields();
      this.form.employee.eid = ''
      // console.log('清空資料')
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
      // console.log(this.form)
    },
    //點擊刪除時操作
    handleDelete(row) {
      // console.log(row)
      this.$confirm(`是否確定要刪除【${row.username}】的資料?`, '提示', {
        confirmButtonText: '確定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        // console.log(row.aid);
        this.fetchDeleteAccount(row.aid).then(() => {
          this.$message({
            type: 'success',
            message: '刪除成功!'
          });
          //重新查詢數據庫資料，更新數據
          setTimeout(() => {
            //重新查詢數據庫資料，更新數據
            this.fetchGetAccountPageData({'pageNum':this.currentPage});
          }, 200);
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
          // console.log("新增帳號")
          this.form.eId = this.form.employee.eid
          //新增或修改員工資料
          this.fetchUpdateAccount(this.form);
        }
        //關閉彈窗
        this.dialogFormVisible = false;
        //清空form表單的數據
        setTimeout(() => {
          //重新查詢數據庫資料，更新數據
          this.fetchGetAccountPageData({'pageNum':this.currentPage});
          // this.$refs.form.resetFields();
          this.$refs.form.resetFields();
          this.form.employee.eid = '';
        }, 200);

      })
    },
    //切換分頁
    handlePage(pageNum){
      //將分頁頁碼保存到參數params中
      const params = {'pageNum': pageNum,'queryKeyWord': this.queryForm.queryKeyword};
      //請求查詢分頁信息
      this.fetchGetAccountPageData(params);
    },
  },
  //點擊新增時操作
  created() {
    this.fetchGetAccountPageData({'pageNum':1});
    this.fetchEmployeeData();
  }
}
</script>

<style  scoped>
/*.el-dialog__header , .el-dialog__body , .el-dialog__footer{*/
/*  width: 400px;*/
/*}*/
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