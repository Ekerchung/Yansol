import {updateOrder, getOrderPageData, deleteOrder, addOrder, getOrderQueryData, getOrderDataByEidByDate} from "../api";

export default {
    namespaced:true,
    actions:{
        fetchAddOrder(context,data) {
            return addOrder(data)
                .then((response) => {
                    // console.log('添加完成')
                })
                .catch((error) => {
                    console.error(error)
                })
        },
        fetchUpdateOrder(context,data) {
            return updateOrder(data)//此處不獲取response、error，交由調用頁面獲取
        },
        fetchOrderPageData(context,data) {
            return getOrderPageData(data)
                .then((response) => {
                    context.commit('setOrderPageData', response.data)
                })
                .catch((error) => {
                    console.error(error)
                })
        },
        fetchGetOrderDataByEidByDate(context,data) {
            return getOrderDataByEidByDate(data)
                .then((response) => {
                    context.commit('setOrderQueryDataByUser', response.data);
                })
                .catch((error) => {
                    console.error(error)
                })
        },
        fetchOrderQueryData(context,data) {
            return getOrderQueryData(data)
                .then((response) => {
                    context.commit('setOrderQueryData', response.data)
                })
                .catch((error) => {
                    console.error(error)
                })
        },
        fetchDeleteOrder(context,data) {
            return deleteOrder(data)
                .then((response) => {
                    context.commit('deleteOrder', response.data)
                })
                .catch((error) => {
                    console.error(error)
                })
        }
    },
    getters: {
    },
    mutations:{
        setOrderPageData(state, data) { // 添加orderPageData數據
            state.orderPageData = data;
        },
        setOrderQueryData(state, data) { // 添加orderQueryData數據
            state.orderQueryData = data;
        },
        setOrderQueryDataByUser(state, data) { // 添加orderQueryDataByUser數據
            state.orderQueryDataByUser = data;
        },
    },
    state:{
        orderPageData: [],
        orderQueryData: [],
        orderQueryDataByUser: [],
    }
}