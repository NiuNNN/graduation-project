<template>
  <div class="container">
    <div class="header">
      <Header title="账单信息"></Header>
    </div>
    <div class="main">
      <div v-for="(item, index) in order" :key="index" class="cell">
        <van-cell
          :title="item.time"
          is-link
          :value="item.state"
          :to="`order/${item.orderId}`"
        >
          <template #label>
            <p>订单编号: {{ item.outTradeNo }}</p>
            <p>类型: {{ item.type }}</p>
            <p>金额: {{ item.total }}</p>
          </template>
        </van-cell>
      </div>
      <van-empty description="暂无更多账单信息" />
    </div>
  </div>
</template>

<script>
import Header from "@/components/utils/HeaderVue.vue";
import { getOrder } from "@/api/financial";
export default {
  components: {
    Header,
  },
  data() {
    return {
      order: [],
    };
  },
  created() {
    this.getOrder();
  },
  methods: {
    async getOrder() {
      try {
        const { data } = await getOrder({ userId: this.$store.getters.userId });
        this.order = data;
        // console.log(data);
      } catch (error) {
        console.log(error);
      }
    },
  },
};
</script>

<style lang="less" scoped>
.container {
  .header {
    height: 60px;
  }
  .cell {
    margin-bottom: 5px;
    .van-cell__label {
      width: 230px;
      flex: none;
    }
    .van-cell__value {
      width: 100px;
      flex: none;
    }
  }
}
</style>
