import { getUserAPI, loginAPI, logoutAPI } from '@/api/user';
import { computedAvatar, getToken, getUser, logout, removeToken, removeUser, setTimeStamp, setToken, setUser } from '@/utils/auth';
import router from '@/router';
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
      try {
        const { data } = await loginAPI(val);
        console.log(data);
        context.commit('setUser', data.menu.user);
        setTimeStamp(); //设置时间戳
        context.commit('setToken', data.token);
      } catch (error) {
        console.log(error);
      }
    },
    // 获取用户资料
    async getUser(context) {
      try {
        const { data: user } = await getUserAPI({
          username: store.getters.username
        });
        context.commit('setUser', user);
        return user;
      } catch (error) {
        console.log(error);
      }
    },
    // 登出action
    async logout(context) {
      try {
        // await logoutAPI();
        logout();
        router.push('/login'); // 跳到登录
      } catch (error) {
        console.log(error);
      }
    }
  }
};
