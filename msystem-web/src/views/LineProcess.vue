<template>
  <div class="manage">
    <div><span class="title">線材生產</span></div>
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
            width="180">
        </el-table-column>
        <el-table-column
            prop="unitPrice"
            label="進貨單價"
            width="180">
        </el-table-column>
        <el-table-column
            prop="addr"
            label="操作"
            width="300">
          <template slot-scope="scope">
            <el-button type="primary" size="mini" @click="handleEdit(scope.row)">分配</el-button>
            <el-button type="info" size="mini" @click="handleDelete(scope.row)">查看</el-button>
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
    return{
      currentPage: 1,
      pageSize: 10,
      queryForm:{
        queryKeyword:'',
      },
    }
  },
  computed: {
    ...mapState('data', ['goodData', 'companyData', 'employeeData', 'orderData', 'returnData', 'countData']),
    ...mapState('line', ['goodPageData']),
  },
  created() {
    this.fetchGoodPageData({'pageNum':1});
  },
  methods: {
    ...mapActions('data', ['fetchGoodData', 'fetchCompanyData', 'fetchEmployeeData', 'fetchOrderData', 'fetchReturnData']),
    ...mapActions('line', ['fetchGoodPageData']),
    //切換分頁
    handlePage(pageNum){
      //將分頁頁碼保存到參數params中
      const params = {'pageNum': pageNum,'queryKeyWord': this.queryForm.queryKeyword};
      //請求查詢分頁信息
      this.fetchGoodPageData(params);
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
  margin-bottom: auto;
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