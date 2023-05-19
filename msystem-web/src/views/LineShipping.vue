<template>
  <div class="manage">
    <div><span class="title">線材出貨</span></div>
    <!-- 點擊分配/修改按鈕的彈框表單 -->
    <el-dialog
        title="發貨"
        :visible.sync="dialogFormVisible"
        width="400px"
        :before-close="handleClose">
      <el-form :model="form" :rules="rules" ref="form" label-width="100px" style="width: 350px">
        <el-form-item label="出貨日期" prop="sDate">
          <el-date-picker
              v-model="form.sDate"
              align="right"
              type="date"
              placeholder="選擇出貨日期"
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
    <!-- 點擊查看按鈕的彈框表單 -->
    <el-dialog class="checkTable"
        :visible.sync="dialogFormVisible_check"
        width="1400px"
        :before-close="handleClose_check">
        <el-table
            :data="this.orderQueryData"
            height="90%"
            stripe
            style="width: 100%">
          <el-table-column
              prop= "oid"
              label="訂單編號"
              width="120">
          </el-table-column>
          <el-table-column
              prop="good.company.companyName"
              label="進貨廠商"
              width="180">
          </el-table-column>
          <el-table-column
              prop="good.lineId"
              label="線材編號"
              width="180">
          </el-table-column>
          <el-table-column
              prop="odate"
              label="訂單日期"
              width="180">
          </el-table-column>
          <el-table-column
              prop="ocount"
              label="訂單數量"
              width="120">
          </el-table-column>
          <el-table-column
              prop="ounitPrice"
              label="訂單單價"
              width="120">
          </el-table-column>
          <el-table-column
              prop="ototalPrice"
              label="訂單總價"
              width="120">
          </el-table-column>
          <el-table-column
              prop="employee.name"
              label="生產員工"
              width="120">
          </el-table-column>
          <el-table-column
              prop="comDate"
              label="完成日期"
              width="180">
          </el-table-column>
        </el-table>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="handleClose_check">關 閉</el-button>
      </div>
    </el-dialog>
    <div class="manage-header">
      <!--       查詢功能-->
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
      <!--    線材生產表單-->
      <el-table
          :data="goodPageData.content"
          height="90%"
          stripe
          style="width: 100%">
        <el-table-column
            prop= "pdate"
            label="進貨日"
            width="120">
        </el-table-column>
        <el-table-column
            prop="company.companyName"
            label="進貨廠商"
            width="120">
        </el-table-column>
        <el-table-column
            prop="lineId"
            label="線材編號"
            width="120">
        </el-table-column>
        <el-table-column
            prop="lineType"
            label="端子"
            width="280">
        </el-table-column>
        <el-table-column
            prop="lineName"
            label="品項"
            width="280">
        </el-table-column>
        <el-table-column
            prop="totalCount"
            label="進貨數量"
            width="120">
        </el-table-column>
        <el-table-column
            prop="remainingCount"
            label="待分配數量"
            width="120">
        </el-table-column>
        <el-table-column
            prop="unitPrice"
            label="進貨單價"
            width="120">
        </el-table-column>
        <el-table-column
            prop="addr"
            label="操作"
            width="300">
          <template slot-scope="scope">
            <el-button type="success" size="mini" @click="handleShipping(scope.row)">發貨</el-button>
            <el-button type="info" size="mini" @click="handleCheck(scope.row)">查看</el-button>
          </template>
        </el-table-column>
      </el-table>
      <!--分頁功能-->
      <div class="pager">
        <el-pagination
            :hide-on-single-page="true"
            :current-page.sync="currentPage"
            :page-size="pageSize"
            :total=goodPageData.totalElements
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
  name:'LineShipping',
  data(){
    return{
      currentPage: 1,
      pageSize: 10,
      state:3, //線材狀態 1:已收貨，2:生產中，3：待發貨，4：已發貨
      dialogFormVisible: false,//分配/修改彈窗開/關
      dialogFormVisible_check: false,//查看彈窗開/關
      queryForm:{
        queryKeyword:'',
      },
      //分配彈窗初始資料
      form: {
        lineId: '',
        totalCount: '',
        remainingCount:'',
        unitPrice: '',
        orderCount: '',
        orderUnitPrice: '',
        eId:'',
        employee:{
          eid:''
        },
        company:{
          companyId:''
        },
        sDate:'',
        companyId:''
      },
      //查看彈窗初始資料
      form_check: {
        lineId: '',
        totalCount: '',
        remainingCount:'',
        unitPrice: '',
        orderCount: '',
        orderUnitPrice: '',
        eId:'',
        employee:{
          eid:''
        }
      },

      //驗證彈窗輸入資料
      rules: {
        sDate: [
          { required: true, message: '請選擇日期', trigger: 'blur' },
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
    ...mapState('data', ['goodData', 'companyData', 'employeeData', 'orderData', 'returnData', 'countData']),
    ...mapState('line', ['goodPageData']),
    ...mapState('order', ['orderPageData','orderQueryData']),

  },
  created() {
    this.fetchGoodPageData({'pageNum':1,'state':this.state});
    this.fetchEmployeeData();
    this.fetchOrderPageData({'pageNum':1,'completeVisible':this.completeVisible});
  },
  methods: {
    ...mapActions('data', ['fetchGoodData', 'fetchCompanyData', 'fetchEmployeeData', 'fetchOrderData', 'fetchReturnData']),
    ...mapActions('line', ['fetchGoodPageData','fetchUpdateGood']),
    ...mapActions('order', ['fetchUpdateOrder', 'fetchOrderPageData', 'fetchDeleteOrder','fetchOrderQueryData']),
    //切換分頁
    handlePage(pageNum){
      //將分頁頁碼保存到參數params中
      const params = {'pageNum': pageNum,'queryKeyWord': this.queryForm.queryKeyword,'state':this.state};
      //請求查詢分頁信息
      this.fetchGoodPageData(params);
    },
    //點擊分配時操作
    handleShipping(row){
      //顯示彈窗
      this.dialogFormVisible = true;
      //【重要】使用深拷貝row資料來回顯到編輯窗口，若不使用深拷貝，則編輯的資料會與表格資料連動，會有錯誤
      this.form = JSON.parse(JSON.stringify(row));
      // console.log(this.form)
    },
    //點擊查看時操作
    handleCheck(row){

      this.fetchOrderQueryData({'lineId':row.lineId})

      //將彈窗設定延時，先等資料查完再顯示表單，否則彈窗高度會只有一行
      setTimeout(() => {
        //顯示彈窗
        this.dialogFormVisible_check = true;
      }, 100);
    },
    //點擊發貨彈窗關閉時操作
    handleClose(){
      this.$refs.form.resetFields();
      // console.log('清空資料')
      this.dialogFormVisible = false;
    },
    //點擊查看彈窗關閉時操作
    handleClose_check(){
      this.dialogFormVisible_check = false;
    },
    //提交用戶表單
    submit(){
      this.$refs.form.validate(valid => {
        //驗證通過，執行if內的代碼
        if(valid){
          // console.log("驗證通過")
          // console.log("this.form",this.form)
          this.form.companyId = this.form.company.companyId;
          //修改訂單資料
          this.fetchUpdateGood(this.form)
              .then((response) => {
            this.$message({
              type: 'success',
              message: '已發貨!'
            });
            //關閉彈窗
            this.dialogFormVisible = false;
            //清空form表單的數據
            setTimeout(() => {
              //重新查詢數據庫資料
              this.fetchGoodPageData({'pageNum':this.currentPage,'state':this.state});
              //清除表單數據，更新數據
              this.$refs.form.resetFields();
              this.form.eId = '';
            }, 200);
          }).catch((error) => {
            this.$message({
              type: 'error',
              message: error
            });
          })
        }

      })
    },
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