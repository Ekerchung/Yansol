import {addLineData, getGoodPageData} from "../api";
import _ from 'lodash';
import moment from "moment";

export default {
    namespaced:true,
    actions:{
        fetchAddLineData(context,data) {
            console.log('fetchAddLineData',data);
            return addLineData(data) //此處不獲取response、error，交由LinePurchase頁面獲取
        },
        fetchGoodPageData(context,data) {
            console.log('fetchGoodPageData',data)
            return getGoodPageData(data)
                .then((response) => {
                    context.commit('setGoodPageData', response.data)
                })
                .catch((error) => {
                    console.error(error)
                })
        }
    },
    getters: {
    },
    mutations:{
        setGoodPageData(state, data) { // 添加goodPageData數據
            state.goodPageData = data;
            console.log('調用了setGoodPageData')
        },
    },
    state:{
        linePageData: [],
        goodPageData: [],
    }
}