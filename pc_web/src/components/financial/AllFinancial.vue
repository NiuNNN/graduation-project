<template>
  <div class="main-container">
    <div class="table-container">
      <div class="left">
        <div class="bg">
          <financial-chart></financial-chart>
        </div>
      </div>
      <div class="right">
        <div class="table">
          <el-table ref="multipleTable" :data="tableData" tooltip-effect="dark" style="width: 100%" height="615" :default-sort="{ prop: 'date', order: 'descending' }" @selection-change="handleSelectionChange">
            <el-table-column type="selection" width="55"> </el-table-column>
            <el-table-column prop="date" label="时间" sortable width="80"> </el-table-column>
            <el-table-column label="状态" width="75">
              <template slot-scope="scope">
                <span v-if="scope.row.state == 0">未审批</span>
                <span v-else>已审批</span>
              </template>
            </el-table-column>
            <el-table-column align="right">
              <template slot="header" slot-scope="scope">
                <el-date-picker size="mini" v-model="month" type="month" placeholder="请选择时间" :picker-options="pickerBeginOption" value-format="yyyy-MM"> </el-date-picker>
                <el-button size="mini" type="primary" style="margin-left: 5px" icon="el-icon-search"></el-button>
                <el-button size="mini" type="primary" style="margin-left: 5px" icon="el-icon-download"></el-button>
              </template>
              <template slot-scope="scope">
                <el-button size="mini" @click="handleEdit(scope.$index, scope.row)">查 看</el-button>
                <el-button type="primary" size="mini" @click="handleEdit(scope.$index, scope.row)">导 出</el-button>
              </template>
            </el-table-column>
            <template #empty>
              <el-empty :image-size="200"></el-empty>
            </template>
          </el-table>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import FinancialChart from '../echart/FinancialChart.vue';
import { getAllFinancial } from '@/api/financial';
export default {
  components: {
    FinancialChart
  },
  async created() {
    await this.getAllFinancial();
  },
  data() {
    return {
      pickerBeginOption: {
        disabledDate: time => {
          return time.getTime() > Date.now();
        }
      },
      month: '',
      tableData: [],
      multipleSelection: []
    };
  },
  methods: {
    handleSelectionChange(val) {
      this.multipleSelection = val;
    },
    async getAllFinancial() {
      try {
        const { data } = await getAllFinancial({ month: this.month });
        this.tableData = data;
      } catch (error) {
        console.log(error);
      }
    }
  }
};
</script>

<style lang="scss" scoped>
.main-container {
  .table-container {
    // height: 649px;
    width: 100%;
    margin-top: 25px;
    background-color: #fff;
    border-radius: 8px;
    padding: 0 25px;
    display: flex;
    .left {
      width: 55%;
      .bg {
        width: 98%;
        margin-bottom: 15px;
        height: 615px;
        box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
      }
    }
    .right {
      width: 45%;
      .table {
        box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
      }
    }
  }
  .el-date-editor.el-input {
    width: 180px;
  }
}
</style>
