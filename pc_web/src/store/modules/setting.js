export default {
  namespaced: true,
  state: {
    loginComponent: 'Login'
  },
  mutations: {
    changeLogin(state, playload) {
      state.loginComponent = playload;
    }
  },
  actions: {
    changeLogin(context, val) {
      context.commit('changeLogin', val);
    }
  }
};
