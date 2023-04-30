import Vue from "vue";
import App from "./App.vue";
import router from "./router";
import store from "./store";

import * as directives from "@/directives";
import "@/permission";

// 导入vant组件
import "@/utils/vant.js";
import "@/style/index.less";

Vue.config.productionTip = false;

/**
 * 自定义指令
 */
Object.keys(directives).forEach((key) => {
  Vue.directive(key, directives[key]);
});

new Vue({
  router,
  store,
  render: (h) => h(App),
}).$mount("#app");
