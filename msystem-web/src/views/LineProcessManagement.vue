<template>
  <div class="manage">
    <div><span class="title">生產管理</span></div>
    <!-- 點擊編輯按鈕的彈框表單 -->
    <el-dialog
        :visible.sync="dialogFormVisible"
        width="400px"
        :before-close="handleClose">
      <el-form :model="form" :rules="rules" ref="form" label-width="100px" style="width: 350px">
        <el-form-item label="進貨廠商" prop="companyName">
          <el-input v-model="form.companyName" :disabled="true"></el-input>
        </el-form-item>
        <el-form-item label="線材編號" prop="lineId">
          <el-input v-model="form.lineId" :disabled="true"></el-input>
        </el-form-item>
        <el-form-item label="訂單日期" prop="odate">
          <el-input v-model="form.odate" :disabled="true"></el-input>
        </el-form-item>
        <el-form-item label="分配數量" prop="ocount">
          <el-input v-model="form.ocount" :placeholder="'最大值為' + form.remainingCount"></el-input>
        </el-form-item>
        <el-form-item label="分配單價" prop="ounitPrice">
          <el-input v-model="form.ounitPrice" :placeholder="'最大值為' + form.unitPrice"></el-input>
        </el-form-item>
        <el-form-item label="生產員工" prop="eid">
          <el-select v-model="form.eid" style="width: 250px" placeholder="請選擇員工">
            <el-option v-for="emp in employeeData" :label= "`${emp.eid}_${emp.name}`"  :value=emp.eid :key="emp.eid"></el-option>
          </el-select>
        </el-form-item>
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
      <!-- 隱藏完成訂單按鈕 -->
      <el-switch
          v-model="completeVisible"
          active-text="隱藏已完成訂單">
      </el-switch>
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
      <!--    生產訂單表單-->
      <el-table
          :data="this.orderPageData.content"
          height="90%"
          stripe
          empty-text="暫無數據"
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
        <el-table-column
            prop="addr"
            label="操作"
            width="300">
          <template slot-scope="scope">
            <el-button type="primary" size="mini" @click="handleEdit(scope.row)">編輯</el-button>
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
            :total=orderPageData.totalElements
            background
            layout="prev, pager, next"
            @current-change="handlePage">>
        </el-pagination>
      </div>
    </div>
  </div>
</template>

<script>
import {mapActions, mapGetters, mapState} from "vuex";

