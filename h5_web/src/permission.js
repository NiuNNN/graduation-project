import router from "@/router";
import store from "@/store";
import nProgress from 'nprogress'; //引入进度条
import 'nprogress/nprogress.css'; //引入进度条样式
const whiteList = ["/login", "/404"]; //定义白名单

//前置守卫
router.beforeEach((to, from, next) => {
  // console.log('前置守卫');
  nProgress.start(); //开启进度条
  if (store.getters.Authorization && store.getters.username) {
    //有token
    if (to.path === "/login") {
      next("/"); //跳到主页
    }
    else{
        next()
    }
  } else {
    //没有token
    if (whiteList.indexOf(to.path) > -1) {
      //要去的地址在白名单里
      next();
    } else {
      next("/login");
    }
  }
  nProgress.done(); //解决手动切换地址时  进度条不关闭问题
});

//后置守卫
//后置守卫
router.afterEach(() => {
  nProgress.done(); //关闭进度条
});
