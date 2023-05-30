import { loginAPI, getUserAPI, logoutAPI } from '@/api/user';
import router from '@/router';
import { setToken, getToken, removeToken, setTimeStamp, setUser, getUser, removeUser, computedAvatar, logout } from '@/utils/auth';
import { setMenu } from '@/utils/menu';

import store from '..';

export default {
  namespaced: true,
  state: {
    Authorization: getToken() || '', //token请求头
    user: getUser() || {} //用户信息
  },
  mutations: {
    // 设置token的mutations
    setToken(state, token) {
      state.Authorization = token; // 只是设置了vuex中的数据
      // 需要将vuex中的数据同步到缓存
      setToken(token);
    },
    removeToken(state) {
      state.Authorization = null; // 设置vuex中的token为null
      removeToken(); // 同步删除缓存中的token
    },
    setUser(state, user) {
      user.avatar = computedAvatar(user.avatar);
      state.user = user; //设置用户信息
      setUser(user);
    },
    removeUser(state) {
      state.user = {}; // 重置为空对象
      removeUser();
    }
  },
  actions: {
    async login(context, val) {
      const { data } = await loginAPI(val);
      setTimeStamp(); //设置时间戳
      context.commit('setToken', data.token);
      context.commit('setUser', data.menu.user);
      //存储菜单信息并且加载路由信息
      setMenu(data.menu.routeList);
    },
    // 获取用户资料
    async getUser(context) {
      const { data: user } = await getUserAPI({
        username: store.getters.username
      });
      context.commit('setUser', user);
      return user;
    },
    // 登出action
    async logout(context) {
      // await logoutAPI();
      logout();
      router.push('/login'); // 跳到登录
    }
  }
};
