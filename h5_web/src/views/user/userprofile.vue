<template>
  <div class="container">
    <div class="header">
      <Header title="个 人 资 料"></Header>
    </div>
    <div class="main">
      <div class="bg">
        <van-cell>
          <template #title>
            <div class="avatar">头像</div>
          </template>
          <template #right-icon>
            <van-image width="55" height="55" radius="5px" :src="avatar" />
          </template>
        </van-cell>
        <van-cell title="姓名" :value="user.name" />
        <van-cell title="性别" :value="user.sex" />
      </div>
      <div class="bg">
        <van-cell title="手机" :value="user.phone" />
        <van-cell title="民族" :value="user.nation" />
        <van-cell title="生日" :value="user.birthday" />
      </div>
      <div class="bg">
        <van-cell title="身份证" :value="user.idcard" />
        <van-cell title="签发机关" :value="user.authority" />
        <van-cell title="签发日期" :value="`${user.sign}`" />
        <van-cell title="失效日期" :value="`${user.lose}`" />
        <van-cell title="录入时间" :value="user.addtime" />
      </div>
    </div>
  </div>
</template>

<script>
import Header from "@/components/utils/HeaderVue.vue";
export default {
  components: {
    Header,
  },
  created() {
    console.log(this.$route);
  },
  computed: {
    avatar() {
      return this.$store.getters.avatar;
    },
    user() {
      return this.$store.getters.user;
    },
  },
};
</script>

<style lang="less" scoped>
.container {
  .header {
    height: 60px;
    margin-bottom: 15px;
  }
  .main {
    .avatar {
      height: 55px;
      line-height: 55px;
    }
    .arrow {
      margin-left: 3px;
      font-size: 16px;
      color: #969799;
      height: 55px;
      line-height: 55px;
    }
    .bg {
      margin-bottom: 15px;
    }
  }
}
</style>
