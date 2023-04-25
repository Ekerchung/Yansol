export default {
    namespaced:true,
    actions:{},
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