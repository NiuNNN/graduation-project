import Vue from 'vue';
import VueRouter from 'vue-router';

import Layout from '@/layout/index.vue';
import Index from '@/views/index.vue';

Vue.use(VueRouter);

//静态路由
export const constantRoutes = [
  {
    path: '/',
    redirect: '/layout'
  },
  {
    path: '/login',
    component: () => import('@/views/login/index')
  },
  {
    path: '/layout',
    component: Layout,
    children: [
      { path: '/', component: Index },
      {
        path: 'user',
        component: () => import('@/views/user/index')
      }
    ]
  },
  {
    path: '/404',
    component: () => import('@/views/404.vue')
  },
  {
    path: '/200',
    component: () => import('@/views/200.vue')
  }
];

const originalPush = VueRouter.prototype.push;
VueRouter.prototype.push = function push(location) {
  return originalPush.call(this, location).catch(err => err);
};

const createRouter = () =>
  new VueRouter({
    mode: 'history', // require service support
    base: '/PRHMS-pc',
    scrollBehavior: () => ({ y: 0 }),
    routes: [...constantRoutes] // 动态路由和静态路由的临时合并
  });

const router = createRouter();

export function resetRouter() {
  const newRouter = createRouter();
  router.matcher = newRouter.matcher; // reset router
}

export default router;
