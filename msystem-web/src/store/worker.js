import {updateEmployee, deleteEmployee, getEmployeePageData} from "../api";
import _ from 'lodash';
import moment from "moment";

export default {
    namespaced:true,
    actions:{
        fetchUpdateEmployee(context,data) {
            console.log(data);
            return updateEmployee(data)
                .then((response) => {
                    // console.log('保存成功')
                })
                .catch((error) => {
                    console.error(error)
                })
        },
        fetchDeleteEmployee(context,data) {
            console.log(data);
            return getEmployeePage(data)
                .then((response) => {
                    // console.log('刪除成功')
                })
                .catch((error) => {
                    console.error(error)
                })
        },
        fetchGetEmployeePageData(context,data) {
            //判斷是否有頁碼傳入，若沒有傳則默認為第1頁
            // if(data.pageNum == null){
            //     // const pageNum = 1;
            //     data.pageNum = 1;
            // }
            // // if(data.queryKeyWord == null){
            //
            // }
            console.log('fetchGetEmployeePageData',data);
            return getEmployeePageData(data)
                .then((response) => {
                    // console.log('調用了fetchGetEmployeePageData');
                    context.commit('setEmployeePageData', response.data);
                })
                .catch((error) => {
                    console.error(error)
                })
        },
    },
    getters: {
        //當前年份
        currentYear(state) {
            return new Date().getFullYear();
        },
    },
    mutations:{
        setEmployeePageData(state, data) { // 添加goodData數據
            // console.log('data',data)
            state.employeePageData = data;
            // console.log('調用了setEmployeePageData')
        },
    },
    state:{
        employeePageData: [],
    }
}