<template>
  <div class="container">
    <van-nav-bar :title="title" left-text="返回" left-arrow @click-left="goBack()">
      <template #right>
        <slot></slot>
      </template>
    </van-nav-bar>
  </div>
</template>

<script>
export default {
  props: {
    title: {
      type: String,
      default: '标题'
    }
  },
  methods: {
    goBack() {
      this.$router.go(-1);
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
