export default {
    namespaced:true,
    actions:{},
    mutations:{
        JIA(state,value){
            console.log('mutations中的JIA被调用了');
        },
    },
    state:{
        isCollapse: false,

    }
}