<template>
  <div class="container">
    <div class="header">
      <header-vue></header-vue>
    </div>
    <div class="main">
      <keep-alive><router-view></router-view></keep-alive>
    </div>
    <div class="footer">
      <nav-bar></nav-bar>
    </div>
  </div>
</template>

<script>
import HeaderVue from "@/layout/component/HeaderVue.vue";
import NavBar from "./component/NavBar.vue";
export default {
  components: {
    HeaderVue,
    NavBar,
  },
};
</script>

<style lang="less" scoped>
.container {
  width: 100%;
  height: 100%;
  position: relative;
  .header {
    width: 100%;
    height: 60px;
    position: fixed;
    background-color: #fff;
    z-index: 999;
    border-bottom: 1px solid #f5f5fa;
  }
  .main {
    position: absolute;
    margin-top: 60px;
    width: 100%;
    margin-bottom: 60px;
  }
  .footer {
    position: fixed;
    z-index: 999;
    bottom: 0;
    left: 0;
    width: 100%;
    height: 60px;
    background-color: #fff;
    border-top: 1px solid #f5f5fa;
  }
}
</style>
