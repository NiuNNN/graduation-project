// import Vue from 'vue';
import App from './App.vue';
import router from './router';
import store from './store';

import '@/utils/element-ui';
import '@/icons/iconfont.css';
import '@/styles/index.scss'; // global css

import * as directives from '@/directives';
import '@/permission';
// import * as echarts from 'echarts'; //引入echart

Vue.prototype.$echarts = echarts;

Vue.config.productionTip = false;

/**
 * 自定义指令
 */
Object.keys(directives).forEach(key => {
  Vue.directive(key, directives[key]);
});

/**
 * 禁止Promise reject抛出异常
 */
window.addEventListener('unhandledrejection', function browserRejectionHandler(event) {
  event && event.preventDefault();
});
/**
 * 禁止控制台输出
 */
console.log = () => {};

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app');
