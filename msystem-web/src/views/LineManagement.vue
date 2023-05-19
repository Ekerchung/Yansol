<template>
  <div class="manage">
    <div><span class="title">線材管理</span></div>
    <!-- 點擊修改按鈕的彈框表單 -->
    <el-dialog
        title="修改線材資料"
        :visible.sync="dialogFormVisible"
        width="400px"
        :before-close="handleClose">
      <el-form :model="form" :rules="rules" ref="form" label-width="100px" style="width: 350px">
        <el-form-item label="進貨日期" prop="pDate">
          <el-date-picker
              v-model="form.pDate"
              align="right"
              type="date"
              placeholder="選擇出貨日期"
              style="width: 250px"
              :picker-options="pickerOptions">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="進貨廠商" prop="companyName">
          <el-select v-model="form.company.companyId" style="width: 250px" placeholder="請選擇廠商">
            <el-option v-for="cmp in companyData" :label= "`${cmp.companyName}`"  :value=cmp.companyId :key="cmp.companyId"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="線材編號" prop="lineId">
          <el-input v-model="form.lineId" oninput="value=value.replace(/[^\d]/g,'')"></el-input>
        </el-form-item>
        <el-form-item label="端子" prop="lineType">
          <el-input v-model="form.lineType"></el-input>
        </el-form-item>
        <el-form-item label="品項" prop="lineName">
          <el-input v-model="form.lineName"></el-input>
        </el-form-item>
        <el-form-item label="進貨數量" prop="totalCount">
          <el-input v-model="form.totalCount" :disabled="form.state !== 1" oninput="value=value.replace(/[^\d]/g,'')"></el-input>
        </el-form-item>
        <el-form-item label="待分配數量" prop="totalCount">
          <el-input v-model="form.totalCount" :disabled="true" oninput="value=value.replace(/[^\d]/g,'')"></el-input>
        </el-form-item>
        <el-form-item label="進貨單價" prop="unitPrice">
          <el-input v-model="form.unitPrice" oninput="value=value.replace(/[^\d]/g,'')"></el-input>
        </el-form-item>
        <el-form-item label="出貨時間" prop="sDate">
          <el-date-picker
              v-model="form.sDate"
              align="right"
              type="date"
              placeholder="選擇出貨日期"
              style="width: 250px"
              :picker-options="pickerOptions">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="線材狀態" prop="state">
          <el-input v-model="form.state" :disabled="true">
            <template v-if="form.state">
              <template slot="prepend">{{ getStateText(form.state) }}</template>
            </template>
          </el-input>
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
            label="進貨日期"
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
            width="240">
        </el-table-column>
        <el-table-column
            prop="lineName"
            label="品項"
            width="240">
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
            prop="sDate"
            label="出貨時間"
            width="120">
        </el-table-column>
        <el-table-column
            prop="state"
            label="線材狀態"
            width="120"
            :formatter="formatState">
        </el-table-column>
        <el-table-column
            prop="addr"
            label="操作"
            width="200">
          <template slot-scope="scope">
            <el-button type="primary" size="mini" @click="handleEdit(scope.row)">編輯</el-button>
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
  name:'LineManagement',
  data(){
    return{
      currentPage: 1,
      pageSize: 10,
      state:1, //線材狀態 1:已收貨，2:生產中，3：待發貨，4：已發貨
      modelType:0, //0:分配生產，1:修改生產
      dialogFormVisible: false,//分配/修改彈窗開/關
      dialogFormVisible_check: false,//查看彈窗開/關
      disabled:'',//編輯按鈕是否禁用
      queryForm:{
        queryKeyword:'',
      },
      //分配彈窗初始資料
      form: {
        lineId: '',
        lineType:'',
        lineName:'',
        totalCount: '',
        remainingCount:'',
        unitPrice: '',
        orderCount: '',
        orderUnitPrice: '',
        eId:'',
        employee:{
          eid:''
        },
        sDate:'',
        state:'',
        pDate:'',
        company:{
          companyId:''
        },
        companyId:'',
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
        pDate: [
          { required: true, message: '請選擇進貨日期', trigger: 'change' }
        ],
        lineId: [
          { required: true, message: '請輸入線材編號', trigger: 'blur' }
        ],
        lineType: [
          { required: true, message: '請輸入端子', trigger: 'blur' }
        ],
        lineName: [
          { required: true, message: '請輸入品項', trigger: 'blur' }
        ],
        totalCount: [
          { required: true, message: '請輸入進貨數量', trigger: 'blur' }
        ],
        unitPrice: [
          { required: true, message: '請輸入進貨單價', trigger: 'blur' }
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
  },
  created() {
    this.fetchGoodPageData({'pageNum':1});
    this.fetchEmployeeData();
    this.fetchCompanyData();
  },
  methods: {
    ...mapActions('data', ['fetchGoodData', 'fetchCompanyData', 'fetchEmployeeData', 'fetchOrderData', 'fetchReturnData']),
    ...mapActions('line', ['fetchGoodPageData','fetchUpdateGood']),
    ...mapActions('order', ['fetchAddOrder']),
    //table中顯示線材狀態
    getStateText() {
      const state = this.form.state
      if (state === 1) {
        return '已收貨'
      } else if (state === 2) {
        return '生產中'
      } else if (state === 3) {
        return '待發貨'
      } else if (state === 4) {
        return '已發貨'
      } else {
        return ''
      }
    },
    //彈窗中顯示線材狀態
    formatState(data) {
      if (data.state === 1) {
        return '已收貨';
      } else if (data.state === 2) {
        return '生產中';
      } else if (data.state === 3) {
        return '待發貨';
      } else if (data.state === 4) {
        return '已發貨';
      } else {
        return '';
      }
    },
    //切換分頁
    handlePage(pageNum){
      //將分頁頁碼保存到參數params中
      const params = {'pageNum': pageNum,'queryKeyWord': this.queryForm.queryKeyword};
      //請求查詢分頁信息
      this.fetchGoodPageData(params);
    },
    //點擊編輯時操作
    handleEdit(row){
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
          console.log(this.form)
          this.form.companyId = this.form.company.companyId //將this.form.company.companyId綁定到this.form.companyId，以便後台獲取數據
          //新增訂單資料
          this.fetchUpdateGood(this.form).then(() => {
            this.$message({
              type: 'success',
              message: '修改完成!'
            });
            //關閉彈窗
            this.dialogFormVisible = false;
            //清空form表單的數據
            setTimeout(() => {
              //重新查詢數據庫資料，更新數據
              this.fetchGoodPageData({'pageNum': this.currentPage});
              //清除表單數據
              this.$refs.form.resetFields();
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