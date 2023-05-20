<template>
  <div class="main-container">
    <div class="header-container">
      <h1>缴 费 处 理</h1>
    </div>
    <div class="table-container">
      <div class="navbar">
        <el-form ref="form" :model="form" label-width="50px" :inline="true">
          <el-form-item label="房号">
            <el-input v-model="form.houseName" placeholder="请输入房号"></el-input>
          </el-form-item>
          <el-form-item label="时间">
            <el-date-picker v-model="form.month" type="month" placeholder="请选择时间" :picker-options="pickerBeginOption" value-format="yyyy-MM"> </el-date-picker>
          </el-form-item>
          <el-button type="primary" style="height: 38px" @click="search">查询</el-button>
        </el-form>
      </div>
      <div class="table">
        <el-table :data="tableData" stripe style="width: 100%" height="478" v-loading="loading" :default-sort="{ prop: 'date', order: 'descending' }">
          <el-table-column prop="date" label="时间" sortable width="120"> </el-table-column>
          <el-table-column prop="outTradeNo" label="订单编号" width="200"> </el-table-column>
          <el-table-column prop="houseName" label="房号"> </el-table-column>
          <el-table-column prop="type" label="类型"> </el-table-column>
          <el-table-column prop="total" label="金额"> </el-table-column>
          <el-table-column prop="time" label="上传时间"> </el-table-column>
          <el-table-column label="操作" width="180">
            <template slot-scope="scope">
              <el-button size="mini" :disabled="isGet" @click="showDrawer(scope.row)">查 看</el-button>
              <el-button v-if="scope.row.type != `退款`" type="primary" size="mini" :disabled="isAdd || scope.row.state == 1" @click="openDrawer(scope.row)">缴 费</el-button>
              <el-button v-else type="primary" size="mini" :disabled="isAdd || scope.row.state == 1" @click="payOut(scope.row)">退 款</el-button>
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
        <el-drawer :title="drawerTitle" :visible.sync="drawer" size="50%">
          <order-detail :order="order" :table-data="misTable" :detail="detail"></order-detail>
        </el-drawer>
      </div>
      <div class="drawer">
        <el-drawer :title="PayTitle" :visible.sync="payDrawer" :destroy-on-close="true" size="50%">
          <pay :order="order" @closePayDrawer="closePayDrawer"></pay>
        </el-drawer>
      </div>
    </div>
  </div>
</template>

<script>
import * as permission from '@/utils/permission';
import { getAllOrder } from '@/api/order';
import OrderDetail from '@/components/financial/OrderDetail.vue';
import { getName } from '@/api/user';
import { getOrderMis } from '@/api/basic';
import { getCostByRentId } from '@/api/cost';
import Pay from '@/components/financial/Pay.vue';
import { payByCash } from '@/api/order';
import { validatePassword } from '@/api/user';
export default {
  components: {
    OrderDetail,
    Pay
  },
  data() {
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
        pageSize: 8, //每页显示的记录数
        total: 0
      },
      loading: false,
      drawer: false,
      drawerTitle: '',
      order: {},
      misTable: [],
      detail: {},
      payDrawer: false,
      PayTitle: ''
    };
  },
  created() {
    this.getAll();
  },
  methods: {
    //退款
    payOut(row) {
      this.$prompt('请输入密码,完成退款操作', '退款', {
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
            await payByCash({
              ...row,
              userId: this.$store.getters.userId
            });
            this.$message.success('系统记账成功...');
          } catch (error) {
            console.log(error);
          } finally {
            this.getAll();
          }
        })
        .catch(() => {
          this.$message({
            type: 'info',
            message: '取消输入'
          });
        });
    },
    //获取全部信息
    async getAll() {
      try {
        this.loading = true;
        const param = `${this.pagination.currentPage}/${this.pagination.pageSize}`;
        const { data } = await getAllOrder(param, {
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
    search() {
      this.getAll();
      this.form = {
        month: '',
        houseName: ''
      };
    },
    async showDrawer(row) {
      try {
        this.detail = {};
        // console.log(row);
        const { data: misTable } = await getOrderMis({ orderId: row.orderId });
        this.misTable = misTable;
        if (row.type != `押金`) {
          const { data } = await getCostByRentId({
            rentId: row.rentId,
            time: row.date
          });
          // console.log(data);
          this.detail = data;
        }
        if (row.userId != '' && row.userId != null) {
          const { data: name } = await getName(row.userId);
          // console.log(result);
          row.name = name;
        }
        this.drawerTitle = `订单编号:${row.outTradeNo}`;
        this.order = row;
        // console.log(row);
        this.drawer = true;
      } catch (error) {
        console.log(error);
      }
    },
    //支付
    openDrawer(row) {
      this.order = row;
      this.PayTitle = `缴纳${row.type}`;
      this.payDrawer = true;
    },
    //关闭支付弹窗
    closePayDrawer() {
      this.getAll();
      this.payDrawer = false;
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
    .btn {
      text-align: right;
      .cancel {
        width: 130px;
        height: 42px;
        border-radius: 27px;
        background-color: #fff;
        font-size: 16px;
        color: #4458fe;
        box-shadow: 0px 4px 16px rgba(179, 192, 231, 0.32);
      }
      .save {
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
  .drawer-container {
    width: 90%;
    margin: 0 auto;
    .table {
      margin-bottom: 15px;
      h3 {
        margin-bottom: 5px;
        font-size: 14px;
        font-weight: 400;
      }
      ::v-deep .el-input {
        height: 23px !important;
        .el-input__inner {
          font-size: 12px !important;
          height: 23px !important;
          border: none !important;
        }
      }
    }
  }
}
</style>
