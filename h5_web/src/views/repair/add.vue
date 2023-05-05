<template>
  <div class="container">
    <div class="header">
      <Header title="申请报修"></Header>
    </div>
    <div class="main">
      <van-form @submit="onSubmit">
        <van-cell-group> <van-field required v-model="message" :rules="[{ required: true }]" rows="2" autosize label="报修描述" type="textarea" maxlength="50" placeholder="请描述报修信息" show-word-limit /></van-cell-group>
        <div style="margin: 16px">
          <van-button round block type="info" native-type="submit">确 认 申 请</van-button>
        </div>
      </van-form>
    </div>
  </div>
</template>

<script>
import { insertRepair } from '@/api/repair';
import { getRentIdByUserId } from '@/api/rent';
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
          title: '申请报修',
          message: '你确定要申请吗？'
        })
        .then(async () => {
          try {
            const { data } = await getRentIdByUserId({
              userId: this.$store.getters.userId
            });
            // console.log(res);
            await insertRepair({ rentId: data.rentId, message: this.message });
            this.$toast.success('申请成功');
            this.message = '';
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
