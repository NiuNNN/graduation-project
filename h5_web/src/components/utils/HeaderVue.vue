<template>
  <div class="container">
    <van-nav-bar :title="title" left-text="返回" left-arrow @click-left="goBack()"> </van-nav-bar>
  </div>
</template>

<script>
export default {
  props: {
    title: {
      type: String,
      default: '标题'
    },
    path: {
      type: String,
      default: '-1'
    }
  },
  methods: {
    goBack() {
      if (this.path == '/') {
        this.$router.push('/');
      } else {
        this.$router.go(this.path);
      }
    }
  }
};
</script>

<style lang="less" scoped>
.container {
  height: 60px;
  /deep/.van-nav-bar__content {
    height: 60px;
  }
}
</style>