export default {
  name:'LineProcessManagement',
  data(){
    //驗證分配數量是否小於待分配數量
    let checkOrderCount = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('請輸入數量'));
      } else if (!isFinite(this.form.ocount)) {
        console.log(this.form.ocount)
        callback(new Error('數量必須為數字'));
      } else if (this.form.ocount <= 0) {
        callback(new Error('數量必須大於零'));
      } else if (this.form.ocount > this.form.orderCount + this.form.good.remainingCount) {
        callback(new Error('分配數量已經超過可分配數量' + '【' + (this.form.orderCount + this.form.good.remainingCount) + '】'));
      } else {
        callback();
      }

    }
    //驗證分配數量是否小於待分配數量
    let checkOUnitPrice = (rule, value, callback) => {
      console.log('this.form.good.unitPrice',this.form.good.unitPrice)
      if (value === '') {
        callback(new Error('請輸入單價'));
      } else if (!isFinite(this.form.ounitPrice)) {
        callback(new Error('單價必須為數字'));
      } else if (this.form.ounitPrice <= 0) {
        callback(new Error('單價必須大於零'));
      } else if (this.form.ounitPrice > this.form.good.unitPrice) {
        callback(new Error('單價必須小於進貨單價' + '【' + this.form.good.unitPrice + '】'));
      } else {
        callback();
      }
    }
    return{
      currentPage: 1,
      pageSize: 10,
      state:1, //1:已收貨，2:生產中，3：待發貨，4：已發貨
      dialogFormVisible: false,//修改彈窗開/關
      dialogFormVisible_complete: false,//完成彈窗開/關
      completeVisible: true,//開/關隱藏已完成訂單
      queryForm:{
        queryKeyword:'',
      },
      //彈窗初始資料
      form: {
        good:{company:{companyName:''},lineId: ''},
        employee:{eid:''},
        oid: '',
        companyName: '',
        lineId:'',
        odate: '',
        ocount: '',
        orderCount: '',
        ounitPrice: '',
        eid:'',
        comDate:'',
      },
      orderPageDataFilter:[],
      //驗證修改彈窗輸入資料
      rules: {
        ocount: [
          { required: true, validator: checkOrderCount, trigger: 'blur' },
        ],
        ounitPrice: [
          { required: true, validator: checkOUnitPrice, trigger: 'blur' },
        ],
      },
      //驗證完成彈窗輸入資料
      rules_complete: {
        ocount: [
          { required: true, validator: checkOrderCount, trigger: 'blur' },
        ],
        ounitPrice: [
          { required: true, validator: checkOUnitPrice, trigger: 'blur' },
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
    ...mapState('data', ['goodData','employeeData']),
    ...mapState('order', ['orderPageData']),
  },
  created() {
    this.fetchEmployeeData();
    this.fetchOrderPageData({'pageNum':1,'completeVisible':this.completeVisible});

  },
  methods: {
    ...mapActions('data', ['fetchEmployeeData']),
    ...mapActions('order', ['fetchUpdateOrder', 'fetchOrderPageData', 'fetchDeleteOrder']),

    //切換隱藏已完成訂單
    handleHidden() {
      //將分頁頁碼保存到參數params中
      this.currentPage = 1;
      const params = {'pageNum': 1, 'queryKeyWord': this.queryForm.queryKeyword,'completeVisible':this.completeVisible};
      //請求查詢分頁信息
      this.fetchOrderPageData(params);
      //使用深拷貝避免vuex的資料被修改
    },
    //切換分頁
    handlePage(pageNum) {
      //將分頁頁碼保存到參數params中
      const params = {'pageNum': pageNum, 'queryKeyWord': this.queryForm.queryKeyword,'completeVisible':this.completeVisible};
      //請求查詢分頁信息
      this.fetchOrderPageData(params);
    },
    //點擊彈窗關閉時操作
    handleClose() {
      //清空表格資料
      this.$refs.form.resetFields();
      this.form.employee.eid = ''
      //關閉彈窗
      this.dialogFormVisible = false;
    },
    //點擊編輯時操作
    handleEdit(row) {
      //顯示彈窗
      this.dialogFormVisible = true;

      //【重要】使用深拷貝row資料來回顯到編輯窗口，若不使用深拷貝，則編輯的資料會與表格資料連動，會有錯誤
      this.form = JSON.parse(JSON.stringify(row));
      this.form.companyName = this.form.good.company.companyName;
      this.form.eid = this.form.employee.eid;
      this.form.lineId = this.form.good.lineId;
      this.form.orderCount = JSON.parse(JSON.stringify(this.form.ocount));
    },
    //點擊刪除時操作
    handleDelete(row) {
      this.$confirm(`是否確定要刪除【訂單編號：${row.oid}】的資料?`, '提示', {
        confirmButtonText: '確定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        console.log(row.oid);
        this.fetchDeleteOrder(row.oid).then(() => {
          this.$message({
            type: 'success',
            message: '刪除成功!'
          });
          //重新查詢數據庫資料，更新數據
          setTimeout(() => {
            //重新查詢數據庫資料，更新數據
            this.fetchOrderPageData({'pageNum': this.currentPage, 'queryKeyWord': this.queryForm.queryKeyword,'completeVisible':this.completeVisible});
          }, 200);
        })

      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消刪除'
        });
      });
    },
    //提交編輯表單
    submit() {
      this.$refs.form.validate(valid => {
        //驗證通過，執行if內的代碼
        if (valid) {
          //新增或修改訂單資料
          this.fetchUpdateOrder(this.form).then(() => {
            this.$message({
              type: 'success',
              message: '修改成功!'
            });
            //關閉彈窗
            this.dialogFormVisible = false;
            //清空form表單的數據
            setTimeout(() => {
              //重新查詢數據庫資料
              this.fetchOrderPageData({'pageNum': this.currentPage, 'queryKeyWord': this.queryForm.queryKeyword,'completeVisible':this.completeVisible});
              //清除表單數據，更新數據
              this.$refs.form.resetFields();
              this.form.eid = '';
            }, 200);
          })
        }
      })
    },
  },
  watch:{
    completeVisible:{
      handler(newValue, oldValue){
        //監控completeVisible的值，當值被修改則調用this.handleHidden()
        this.handleHidden();
      }

    }
  }
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
  justify-content: space-between;
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