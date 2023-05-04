<template>
  <el-row>
    <el-col :span="9" style="padding-right: 10px">
      <el-card class="box-card" style="height: 255px">
        <div class="user">
          <img src="../assets/images/male.png" alt="">
          <div class="userinfo">
            <p class="name">Admin</p>
            <p class="access">超级管理员</p>
          </div>
        </div>
        <div class="login-info">
          <p>上次登錄時間：<span>2021-7-19</span></p>
        </div>
      </el-card>
      <el-card style="margin-top: 20px;height: 550px;">
        <el-table
            :data="goodData"
            height="510"
            stripe
            size="small"
            style="width: 100%">
          <el-table-column
              prop="pdate"
              label="進貨日期"
              width="85">
          </el-table-column>
          <el-table-column
              prop="company.companyName"
              label="廠商"
              width="60">
          </el-table-column>
          <el-table-column
              prop="lineName"
              label="品名">
          </el-table-column>
          <el-table-column
              prop="lineType"
              label="端子"
              width="170">
          </el-table-column>
          <el-table-column
              prop="totalCount"
              label="數量"
              width="50">
          </el-table-column>
        </el-table>
      </el-card>
     </el-col>
    <el-col :span="15" style="padding-left: 10px">
      <div class="num">
        <el-card v-for="item in countData" :key="item.name" :body-style="{ display: 'flex', padding: 0 }">
          <i class="icon" :class="`el-icon-${item.icon}`" :style="{ background: item.color }"></i>
          <div class="detail">
            <p class="price" v-if="item.icon === 'coin'">{{ item.value }}  元</p>
            <p class="price" v-if="item.icon !== 'coin'">{{ item.value }}  件</p>
            <p class="desc">{{ item.name }}</p>
          </div>
        </el-card>
      </div>
      <el-card style="height: 300px">
        <!-- 每月營收折線圖 -->
        <div ref="echarts1" class="echarts1" style="height: 320px"></div>
      </el-card>
      <div class="graph">
        <el-card style="height: 340px">
          <!-- 廠商收入柱狀圖 -->
          <div ref="echarts2" class="echarts2" style="height: 340px"></div>
        </el-card>
        <el-card style="height: 340px ; width: 49%">
          <!-- 員工月收入餅圖 -->
          <div ref="echarts3" class="echarts3" style="height: 340px"></div>
        </el-card>
      </div>
    </el-col>
  </el-row>
</template>

