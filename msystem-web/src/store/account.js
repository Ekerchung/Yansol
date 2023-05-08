import {updateAccount, deleteAccount, getAccountPageData} from "../api";

export default {
    namespaced:true,
    actions:{
        fetchUpdateAccount(context,data) {
            // console.log('fetchUpdateAccount',data);
            return updateAccount(data)
                .then((response) => {
                    // console.log('保存成功')
                })
                .catch((error) => {
                    console.error(error)
                })
        },
        fetchDeleteAccount(context,data) {
            // console.log('fetchDeleteAccount',data);
            return deleteAccount(data)
                .then((response) => {
                    console.log('刪除成功')
                })
                .catch((error) => {
                    console.error(error)
                })
        },
        fetchGetAccountPageData(context,data) {
            // console.log('fetchAccountPageData',data);
            return getAccountPageData(data)
                .then((response) => {
                    // console.log('調用了fetchGetEmployeePageData');
                    context.commit('setAccountPageData', response.data);
                })
                .catch((error) => {
                    // console.log(error)
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
        setAccountPageData(state, data) { // 添加goodData數據
            // console.log('data',data)
            state.accountPageData = data;
            // console.log('調用了setEmployeePageData')
        },
    },
    state:{
        accountPageData: [],
    }
}