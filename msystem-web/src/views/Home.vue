<template>
  <el-row>
    <el-col :span="8" style="padding-right: 10px">
      <el-card class="box-card">
        <div class="user">
          <img src="../assets/images/male.png" alt="">
          <div class="userinfo">
            <p class="name">Admin</p>
            <p class="access">超级管理员</p>
          </div>
        </div>
        <div class="login-info">
          <p>上次登录时间：<span>2021-7-19</span></p>
        </div>
      </el-card>
      <el-card style="margin-top: 20px;height: 620px;">
<!--        <div slot="header" class="clearfix">-->
<!--          <span>待完成線材清單</span>-->
<!--        </div>-->
<!--        <p class="tablename">《 待完成線材清單 》</p>-->
        <el-table
            :data="goodData"
            height="580"
            stripe
            size="small"
            style="width: 100%">
          <el-table-column
              prop="pdate"
              label="進貨日期"
              width="90">
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
              width="160">
          </el-table-column>
          <el-table-column
              prop="totalCount"
              label="數量"
              width="60">
          </el-table-column>
        </el-table>
      </el-card>
     </el-col>
    <el-col :span="16" style="padding-left: 10px">
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
      <el-card style="height: 350px">
        <!-- 折线图 -->
        <div ref="echarts1" class="echarts3" style="height: 350px"></div>
      </el-card>
      <div class="graph">
        <el-card style="height: 340px">
          <div ref="echarts2" class="echarts3" style="height: 340px"></div>
        </el-card>
        <el-card style="height: 340px">
          <div ref="echarts3" class="echarts3" style="height: 340px"></div>
        </el-card>
      </div>
    </el-col>
  </el-row>
</template>

