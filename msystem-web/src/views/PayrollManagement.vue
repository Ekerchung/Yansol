<template>
  <div class="manage">
<!--    <div class="manage-header">-->
<!--      &lt;!&ndash;新增按鈕&ndash;&gt;-->
<!--      <el-button type="primary" @click="dialogFormVisible = true">+ 新增</el-button>-->
<!--      <el-form :inline="true" :model="queryForm">-->
<!--        <el-form-item>-->
<!--          <el-input v-model="queryForm.queryKeyword" placeholder="輸入帳號"></el-input>-->
<!--        </el-form-item>-->
<!--        <el-form-item>-->
<!--          <el-button type="primary" @click="handlePage(1)">查詢</el-button>-->
<!--        </el-form-item>-->
<!--      </el-form>-->
<!--    </div>-->
    <div class="common-tabel">
      <!--    人員薪資表格-->
      <el-table
          :data="salaryList"
          height="90%"
          stripe
          style="width: 100%">
        <el-table-column
            prop="name"
            label="員工姓名"
            width="180">
        </el-table-column>
        <el-table-column
            prop= "lastSalary"
            :label="`${lastMonth}月應付薪資`"
            width="180">
        </el-table-column>
        <el-table-column
            prop="thisSalary"
            :label="`${currentMonth}月應付薪資`"
            width="180">
        </el-table-column>
      </el-table>
      <!--分頁功能-->
<!--      <div class="pager">-->
<!--        <el-pagination-->
<!--            :hide-on-single-page="true"-->
<!--            :current-page.sync="currentPage"-->
<!--            :page-size="pageSize"-->
<!--            :total=accountPageData.totalElements-->
<!--            background-->
<!--            layout="prev, pager, next"-->
<!--            @current-change="handlePage">>-->
<!--        </el-pagination>-->
<!--      </div>-->
    </div>
  </div>
</template>


<script>
import {mapActions, mapGetters, mapMutations, mapState} from 'vuex';
export default {
  name:'PayrollManagement',
  data(){
    return{
      // currentPage: 1,
      // pageSize: 10,
      //彈窗開/關
      // dialogFormVisible: false,
      //employee表彈窗初始資料
      // form: {
      //   eName: '',
      //   salary: '',
      //   paid: '',
      // },

      // modelType:0, //0：新增窗口，1：修改窗口
      //查詢功能信息
      // queryForm:{
      //   queryKeyword:'',
      // },
      // thisMonthSallary:[],
      // lastMonthSallary:[],
    }
  },

  computed: {
    ...mapState('data',['employeeData','orderData']),
    ...mapGetters('data',[
      'salaryList',
      'currentMonth',
      'lastMonth'
    ]),
  },
  methods:{
    ...mapActions('data',['fetchEmployeeData','fetchOrderData']),
    // //點擊彈窗關閉時操作
    // handleClose(){
    //   this.$refs.form.resetFields();
    //   this.form.employee.eid = ''
    //   // console.log('清空資料')
    //   this.dialogFormVisible = false;
    // },
    // //點擊編輯時操作
    // handleEdit(row){
    //   //設定為編輯窗口
    //   this.modelType = 1;
    //   //顯示彈窗
    //   this.dialogFormVisible = true;
    //   //【重要】使用深拷貝row資料來回顯到編輯窗口，若不使用深拷貝，則編輯的資料會與表格資料連動，會有錯誤
    //   this.form = JSON.parse(JSON.stringify(row));
    //   // console.log(this.form)
    // },
    // //點擊刪除時操作
    // handleDelete(row) {
    //   // console.log(row)
    //   this.$confirm(`是否確定要刪除【${row.username}】的資料?`, '提示', {
    //     confirmButtonText: '確定',
    //     cancelButtonText: '取消',
    //     type: 'warning'
    //   }).then(() => {
    //     // console.log(row.aid);
    //     this.fetchDeleteAccount(row.aid).then(() => {
    //       this.$message({
    //         type: 'success',
    //         message: '刪除成功!'
    //       });
    //       //重新查詢數據庫資料，更新數據
    //       setTimeout(() => {
    //         //重新查詢數據庫資料，更新數據
    //         this.fetchGetAccountPageData({'pageNum':this.currentPage});
    //       }, 200);
    //     })
    //
    //   }).catch(() => {
    //     this.$message({
    //       type: 'info',
    //       message: '已取消刪除'
    //     });
    //   });
    // },
  //   //提交用戶表單
  //   submit(){
  //     this.$refs.form.validate(valid => {
  //       //驗證通過，執行if內的代碼
  //       if(valid){
  //         // console.log("新增帳號")
  //         this.form.eId = this.form.employee.eid
  //         //新增或修改員工資料
  //         this.fetchUpdateAccount(this.form);
  //       }
  //       //關閉彈窗
  //       this.dialogFormVisible = false;
  //       //清空form表單的數據
  //       setTimeout(() => {
  //         //重新查詢數據庫資料，更新數據
  //         this.fetchGetAccountPageData({'pageNum':this.currentPage});
  //         // this.$refs.form.resetFields();
  //         this.$refs.form.resetFields();
  //         this.form.employee.eid = '';
  //       }, 200);
  //
  //     })
  //   },
  //   //切換分頁
  //   handlePage(pageNum){
  //     //將分頁頁碼保存到參數params中
  //     const params = {'pageNum': pageNum,'queryKeyWord': this.queryForm.queryKeyword};
  //     //請求查詢分頁信息
  //     this.fetchGetAccountPageData(params);
  //   },
  // },
  //點擊新增時操作
  },
  created() {
    this.fetchEmployeeData();
    this.fetchOrderData();
  },

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