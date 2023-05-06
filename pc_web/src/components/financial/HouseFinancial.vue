<template>
  <div class="main-container">
    <div class="table-container">
      <div class="navbar">
        <div style="width: 60%">
          <el-form ref="form" :model="form" label-width="50px" :inline="true">
            <el-form-item label="房号">
              <el-input v-model="form.houseName" placeholder="请输入房号"></el-input>
            </el-form-item>
            <el-form-item label="时间">
              <el-date-picker v-model="form.month" type="month" placeholder="请选择时间" :picker-options="pickerBeginOption" value-format="yyyy-MM"> </el-date-picker>
            </el-form-item>
            <el-button type="primary" style="height: 35px; line-height: 5px" @click="search">查询</el-button>
          </el-form>
        </div>
        <div style="margin-right: 35px">
          <el-button type="primary" style="height: 35px; line-height: 5px" @click="dialogVisible = true">一键报表</el-button>
        </div>
      </div>
      <div class="table">
        <el-table :data="tableData" stripe style="width: 100%" height="454" v-loading="loading" :default-sort="{ prop: 'date', order: 'descending' }">
          <el-table-column prop="date" label="时间" sortable> </el-table-column>
          <el-table-column prop="houseName" label="房号"> </el-table-column>
          <el-table-column prop="numElectric" label="用电量(度)"> </el-table-column>
          <el-table-column prop="costElectric" label="电费(元)"> </el-table-column>
          <el-table-column prop="numWater" label="用水量(方)"> </el-table-column>
          <el-table-column prop="costWater" label="水费(元)"> </el-table-column>
          <el-table-column label="操作" width="180">
            <template slot-scope="scope">
              <el-button type="primary" size="small" :disabled="isEdit" @click="generateRent(scope.row)">报 表</el-button>
              <el-button type="danger" size="small" :disabled="isDel || scope.row.state != `0`" @click="deleteCost(scope.row)">删 除</el-button>
            </template>
          </el-table-column>
          <template #empty>
            <el-empty :image-size="200"></el-empty>
          </template>
        </el-table>
      </div>
      <div class="pagination">
        <el-pagination @current-change="handleCurrentChange" :current-page="pagination.currentPage" :page-size="pagination.pageSize" background layout="prev, pager, next" :total="pagination.total"> </el-pagination>
      </div>
      <div class="dialog">
        <el-dialog title="报表" :visible.sync="dialogVisible" width="30%">
          <el-form ref="form" :model="generate" label-width="50px" :inline="true">
            <el-form-item label="时间">
              <el-date-picker v-model="generate.month" type="month" placeholder="请选择时间" :picker-options="pickerBeginOption" value-format="yyyy-MM"> </el-date-picker>
            </el-form-item>
          </el-form>
          <span slot="footer" class="dialog-footer">
            <el-button @click="dialogVisible = false">取 消</el-button>
            <el-button type="primary" @click="addAll">确 定</el-button>
          </span>
        </el-dialog>
      </div>
    </div>
  </div>
</template>

