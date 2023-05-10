import {addLineData} from "../api";
import _ from 'lodash';
import moment from "moment";

export default {
    namespaced:true,
    actions:{
        fetchAddLineData(context,data) {
            console.log('fetchAddLineData',data);
            return addLineData(data)
                .then((response) => {
                    // console.log('保存成功')
                })
                .catch((error) => {
                    console.error(error)
                })
        },
    },
    getters: {
    },
    mutations:{
        // setEmployeePageData(state, data) { // 添加EmployeePageData數據
        //     // console.log('data',data)
        //     state.employeePageData = data;
        //     console.log('調用了setEmployeePageData')
        // },
    },
    state:{
        linePageData: [],
    }
}