<script>
import * as echarts from 'echarts';
import { mapGetters,mapActions,mapState,mapMutations } from 'vuex'
export default {
  name:'Home',
  data() {
    return {
    }
  },
  computed: {
    ...mapState('data',['goodData','companyData','employeeData','orderData','returnData','countData']),
    ...mapGetters('data',[
      'lastYear',
      'currentYear',
      'monthlyRevenue',
      'companySalesList',
      'employeeSalesList',
      'monthlyPurchaseList',
      'dailyPurchaseList',
      'monthlyShipList',
      'dailyShipList',
      'monthlyReturnList',
      'dailyReturnList',
      'monthlyAccountsReceivable',
      'dailyAccountsReceivable',
    ]),
  },
  created() {
    this.fetchGoodData();
    this.fetchCompanyData();
    this.fetchEmployeeData();
    this.fetchOrderData();
    this.fetchReturnData();
  },
  methods:{
    ...mapActions('data',['fetchGoodData','fetchCompanyData','fetchEmployeeData','fetchOrderData','fetchReturnData']),
    renderChart(){
      // 基於準備好的dom，初始化echarts實例
      var myChart1 = echarts.init(this.$refs.echarts1);
      // 配製圖表1
      myChart1.setOption({
        title: {
          text: '每月營收'
        },
        tooltip: {},
        legend: {
          data: [this.lastYear.toString(),this.currentYear.toString()]
        },
        xAxis: {
          data: ['1月', '2月', '3月', '4月', '5月', '6月', '7月', '8月', '9月', '10月', '11月', '12月']
        },
        yAxis: {
          type: 'value',
          name: '營收/(仟)'
        },
        series: [
          {
            name: this.lastYear,
            type: 'line',
            data: [
              this.monthlyRevenue[`${this.lastYear}-01`],
              this.monthlyRevenue[`${this.lastYear}-02`],
              this.monthlyRevenue[`${this.lastYear}-03`],
              this.monthlyRevenue[`${this.lastYear}-04`],
              this.monthlyRevenue[`${this.lastYear}-05`],
              this.monthlyRevenue[`${this.lastYear}-06`],
              this.monthlyRevenue[`${this.lastYear}-07`],
              this.monthlyRevenue[`${this.lastYear}-08`],
              this.monthlyRevenue[`${this.lastYear}-09`],
              this.monthlyRevenue[`${this.lastYear}-010`],
              this.monthlyRevenue[`${this.lastYear}-011`],
              this.monthlyRevenue[`${this.lastYear}-012`]]
          },
          {
            name: this.currentYear,
            type: 'line',
            data: [
              this.monthlyRevenue[`${this.currentYear}-01`],
              this.monthlyRevenue[`${this.currentYear}-02`],
              this.monthlyRevenue[`${this.currentYear}-03`],
              this.monthlyRevenue[`${this.currentYear}-04`],
              this.monthlyRevenue[`${this.currentYear}-05`],
              this.monthlyRevenue[`${this.currentYear}-06`],
              this.monthlyRevenue[`${this.currentYear}-07`],
              this.monthlyRevenue[`${this.currentYear}-08`],
              this.monthlyRevenue[`${this.currentYear}-09`],
              this.monthlyRevenue[`${this.currentYear}-010`],
              this.monthlyRevenue[`${this.currentYear}-011`],
              this.monthlyRevenue[`${this.currentYear}-012`]]
          }
        ]
      });
      // 基於準備好的dom，初始化echarts實例
      var myChart2 = echarts.init(this.$refs.echarts2);
      // 配製圖表2
      myChart2.setOption({
        title: {
          text: '廠商收入'
        },
        tooltip: {},
        legend: {
          data: [this.lastYear.toString(),this.currentYear.toString()]
        },
        xAxis: {
          data: this.companySalesList.map(company => company.companyName)
        },
        yAxis: {
          type: 'value',
          name: '營收/(仟)'
        },
        series: [
          {
            name: this.lastYear,
            type: 'bar',
            barGap: '20%',
            barCategoryGap: '40%',
            data: this.companySalesList.map(company => company.total_sales_last_year / 1000)
          },
          {
            name: this.currentYear,
            type: 'bar',
            data: this.companySalesList.map(company => company.total_sales_this_year / 1000)
          }
        ]
      });
      // 基於準備好的dom，初始化echarts實例
      var myChart3 = echarts.init(this.$refs.echarts3);
      // 配製圖表3
      myChart3.setOption({
        title: {
          text: '員工收入'
        },
        tooltip: {
          trigger: 'item',
          formatter: '{b}: {c} 元 ({d}%)'
        },
        series: [
          {
            type: 'pie',
            data: this.employeeSalesList,

            label: {
              show: true,
              formatter: '{b}: {c} 元'
            },
          }
        ]

      });

      //綁定countDate數據
      this.countData[0].value = this.dailyPurchaseList.length;
      this.countData[1].value = this.dailyShipList.length;
      this.countData[2].value = this.dailyReturnList.length;
      this.countData[3].value = this.dailyAccountsReceivable;
      this.countData[4].value = this.monthlyPurchaseList.length;
      this.countData[5].value = this.monthlyShipList.length;
      this.countData[6].value = this.monthlyReturnList.length;
      this.countData[7].value = this.monthlyAccountsReceivable;
    },
  },
  mounted() {
    //使用定時器確保vuex異步取得數據後，再進行渲染
    setTimeout(() => {
      this.renderChart();
    }, 1000);
  },
}
</script>

<style scoped>
.user {
  padding-bottom: 20px;
  margin-bottom: 20px;
  border-bottom: 1px solid #ccc;
  display: flex;
  align-items: center;
  justify-content: center;
}
.user img {
  margin-right: 40px;
  width: 150px;
  height: 150px;
  border-radius: 50%;
}
.user .userinfo .name {
  font-size: 32px;
  margin-bottom: 10px;
}
.user .userinfo .access {
  color: #999999;
}
.tablename {
  font-size: 18px;
  margin-bottom: 10px;
  margin-top: 0px;
  text-align: left;
  font-weight: bold;
  margin-left: 10px;
  /*color: #999999;*/
}
.login-info p {
  line-height: 28px;
  font-size: 14px;
  color: #999999;
}
.login-info p span {
  color: #666666;
  margin-left: 60px;
}
.num {
  display: flex;
  flex-wrap: wrap;
  justify-content: space-between;
}
.num .icon {
  width: 60px;
  height: 60px;
  font-size: 30px;
  text-align: center;
  line-height: 60px;
  color: #fff;
}
.num .detail {
  margin-left: 15px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  height: 60px;
}
.num .detail .price {
  font-size: 24px;
  margin-bottom: 0px;
  margin-top: 20px;
  line-height: 24px;
  height: 24px;
}
.num .detail .desc {
  font-size: 14px;
  color: #999;
  text-align: center;
  margin-top: 0;
}
.num .el-card {
  width: 24%;
  margin-bottom: 20px;
}
.graph {
  margin-top: 20px;
  display: flex;
  justify-content: space-between;
  align-items: center;
}
.graph .el-card {
  width: 48%;
}
.echarts2 , .echarts3 {
  display: flex;
  align-items: center;
  justify-content: center
}
</style>