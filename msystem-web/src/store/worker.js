import {updateEmployee, deleteEmployee, getEmployeePageData} from "../api";
import _ from 'lodash';
import moment from "moment";

export default {
    namespaced:true,
    actions:{
        fetchUpdateEmployee(context,data) {
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
            console.log('fetchGetEmployeePageData',data);
            return getEmployeePageData(data)
                .then((response) => {
                    context.commit('setEmployeePageData', response.data);
                })
                .catch((error) => {
                    console.error(error)
                })
        },
    },
    getters: {
    },
    mutations:{
        setEmployeePageData(state, data) { // 添加EmployeePageData數據
            state.employeePageData = data;
            console.log('調用了setEmployeePageData')
        },
    },
    state:{
        employeePageData: [],
    }
}