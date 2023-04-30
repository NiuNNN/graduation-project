<template>
  <div class="sidebar-container">
    <el-menu
      default-active="100"
      class="el-menu-vertical-demo"
      background-color="#222653"
      text-color="#B3C0E7"
      active-text-color="#fff"
      router
      v-if="menus && menus.length > 0"
    >
      <side-bar-item v-for="(menu, idx) in menus" :menu="menu" :key="idx" />
      <el-menu-item index="/layout/user">
        <i class="iconfont">&#xe8e2;</i>
        <span slot="title">用 户 设 置</span>
      </el-menu-item>
    </el-menu>
  </div>
</template>

<script>
import SideBarItem from "./SideBarItem.vue";
export default {
  components: {
    SideBarItem,
  },
  data() {
    return {};
  },
  computed: {
    menus() {
      return this.$store.getters.menu;
    },
  },
};
</script>

<style lang="scss" scoped>
.sidebar-container {
  width: 100%;
  height: 100%;
  background-color: "#222653";
  .el-menu {
    width: 100%;
    height: 100%;
    .is-active {
      background: linear-gradient(45deg, #4f8aff 0%, #4b5eff 100%);
      background-blend-mode: normal;
    }
    .iconfont {
      margin-right: 5px !important;
    }
  }
}
</style>
