<template>
  <div class="container">
    <div class="header">
      <Header title="房屋报修"></Header>
    </div>
    <div class="main">
      <div class="cell">
        <van-cell-group>
          <van-cell title="申请报修" is-link to="addRepair" />
          <van-cell title="报修记录" is-link to="repairDetail" />
        </van-cell-group>
      </div>
    </div>
  </div>
</template>

<script>
import Header from '@/components/utils/HeaderVue.vue';
export default {
  components: {
    Header
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
