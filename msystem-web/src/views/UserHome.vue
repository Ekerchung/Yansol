<template>
  <el-row>
      <el-card class="box-card" style="height: 255px">
        <div class="user">
          <img :src=imgPath alt="">
          <div class="userinfo">
            <p class="name">{{employee.name}}</p>
            <p class="access">一般使用者</p>
          </div>
        </div>
        <div class="login-info">
          <p>上次登錄時間：<span>{{lastLoginTime}}</span></p>
        </div>
      </el-card>
      <el-card style="margin-top: 20px; height: 550px;">
        <h3 style="text-align: left ; margin-left: 10px">待完成訂單：</h3>
        <el-table
            :data="orderDataByEid"
            height="510"
            stripe
            size="small"
            empty-text="暫無數據"
            style="width: 100%">
          <el-table-column
              type="index"
              width="50">
          </el-table-column>
          <el-table-column
              prop="oDate"
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
              label="端子">
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
        </el-table>
      </el-card>
  </el-row>
</template>

<script>
import { mapGetters,mapActions,mapState,mapMutations } from 'vuex'
export default {
  name:'UserHome',
  data() {
    return {
      employee: {eid:''},
      lastLoginTime: '',
      imgPath:'',
      username:'',
    }
  },
  computed: {
    ...mapState('data',['goodData','companyData','employeeData','orderData','orderDataByEid']),
  },
  methods:{
    ...mapActions('data',['fetchGoodData','fetchCompanyData','fetchEmployeeData','fetchOrderData','fetchOrderDataByEid']),
  },
  created() {
    this.username = localStorage.getItem('username');
    this.imgPath = require(`@/assets/images/${this.username}.png`);
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