//权限拦截在路由跳转 导航守卫
import store from "@/store";
import router from "@/router";
import nProgress from "nprogress"; //引入进度条
import "nprogress/nprogress.css"; //引入进度条样式
import { stitch } from "./utils/menu";

const whiteList = ["/login", "/404", "/200"]; //定义白名单

//前置守卫
router.beforeEach(async (to, from, next) => {
  // console.log('前置守卫');
  nProgress.start(); //开启进度条
  if (store.getters.Authorization && store.getters.username) {
    //有token
    if (to.path === "/login") {
      next("/"); //跳到主页
    } else {
      // console.log('判断菜单');
      // console.log(!store.state.permission.asyncRoutes.length > 0);
      if (!store.state.permission.asyncRoutes.length > 0) {
        // console.log('进来if');
        //获取用户资料 菜单信息
        const menu = await store.dispatch("permission/getMenu");
        const asyncRoutes = stitch(menu);
        for (let item of asyncRoutes) {
          router.addRoute(item);
        }
        router.addRoute({ path: "*", redirect: "/404" });
        store.dispatch("permission/setAsyncRoutes", asyncRoutes);
        next(to.path);
      } else {
        // console.log('进来else');
        next();
      }
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
router.afterEach(() => {
  nProgress.done(); //关闭进度条
});
