<template>
  <div class="main-container">
    <div class="table-container">
      <div class="navbar">
        <div style="width: 60%">
          <el-form ref="form" :model="form" label-width="50px" :inline="true">
            <el-form-item label="姓名">
              <el-input v-model="form.name" placeholder="请输入姓名"></el-input>
            </el-form-item>
            <el-form-item label="时间">
              <el-date-picker v-model="form.time" type="month" placeholder="请选择时间" :picker-options="pickerBeginOption" value-format="yyyy-MM"> </el-date-picker>
            </el-form-item>
            <el-button type="primary" style="height: 35px; line-height: 5px; margin-top: 2px" @click="search">查询</el-button>
          </el-form>
        </div>
        <div style="margin-right: 35px">
          <el-button style="height: 35px; line-height: 5px" @click="generateStaffSalary">生成账单</el-button>
          <el-button style="height: 35px; line-height: 5px" @click="generateStaffExcel(`all`)">一键导出</el-button>
        </div>
      </div>
      <div class="table">
        <el-table :data="tableData" stripe style="width: 100%" height="454" v-loading="loading" :default-sort="{ prop: 'date', order: 'descending' }" @selection-change="handleSelectionChange" :row-key="getRowKey">
          <el-table-column type="selection" width="55" :reserve-selection="true"> </el-table-column>
          <el-table-column prop="time" label="时间" sortable> </el-table-column>
          <el-table-column prop="username" label="账号" width="120"> </el-table-column>
          <el-table-column prop="name" label="姓名" width="80"> </el-table-column>
          <el-table-column prop="roleName" label="职位"> </el-table-column>
          <el-table-column label="基本薪水(元)">
            <template slot-scope="scope">
              <span>￥{{ scope.row.basePrice }}</span>
            </template>
          </el-table-column>
          <el-table-column label="福利(元)">
            <template slot-scope="scope">
              <span>￥{{ scope.row.elsePrice }}</span>
            </template>
          </el-table-column>
          <el-table-column prop="price" label="总薪水(元)">
            <template slot-scope="scope">
              <span>￥{{ scope.row.price }}</span>
            </template>
          </el-table-column>
          <el-table-column label="操作" width="100">
            <template slot-scope="scope">
              <el-button type="primary" size="small" :disabled="isEdit" @click="generateStaffExcel(`single`, scope.row)">报 表</el-button>
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
    </div>
  </div>
</template>

<script>
import * as permission from '@/utils/permission';
import { validatePassword } from '@/api/user';
import { selectSalaryPay, generateStaffExcel, generateStaffSalary, judgeGenerateStaff } from '@/api/salary';
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
        time: '',
        name: ''
      },
      tableData: [],
      pagination: {
        currentPage: 1, //当前页码
        pageSize: 7, //每页显示的记录数
        total: 0
      },
      loading: false,
      password: '',
      multipleSelection: []
    };
  },
  created() {
    this.getAll();
  },
  methods: {
    getRowKey(row) {
      return row.id;
    },
    //获取全部人工薪水
    async getAll() {
      try {
        this.loading = true;
        const param = `${this.pagination.currentPage}/${this.pagination.pageSize}`;
        const { data } = await selectSalaryPay(param, {
          name: this.form.name,
          time: this.form.time
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
    //换页
    handleCurrentChange(currentPage) {
      this.loading = true;
      this.pagination.currentPage = currentPage;
      this.getAll();
      this.loading = false;
    },
    //查询
    search() {
      this.getAll();
      this.form = {
        time: '',
        name: ''
      };
    },
    //多选操作
    handleSelectionChange(val) {
      this.multipleSelection = val;
      console.log(this.multipleSelection);
    },
    //按需导出
    async generateStaffExcel(style, row) {
      if (style == `all`) {
        if (this.multipleSelection.length > 0) {
          this.$prompt('请输入密码', '导出账单', {
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
              await generateStaffExcel({ list: JSON.stringify(this.multipleSelection) });
            } catch (error) {
              console.log(error);
            } finally {
              this.multipleSelection = [];
            }
          });
        } else {
          this.$message.info('请勾选后导出账单...');
        }
      } else if (style == `single`) {
        try {
          let arr = [];
          arr.push(row);
          await generateStaffExcel({ list: JSON.stringify(arr) });
        } catch (error) {
          console.log(error);
        }
      }
    },
    //生成全部员工薪水并导出
    generateStaffSalary() {
      this.$confirm(`此操作将生成全部员工薪水账单, 是否继续?`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(() => {
          this.$prompt('请输入密码', '导出账单', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            inputType: 'password',
            inputPattern: /^\w{5,12}$/,
            inputErrorMessage: '密码格式不正确'
          }).then(async ({ value }) => {
            try {
              await judgeGenerateStaff();
              // console.log(value);
              await validatePassword({
                password: value,
                username: this.$store.getters.username
              });
              await generateStaffSalary();
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
