import {addLineData, getGoodPageData, updateGood} from "../api";
import _ from 'lodash';
import moment from "moment";

export default {
    namespaced:true,
    actions:{
        fetchAddLineData(context,data) {
            return addLineData(data) //此處不獲取response、error，交由調用頁面獲取
        },
        fetchGoodPageData(context,data) {
            return getGoodPageData(data)
                .then((response) => {
                    context.commit('setGoodPageData', response.data)
                })
                .catch((error) => {
                    console.error(error)
                })
        },
        fetchUpdateGood(context,data) {
            return updateGood(data) //.then().catch()不寫，由調用的頁面接收信息
        },
    },
    getters: {
    },
    mutations:{
        setGoodPageData(state, data) { // 添加goodPageData數據
            state.goodPageData = data;
        },
    },
    state:{
        linePageData: [],
        goodPageData: [],
    }
}