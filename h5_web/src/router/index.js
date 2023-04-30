import Vue from "vue";
import VueRouter from "vue-router";

import Layout from "@/layout/index.vue";

Vue.use(VueRouter);

//静态路由
export const constantRoutes = [
  {
    path: "/",
    redirect: "/layout",
  },
  {
    path: "/login",
    component: () => import("@/views/login/index"),
  },
  {
    path: "/layout",
    component: Layout,
    children: [
      {
        path: "/",
        component: () => import("@/views/dashbord/index"),
      },
      {
        path: "/user",
        component: () => import("@/views/user/index"),
      },
    ],
  },
  {
    path: "/userprofile/:id",
    component: () => import("@/views/user/userprofile"),
  },
  {
    path: "/userhouse/:id",
    component: () => import("@/views/user/userhouse"),
  },
  {
    path: "/report",
    component: () => import("@/views/report/index"),
  },
  {
    path: "/detail/:id",
    component: () => import("@/views/report/detail"),
  },
  {
    path: "/checkout",
    component: () => import("@/views/checkout/index"),
  },
  {
    path: "/password",
    component: () => import("@/views/user/password"),
  },
  {
    path: "/financial",
    component: () => import("@/views/financial/index"),
  },
  {
    path: "/order/:id",
    component: () => import("@/views/financial/detail"),
  },
  {
    path: "/miscellanous",
    component: () => import("@/views/miscellanous/index"),
  },
  {
    path: "/contract",
    component: () => import("@/views/contract/index"),
  },
  {
    path: "/repair",
    component: () => import("@/views/repair/index"),
  },
  {
    path: "/addRepair",
    component: () => import("@/views/repair/add"),
  },
  {
    path: "/repairDetail",
    component: () => import("@/views/repair/detail"),
  },
  {
    path: "/feedback",
    component: () => import("@/views/feedback/index"),
  },
  {
    path: "/404",
    component: () => import("@/views/404.vue"),
  },
  {
    path: "/200",
    component: () => import("@/views/200.vue"),
  },
  { path: "*", redirect: "/404" },
];

const originalPush = VueRouter.prototype.push;
VueRouter.prototype.push = function push(location) {
  return originalPush.call(this, location).catch((err) => err);
};

const createRouter = () =>
  new VueRouter({
    mode: "history", // require service support
    base: "/PRHMS/",
    scrollBehavior: () => ({ y: 0 }),
    routes: [...constantRoutes], // 动态路由和静态路由的临时合并
  });

const router = createRouter();

export function resetRouter() {
  const newRouter = createRouter();
  router.matcher = newRouter.matcher; // reset router
}

export default router;
