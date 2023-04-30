<template>
  <div class="container">
    <div class="header">
      <HeaderVue></HeaderVue>
    </div>
    <div class="main">
      <p>WELCOME TO THE SYSTEM!</p>
      <div class="form">
        <van-form @submit="onSubmit">
          <van-field
            v-model.trim="username"
            name="username"
            label="用户名"
            placeholder="用户名"
            autocomplete="off"
            :rules="[
              { required: true, message: '请填写用户名' },
              { pattern: /^\d{5,12}$/, message: '用户名有误' },
            ]"
          />
          <van-field
            v-model.trim="password"
            type="password"
            name="password"
            label="密码"
            placeholder="密码"
            autocomplete="off"
            :rules="[
              { required: true, message: '请填写密码' },
              { pattern: /^\w{5,12}$/, message: '密码有误' },
            ]"
          />
          <div style="margin: 16px">
            <van-button
              round
              block
              type="info"
              native-type="submit"
              :loading="loading"
              loading-text="登陆中..."
              >登录</van-button
            >
          </div>
        </van-form>
      </div>
    </div>
    <p class="footer">Design by NiuNiu</p>
  </div>
</template>

<script>
import HeaderVue from "@/layout/component/HeaderVue.vue";
export default {
  components: {
    HeaderVue,
  },
  data() {
    return {
      username: "",
      password: "",
      loading: false,
    };
  },
  methods: {
    async onSubmit(values) {
      try {
        console.log("submit", values);
        this.loading = true;
        await this.$store.dispatch("user/login", values);
        this.loading = false;
        this.$router.push("/");
      } catch (error) {
        console.log(error);
      }
    },
  },
};
</script>

<style lang="less" scoped>
.container {
  width: 100%;
  height: 100%;
  .header {
    width: 100%;
    height: 60px;
  }
  .main {
    margin-top: 80px;
    p {
      color: #232253;
      font-size: 33px;
      font-weight: 700;
      text-align: center;
      margin-bottom: 15px;
    }
  }
  .footer {
    color: #3a3f63;
    font-size: 8px;
    font-weight: 500;
    text-align: center;
  }
}
</style>
