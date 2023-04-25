export default {
    namespaced:true,
    actions:{},
    mutations:{
        handleMenu(state){
            console.log('handleMenu被調用了');
            state.isCollapse = !state.isCollapse;
        },
    },
    state:{
        isCollapse: false,

    }
}