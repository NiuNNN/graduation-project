import Vue from 'vue';
import Vuex from 'vuex';
import user from './modules/user';
import getters from './getters';
import setting from './modules/setting';
import permission from './modules/permission';

Vue.use(Vuex);

export default new Vuex.Store({
  modules: {
    user,
    setting,
    permission
  },
  getters
});
