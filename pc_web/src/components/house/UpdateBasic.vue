<template>
  <div class="container">
    <div class="table">
      <p style="color: #303133; font-size: 14px">已选择杂费：</p>
      <el-table
        :data="tableData"
        stripe
        style="width: 100%"
        height="250"
        size="small"
        border
        v-loading="loading"
      >
        <el-table-column prop="baseName" label="收费名称" width="100">
        </el-table-column>
        <el-table-column prop="price" label="价格(月/元)" width="100">
        </el-table-column>
        <el-table-column prop="remark" label="收费详情"> </el-table-column>
        <el-table-column prop="time" label="录入时间"> </el-table-column>
        <el-table-column fixed="right" label="操作" width="80">
          <template slot-scope="scope">
            <el-button
              @click="deleteRow(scope.row)"
              type="text"
              size="mini"
              :disabled="isDel"
            >
              删除
            </el-button>
          </template>
        </el-table-column>
        <template #empty>
          <el-empty :image-size="80"></el-empty>
        </template>
      </el-table>
    </div>
    <div class="table">
      <p style="color: #303133; font-size: 14px">待选择杂费:</p>
      <el-table
        :data="noBasicData"
        stripe
        style="width: 100%"
        height="250"
        size="small"
        border
        v-loading="loading"
      >
        <el-table-column prop="baseName" label="收费名称" width="120">
        </el-table-column>
        <el-table-column prop="price" label="价格(月/元)" width="120">
        </el-table-column>
        <el-table-column prop="remark" label="收费详情"> </el-table-column>
        <el-table-column fixed="right" label="操作" width="80">
          <template slot-scope="scope">
            <el-button
              @click="addRow(scope.row)"
              type="text"
              size="mini"
              :disabled="isAdd"
            >
              添加
            </el-button>
          </template>
        </el-table-column>
        <template #empty>
          <el-empty :image-size="80"></el-empty>
        </template>
      </el-table>
    </div>
    <div class="btn">
      <el-button @click="close" type="primary">完成</el-button>
    </div>
  </div>
</template>

<script>
import {
  changeMiscellaneousState,
  insertMiscellaneous,
  getNoBasicByRentId,
} from "@/api/basic";
export default {
  props: {
    user: {
      type: Object,
      default: () => {
        return {};
      },
    },
    tableData: {
      type: Array,
      default: () => {
        return [];
      },
    },
    isDel: {
      type: Boolean,
      default: true,
    },
    isAdd: {
      type: Boolean,
      default: true,
    },
  },
  created() {
    this.getNoBasic();
  },
  data() {
    return {
      newTableData: [],
      noBasicData: [],
      loading: true,
    };
  },
  methods: {
    //获取未选择的杂费
    async getNoBasic() {
      try {
        this.loading = true;
        const { data } = await getNoBasicByRentId({ rentId: this.user.rentId });
        console.log(data);
        this.noBasicData = data;
      } catch (error) {
        console.log(error);
      } finally {
        this.loading = false;
      }
    },
    //删除
    async deleteRow(row) {
      this.$confirm("此操作将永久删除该杂费, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(async () => {
          try {
            await changeMiscellaneousState({
              state: 2,
              miscellaneousId: row.miscellaneousId,
            });
            this.$message.success("删除成功");
          } catch (error) {
            console.log(error);
          } finally {
            this.$emit(`deleteMiscellaneous`);
            this.getNoBasic();
          }
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取消删除",
          });
        });
    },
    //添加
    async addRow(row) {
      this.$confirm("此操作将添加该杂费, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(async () => {
          try {
            this.loading = true;
            await insertMiscellaneous({
              rentId: this.user.rentId,
              baseId: row.baseId,
            });
            this.$message.success("添加成功");
          } catch (error) {
            console.log(error);
          } finally {
            this.$emit(`deleteMiscellaneous`);
            this.getNoBasic();
            this.loading = false;
          }
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取消添加",
          });
        });
    },
    //关闭弹窗
    close() {
      this.newTableData = this.tableData;
      this.$emit("afterUpdateBasic", this.newTableData);
    },
  },
};
</script>

<style lang="scss" scoped>
.container {
  width: 90%;
  margin: 0 auto;
  .table {
    margin-bottom: 15px;
  }
}
</style>
