import {updateAccount, deleteAccount, getAccountPageData, getAccountData} from "../api";

export default {
    namespaced:true,
    actions:{
        fetchUpdateAccount(context,data) {
            return updateAccount(data)
                .then((response) => {
                })
                .catch((error) => {
                    console.error(error)
                })
        },
        fetchDeleteAccount(context,data) {
            return deleteAccount(data)
                .then((response) => {
                    // console.log('刪除成功')
                })
                .catch((error) => {
                    console.error(error)
                })
        },
        fetchGetAccountPageData(context,data) {
            return getAccountPageData(data)
                .then((response) => {
                    context.commit('setAccountPageData', response.data);
                })
                .catch((error) => {
                    console.error(error)
                })
        },
        fetchGetAccountData(context) {
            console.log('fetchGetAccountData');
            return getAccountData()
                .then((response) => {
                    context.commit('setAccountData', response.data);
                })
                .catch((error) => {
                    console.error(error)
                })
        },
    },
    getters: {
    },
    mutations:{
        setAccountPageData(state, data) { // 添加accountPageData數據
            state.accountPageData = data;
        },
        setAccountData(state, data) { // 添加accountData數據
            state.accountData = data;
        },
    },
    state:{
        accountPageData: [],
        accountData: [],
    }
}