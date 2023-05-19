<template>
  <div class="manage">
    <div><span class="title">線材生產</span></div>
    <!-- 點擊分配/修改按鈕的彈框表單 -->
    <el-dialog
        :title="`${this.modelType === 0 ? '分配生產' : '修改生產'}`"
        :visible.sync="dialogFormVisible"
        width="400px"
        :before-close="handleClose">
      <el-form :model="form" :rules="rules" ref="form" label-width="100px" style="width: 350px">
        <el-form-item label="線材編號" prop="lineId">
          <el-input v-model="form.lineId" :disabled="true"></el-input>
        </el-form-item>
        <el-form-item label="進貨數量" prop="totalCount">
          <el-input v-model="form.totalCount" :disabled="true"></el-input>
        </el-form-item>
        <el-form-item label="待分配數量" prop="totalCount">
          <el-input v-model="form.remainingCount" :disabled="true"></el-input>
        </el-form-item>
        <el-form-item label="進貨單價" prop="unitPrice">
          <el-input v-model="form.unitPrice" :disabled="true"></el-input>
        </el-form-item>
        <el-form-item label="生產員工" prop="eId">
          <el-select v-model="form.eId" style="width: 250px" placeholder="請選擇員工">
            <el-option v-for="emp in employeeData" :label= "`${emp.eid}_${emp.name}`"  :value=emp.eid :key="emp.eid"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="分配數量" prop="orderCount">
          <el-input v-model="form.orderCount" :placeholder="'最大值為' + form.remainingCount"></el-input>
        </el-form-item>
        <el-form-item label="分配單價" prop="orderUnitPrice">
          <el-input v-model="form.orderUnitPrice" :placeholder="'最大值為' + form.unitPrice"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="handleClose">取 消</el-button>
        <el-button type="primary" @click="submit">確 定</el-button>
      </div>
    </el-dialog>
    <!-- 點擊查看按鈕的彈框表單 -->
    <el-dialog
        :visible.sync="dialogFormVisible_check"
        width="400px"
        :before-close="handleClose">
      <el-form :model="form_check" ref="form" label-width="100px" style="width: 350px">
        <el-form-item label="線材編號" prop="lineId">
          <el-input v-model="form.lineId" :disabled="true"></el-input>
        </el-form-item>
        <el-form-item label="生產數量" prop="oCount">
          <el-input v-model="form.oCount" :disabled="true"></el-input>
        </el-form-item>
        <el-form-item label="生產單價" prop="oPrice">
          <el-input v-model="form.oPrice" :disabled="true"></el-input>
        </el-form-item>
        <el-form-item label="生產總價" prop="oTotalPrice">
          <el-input v-model="form.oTotalPrice" :disabled="true"></el-input>
        </el-form-item>
        <el-form-item label="生產員工" prop="eId">
          <el-input v-model="form.oTotalPrice" :disabled="true"></el-input>
        </el-form-item>
        <el-form-item label="生產員工" prop="eId">
          <el-input v-model="form.eId" :disabled="true"></el-input>
