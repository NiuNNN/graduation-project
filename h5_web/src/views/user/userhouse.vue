<template>
  <div class="container">
    <div class="header">
      <Header title="住 房 信 息"></Header>
    </div>
    <div class="main">
      <div class="bg">
        <van-cell title="房号" :value="house.houseName" />
        <van-cell title="录入时间" :value="house.loginTime" />
      </div>
      <div class="bg">
        <van-cell title="房型" :value="house.styleName" />
        <van-cell title="面积" :value="house.area" />
        <van-cell title="阳台" :value="house.balcony" />
        <van-cell title="租金" :value="house.price" />
      </div>
    </div>
  </div>
</template>

<script>
import { getHouseDetailByUserId } from "@/api/house";
import Header from "@/components/utils/HeaderVue.vue";
export default {
  components: {
    Header,
  },
  data() {
    return {
      house: {},
    };
  },
  created() {
    this.getHouseDetail();
  },
  methods: {
    async getHouseDetail() {
      try {
        this.$toast.loading({
          duration: 0,
          message: "加载中...",
          forbidClick: true,
        });
        const { data } = await getHouseDetailByUserId({
          userId: this.$store.getters.userId,
        });
        this.house = data;
        this.$toast.clear();
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
    margin-bottom: 15px;
  }
  .main {
    .bg {
      margin-bottom: 15px;
    }
  }
}
</style>
