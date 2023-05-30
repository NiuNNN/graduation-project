<template>
  <div class="main-container">
    <div class="header-container">
      <h1>维 修 管 理</h1>
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
        <el-table :data="tableData" stripe style="width: 100%" height="454" :default-sort="{ prop: 'date', order: 'descending' }">
          <el-table-column prop="report" label="时间" sortable width="180"> </el-table-column>
          <el-table-column prop="houseName" label="房号" width="180"> </el-table-column>
          <el-table-column prop="message" label="详情"> </el-table-column>
          <el-table-column prop="state" label="状态" width="100">
            <template slot-scope="scope">
              <span v-if="scope.row.state == 0">待处理</span>
              <span v-else>已处理</span>
            </template>
          </el-table-column>
          <el-table-column label="操作" width="200">
            <template slot-scope="scope">
              <el-button type="primary" size="small" :disabled="isGet" @click="openDrawer(`show`, scope.row)">查 看</el-button>
              <el-button type="danger" size="small" :disabled="isDel || scope.row.state == `1`" @click="deleteRepair(scope.row)">取 消</el-button>
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
      <div class="drawer-container">
        <el-drawer :title="drawerTitle" size="35%" :visible.sync="isShowDrawer" :before-close="handleClose">
          <div class="bg">
            <div class="main">
              <el-descriptions title=" 维修信息" :column="2">
                <template slot="extra">
                  <el-button type="primary" size="small" :disabled="isEdit || state == `已处理`" @click="dialogVisible = true">确认维修</el-button>
                </template>
                <el-descriptions-item label="上报时间">{{ repair.report }}</el-descriptions-item>
                <el-descriptions-item label="维修时间">{{ repair.fix }}</el-descriptions-item>
                <el-descriptions-item label="房号">{{ repair.houseName }}</el-descriptions-item>
                <el-descriptions-item label="维修员">{{ repair.name }}</el-descriptions-item>
                <el-descriptions-item label="详情">{{ repair.message }}</el-descriptions-item>
                <el-descriptions-item label="维修信息">{{ repair.remark }}</el-descriptions-item>
                <el-descriptions-item label="状态">{{ state }}</el-descriptions-item>
                <el-descriptions-item label="维修金额">{{ repair.price }}</el-descriptions-item>
              </el-descriptions>
            </div>
          </div>
        </el-drawer>
      </div>
      <div class="dialog">
        <el-dialog title="填写维修信息" :visible.sync="dialogVisible" width="30%" :before-close="handleClose">
          <el-form :model="repairForm" label-width="100x" :rules="rules" ref="repairForm">
            <el-form-item label="维修金额" prop="price">
              <el-input v-model="repairForm.price"></el-input>
            </el-form-item>
            <el-form-item label="维修信息" prop="remark">
              <el-input type="textarea" v-model="repairForm.remark"></el-input>
            </el-form-item>
          </el-form>
          <span slot="footer" class="dialog-footer">
            <el-button @click="reset">取 消</el-button>
            <el-button type="primary" @click="handleRepair()">确 定</el-button>
          </span>
        </el-dialog>
      </div>
    </div>
  </div>
</template>

<script>
import * as permission from '@/utils/permission';
import { getAllRepair, deleteRepair, getRepairByRepairId, completeRepair } from '@/api/repair';
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
      isShowDrawer: false,
      drawerType: '',
      repair: {},
      state: '',
      dialogVisible: false,
      repairForm: {
        price: '',
        remark: ''
      },
      rules: {
        price: [
          { required: true, message: '请输入维修金额', trigger: 'blur' },
          {
            validator: validateNumber,
            message: '维修金额必须为数字值',
            trigger: 'blur'
          }
        ],
        remark: [{ required: true, message: '请填写维修信息', trigger: 'blur' }]
      }
    };
  },
  created() {
    this.getAll();
  },
  methods: {
    //获取全部维修记录
    async getAll() {
      const param = `${this.pagination.currentPage}/${this.pagination.pageSize}`;
      const { data } = await getAllRepair(param, {
        houseName: this.form.houseName,
        report: this.form.month
      });
      this.tableData = data.records;
      this.pagination.currentPage = data.current;
      this.pagination.total = data.total;
      this.pagination.pageSize = data.size;
      // console.log(data);
    },
    //换页
    handleCurrentChange(currentPage) {
      this.pagination.currentPage = currentPage;
      this.getAll();
    },
    //查询
    search() {
      this.getAll();
      this.form = {};
    },
    //打开抽屉
    async openDrawer(type, row) {
      try {
        this.isShowDrawer = true;
        this.drawerType = type;
        if (row.state == 0) {
          this.state = '待处理';
          this.repair = row;
        } else {
          this.state = '已处理';
          const { data } = await getRepairByRepairId({ repairId: row.repairId });
          this.repair = data;
        }
        // console.log(this.repair);
      } catch (error) {
        console.log(error);
      }
    },
    //关闭提醒
    handleClose(done) {
      this.$confirm('确认关闭？')
        .then(_ => {
          done();
        })
        .catch(_ => {});
    },
    //取消维修
    deleteRepair(row) {
      this.$confirm('确定要取消维修吗?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(async () => {
          try {
            await deleteRepair(row);
            this.$message.success('取消成功');
          } catch (error) {
            console.log(error);
          } finally {
            this.getAll();
          }
        })
        .catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          });
        });
    },
    //处理维修
    handleRepair() {
      this.$refs.repairForm.validate(async isOK => {
        if (isOK) {
          this.$confirm('确定已处理维修了吗?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          })
            .then(async () => {
              try {
                this.repair = { ...this.repair, ...this.repairForm, state: 1, userId: this.$store.getters.userId };
                await completeRepair(this.repair);
                this.$message.success('处理完成');
              } catch (error) {
                console.log(error);
              } finally {
                this.reset();
                this.openDrawer(`show`, this.repair);
                this.getAll();
              }
            })
            .catch(() => {
              this.$message({
                type: 'info',
                message: '已取消'
              });
            });
        }
      });
    },
    //取消
    reset() {
      this.dialogVisible = false;
      this.repairForm = { price: '', remark: '' };
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
      if (this.drawerType === 'show') title = '查 看 维 修 信 息';
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
  .drawer-container {
    .bg {
      width: 100%;
      padding: 0 25px 0 25px;
      .main {
        padding: 10px;
        box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
      }
    }
  }
  .dialog {
    ::v-deep .el-form {
      padding: 0 30px 0 20px;

      .el-form-item {
        margin-bottom: 20px;
      }

      .el-form-item__label {
        font-size: 14px !important;
        padding: 0 15px 0 0 !important;
      }

      .el-input__inner,
      .el-textarea__inner {
        border: 1px solid #ccc;
        color: #606266;

        &:hover {
          border: 1px solid #ccc;
        }

        &::placeholder {
          font-size: 14px;
          color: #606266;
        }
      }
    }
  }
}
</style>
