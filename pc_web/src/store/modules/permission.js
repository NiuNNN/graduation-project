import { getMenu, removeMenu } from '@/utils/menu';
import { getMenuAPI } from '@/api/user';
import store from '..';

export default {
  namespaced: true,
  state: {
    menu: getMenu() || {},
    asyncRoutes: []
  },
  mutations: {
    //设置菜单
    setMenu(state, menu) {
      state.menu = menu;
    },
    //移除菜单
    removeMenu(state) {
      removeMenu();
      state.menu = {};
      state.asyncRoutes = []
    },
    //设置路由
    setAsyncRoutes(state, asyncRoutes) {
      state.asyncRoutes = asyncRoutes;
    }
  },
  actions: {
    setMenu(context, menu) {
      context.commit('setMenu', menu);
    },
    setAsyncRoutes(context, asyncRoutes) {
      context.commit('setAsyncRoutes', asyncRoutes);
    },
    async getMenu(context) {
      const { data } = await getMenuAPI({ username: store.getters.username });
      context.commit('setMenu', data.routeVOList);
      return data.routeVOList;
    }
  }
};
