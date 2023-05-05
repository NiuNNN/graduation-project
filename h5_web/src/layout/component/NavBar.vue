<template>
  <div class="container">
    <router-link to="/">
      <div :class="['link', { current: isCurrent == `home` }]" @click="change(`home`)">
        <van-icon name="apps-o" />
        <p>工 作 台</p>
      </div>
    </router-link>
    <router-link to="/user">
      <div :class="['link', { current: isCurrent == `user` }]" @click="change(`user`)">
        <van-icon name="user-o" />
        <p>我 的</p>
      </div>
    </router-link>
  </div>
</template>

<script>
export default {
  created() {
    this.goRoute();
  },
  methods: {
    change(val) {
      this.$store.dispatch('setting/changeCurrent', val);
    },
    goRoute() {
      if (this.isCurrent == `user`) {
        this.$router.push('/user');
      } else {
        this.$router.push('/');
      }
    }
  },
  computed: {
    isCurrent() {
      return this.$store.getters.isCurrent || 'home';
    }
  }
};
</script>

<style lang="less" scoped>
.container {
  width: 100%;
  height: 60px;
  display: flex;
  justify-content: space-evenly;
  align-items: center;
  .link {
    width: 187px;
    height: 60px;
    text-align: center;
    color: #3a3f63;
    background-color: #fff;
    .van-icon {
      margin-top: 13px;
      font-size: 20px;
    }
  }
  .current {
    color: #fff;
    background-color: #232253;
  }
}
</style>
