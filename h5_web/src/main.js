// import Vue from 'vue';
import App from './App.vue';
import router from './router';
import store from './store';

import * as directives from '@/directives';
import '@/permission';

// 导入vant组件
import '@/utils/vant.js';
import '@/style/index.less';

//导入eCharts
// import * as echarts from 'echarts';
Vue.prototype.$echarts = echarts;

Vue.config.productionTip = false;

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

/**
 * 自定义指令
 */
Object.keys(directives).forEach(key => {
  Vue.directive(key, directives[key]);
});

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app');