<!--          <el-select v-model="form.eId" style="width: 250px" placeholder="請選擇員工">-->
<!--            <el-option v-for="emp in employeeData" :label= "`${emp.eid}_${emp.name}`"  :value=emp.eid :key="emp.eid"></el-option>-->
<!--          </el-select>-->
        </el-form-item>
        <el-form-item label="完成時間" prop="comDate">
          <el-input v-model="form.comDate" :disabled="true"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="handleClose">取 消</el-button>
        <el-button type="primary" @click="submit">確 定</el-button>
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
            <el-button type="primary" size="mini" @click="handleAssign(scope.row)">分配</el-button>
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
  name:'LineProcess',
  data(){
    //驗證分配數量是否小於待分配數量
    let checkOrderCount = (rule, value, callback) => {
      if (value === '') {
          callback(new Error('請輸入數量'));
        } else if (!isFinite(this.form.orderCount)) {
        console.log(this.form.orderCount)
          callback(new Error('數量必須為數字'));
        } else if (this.form.orderCount <= 0) {
          callback(new Error('數量必須大於零'));
        } else if (this.form.orderCount > this.form.totalCount) {
          callback(new Error('數量必須小於進貨數量'));
        } else {
          callback();
        }
      }
    //驗證分配數量是否小於待分配數量
    let checkUnitPrice = (rule, value, callback) => {
      if (value === '') {
          callback(new Error('請輸入單價'));
        } else if (!isFinite(this.form.orderUnitPrice)) {
          callback(new Error('單價必須為數字'));
        } else if (this.form.orderUnitPrice <= 0) {
          callback(new Error('單價必須大於零'));
        } else if (this.form.orderUnitPrice > this.form.unitPrice) {
          callback(new Error('單價必須小於進貨單價'));
        } else {
          callback();
        }
      }
    return{
      currentPage: 1,
      pageSize: 10,
      state:1, //線材狀態 1:已收貨，2:生產中，3：待發貨，4：已發貨
      modelType:0, //0:分配生產，1:修改生產
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
        }
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
        orderCount: [
          { required: true, validator: checkOrderCount, trigger: 'blur' },
        ],
        orderUnitPrice: [
          { required: true, validator: checkUnitPrice, trigger: 'blur' },
        ],
        eId: [
          { required: true, message: '請選擇員工', trigger: 'blur' },
        ],
      },

    }
  },
  computed: {
    ...mapState('data', ['goodData', 'companyData', 'employeeData', 'orderData', 'returnData', 'countData']),
    ...mapState('line', ['goodPageData']),
  },
  created() {
    this.fetchGoodPageData({'pageNum':1,'state':this.state});
    this.fetchEmployeeData();
  },
  methods: {
    ...mapActions('data', ['fetchGoodData', 'fetchCompanyData', 'fetchEmployeeData', 'fetchOrderData', 'fetchReturnData']),
    ...mapActions('line', ['fetchGoodPageData']),
    ...mapActions('order', ['fetchAddOrder']),

    //切換分頁
    handlePage(pageNum){
      //將分頁頁碼保存到參數params中
      const params = {'pageNum': pageNum,'queryKeyWord': this.queryForm.queryKeyword,'state':this.state};
      //請求查詢分頁信息
      this.fetchGoodPageData(params);
    },
    //點擊分配時操作
    handleAssign(row){
      //設定為編輯窗口
      this.modelType = 0;
      //顯示彈窗
      this.dialogFormVisible = true;
      //【重要】使用深拷貝row資料來回顯到編輯窗口，若不使用深拷貝，則編輯的資料會與表格資料連動，會有錯誤
      this.form = JSON.parse(JSON.stringify(row));
      // console.log(this.form)
    },
    //點擊查看時操作
    handleCheck(row){
      //設定為編輯窗口
      this.modelType = 0;
      //顯示彈窗
      this.dialogFormVisible = true;
      //【重要】使用深拷貝row資料來回顯到編輯窗口，若不使用深拷貝，則編輯的資料會與表格資料連動，會有錯誤
      this.form = JSON.parse(JSON.stringify(row));
      // console.log(this.form)
    },
    //點擊彈窗關閉時操作
    handleClose(){
      this.$refs.form.resetFields();
      // this.form.employee.eid = ''
      // console.log('清空資料')
      this.dialogFormVisible = false;
    },
    //提交用戶表單
    submit(){
      this.$refs.form.validate(valid => {
        //驗證通過，執行if內的代碼
        if(valid){
          console.log("驗證通過")
          // this.form.eId = this.form.employee.eid
          //新增訂單資料
          this.fetchAddOrder(this.form).then(() => {
            this.$message({
              type: 'success',
              message: '分配完成!'
            });
            //關閉彈窗
            this.dialogFormVisible = false;
            //清空form表單的數據
            setTimeout(() => {
              //重新查詢數據庫資料，更新數據
              this.fetchGoodPageData({'pageNum': this.currentPage, 'state': this.state});
              //清除表單數據
              this.$refs.form.resetFields();
              this.form.eId = '';
            }, 200);
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