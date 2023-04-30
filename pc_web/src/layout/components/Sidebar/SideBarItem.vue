<template>
  <div id="side-bar-item">
    <template v-if="menu.leaf">
      <el-submenu :index="menu.path">
        <template slot="title">
          <i class="iconfont" v-html="menu.meta.icon"></i>
          <span slot="title">{{ menu.meta.title }}</span>
        </template>
        <template v-if="menu.children && menu.children.length > 0">
          <side-bar-item
            v-for="(innerMenu, innerIdx) in menu.children"
            :menu="innerMenu"
            :key="innerIdx"
          />
        </template>
      </el-submenu>
    </template>
    <template v-else>
      <!-- 按钮权限 通过route进行传递 -->
      <el-menu-item
        :index="menu.path"
        :route="{ name: menu.name, params: menu.params }"
      >
        <i class="iconfont" v-html="menu.meta.icon"></i>
        <span slot="title">{{ menu.meta.title }}</span>
      </el-menu-item>
    </template>
  </div>
</template>

<script>
import SideBarItem from "./SideBarItem.vue";
export default {
  name: "side-bar-item",
  components: {
    SideBarItem,
  },
  props: ["menu"],
};
</script>

<style lang="scss" scoped>
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
</style>
