<template>
  <div class="main-container">
    <div class="header-container">
      <h1>退 房 处 理</h1>
      <el-button class="add" @click="openDrawer('add')" :disabled="isAdd">退 房 +</el-button>
    </div>
    <div class="table-container">
      <div class="navbar">
        <el-form ref="form" :model="form" label-width="50px" :inline="true">
          <el-form-item label="房号">
            <el-input v-model="form.houseName" placeholder="请输入房号"></el-input>
          </el-form-item>
          <el-form-item label="时间">
            <el-date-picker v-model="form.month" type="date" placeholder="请选择时间" :picker-options="pickerBeginOption" value-format="yyyy-MM-DD"> </el-date-picker>
          </el-form-item>
          <el-button type="primary" style="height: 38px" @click="search">查询</el-button>
        </el-form>
      </div>
      <div class="table">
        <el-table :data="tableData" stripe style="width: 100%" height="454" v-loading="loading" :default-sort="{ prop: 'date', order: 'descending' }">
          <el-table-column prop="start" label="申请时间" sortable width="180"> </el-table-column>
          <el-table-column prop="end" label="处理时间" width="180"> </el-table-column>
          <el-table-column prop="houseName" label="房号" width="180"> </el-table-column>
          <el-table-column prop="message" label="申请备注"> </el-table-column>
          <el-table-column prop="state" label="状态" width="100">
            <template slot-scope="scope">
              <span v-if="scope.row.state == 0">待处理</span>
              <span v-else-if="scope.row.state == 2">已驳回</span>
              <span v-else-if="scope.row.state == 3">已取消</span>
              <span v-else>已处理</span>
            </template>
          </el-table-column>
          <el-table-column label="操作" width="200">
            <template slot-scope="scope">
              <el-button type="primary" size="small" :disabled="isGet" @click="openDrawer(`show`, scope.row)">查 看</el-button>
              <el-button type="danger" size="small" :disabled="isDel || scope.row.state != `0`" @click="cancel(scope.row)">撤 销</el-button>
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
      <div class="drawer">
        <el-drawer :title="drawerTitle" :visible.sync="drawer" :before-close="handleClose">
          <div class="bg">
            <div class="drawer-show" v-if="drawerType == `show`">
              <el-descriptions title=" 退房信息" :column="2">
                <template slot="extra">
                  <el-button type="primary" size="small" :disabled="isPull || checkout.state != `0`" @click="deleteCheckOut">退 房</el-button>
                </template>
                <el-descriptions-item label="房号">{{ checkout.houseName }}</el-descriptions-item>
                <el-descriptions-item label="处理人">{{ checkout.name }}</el-descriptions-item>
                <el-descriptions-item label="申请时间">{{ checkout.start }}</el-descriptions-item>
                <el-descriptions-item label="处理时间">{{ checkout.end }}</el-descriptions-item>
                <el-descriptions-item label="申请备注">{{ checkout.message }}</el-descriptions-item>
                <el-descriptions-item label="审核备注">{{ checkout.remark }}</el-descriptions-item>
              </el-descriptions>
            </div>
            <div class="drawer-add" v-if="drawerType == `add`">
              <el-form ref="form" :model="form" label-width="80px">
                <el-form-item label="活动名称">
                  <el-input v-model="form.name"></el-input>
                </el-form-item>
              </el-form>
            </div>
          </div>
        </el-drawer>
      </div>
    </div>
  </div>
</template>

<script>
import * as permission from '@/utils/permission';
import { getAllCheckOut, cancelCheckOut, deleteCheckOut, getCheckOutDetail } from '@/api/checkout';
import { validNumber } from '@/utils/validate';
export default {
  data() {
    const validateNumber = (rule, value, callback) => {
      validNumber(value) ? callback() : callback(new Error('请输入数字'));
    };
    return {
      pickerBeginOption: {
        disabledDate: time => {
          return time.getTime() > Date.now();
        }
      },
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
      drawer: false,
      drawerType: '',
      checkout: {}
    };
  },
  created() {
    this.getAll();
  },
  methods: {
    //获取全部维修记录
    async getAll() {
      const param = `${this.pagination.currentPage}/${this.pagination.pageSize}`;
      const { data } = await getAllCheckOut(param, {
        houseName: this.form.houseName,
        time: this.form.month
      });
      this.tableData = data.records;
      this.pagination.currentPage = data.current;
      this.pagination.total = data.total;
      this.pagination.pageSize = data.size;
      // console.log(data);
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
      this.form = {};
    },
    //关闭弹窗
    handleClose(done) {
      this.$confirm('确认关闭？')
        .then(_ => {
          done();
        })
        .catch(_ => {});
    },
    //打开弹窗
    openDrawer(type, row) {
      this.drawerType = type;
      if (type == `add`) {
      }
      if (type == `show`) {
        // console.log(row);
        this.getCheckOutDetail({ checkoutId: row.checkoutId });
      }
      this.drawer = true;
    },
    //撤销申请
    cancel(row) {
      this.$confirm('此操作将撤销该申请, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(async () => {
          try {
            await cancelCheckOut(row);
            this.$message.success('撤销成功...');
          } catch (error) {
            console.log(error);
          } finally {
            this.getAll();
          }
        })
        .catch(() => {});
    },
    //退房处理
    deleteCheckOut() {
      this.$confirm('此操作将进行退房处理, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(async () => {
          try {
            await deleteCheckOut({ ...this.checkout, userId: this.$store.getters.userId });
            this.$message.success('退房成功');
          } catch (error) {
            console.log(error);
          } finally {
            this.getCheckOutDetail({ checkoutId: this.checkout.checkoutId });
            this.getAll();
          }
        })
        .catch(() => {});
    },
    //获取详细信息
    async getCheckOutDetail(id) {
      try {
        const { data } = await getCheckOutDetail(id);
        this.checkout = data;
      } catch (error) {
        console.log(error);
      }
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
    drawerTitle() {
      let title = '默认大标题';
      if (this.drawerType === 'show') title = '查 看 退 房 信 息';
      if (this.drawerType === 'add') title = '退 房 处 理';
      return title;
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
    .add {
      width: 100px;
      height: 42px;
      border-radius: 27px;
      background: linear-gradient(45deg, #4f8aff 0%, #4b5eff 100%);
      background-blend-mode: normal;
      box-shadow: 0px 4px 16px #b3c0e7;
      color: #fff;
      font-size: 16px;
    }
  }
  .table-container {
    height: 649px;
    width: 100%;
    margin-top: 25px;
    background-color: #fff;
    border-radius: 8px;
    padding: 25px;
    .navbar {
      margin-top: 10px;
      height: 70px;
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
  }
  .drawer {
    .bg {
      width: 100%;
      padding: 25px 25px 0 25px;
      .drawer-show {
        padding: 10px;
        box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
      }
    }
  }
}
</style>
