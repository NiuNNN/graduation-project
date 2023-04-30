<template>
  <div class="container">
    <div class="header">
      <Header title="账单详情"></Header>
    </div>
    <div class="main">
      <div class="cell">
        <van-cell title="日期" :value="house.date" />
        <van-cell title="房号" :value="house.houseName" />
        <van-cell title="订单编号" :value="house.outTradeNo" />
        <van-cell title="类型" :value="house.type" />
        <van-cell title="金额" :value="house.total" />
        <van-cell title="支付方式" :value="house.operation" />
        <van-cell title="付款时间" :value="house.payTime" />
      </div>
      <div v-if="mis.length > 0">
        <van-collapse v-model="activeNames">
          <van-collapse-item title="杂费信息" name="1">
            <div class="cell" v-for="(item, index) in mis" :key="index">
              <van-cell :title="item.baseName">
                <template #label>
                  <p>价格：{{ item.price }}</p>
                  <p>说明：{{ item.remark }}</p>
                </template>
              </van-cell>
            </div>
          </van-collapse-item>
        </van-collapse>
      </div>
      <div v-if="!show">
        <van-collapse v-model="activeMessage">
          <van-collapse-item title="水电信息" name="1">
            <div class="cell">
              <van-cell title="用水信息">
                <template #label>
                  <p>用水量(方): {{ message.numWater }}</p>
                  <p>水费(元): {{ message.costWater }}</p>
                  <p>上传时间:{{ message.uploadWater }}</p>
                </template>
              </van-cell>
            </div>
            <div class="cell">
              <van-cell title="用电信息">
                <template #label>
                  <p>用电量(度):{{ message.numElectric }}</p>
                  <p>说明：{{ message.costElectric }}</p>
                  <p>上传时间{{ message.uploadElectric }}</p>
                </template>
              </van-cell>
            </div>
          </van-collapse-item>
        </van-collapse>
      </div>
      <div style="margin: 16px" v-if="house.state == 0">
        <van-button
          round
          block
          type="info"
          native-type="submit"
          @click="onSubmit"
          >确 认 支 付</van-button
        >
      </div>
    </div>
  </div>
</template>

<script>
import Header from "@/components/utils/HeaderVue.vue";
import { getUserOrderDetail, pay } from "@/api/financial";
import { valueAllEmpty } from "@/utils/validate";
export default {
  components: {
    Header,
  },
  created() {
    this.getUserOrderDetail();
  },
  data() {
    return {
      house: {},
      mis: [],
      message: {},
      activeNames: [],
      activeMessage: [],
      show: false,
    };
  },
  methods: {
    async getUserOrderDetail() {
      try {
        this.$toast.loading({
          duration: 0,
          message: "加载中...",
          forbidClick: true,
        });
        const { data } = await getUserOrderDetail({
          orderId: this.$route.params.id,
        });
        this.house = data.house;
        if (data.mis) {
          this.mis = data.mis;
        }
        if (data.message) {
          this.message = data.message;
        }
        this.show = valueAllEmpty(this.message);
        // console.log(data);
        this.$toast.clear();
      } catch (error) {
        console.log(error);
      }
    },
    async onSubmit() {
      //   console.log(123);
      try {
        const { data } = await pay({
          ...this.house,
          subject: this.house.type,
          userId: this.$store.getters.userId,
        });
        console.log(data);
        const newWindow = window.open("", "_target");
        newWindow.document.write(data);
        newWindow.focus();
        this.$dialog
          .alert({
            message: "是否支付成功",
          })
          .then(() => {
            this.getUserOrderDetail();
          });
      } catch (error) {
        console.log(error);
      }
    },
  },
};
</script>

<style lang="less" scoped>
.container {
  .cell {
    margin-bottom: 5px;
  }
  .header {
    height: 60px;
  }
}
</style>
