import Vue from 'vue'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import App from './App.vue';
import VueRouter from 'vue-router';
import router from './router';
import store from './store';
import moment from 'moment-timezone';

// 設置Moment.js庫為UTC時區
moment.tz.setDefault('Asia/Taipei');

Vue.use(ElementUI);
Vue.use(VueRouter);

// 設置全局moment對象
Vue.prototype.$moment = moment;
Vue.config.productionTip = false;

new Vue({
  render: h => h(App),
  router: router,
  store,
  created() {
    store.commit('data/setMenuData',localStorage.getItem('role'))
  }
}).$mount('#app')
