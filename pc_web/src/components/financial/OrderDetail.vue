<template>
  <div class="container">
    <div class="bg" style="padding-top: 5px">
      <div class="main">
        <el-descriptions title=" 订单详细信息" :column="3">
          <el-descriptions-item label="房号">{{ order.houseName }}</el-descriptions-item>
          <el-descriptions-item label="时间">{{ order.date }}</el-descriptions-item>
          <el-descriptions-item label="类型">{{ order.type }}</el-descriptions-item>
          <el-descriptions-item label="电费单价">{{ detail.basicElectric }}</el-descriptions-item>
          <el-descriptions-item label="用电量">{{ detail.numElectric }}</el-descriptions-item>
          <el-descriptions-item label="电费">{{ detail.costElectric }}</el-descriptions-item>
          <el-descriptions-item label="水费单价">{{ detail.basicWater }}</el-descriptions-item>
          <el-descriptions-item label="用水量">{{ detail.numWater }}</el-descriptions-item>
          <el-descriptions-item label="水费">{{ detail.costWater }}</el-descriptions-item>
          <el-descriptions-item label="总费用">{{ order.total }}</el-descriptions-item>
          <el-descriptions-item label="房费">{{ order.housePrice }}</el-descriptions-item>
          <el-descriptions-item label="杂费">{{ detail.mis }}</el-descriptions-item>
          <el-descriptions-item label="付款状态">{{ state }}</el-descriptions-item>
          <el-descriptions-item label="支付方式">{{ order.operation }}</el-descriptions-item>
          <el-descriptions-item label="支付时间">{{ order.payTime }}</el-descriptions-item>
          <el-descriptions-item label="收款人">{{ order.name }}</el-descriptions-item>
          <el-descriptions-item label="上传时间">{{ order.time }}</el-descriptions-item>
        </el-descriptions>
      </div>
    </div>
    <div class="bg">
      <div class="main">
        <el-descriptions title=" 杂费详情"> </el-descriptions>
        <el-table :data="tableData" stripe style="width: 100%" height="250" size="small">
          <el-table-column prop="baseName" label="收费名称"> </el-table-column>
          <el-table-column prop="price" label="价格(月/元)" width="120"> </el-table-column>
          <el-table-column prop="remark" label="收费详情" width="280"> </el-table-column>
          <el-table-column prop="time" label="录入时间"> </el-table-column>
          <template #empty>
            <el-empty :image-size="80"></el-empty>
          </template>
        </el-table>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    order: {
      type: Object,
      default: () => {
        return {};
      }
    },
    tableData: {
      type: Array,
      default: () => {
        return [];
      }
    },
    detail: {
      type: Object,
      default: () => {
        return {};
      }
    }
  },
  computed: {
    state() {
      return this.order.state == '1' ? '已缴费' : '待缴费';
    }
  }
};
</script>

<style lang="scss" scoped>
.container {
  .bg {
    width: 100%;
    padding: 25px 25px 0 25px;
    .main {
      padding: 10px;
      box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
    }
  }
}
</style>
