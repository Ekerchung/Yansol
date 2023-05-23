<template>
  <div class="manage">
    <div><span class="title">{{formattedDate}}薪資試算</span></div>
    <div class="manage-header">
      <!--       查詢功能-->
      <el-form :inline="true">
        <el-form-item>
          <el-date-picker
              v-model="queryDate"
              type="month"
              value-format='yyyy-MM-dd HH:mm:ss'
              placeholder="選擇查詢月份">
          </el-date-picker>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleMonth()">查詢</el-button>
        </el-form-item>
      </el-form>
      <br><br><br>
    </div>
    <div class="common-tabel">
      <!--    當月完成訂單表單-->
      <el-table
          :data="orderQueryDataByUser"
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
            prop= "comDate"
            label="訂單完成日期"
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
            width="120">
        </el-table-column>
        <el-table-column
            prop="ounitPrice"
            label="單價"
            width="120">
        </el-table-column>
        <el-table-column
            prop="ototalPrice"
            label="總價"
            width="180">
        </el-table-column>
      </el-table>
      <br><span class="sum" v-if="this.sumOrderCount !== 0">共完成 {{this.sumOrderCount}} 筆訂單，總金額為$ {{this.sumSalary}} 元</span>

    </div>
  </div>
</template>

<script>
import {mapActions, mapState} from "vuex";
import moment from 'moment'

export default {
  name:'PrePayroll',
  data(){
    return{
      queryDate:'',
      useDate:'',
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
    ...mapState('order', ['orderQueryDataByUser']),
    //格式化時間為YYYY-MM
    formattedDate() {
      let date = new Date(this.useDate);
      let year = date.getFullYear();
      let month = (date.getMonth() + 1).toString().padStart(2, "0");
      return `${year}-${month}`;
    },
    //計算完成訂單數量
    sumOrderCount(){
      return this.orderQueryDataByUser.length;
    },
    //計算當月完成訂單總金額
    sumSalary(){
      let sumSalary = 0;
      //計算查詢出的訂單金額加總
      for (let i = 0; i < this.orderQueryDataByUser.length; i++) {
        sumSalary += this.orderQueryDataByUser[i].ototalPrice;
      }
      return sumSalary;
    },
  },
  methods: {
    ...mapActions('order', ['fetchGetOrderDataByEidByDate']),
    //查詢薪資月份
    handleMonth(){
      //將參數保存到參數params中
      let formatDate = moment(this.queryDate).format('YYYY-MM-DD HH:mm:ss');
      const params = {'eid': this.employee.eid,'queryDate': formatDate};
      //請求查詢分頁信息
      this.fetchGetOrderDataByEidByDate(params);
      //將查詢時間保存為當前使用時間
      this.useDate = this.queryDate;
    },
  },
  mounted() {
    //獲取localStorage保存的此帳號對應的員工信息
    this.employee = JSON.parse(localStorage.getItem("employee"));
    //查詢當前時間
    let formatDate = moment(new Date()).format('YYYY-MM-DD HH:mm:ss');
    this.useDate = formatDate;
    //保存查詢參數到params
    const params = {'eid': this.employee.eid,'queryDate': formatDate};
    //查詢當月完成訂單
    this.fetchGetOrderDataByEidByDate(params);

  },
}
</script>

<style scoped>
.title{
  font-size: 24px;
  font-weight: bold;
  border-bottom: 1px solid black;
}
.sum{
  float: right;
  margin-right: 200px;
  font-size: 24px;
  font-weight: bold;
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