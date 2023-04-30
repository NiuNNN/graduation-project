<template>
  <div class="navbar-container">
    <div class="logo">
      <img src="@/assets/logo.png" alt="" /><span>PRHMS</span>
    </div>
    <div class="right">
      <el-avatar
        :size="40"
        :src="avatar"
        shape="square"
        v-imageerror="defaultImg"
      ></el-avatar>
      <p>{{ name }}</p>
      <el-dropdown @command="dropDownClick">
        <span class="el-dropdown-link">
          <i class="el-icon-arrow-down el-icon--right"></i>
        </span>
        <el-dropdown-menu slot="dropdown">
          <el-dropdown-item command="logout">注 销</el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
    </div>
  </div>
</template>

<script>
export default {
  name: "Navbar",
  methods: {
    dropDownClick(val) {
      switch (val) {
        case "logout":
          {
            this.$store.dispatch("user/logout");
          }
          break;
      }
    },
  },
  computed: {
    name() {
      return this.$store.state.user.user.name;
    },
    avatar() {
      return this.$store.getters.avatar;
    },
  },
  data() {
    return {
      defaultImg: require("@/assets/image/avatar_default.png"),
    };
  },
};
</script>

<style lang="scss" scoped>
@import "@/styles/navbar.scss";
.logo {
  display: flex;
  align-items: center;
  img {
    margin: 0 10px 0 15px;
    height: 48px;
    line-height: 60px;
  }
  span {
    height: 60px;
    line-height: 75px;
    font-weight: 700;
  }
}
</style>
