<template>
  <div class="container">
    <div class="header">
      <Header title="杂费信息"></Header>
    </div>
    <div class="main">
      <div class="cell">
        <p class="nav">已添加杂费:</p>
        <van-swipe-cell v-for="(item, index) in hasMis" :key="index">
          <van-cell :title="item.baseName">
            <template #label>
              <p>价格：{{ item.price }}</p>
              <p>说明：{{ item.remark }}</p>
            </template>
          </van-cell>
          <template #right>
            <van-button
              square
              text="删除"
              type="danger"
              class="delete-button"
              @click="remove(item.miscellaneousId)"
            />
          </template>
        </van-swipe-cell>
      </div>
      <div class="cell">
        <p class="nav">未添加杂费:</p>
        <van-swipe-cell v-for="(item, index) in noMis" :key="index">
          <van-cell :title="item.baseName">
            <template #label>
              <p>价格：{{ item.price }}</p>
              <p>说明：{{ item.remark }}</p>
            </template>
          </van-cell>
          <template #right>
            <van-button
              square
              text="添加"
              type="primary"
              class="primary-button"
              @click="add(item.baseId)"
            />
          </template>
        </van-swipe-cell>
      </div>
    </div>
  </div>
</template>

<script>
import Header from "@/components/utils/HeaderVue.vue";
import {
  getBasicByUserId,
  getNoBasic,
  insertMiscellaneous,
  changeMiscellaneousState,
} from "@/api/basic";
import { getRentIdByUserId } from "@/api/rent";
export default {
  components: {
    Header,
  },
  data() {
    return {
      hasMis: {},
      rent: {},
      noMis: {},
    };
  },
  created() {
    this.reset();
  },
  methods: {
    //获取已添加杂费
    async getBasicByUserId() {
      try {
        const { data } = await getBasicByUserId({
          userId: this.$store.getters.userId,
        });
        this.hasMis = data;
        // console.log(data);
      } catch (error) {
        console.log(error);
      }
    },
    //获取没有添加的杂费
    async getNoBasic() {
      try {
        const res = await getRentIdByUserId({
          userId: this.$store.getters.userId,
        });
        this.rent = res.data;
        // console.log(this.rent);
        const { data } = await getNoBasic({ rentId: this.rent.rentId });
        this.noMis = data;
        // console.log(data);
      } catch (error) {
        console.log(error);
      }
    },
    //添加
    add(val) {
      // console.log(val);
      this.$dialog
        .confirm({
          title: "添加杂费",
          message: "添加后立即生效，并参与本月结算",
        })
        .then(async () => {
          try {
            await insertMiscellaneous({
              rentId: this.rent.rentId,
              baseId: val,
            });
            this.$toast.success("添加成功");
          } catch (error) {
            console.log(error);
          } finally {
            this.reset();
          }
        });
    },
    //删除
    remove(val) {
      // console.log(val);
      this.$dialog
        .confirm({
          title: "删除杂费",
          message: "删除后该月可使用，下月将使用将重新计算",
        })
        .then(async () => {
          try {
            await changeMiscellaneousState({
              state: 2,
              miscellaneousId: val,
            });
            this.$toast.success("删除成功");
          } catch (error) {
            console.log(error);
          } finally {
            this.reset();
          }
        });
    },
    async reset() {
      this.$toast.loading({
        duration: 0,
        message: "加载中...",
        forbidClick: true,
      });
      await this.getBasicByUserId();
      await this.getNoBasic();
      this.$toast.clear();
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
    margin-bottom: 15px;
    .van-button {
      height: 85px;
    }
    .nav {
      margin: 5px;
      font-size: 14px;
    }
  }
}
</style>