<script>
import * as echarts from 'echarts';
import _ from 'lodash';
import {getCompanyData, getEmployeeData, getGoodData, getOrderData, getReturnData} from "../api";
export default {
  name:'Home',
  data() {
    return {
      goodData: [],
      companyData: [],
      employeeData: [],
      orderData: [],
      returnData: [],
      countData: [
        {
          name: "今日進貨",
          value: '',
          icon: "success",
          color: "#2ec7c9",
        },
        {
          name: "今日出貨",
          value: '',
          icon: "truck",
          color: "#ffb980",
        },
        {
          name: "今日退貨",
          value: '',
          icon: "message-solid",
          color: "#c55aef",
        },
        {
          name: "今日應收款",
          value: '',
          icon: "coin",
          color: "#5ab1ef",
        },
        {
          name: "本月進貨",
          value: '',
          icon: "success",
          color: "#2ec7c9",
        },
        {
          name: "本月出貨",
          value: '',
          icon: "truck",
          color: "#ffb980",
        },
        {
          name: "本月退貨",
          value: '',
          icon: "message-solid",
          color: "#c55aef",
        },
        {
          name: "本月應收款",
          value: '',
          icon: "coin",
          color: "#5ab1ef",
        },
      ]
    }
  },
  created() {
    getGoodData().then(
        response => {
          this.goodData = response.data;
        },
        error => {
          alert(error.message)
        }
    )
    getCompanyData().then(
        response => {
          this.companyData = response.data;
        },
        error => {
          alert(error.message)
        }
    )
    getEmployeeData().then(
        response => {
          this.employeeData = response.data;
        },
        error => {
          alert(error.message)
        }
    )
    getOrderData().then(
        response => {
          this.orderData = response.data;
        },
        error => {
          alert(error.message)
        }
    )
    getReturnData().then(
        response => {
          this.returnData = response.data;
        },
        error => {
          alert(error.message)
        }
    )


  },
  mounted() {
    getGoodData().then(
        response => {
          //將響應的數據綁定到this.goodData
          // this.goodData = response.data;
          //下面開始處理myChart1所需數據
          let goodDataList = response.data;
          //取得當前年份
          let currentYear = new Date().getFullYear();
          //取得去年年份
          let lastYear = currentYear-1;
          // 首先使用lodash將數據按照月份進行分組
          const byMonth = _.groupBy(goodDataList, good => good.pDate.slice(0, 7));
          // 然後對每個月份的訂單進行總收入計算，最後得到的monthlyRevenue對象的鍵表示月份（如'2022-01'），值表示當月的總收入（如300）
          const monthlyRevenue = _.mapValues(byMonth, goodDataList => _.sumBy(goodDataList, 'totalPrice')/1000);
          //動態設定myChart1表
          myChart1.setOption({
            legend: {
              //echart中，legend的值必須要是String才會顯示顏色
              data: [lastYear.toString(),currentYear.toString()]
            },
            series: [
              {
                name: lastYear,
                type: 'line',
                data: [
                  monthlyRevenue[`${lastYear}-01`],
                  monthlyRevenue[`${lastYear}-02`],
                  monthlyRevenue[`${lastYear}-03`],
                  monthlyRevenue[`${lastYear}-04`],
                  monthlyRevenue[`${lastYear}-05`],
                  monthlyRevenue[`${lastYear}-06`],
                  monthlyRevenue[`${lastYear}-07`],
                  monthlyRevenue[`${lastYear}-08`],
                  monthlyRevenue[`${lastYear}-09`],
                  monthlyRevenue[`${lastYear}-010`],
                  monthlyRevenue[`${lastYear}-011`],
                  monthlyRevenue[`${lastYear}-012`]]
              },
              {
                name: currentYear,
                type: 'line',
                data: [
                  monthlyRevenue[`${currentYear}-01`],
                  monthlyRevenue[`${currentYear}-02`],
                  monthlyRevenue[`${currentYear}-03`],
                  monthlyRevenue[`${currentYear}-04`],
                  monthlyRevenue[`${currentYear}-05`],
                  monthlyRevenue[`${currentYear}-06`],
                  monthlyRevenue[`${currentYear}-07`],
                  monthlyRevenue[`${currentYear}-08`],
                  monthlyRevenue[`${currentYear}-09`],
                  monthlyRevenue[`${currentYear}-010`],
                  monthlyRevenue[`${currentYear}-011`],
                  monthlyRevenue[`${currentYear}-012`]]
              }
            ]
          });

        },
        error => {
          alert(error.message)
        }
    )
    getCompanyData().then(
        response => {
          //處理myChart1所需的數據
          //在返回的company數據中添加當年營收及去年營收欄位
          let companyDataList = response.data.map(c => ({ ...c, total_sales_this_year: 0,total_sales_last_year: 0 }));
          //取得當前年份
          let currentYear = new Date().getFullYear();
          //取得去年年份
          let lastYear = currentYear-1;
          //由this.goodDate中取得當前年份的營收
          for (let i = 0; i < this.goodData.length; i++) {
            let item = this.goodData[i];
            if(item.pDate.includes(currentYear)){
              let company = companyDataList.find(c => c.companyId === item.company.companyId );
              if (company) {
                company.total_sales_this_year += item.totalPrice;
              }
              //由this.goodDate中取得去年年份的營收
            }else if(item.pDate.includes(lastYear)){
              let companyLastYear = companyDataList.find(c => c.companyId === item.company.companyId );
              if (companyLastYear) {
                companyLastYear.total_sales_last_year += item.totalPrice;
              }
            }
          }
          //將取的資料保存到this.companyData
          this.companyData = companyDataList;
          //動態設定myChart2資料
          myChart2.setOption({
            xAxis: {
              data: this.companyData.map(company => company.companyName)
            },
            legend: {
              data: [lastYear.toString(),currentYear.toString()]
            },
            series: [
              {
                name: lastYear,
                data: this.companyData.map(company => company.total_sales_last_year / 1000)
              },
              {
                name: currentYear,
                data: this.companyData.map(company => company.total_sales_this_year / 1000)
              }
            ]
          });
        },
        error => {
          alert(error.message)
        }
    )
    getOrderData().then(
        response => {
          //以下處理myChart3所需數據
          let orderDataList = response.data;
          let employeeDataList = this.employeeData.map(c => ({ ...c, total_sales_this_month: 0}));
          //取得當前時間
          let now = new Date();
          //取得當前年份
          let year = now.getFullYear();
          //取得當前月份
          let month = now.getMonth() + 1;
          //月份補零操作
          month = month < 10 ? "0" + month : month;
          //由order篩選出當月每個員工的收入
          for (let i = 0; i < orderDataList.length; i++) {
            let item = orderDataList[i];
            if(item.oDate.includes(`${year}-${month}`)){
              let employee = employeeDataList.find(e => e.eid === item.employee.eid );
              if (employee) {
                employeeDataList[item.employee.eid].total_sales_this_month += item.ototalPrice;
              }
            }
          }
          //將當月每個員工的收入依myChart3所需格式保存到map中
          let mappedDataList = employeeDataList.map((item, index, arr) => {
            return {
              value: item.ename,
              name: item.total_sales_this_month
            };
          });
          myChart3.setOption({
            //動態取得數據並設定到圖表
            series: [
              {
                data: employeeDataList.filter(emp => emp.total_sales_this_month !== 0).map(emp => {
                  return {
                    name: emp.ename,
                    value: [emp.total_sales_this_month]
                  };
                })
              }
            ],
           //動態綁定提式框
            tooltip: {
              trigger: 'item',
              formatter: '{b}: {c} ({d}%)'
            },
          });

          //以下處理countDate數據
          //使用moment.js取得當前時間，格式為"YYYY-MM-DD"
          let moment = require('moment');
          let currentDate = moment().format('YYYY-MM-DD');
          let currentMonth = moment().format('YYYY-MM');
          //將this.goodData中符合當日時間的進貨商品保存到PurchaseLis中
          let monthlyPurchaseList = this.goodData.filter(g => g.pDate && g.pDate !== 0).filter(g => g.pDate.slice(0, 7) === currentMonth);
          let dailyPurchaseList  = monthlyPurchaseList.filter(g => g.pDate === currentDate);
          //將this.goodData中符合當日時間的出貨商品保存到ShipList中
          let monthlyShipList = this.goodData.filter(g => g.sDate && g.sDate !== 0).filter(g => g.sDate.slice(0, 7) === currentMonth);
          let dailyShipList = monthlyShipList.filter(g => g.sDate === currentDate);
          //將this.returnData中符合當日時間的退貨商品保存到ReturnList中
          let monthlyReturnList = this.returnData.filter(r => r.returnDate && r.returnDate !== 0).filter(r => r.returnDate.slice(0, 7) === currentMonth);
          let dailyReturnList = monthlyReturnList.filter(r => r.returnDate === currentDate);
          //定義當月應收款
          let monthlyRevenue = 0;
          //定義當日應收款
          let dailyRevenue = 0;
          //求得當月應收款
          for(let i = 0; i < monthlyShipList.length; i++){
            monthlyRevenue += monthlyShipList[i].totalPrice;
          }
          //求得當日應收款
          for(let i = 0; i < dailyShipList.length; i++){
            dailyRevenue += dailyShipList[i].totalPrice;
          }
          //動態綁定countDate數據
          this.countData[0].value = dailyPurchaseList.length;
          this.countData[1].value = dailyShipList.length;
          this.countData[2].value = dailyReturnList.length;
          this.countData[3].value = dailyRevenue;
          this.countData[4].value = monthlyPurchaseList.length;
          this.countData[5].value = monthlyShipList.length;
          this.countData[6].value = monthlyReturnList.length;
          this.countData[7].value = monthlyRevenue;
        },
        error => {
          alert(error.message)
        }
    )

    // 基於準備好的dom，初始化echarts實例
    var myChart1 = echarts.init(this.$refs.echarts1);
    // 繪製圖表1
    myChart1.setOption({
      title: {
        text: '每月營收'
      },
      tooltip: {},
      legend: {
        data: []
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
          name: '',
          type: 'line',
          data: []
        },
        {
          name: '',
          type: 'line',
          data: []
        }
      ]
    });
    // 基於準備好的dom，初始化echarts實例
    var myChart2 = echarts.init(this.$refs.echarts2);
    // 繪製圖表2
    myChart2.setOption({
      title: {
        text: '廠商收入'
      },
      tooltip: {},
      legend: {
        data: ['','']
      },
      xAxis: {
        data: []
      },
      yAxis: {
        type: 'value',
        name: '營收/(仟)'
      },
      series: [
        {
          name: '2019',
          type: 'bar',
          barGap: '20%',
          barCategoryGap: '40%',
          data: []
        },
        {
          name: '2020',
          type: 'bar',
          data: []
        }
      ]
    });
    // 基於準備好的dom，初始化echarts實例
    var myChart3 = echarts.init(this.$refs.echarts3);
    // 繪製圖表3
    myChart3.setOption({
      title: {
        text: '員工收入'
      },
      series: [
        {
          type: 'pie',
          data: [],
          label: {
            show: true,
            formatter: '{b}: {c} 元  ({d}%)'
          },
        }
      ]

    });

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
  width: 80px;
  height: 80px;
  font-size: 30px;
  text-align: center;
  line-height: 80px;
  color: #fff;
}
.num .detail {
  margin-left: 15px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  height: 80px;
}
.num .detail .price {
  font-size: 30px;
  margin-bottom: 10px;
  margin-top: 20px;
  line-height: 30px;
  height: 30px;
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