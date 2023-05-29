<template>
  <div class="container">
    <div class="left">
      <div class="top">
        <el-calendar v-model="value"> </el-calendar>
      </div>
      <div class="bottom">
        <el-row :gutter="20">
          <el-col :span="6">
            <div>
              <el-statistic group-separator="," :value="houseNum" title="住房数量"></el-statistic>
            </div>
          </el-col>
          <el-col :span="6">
            <div>
              <el-statistic group-separator="," :value="userNum" title="住户数量"></el-statistic>
            </div>
          </el-col>
          <el-col :span="6">
            <div>
              <el-statistic group-separator="," :value="outNum" title="申请退房"></el-statistic>
            </div>
          </el-col>
          <el-col :span="6">
            <div>
              <el-statistic group-separator="," :value="repairNum" title="申请维修"></el-statistic>
            </div>
          </el-col>
        </el-row>
      </div>
    </div>
    <div class="right"></div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      value: new Date(),
      houseNum: 0,
      userNum: 0,
      outNum: 0,
      repairNum: 0
    };
  },
  methods: {
    getNum() {}
  }
};
</script>

<style lang="scss" scoped>
.container {
  width: 100%;
  height: 100%;
  background-color: #fff;
  border-radius: 8px;
  display: flex;
  .left {
    width: 45%;
    padding: 25px;
    ::v-deep .top {
      .el-calendar-table .el-calendar-day {
        width: 60px;
        height: 40px;
      }
    }
    .bottom {
      padding-top: 10px;
      border-top: 1px solid #ebeef5;
    }
  }
}
</style>
