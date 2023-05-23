<template>
  <div class="manage">
    <div><span class="title">線材生產</span></div>
    <!-- 點擊完成按鈕的彈框表單 -->
    <el-dialog
        title="完成日期"
        :visible.sync="dialogFormVisible"
        width="400px"
        :before-close="handleClose">
      <el-form :model="form" :rules="rules" ref="form" label-width="100px" style="width: 350px">
        <el-form-item label="完成日期" prop="comDate">
          <el-date-picker
              v-model="form.comDate"
              align="right"
              type="date"
              placeholder="選擇完成日期"
              style="width: 250px"
              :picker-options="pickerOptions">
          </el-date-picker>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="handleClose">取 消</el-button>
        <el-button type="primary" @click="submit">確 定</el-button>
      </div>
    </el-dialog>
    <div class="manage-header">
      <br><br><br>
    </div>
    <div class="common-tabel">
      <!--    線材生產表單-->
      <el-table
          :data="orderDataByEid"
          height="90%"
          stripe
          empty-text="暫無數據"
          style="width: 100%">
        <el-table-column
            type="index"
            width="50">
        </el-table-column>
        <el-table-column
            prop= "oDate"
            label="訂單日期"
            width="180">
        </el-table-column>
        <el-table-column
            prop="good.lineId"
            label="線材編號"
            width="180">
        </el-table-column>
        <el-table-column
            prop="good.lineName"
            label="品名"
            width="300">
        </el-table-column>
        <el-table-column
            prop="good.lineType"
            label="端子"
            width="300">
        </el-table-column>
        <el-table-column
            prop="ocount"
            label="數量"
            width="180">
        </el-table-column>
        <el-table-column
            prop="ounitPrice"
            label="單價"
            width="180">
        </el-table-column>
        <el-table-column
            prop="addr"
            label="操作"
            width="240">
          <template slot-scope="scope">
            <el-button type="primary" size="mini" @click="handleComplete(scope.row)">完成</el-button>
          </template>
        </el-table-column>
      </el-table>
      <!--分頁功能-->
      <div class="pager">
        <el-pagination
            :hide-on-single-page="true"
            :current-page.sync="currentPage"
            :page-size="pageSize"
            :total=orderDataByEid.totalElements
            background
            layout="prev, pager, next"
            @current-change="handlePage">>
        </el-pagination>
      </div>
    </div>
  </div>
</template>

<script>
import {mapActions, mapState} from "vuex";

export default {
  name:'UserLineProcess',
  data(){
    return{
      currentPage: 1,
      pageSize: 10,
      state:1, //線材狀態 1:已收貨，2:生產中，3：待發貨，4：已發貨
      dialogFormVisible: false,//分配/修改彈窗開/關
      dialogFormVisible_check: false,//查看彈窗開/關
      queryForm:{
        queryKeyword:'',
      },
      //分配彈窗初始資料
      form: {
        comDate:'',
        eid:'',
      },

      //驗證彈窗輸入資料
      rules: {
        comDate: [
          { required: true, message: '請選擇日期',  trigger: 'blur' },
        ],
      },
      pickerOptions: {
        disabledDate(time) {
          return time.getTime() > Date.now();
        },
        shortcuts: [{
          text: '今天',
          onClick(picker) {
            picker.$emit('pick', new Date());
          }
        }, {
          text: '昨天',
          onClick(picker) {
            const date = new Date();
            date.setTime(date.getTime() - 3600 * 1000 * 24);
            picker.$emit('pick', date);
          }
        }, {
          text: '一周前',
          onClick(picker) {
            const date = new Date();
            date.setTime(date.getTime() - 3600 * 1000 * 24 * 7);
            picker.$emit('pick', date);
          }
        }]
      },
    }
  },
  computed: {
    ...mapState('data', ['orderDataByEid']),
  },
  methods: {
    ...mapActions('data', ['fetchOrderDataByEid']),
    ...mapActions('order', ['fetchUpdateOrder']),

    //切換分頁
    handlePage(pageNum){
      //將分頁頁碼保存到參數params中
      const params = {'pageNum': pageNum,'queryKeyWord': this.queryForm.queryKeyword,'state':this.state};
      //請求查詢分頁信息
      this.fetchOrderPageData(params);
    },
    //點擊完成時操作
    handleComplete(row){
      //顯示彈窗
      this.dialogFormVisible = true;
      //【重要】使用深拷貝row資料來回顯到編輯窗口，若不使用深拷貝，則編輯的資料會與表格資料連動，會有錯誤
      this.form = JSON.parse(JSON.stringify(row));
    },
    //點擊彈窗關閉時操作
    handleClose(){
      //清空彈窗資料
      this.$refs.form.resetFields();
      this.dialogFormVisible = false;
    },
    //提交用戶表單
    submit(){
      this.$refs.form.validate(valid => {
        //驗證通過，執行if內的代碼
        if(valid){
          this.form.eId = this.form.employee.eid
          this.form.eid = this.form.employee.eid
          console.log(this.form)
          //新增訂單資料
          this.fetchUpdateOrder(this.form).then(() => {
            this.$message({
              type: 'success',
              message: '分配完成!'
            });
            //關閉彈窗
            this.dialogFormVisible = false;
            //清空form表單的數據
            setTimeout(() => {
              //重新查詢數據庫資料，更新數據
              this.fetchOrderDatByEid(this.employee.eid);
              //清除表單數據
              this.$refs.form.resetFields();
            }, 200);
          }).catch((error) => {
            this.$message({
              type: 'error',
              message: "發生錯誤，請聯絡管理員"
            });
          })
        }
      })
    },
  },
  mounted() {
    //獲取localStorage保存的此帳號對應的員工信息
    this.employee = JSON.parse(localStorage.getItem("employee"));
    //獲取localStorage保存的此帳號上次登入時間
    this.lastLoginTime = localStorage.getItem("lastLoginTime").substr(0, 10);
    console.log(this.employee.eid)
    this.fetchOrderDataByEid(this.employee.eid);
  },
}
</script>

<style scoped>
.title{
  font-size: 24px;
  font-weight: bold;
  border-bottom: 1px solid black;
}
.el-form-item {
  margin-bottom: 15px;
}

.manage {
  height: 100%;
}

.manage .manage-header {
  display: flex;
  justify-content: end;
  align-items: center;
}

.manage .common-tabel {
  position: relative;
  height: 80vh;
  /*height: 700px;*/
}

.manage .common-tabel .pager {
  position: absolute;
  bottom: 40px;
  right: 20px;
}
.submit_btn{
  margin-left: 1400px;
  margin-top: 20px;
}
.manage .common-tabel .pager {
  position: absolute;
  bottom: 40px;
  right: 20px;
}
</style>