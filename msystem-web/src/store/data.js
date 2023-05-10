import {getCompanyData, getEmployeeData, getGoodData, getOrderData, getReturnData} from "../api";
import _ from 'lodash';
import moment from "moment";

export default {
    namespaced:true,
    actions:{
        fetchGoodData(context) {
            return getGoodData()
                .then((response) => {
                    context.commit('setGoodData', response.data)
                })
                .catch((error) => {
                    console.error(error)
                })
        },
        fetchCompanyData(context) {
            return getCompanyData()
                .then((response) => {
                    console.log('調用了fetchCompanyData')
                    context.commit('setCompanyData', response.data)
                })
                .catch((error) => {
                    console.error(error)
                })
        },
        fetchEmployeeData(context) {
            return getEmployeeData()
                .then((response) => {
                    console.log('調用了fetchEmployeeData')
                    context.commit('setEmployeeData', response.data)
                })
                .catch((error) => {
                    console.error(error)
                })
        },
        fetchOrderData(context) {
            return getOrderData()
                .then((response) => {
                    console.log('調用了fetchOrderData');
                    context.commit('setOrderData', response.data);
                })
                .catch((error) => {
                    console.error(error)
                })
        },
        fetchReturnData(context) {
            return getReturnData()
                .then((response) => {
                    console.log('調用了fetchReturnData')
                    context.commit('setReturnData', response.data)
                })
                .catch((error) => {
                    console.error(error)
                })
        }
    },
    getters: {
        //當前年份
        currentYear(state) {
            return new Date().getFullYear();
        },
        //去年年份
        lastYear(state,getters) {
            return getters.currentYear - 1;
        },
        //今年每月營收
        monthlyRevenue(state) {
            let goodDataList = state.goodData;
            // 首先使用lodash將數據按照月份進行分組
            const byMonth = _.groupBy(goodDataList, good => good.pDate.slice(0, 7));
            // 然後對每個月份的訂單進行總收入計算，最後得到的monthlyRevenue對象的鍵表示月份（如'2022-01'），值表示當月的總收入（如300）
            const monthlyRevenue = _.mapValues(byMonth, goodDataList => _.sumBy(goodDataList, 'totalPrice')/1000);
            return monthlyRevenue;
        },
        //取得廠商當年營收及去年營收
        companySalesList(state,getters) {
            //在返回的company數據中添加當年營收及去年營收欄位
            let companySalesList = state.companyData.map(c => ({ ...c, total_sales_this_year: 0,total_sales_last_year: 0 }));
            //取得當前年份
            let currentYear = new Date().getFullYear();
            //取得去年年份
            let lastYear = currentYear-1;
            //由this.goodDate中取得當前年份的營收
            for (let i = 0; i < state.goodData.length; i++) {
                let item = state.goodData[i];
                if(item.pDate.includes(getters.currentYear)){
                    let company = companySalesList.find(c => c.companyId === item.company.companyId );
                    if (company) {
                        company.total_sales_this_year += item.totalPrice;
                    }
                    //由this.goodDate中取得去年年份的營收
                }else if(item.pDate.includes(getters.lastYear)){
                    let companyLastYear = companySalesList.find(c => c.companyId === item.company.companyId );
                    if (companyLastYear) {
                        companyLastYear.total_sales_last_year += item.totalPrice;
                    }
                }
            }
            //將取的資料保存到companySalesList
            return companySalesList;
        },
        employeeSalesList(state,getters) {
            //以下處理myChart3所需數據
            let employeeDataList = state.employeeData.map(c => ({ ...c, total_sales_this_month: 0}));
            //由order篩選出當月每個員工的收入
            for (let i = 0; i < state.orderData.length; i++) {
                let item = state.orderData[i];
                if(item.oDate.includes(getters.currentMonth)){
                    let employee = employeeDataList.find(e => e.eid === item.employee.eid );
                    if (employee) {
                        employeeDataList[item.employee.eid].total_sales_this_month += item.ototalPrice;
                    }
                }
            }
            //將當月每個員工的收入依myChart3所需格式保存到map中
            let mappedDataList = employeeDataList.filter(e => e.total_sales_this_month !== 0).map((item, index, arr) => {
                return {
                    name: item.name,
                    value: item.total_sales_this_month
                };
            });
            return mappedDataList;
        },
        salaryList(state,getters) {
            //以下處理myChart3所需數據
            let employeeDataList = state.employeeData.map(c => ({ ...c, total_sales_this_month: 0,total_sales_last_month: 0}));
            //由order篩選出當月每個員工的收入
            for (let i = 0; i < state.orderData.length; i++) {
                let item = state.orderData[i];
                if(item.oDate.includes(getters.currentMonth)){
                    let employee = employeeDataList.find(e => e.eid === item.employee.eid );
                    if (employee) {
                        employeeDataList[item.employee.eid].total_sales_this_month += item.ototalPrice;
                    }
                }
            }
            for (let i = 0; i < state.orderData.length; i++) {
                let item = state.orderData[i];
                if(item.oDate.includes(getters.lastMonth)){
                    let employee = employeeDataList.find(e => e.eid === item.employee.eid );
                    if (employee) {
                        employeeDataList[item.employee.eid].total_sales_last_month += item.ototalPrice;
                    }
                }
            }
            //將當月每個員工的收入依myChart3所需格式保存到map中
            let mappedDataList = employeeDataList.filter(e => e.total_sales_this_month !== 0 || e.total_sales_last_month !== 0  ).map((item, index, arr) => {
                return {
                    name: item.name,
                    lastSalary: item.total_sales_last_month,
                    thisSalary: item.total_sales_this_month
                };
            });
            return mappedDataList;
        },
        //使用moment.js取得當前時間，格式為"YYYY-MM-DD"
        currentDate(state) {
            let moment = require('moment');
            let currentDate = moment().format('YYYY-MM-DD');
            return currentDate;
        },
        //使用moment.js取得當前月份，格式為"YYYY-MM"
        currentMonth(state) {
            let moment = require('moment');
            let currentMonth = moment().format('YYYY-MM');
            return currentMonth
        },
        //使用moment.js取得上個月份，格式為"YYYY-MM"
        lastMonth(state) {
            let moment = require('moment');
            const lastMonth = moment().subtract(1, 'month').format('YYYY-MM');
            return lastMonth
        },
        //每月進貨清單
        monthlyPurchaseList(state,getters) {

            return state.goodData.filter(g => g.pDate && g.pDate !== 0).filter(g => g.pDate.slice(0, 7) === getters.currentMonth);
        },
        //每日進貨清單
        dailyPurchaseList(state,getters) {
            return getters.monthlyPurchaseList.filter(g => g.pDate === getters.currentDate);
        },
        //每月出貨清單
        monthlyShipList(state,getters) {
            return state.goodData.filter(g => g.sDate && g.sDate !== 0).filter(g => g.sDate.slice(0, 7) === getters.currentMonth);
        },
        //每日出貨清單
        dailyShipList(state,getters) {
            return getters.monthlyShipList.filter(g => g.sDate === getters.currentDate);
        },
        //每月退貨清單
        monthlyReturnList(state,getters) {
            return state.returnData.filter(r => r.returnDate && r.returnDate !== 0).filter(r => r.returnDate.slice(0, 7) === getters.currentMonth);
        },
        //每日退貨清單
        dailyReturnList(state,getters) {
            return getters.monthlyReturnList.filter(r => r.returnDate === getters.currentDate);
        },
        //求得當月應收款
        monthlyAccountsReceivable(state,getters) {
            let monthlyAccountsReceivable = 0;
            for(let i = 0; i < getters.monthlyShipList.length; i++){
                monthlyAccountsReceivable += getters.monthlyShipList[i].totalPrice;
            }
            return monthlyAccountsReceivable;
        },
        //求得當日應收款
        dailyAccountsReceivable(state,getters) {
            let dailyAccountsReceivable = 0;
            for(let i = 0; i < getters.dailyShipList.length; i++){
                dailyAccountsReceivable += getters.dailyShipList[i].totalPrice;
            }
            return dailyAccountsReceivable;
        },
    },
    mutations:{
        setGoodData(state, data) { // 添加goodData數據
            state.goodData = data;
            console.log('調用了setGoodData')
        },
        setCompanyData(state, data) { // 添加companyData數據
            state.companyData = data;
            console.log('調用了setCompanyData')
        },
        setEmployeeData(state, data) { // 添加employeeData數據
            state.employeeData = data;
            console.log('調用了setEmployeeData')
        },
        setThisMonthSallary(state, data) { // 添加employeeData數據
            state.thisMonthSallary = data;
            console.log('調用了setthisMonthSallary')
        },
        setLastMonthSallary(state, data) { // 添加employeeData數據
            state.lastMonthSallary = data;
            console.log('調用了setlastMonthSallary')
        },
        setOrderData(state, data) { // 添加orderData數據
            state.orderData = data;
            console.log('調用了setOrderData')
        },
        setReturnData(state, data) { // 添加returnData數據
            state.returnData = data;
            console.log('調用了setReturnData')
        },
        setCountData(state, data) { // 添加countData數據
            state.countData = data;
            console.log('調用了setCountData')
        },
        setMenuData(state, data) { // 依據帳號權限添加MenuData數據
            console.log(data)
            if(localStorage.getItem('menuData')){
                console.log('11調用了setMenuData')
                state.menuData = JSON.parse(localStorage.getItem('menuData'));
            }else if(data === 1){
                console.log('22調用了setMenuData')
                state.menuData = state.adminMenuData;//將管理員權限的菜單賦予menuData
                localStorage.setItem('menuData',JSON.stringify(state.adminMenuData));//保存菜單內容到localStorage中，避免刷新失去Vuex中的menuData，失去導航頁面
            }else {
                console.log('33調用了setMenuData')
                state.menuData = state.userMenuData;//將一般使用者權限的菜單賦予menuData
                localStorage.setItem('menuData',JSON.stringify(state.userMenuData));//保存菜單內容到localStorage中，避免刷新失去Vuex中的menuData，失去導航頁面
            }
            console.log('44調用了setMenuData')
        },
    },
    state:{
        goodData: [],
        companyData: [],
        employeeData: [],
        orderData: [],
        returnData: [],
        countData: [
            {
                name: "今日進貨",
                value: '',
                icon: "success",
                color: "#2ec7c9",
            },
            {
                name: "今日出貨",
                value: '',
                icon: "truck",
                color: "#ffb980",
            },
            {
                name: "今日退貨",
                value: '',
                icon: "message-solid",
                color: "#c55aef",
            },
            {
                name: "今日應收款",
                value: '',
                icon: "coin",
                color: "#5ab1ef",
            },
            {
                name: "本月進貨",
                value: '',
                icon: "success",
                color: "#2ec7c9",
            },
            {
                name: "本月出貨",
                value: '',
                icon: "truck",
                color: "#ffb980",
            },
            {
                name: "本月退貨",
                value: '',
                icon: "message-solid",
                color: "#c55aef",
            },
            {
                name: "本月應收款",
                value: '',
                icon: "coin",
                color: "#5ab1ef",
            },
        ],
        adminMenuData:[

            {
                path: '/home',
                name: 'home',
                label: '首页',
                icon: 's-home',
                url: 'Home/Home',
                meta:{role: ['1']}
            },
            {
                label: '線材進出貨管理',
                icon: 'location',
                meta:{role: ['1']},
                children: [
                    {
                        path: '/linepurchase',
                        name: 'linepurchase',
                        label: '線材進貨',
                        icon: 'setting',
                        url: 'management/linepurchase',
                        meta:{role: ['1']}
                    },
                    {
                        path: '/lineshipping',
                        name: 'lineshipping',
                        label: '線材出貨',
                        icon: 'setting',
                        url: 'management/lineshipping',
                        meta:{role: ['1']}
                    },
                    {
                        path: '/lineprocess',
                        name: 'lineprocess',
                        label: '線材生產',
                        icon: 'setting',
                        url: 'management/lineprocess',
                        meta:{role: ['1']}
                    },
                    {
                        path: '/linereturn',
                        name: 'linereturn',
                        label: '線材退貨',
                        icon: 'setting',
                        url: 'management/linereturn',
                        meta:{role: ['1']}
                    },
                    {
                        path: '/linemanagement',
                        name: 'linemanagement',
                        label: '線材管理',
                        icon: 'setting',
                        url: 'management/linemanagement',
                        meta:{role: ['1']}
                    },
                ]
            },
            {
                label: '人員管理',
                icon: 'location',
                meta:{role: ['1']},
                children: [
                    {
                        path: '/payrollmanagement',
                        name: 'payrollmanagement',
                        label: '薪資管理',
                        icon: 'setting',
                        url: 'management/payrollmanagement',
                        meta:{role: ['1']}
                    },
                    {
                        path: '/workermanagement',
                        name: 'workermanagement',
                        label: '人員管理',
                        icon: 'setting',
                        url: 'management/workermanagement',
                        meta:{role: ['1']}
                    },
                    {
                        path: '/accountManagement',
                        name: 'accountManagement',
                        label: '帳號管理',
                        icon: 'setting',
                        url: 'management/accountManagement',
                        meta:{role: ['1']}
                    }
                ]
            }
        ],
        userMenuData:[

            {
                path: '/UserHome',
                name: 'home',
                label: '首页',
                icon: 's-home',
                url: 'Home/UserHome',
                meta:{role: ['2']}
            },
            {
                path: '/lineprocess',
                name: 'userlineprocess',
                label: '線材生產',
                icon: 'video-play',
                url: 'management/lineprocess',
                meta:{role: ['2']}
            },
            {
                path: '/prepayroll',
                name: 'prepayroll',
                label: '當月薪資試算',
                icon: 'user',
                url: 'prepayroll/prepayroll',
                meta:{role: ['2']}
            },
            {
                path: '/payroll',
                name: 'user',
                label: '薪資紀錄',
                icon: 'user',
                url: 'payroll/payroll',
                meta:{role: ['2']}
            },
        ],
        menuData:[],

    }
}