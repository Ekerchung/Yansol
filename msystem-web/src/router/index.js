//導入vue router
import VueRouter from 'vue-router'
import Home from '../views/Home'
import LineManagement from '../views/LineManagement'
import LineProcess from '../views/LineProcess'
import LinePurchase from '../views/LinePurchase'
import LineReturn from '../views/LineReturn'
import LineShipping from '../views/LineShipping'
import Login from '../views/Login'
import Payroll from '../views/Payroll'
import PayrollManagement from '../views/PayrollManagement'
import PrePayroll from '../views/PrePayroll'
import WorkerManagement from '../views/WorkerManagement'

//解決點擊兩次element ui的麵包屑報錯
const originalPush = VueRouter.prototype.push
VueRouter.prototype.push = function push(location) {
    return originalPush.call(this, location).catch(err => err)
}

export default new VueRouter({
        routes:[
        {
            path: '/',
            component: Home
        },
        {
            path: '/linemanagement',
            component: LineManagement
        },
        {
            path: '/lineprocess',
            component: LineProcess
        },
        {
            path: '/linepurchase',
            component: LinePurchase
        },
        {
            path: '/linereturn',
            component: LineReturn
        },
        {
            path: '/lineshipping',
            component: LineShipping
        },
        {
            path: '/login',
            component: Login
        },
        {
            path: '/payroll',
            component: Payroll
        },
        {
            path: '/payrollmanagement',
            component: PayrollManagement
        },
        {
            path: '/prepayroll',
            component: PrePayroll
        },
        {
            path: '/workermanagement',
            component: WorkerManagement
        },

    ]

})