<script>
import * as permission from '@/utils/permission';
import { getAllCost, deleteCost, generatePersonCost, generateAllCost, judgeCost } from '@/api/cost';
import { validatePassword } from '@/api/user';
export default {
  data() {
    return {
      pickerBeginOption: {
        disabledDate: time => {
          return time.getTime() > Date.now();
        }
      },
      dialogVisible: false,
      form: {
        month: '',
        houseName: ''
      },
      tableData: [],
      pagination: {
        currentPage: 1, //当前页码
        pageSize: 7, //每页显示的记录数
        total: 0
      },
      loading: false,
      password: '',
      personData: [],
      generate: {
        month: ''
      }
    };
  },
  created() {
    this.getAll();
  },
  methods: {
    async addAll() {
      this.$confirm(`此操作将生成住房账单, 是否继续?`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(() => {
          this.$prompt('请输入密码', '生成账单', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            inputType: 'password',
            inputPattern: /^\w{5,12}$/,
            inputErrorMessage: '密码格式不正确'
          }).then(async ({ value }) => {
            try {
              await validatePassword({
                password: value,
                username: this.$store.getters.username
              });
              await judgeCost({ time: this.generate.month });
              this.$message.info('正在生成中，请稍等...');
              await generateAllCost({ time: this.generate.month });
            } catch (error) {
              console.log(error);
            } finally {
              this.getAll();
              this.generate = { month: '' };
              this.dialogVisible = false;
            }
          });
        })
        .catch(() => {
          this.$message({
            type: 'info',
            message: '已取消生成'
          });
        });
    },
    async getAll() {
      try {
        this.loading = true;
        const param = `${this.pagination.currentPage}/${this.pagination.pageSize}`;
        const { data } = await getAllCost(param, {
          houseName: this.form.houseName,
          time: this.form.month
        });
        // console.log(data);
        this.tableData = data.records;
        this.pagination.currentPage = data.current;
        this.pagination.total = data.total;
        this.pagination.pageSize = data.size;
      } catch (error) {
        console.log(error);
      } finally {
        this.loading = false;
      }
    },
    handleCurrentChange(currentPage) {
      this.loading = true;
      this.pagination.currentPage = currentPage;
      this.getAll();
      this.loading = false;
    },
    //查找
    search() {
      this.getAll();
      this.form = {
        month: '',
        houseName: ''
      };
    },
    //生成报表
    generateRent(row) {
      if (row.numElectric == '待处理' || row.numWater == '待处理') {
        this.$message.info(`请先录入 ${row.houseName}房 该月用电或用水信息信息`);
      } else {
        this.$confirm(`此操作将生成 ${row.houseName}房 该月账单, 是否继续?`, '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        })
          .then(() => {
            this.$prompt('请输入密码', '生成账单', {
              confirmButtonText: '确定',
              cancelButtonText: '取消',
              inputType: 'password',
              inputPattern: /^\w{5,12}$/,
              inputErrorMessage: '密码格式不正确'
            }).then(async ({ value }) => {
              try {
                // console.log(value);
                await validatePassword({
                  password: value,
                  username: this.$store.getters.username
                });
                this.$message.info('正在生成中，请稍等...');
                // console.log(row);
                await generatePersonCost(row);
              } catch (error) {
                console.log(error);
              } finally {
                this.getAll();
              }
            });
          })
          .catch(() => {
            this.$message({
              type: 'info',
              message: '已取消生成'
            });
          });
      }
    },
    //删除账单
    deleteCost(row) {
      this.$prompt('请输入密码', '删除', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        inputType: 'password',
        inputPattern: /^\w{5,12}$/,
        inputErrorMessage: '密码格式不正确'
      })
        .then(async ({ value }) => {
          try {
            // console.log(value);
            await validatePassword({
              password: value,
              username: this.$store.getters.username
            });
            await deleteCost({ costId: row.costId });
          } catch (error) {
            console.log(error);
          } finally {
            this.getAll();
          }
        })
        .catch(() => {
          this.$message({
            type: 'info',
            message: '取消删除'
          });
        });
    }
  },
  computed: {
    isAdd() {
      return permission.isAdd(this.$route.params.type);
    },
    isDel() {
      return permission.isDel(this.$route.params.type);
    },
    isUp() {
      return permission.isUp(this.$route.params.type);
    },
    isPull() {
      return permission.isPull(this.$route.params.type);
    },
    isEdit() {
      return permission.isEdit(this.$route.params.type);
    },
    isGet() {
      return permission.isGet(this.$route.params.type);
    },
    isTooggle() {
      return this.multipleSelection.length > 0 ? false : true;
    }
  }
};
</script>

<style lang="scss" scoped>
.main-container {
  .header-container {
    display: flex;
    justify-content: space-between;
    h1 {
      font-size: 24px;
      line-height: 40px;
      color: #3a3f63;
      font-weight: 400;
    }
  }
  .table-container {
    // height: 649px;
    width: 100%;
    margin-top: 25px;
    background-color: #fff;
    border-radius: 8px;
    padding: 0 25px;
    .navbar {
      display: flex;
      justify-content: space-between;
      align-items: center;
      width: 100%;
      border-bottom: 1px solid #e6e9f4;
      ::v-deep .el-form {
        .el-form-item__label {
          font-size: 14px !important;
          padding: 0 15px 0 0 !important;
          color: #6a74a5;
          font-weight: 700;
        }

        .el-input__inner {
          width: 180px;
          height: 35px;
          border: 1px solid #ccc;
          color: #606266;
          text-align: left;

          &:hover {
            border: 1px solid #ccc;
          }
        }
      }
    }
    .pagination {
      margin-top: 15px;
    }
    .dialog {
      ::v-deep .el-form {
        .el-form-item__label {
          font-size: 14px !important;
          padding: 0 15px 0 0 !important;
          color: #6a74a5;
          font-weight: 700;
        }

        .el-input__inner {
          width: 180px;
          height: 35px;
          border: 1px solid #ccc;
          color: #606266;
          text-align: left;

          &:hover {
            border: 1px solid #ccc;
          }
        }
      }
    }
  }
}
</style>
