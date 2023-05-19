import {updateOrder, getOrderPageData, deleteOrder,addOrder,getOrderQueryData} from "../api";

export default {
    namespaced:true,
    actions:{
        fetchAddOrder(context,data) {
            console.log('fetchAddOrder',data);
            return addOrder(data)
                .then((response) => {
                    console.log('添加完成')
                })
                .catch((error) => {
                    console.error(error)
                })
        },
        fetchUpdateOrder(context,data) {
            console.log('fetchUpdateOrder',data);
            return updateOrder(data)
                .then((response) => {
                    console.log('添加完成')
                })
                .catch((error) => {
                    console.error(error)
                })
        },
        fetchOrderPageData(context,data) {
            console.log('fetchOrderPageData',data)
            return getOrderPageData(data)
                .then((response) => {
                    context.commit('setOrderPageData', response.data)
                })
                .catch((error) => {
                    console.error(error)
                })
        },
        fetchOrderQueryData(context,data) {
            console.log('fetchOrderQueryData',data)
            return getOrderQueryData(data)
                .then((response) => {
                    context.commit('setOrderQueryData', response.data)
                })
                .catch((error) => {
                    console.error(error)
                })
        },
        fetchDeleteOrder(context,data) {
            console.log('fetchDeleteOrder',data)
            return deleteOrder(data)
                .then((response) => {
                    context.commit('deleteOrder', response.data)
                })
                .catch((error) => {
                    console.error(error)
                })
        }
    },
    // getters: {
    //     getOrderPageDataFilterComplete(state){
    //         let orderPageDataFilter = state.orderPageData.content.filter(o => o.comDate === null);
    //         return orderPageDataFilter;
    //     }
    // },
    mutations:{
        setOrderPageData(state, data) { // 添加orderPageData數據
            state.orderPageData = data;
            console.log('調用了setOrderPageData')
        },
        setOrderQueryData(state, data) { // 添加orderQueryData數據
            state.orderQueryData = data;
            console.log('調用了setOrderQueryData')
        },
    },
    state:{
        orderPageData: [],
        orderQueryData: [],
    }
}