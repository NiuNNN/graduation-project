<template>
  <div class="container">
    <div class="header">
      <Header title="报修记录"></Header>
    </div>
    <div class="main">
      <div class="cell">
        <van-cell-group>
          <van-swipe-cell v-for="(item, index) in repair" :key="index"
            ><van-cell :title="item.report" :value="item.state">
              <template #label>
                <p>描述信息: {{ item.message }}</p>
                <p>维修时间: {{ item.fix }}</p>
              </template>
            </van-cell>
            <template #right> <van-button square type="info" text="取消" :disabled="item.state !== `待处理`" @click="cancel(item)" /> </template
          ></van-swipe-cell>
        </van-cell-group>
        <van-empty description="暂无更多报修记录" />
      </div>
    </div>
  </div>
</template>

<script>
import { getRepair, deleteRepair } from '@/api/repair';
import Header from '@/components/utils/HeaderVue.vue';
export default {
  components: {
    Header
  },
  data() {
    return {
      repair: {}
    };
  },
  created() {
    this.getRepair();
  },
  methods: {
    cancel(val) {
      this.$dialog
        .confirm({
          title: '取消报修',
          message: '你确定要取消吗？'
        })
        .then(async () => {
          try {
            await deleteRepair(val);
            this.$toast.success('取消成功');
          } catch (error) {
            console.log(error);
          } finally {
            this.getRepair();
          }
        });
    },
    async getRepair() {
      try {
        const { data } = await getRepair({
          userId: this.$store.getters.userId
        });
        this.repair = data;
      } catch (error) {
        console.log(error);
      }
    }
  }
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
