<template>
  <div class="container">
    <div class="header">
      <Header title="问题反馈"></Header>
    </div>
    <div class="main">
      <van-form @submit="onSubmit">
        <van-cell-group> <van-field required v-model="message" :rules="[{ required: true }]" rows="2" autosize label="问题反馈" type="textarea" maxlength="50" placeholder="请填写反馈信息" show-word-limit /></van-cell-group>
        <div style="margin: 16px">
          <van-button round block type="info" native-type="submit">确 认</van-button>
        </div>
      </van-form>
    </div>
  </div>
</template>

<script>
import { insertFeedBack } from '@/api/feedback';
import Header from '@/components/utils/HeaderVue.vue';
export default {
  components: {
    Header
  },
  data() {
    return {
      message: ''
    };
  },
  methods: {
    onSubmit() {
      this.$dialog
        .confirm({
          title: '反馈问题',
          message: '你确定要反馈问题吗？'
        })
        .then(async () => {
          try {
            await insertFeedBack({
              userId: this.$store.getters.userId,
              message: this.message
            });
            this.message = '';
            this.$toast.success('感谢您的反馈意见');
          } catch (error) {
            console.log(error);
          }
        });
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
