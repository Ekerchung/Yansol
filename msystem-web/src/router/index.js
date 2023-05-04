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
import Main from '../views/Main'

//解決點擊兩次element ui的麵包屑報錯
const originalPush = VueRouter.prototype.push
VueRouter.prototype.push = function push(location) {
    return originalPush.call(this, location).catch(err => err)
}

const router = new VueRouter({
    routes:[
        {   path:'/',
            component:Main,
            redirect: '/home',
            //子路由
            children:[
                {path: 'home',component: Home},
                {path: 'linemanagement',component: LineManagement},
                {path: 'lineprocess',component: LineProcess},
                {path: 'linepurchase',component: LinePurchase},
                {path: 'linereturn',component: LineReturn},
                {path: 'lineshipping',component: LineShipping},
                {path: 'payroll',component: Payroll},
                {path: 'payrollmanagement',component: PayrollManagement},
                {path: 'prepayroll',component: PrePayroll},
                {path: 'workermanagement',component: WorkerManagement},
            ]
        },
        {
            path: '/login',
            component: Login
        },
    ]
})
// 全局前置路由守衛————初始化的時候被調用、每次路由切換之前被調用
router.beforeEach((to, from, next) => {
    console.log('前置路由守卫', to, from);
    let token = localStorage.getItem('token');
    if(!token && to.path !== '/login'){ //若沒有token，前往的頁面不能為login，不然會無限跳轉login頁面
        next({path : '/login'})
    } else if(token && to.path === '/login') { //有token時，若前往的頁面為login頁面，則跳到home頁面，避免重複登入
        next({path: '/home'})
    }else{
        next()
    }
})

export default router;