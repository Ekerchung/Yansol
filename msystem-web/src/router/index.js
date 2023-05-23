//導入vue router
import VueRouter from 'vue-router'
import Home from '../views/Home'
import UserHome from '../views/UserHome'
import LineManagement from '../views/LineManagement'
import LineProcess from '../views/LineProcess'
import UserLineProcess from '../views/UserLineProcess'
import LineProcessManagement from '../views/LineProcessManagement'
import LinePurchase from '../views/LinePurchase'
// import LineReturn from '../views/LineReturn'
import LineShipping from '../views/LineShipping'
import Login from '../views/Login'
// import Payroll from '../views/Payroll'
import PayrollManagement from '../views/PayrollManagement'
import PrePayroll from '../views/PrePayroll'
import WorkerManagement from '../views/WorkerManagement'
import AccountManagement from '../views/AccountManagement'
// import UserInfo from '../views/UserInfo'
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
            redirect: '/login',
            //子路由
            children:[
                {path: 'home',component: Home,meta:{role: ['1']}},
                {path: 'userhome',component: UserHome,meta:{role: ['2']}},
                {path: 'lineprocess',component: LineProcess,meta:{role: ['1']}},
                {path: 'userlineprocess',component: UserLineProcess,meta:{role: ['2']}},
                {path: 'lineProcessManagement',component: LineProcessManagement,meta:{role: ['1', '2']}},
                {path: 'prepayroll',component: PrePayroll,meta:{role:['2']}},
                // {path: 'payroll',component: Payroll,meta:{role:['2']}},
                {path: 'linepurchase',component: LinePurchase,meta:{role:['1']}},
                {path: 'lineshipping',component: LineShipping,meta:{role:['1']}},
                // {path: 'linereturn',component: LineReturn,meta:{role:['1']}},
                {path: 'linemanagement',component: LineManagement,meta:{role:['1']}},
                {path: 'payrollmanagement',component: PayrollManagement,meta:{role:['1']}},
                {path: 'workermanagement',component: WorkerManagement,meta:{role:['1']}},
                {path: 'accountManagement',component: AccountManagement,meta:{role:['1']}},
                // {path: 'userInfo',component: UserInfo,meta:{role:['1', '2']}},
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
    let token = localStorage.getItem('token');
    let role = localStorage.getItem('role');
        if (!token && to.path !== '/login') { //若沒有token，前往的頁面只能為login，不然會無限跳轉login頁面
            next({path: '/login'})
        } else if (token && to.path === '/login') { //有token時，若前往的頁面為login頁面，則跳到home頁面，避免重複登入
            next({path: `${role === '1'?'/home' : '/userhome' }`})
        } else if (token && !to.meta.role.includes(role) && to.path !== '/home') { //有token時，但沒有權限時，跳到home頁面，避免用戶使用url直接跳轉
            next({path: `${role === '1'?'/home' : '/userhome' }`})
        } else {
            next()  //正常訪問
        }
})

export default router;