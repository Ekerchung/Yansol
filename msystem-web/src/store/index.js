//引入Vue核心庫
import Vue from 'vue'
//引入Vuex
import Vuex from 'vuex'
import menu from './menu'
import data from './data'
import worker from './worker'
import account from './account'
import line from './line'

//應用Vuex插件
Vue.use(Vuex)

//actions對象
// const actions = {}
//mutations對象
// const mutations = {}
//state對象
// const state = {}

//創建並暴露store
export default new Vuex.Store({
    modules:{
        menu,
        data,
        worker,
        account,
        line,
    },
})