import { getIsCurrent, removeIsCurrent, setIsCurrent } from "@/utils/setting";

export default {
  namespaced: true,
  state: {
    isCurrent: getIsCurrent() || "home", //保存当前的路由样式
  },
  mutations: {
    setIsCurrent(state, isCurrent) {
      state.isCurrent = isCurrent;
      setIsCurrent(isCurrent);
    },
    removeIsCurrent(state) {
      state.isCurrent = "";
      removeIsCurrent();
    },
  },
  actions: {
    changeCurrent(context, val) {
      context.commit("setIsCurrent", val);
    },
  },
};
