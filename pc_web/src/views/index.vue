<template>
  <div class="container">
    <div class="left">
      <div class="statistic">
        <div class="box">
          <el-statistic group-separator="," :value="num.houseNum" title="住房数量"></el-statistic>
        </div>
        <div class="box">
          <el-statistic group-separator="," :value="num.userNum" title="住户数量"></el-statistic>
        </div>
        <div class="box">
          <el-statistic group-separator="," :value="num.outNum" title="退房数量"></el-statistic>
        </div>
        <div class="box">
          <el-statistic group-separator="," :value="num.repairNum" title="维修数量"></el-statistic>
        </div>
        <div class="box">
          <el-statistic group-separator="," :value="num.repairNum" title="今天访问量"></el-statistic>
        </div>
      </div>
      <div class="calendar">
        <el-calendar v-model="date"> </el-calendar>
      </div>
    </div>
    <div class="right">
      <div class="top">
        <el-table :data="tableData" style="width: 100%" height="300" :default-sort="{ prop: 'date', order: 'descending' }">
          <el-table-column prop="time" label="日期" width="120" sortable> </el-table-column>
          <el-table-column prop="message" label="住户反馈"> </el-table-column>
          <el-table-column fixed="right" label="操作" width="80">
            <template slot-scope="scope">
              <el-button @click.native.prevent="deleteRow(scope.row)" type="text" size="small" :disabled="disabled"> 移除 </el-button>
            </template>
          </el-table-column>
          <template #empty>
            <el-empty :image-size="80"></el-empty>
          </template>
        </el-table>
      </div>
      <div class="bottom">
        <pie-chart :num="num"></pie-chart>
      </div>
    </div>
  </div>
</template>

<script>
import { getNum, getFeedBack, checkFeedBack } from '@/api/feedback';
import PieChart from '@/components/echart/PieChart.vue';
export default {
  components: {
    PieChart
  },
  data() {
    return {
      date: new Date(),
      num: {
        houseNum: 0,
        userNum: 0,
        outNum: 0,
        repairNum: 0,
        noneNum: 0
      },
      tableData: []
    };
  },
  async created() {
    await this.getNum();
    await this.getFeedBack();
  },
  methods: {
    async getNum() {
      try {
        const { data } = await getNum();
        this.num.houseNum = data.houseNum;
        this.num.userNum = data.userNum;
        this.num.outNum = data.outNum;
        this.num.repairNum = data.repairNum;
        this.num.noneNum = this.num.houseNum - this.num.userNum - this.num.outNum;
      } catch (error) {
        console.log(error);
      }
    },
    async getFeedBack() {
      try {
        const { data } = await getFeedBack();
        this.tableData = data;
      } catch (error) {
        console.log(error);
      }
    },
    async deleteRow(row) {
      try {
        await checkFeedBack(row);
        this.$message.success('该条用户反馈移除成功...');
      } catch (error) {
        console.log(error);
      } finally {
        this.getFeedBack();
      }
    }
  },
  computed: {
    disabled() {
      let flag = true;
      if (this.$store.getters.user.roleId == 2) {
        flag = false;
      }
      return flag;
    }
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
  padding: 25px;
  .left {
    width: 55%;
    padding: 25px;
    border-right: 1px solid #ebeef5;
    ::v-deep .calendar {
      .el-calendar-table .el-calendar-day {
        width: 60px;
        height: 40px;
      }
    }
    .statistic {
      margin-bottom: 60px;
      padding: 20px 0 30px 0;
      border-bottom: 1px solid #ebeef5;
      display: flex;
      justify-content: space-around;
      .box {
        width: 20%;
        border-right: 1px solid #ebeef5;
        &:last-child {
          border: none;
        }
      }
    }
  }
  .right {
    margin-left: 2%;
    width: 43%;
    height: 100%;
    padding: 25px;
    .top {
      width: 100%;
      height: 300px;
      // background-color: pink;
      margin-bottom: 5px;
      border-bottom: 1px solid #ebeef5;
    }
    .bottom {
      width: 100%;
      height: 300px;
    }
  }
}
</style>
