<template>
  <div class="container">
    <div class="notice">
      <van-notice-bar scrollable left-icon="volume-o" text="提前祝贺我答辩成功!!!" />
    </div>
    <div class="chart">
      <chart></chart>
    </div>
    <div class="grad">
      <van-grid :column-num="2">
        <van-grid-item icon="balance-list-o" text="账单" to="financial" />
        <van-grid-item icon="bullhorn-o" text="公告" to="report" />
        <van-grid-item icon="other-pay" text="杂费" to="miscellanous" />
        <van-grid-item icon="orders-o" text="合同" to="contract" />
        <van-grid-item icon="setting-o" text="报修" to="repair" />
        <van-grid-item icon="wap-home-o" text="退房" to="checkout" />
        <van-grid-item icon="service-o" text="问题反馈" to="feedback" />
        <van-grid-item icon="ellipsis" />
      </van-grid>
    </div>
    <p class="footer">Design by NiuNiu</p>
  </div>
</template>

<script>
import chart from '@/components/utils/chart.vue';
export default {
  components: {
    chart
  }
};
</script>

<style lang="less" scoped>
.container {
  padding-top: 5px;
  height: 775px;
  .notice {
    height: 30px;
    .van-notice-bar {
      height: 30px;
      font-size: 10px;
    }
  }
  .chart {
    width: 100%;
    height: 300px;
    background-color: #fff;
    margin: 5px 0;
  }
  .gard {
    width: 100%;
    height: 500px;
  }
  .footer {
    margin-top: 5px;
    color: #3a3f63;
    font-size: 8px;
    font-weight: 500;
    text-align: center;
  }
}
</style>
