<template>
  <div class="container">
    <div class="header">
      <Header title="修 改 密 码"></Header>
    </div>
    <div class="main">
      <van-form @submit="onSubmit">
        <van-field
          v-model="password1"
          type="password"
          name="password1"
          label="新密码"
          placeholder="新密码需大于5位数字且小于12位"
          :rules="[
            { required: true, message: '请输入新密码' },
            { pattern: /^\w{5,12}$/, message: '密码不符合要求' }
          ]"
        />
        <van-field
          v-model="password2"
          type="password"
          name="password2"
          label="确认密码"
          placeholder="请再次输入新密码"
          :rules="[
            { required: true, message: '请再次输入密码' },
            { validator, message: '密码不一致' }
          ]"
        />
        <div style="margin: 16px">
          <van-button round block type="info" native-type="submit">确 认 修 改</van-button>
        </div>
      </van-form>
    </div>
  </div>
</template>

<script>
import Header from '@/components/utils/HeaderVue.vue';
import { updateUser } from '@/api/user';
export default {
  components: {
    Header
  },
  data() {
    return {
      password1: '',
      password2: ''
    };
  },
  methods: {
    async onSubmit(values) {
      //   console.log("submit", values);
      try {
        this.$toast.loading();
        await updateUser({
          username: this.$store.getters.username,
          password: this.password2
        });
        this.$toast.success('密码修改成功');
      } catch (error) {
        console.log(error);
      } finally {
        this.password1 = '';
        this.password2 = '';
      }
    },
    validator(val) {
      return val == this.password1;
    }
  }
};
</script>

<style lang="less" scoped>
.container {
  .header {
    height: 60px;
  }
}
</style>
