import {userLogin} from "@/api";

export default {
    namespaced:true,
    actions:{
        fetchLogin(context,data) {
            // console.log(data);
            return userLogin(data)
                .then((response) => {
                    localStorage.setItem('token',response.data.token)
                    localStorage.setItem('role',response.data.role)
                    localStorage.setItem('username',response.data.username)
                    localStorage.setItem('lastLoginTime',response.data.lastLoginTime)
                    localStorage.setItem('employee',JSON.stringify(response.data.employee))
                    return response;
                })
                .catch((error) => {
                    throw error;
                })
        },
    },
    mutations:{
        handleMenu(state){
            // console.log('handleMenu被調用了');
            state.isCollapse = !state.isCollapse;
        },
        breadcrumb(state,val){
            // console.log('breadcrumb被調用了');
            let isInclude = state.breadcrumbData.filter(b => b.name === val.name);
            console.log(isInclude);
            if(isInclude.length === 0){
                state.breadcrumbData.push(val);
            }
        },
    },
    state:{
        isCollapse: false,
        breadcrumbData:[
            {
                path: '/',
                name: 'home',
                label: '首页',
                icon: 's-home',
                url: 'Home/Home'
            },
        ],

    }
}