<template>
  <div class="manage">
    <div class="common-tabel">
      <!--    人員薪資表格-->
      <el-table
          :data="salaryList"
          height="90%"
          stripe
          empty-text="暫無數據"
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
    </div>
  </div>
</template>


<script>
import {mapActions, mapGetters, mapMutations, mapState} from 'vuex';
export default {
  name:'PayrollManagement',
  data(){
    return{
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
  },
  created() {
    this.fetchEmployeeData();
    this.fetchOrderData();
  },

}
</script>

<style  scoped>
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