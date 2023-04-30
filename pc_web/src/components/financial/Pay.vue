<template>
  <div class="container">
    <div v-if="active == 0" v-loading="loading">
      <p class="header">订单编号：{{ order.outTradeNo }}</p>
      <div class="pay">{{ payTitle }}</div>
      <p class="header">请选择支付方式：</p>
      <div style="margin-top: 30px; padding-left: 15px">
        <el-button @click="alipay"
          ><i
            style="margin-right: 5px; color: #1296db; font-size: 20px"
            class="iconfont"
            >&#xe634;</i
          >支付宝支付</el-button
        >
        <el-button @click="cashpay"
          ><i
            style="margin-right: 5px; color: #1296db; font-size: 20px"
            class="iconfont"
            >&#xea5b;</i
          >现金支付</el-button
        >
      </div>
      <p style="color: #303133; font-size: 14px; padding: 25px 15px 0">
        *支付后请点击此<el-button type="text" @click="judgePay">刷新</el-button>
      </p>
    </div>
    <div v-if="active == 1">
      <el-result
        icon="success"
        title="支付成功"
        :subTitle="`缴费成功，${count}秒后关闭页面`"
      >
        <template slot="extra">
          <el-button type="primary" size="medium" @click="close"
            >确定</el-button
          >
        </template>
      </el-result>
    </div>
  </div>
</template>

<script>
import { payByCash, payByAlipay, judgePay } from "@/api/order";
export default {
  data() {
    return {
      active: 0,
      count: "", //倒计时时间
      timer: "",
      loading: false,
    };
  },
  props: {
    order: {
      type: Object,
      default: () => {
        return {};
      },
    },
  },
  computed: {
    payTitle() {
      console.log(this.order);
      return `待支付：￥${this.order.total}`;
    },
  },
  methods: {
    //支付宝支付
    async alipay() {
      this.judgePay();
      try {
        // 首先先跳转到新的页面 然后等待支付结果
        const { data } = await payByAlipay({
          ...this.order,
          subject: "押金",
          userId: this.$store.getters.userId,
        });
        // console.log(data);
        const newWindow = window.open("", "_target");
        newWindow.document.write(data);
        newWindow.focus();
      } catch (error) {
        console.log(error);
      }
    },
    //现金支付
    cashpay() {
      this.judgePay();
      this.$confirm("确定收款成功, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      }).then(async () => {
        await payByCash({
          ...this.order,
          userId: this.$store.getters.userId,
        });
        this.active++;
      });
    },
    //判断是否支付
    async judgePay() {
      this.loading = true;
      try {
        const { data } = await judgePay(this.order);
        if (data > 0) {
          this.$message.success("支付成功");
          this.payTitle = `已支付：￥${this.order.total}`;
          this.active++;
        }
      } catch (error) {
        console.log(error);
      } finally {
        this.loading = false;
      }
    },
    //倒计时
    countDown() {
      const TIME_COUNT = 3;
      if (!this.timer) {
        this.count = TIME_COUNT;
        this.timer = setInterval(() => {
          if (this.count > 1 && this.count <= TIME_COUNT) {
            //限制倒计时区间
            this.count--;
          } else {
            clearInterval(this.timer); //删除定时器
            this.timer = null;
            this.close();
          }
        }, 1000);
      }
    },
    //关闭
    close() {
      this.$emit(`closePayDrawer`);
    },
  },
  watch: {
    active(newVal, oldVal) {
      if (newVal == 1) {
        this.countDown();
      }
    },
  },
};
</script>

<style lang="scss" scoped>
.container {
  width: 90%;
  margin: 0 auto;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
  padding: 15px;
  height: 400px;
  .pay {
    width: 100%;
    height: 150px;
    font-size: 50px;
    line-height: 150px;
    text-align: center;
  }
  .header {
    color: #303133;
    font-size: 18px;
    border-left: 5px solid #3a3f63;
    padding-left: 5px;
  }
}
</style>
