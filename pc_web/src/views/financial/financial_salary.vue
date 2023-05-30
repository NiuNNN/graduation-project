<template>
  <div class="main-container">
    <div class="header-container">
      <h1>薪 水 管 理</h1>
      <el-button class="add" @click="openDrawer('add')" :disabled="isAdd">添 加 +</el-button>
    </div>
    <div class="table-container">
      <div class="table">
        <el-table :data="tableData" style="width: 100%">
          <el-table-column prop="salaryName" label="薪水名称" width="120"> </el-table-column>
          <el-table-column prop="price" label="薪水(元)" width="120"> </el-table-column>
          <el-table-column prop="remark" label="详情"> </el-table-column>
          <el-table-column fixed="right" label="操作" width="200">
            <template slot-scope="scope">
              <el-button type="primary" size="mini" @click="openDrawer('edit', scope.row)" :disabled="isEdit">编辑</el-button>
              <el-button type="danger" size="mini" @click="deleteSalary(scope.row)" :disabled="isDel">删除</el-button>
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
        <el-drawer :title="drawerTitle" size="28%" :visible.sync="isShowDrawer" :before-close="handleClose">
          <el-form ref="salaryForm" :model="salaryForm" :rules="rules" label-width="100px">
            <el-form-item label="薪水名称" prop="salaryName">
              <el-input v-model.trim="salaryForm.salaryName" placeholder="请输入薪水名称"></el-input>
            </el-form-item>
            <el-form-item label="薪水(元)" prop="price">
              <el-input v-model.trim="salaryForm.price" placeholder="请输入薪水"></el-input>
            </el-form-item>
            <el-form-item label="薪水对象" prop="state">
              <el-radio-group v-model="salaryForm.state">
                <el-radio label="1">正式</el-radio>
                <el-radio label="2">试用期</el-radio>
                <el-radio label="3">其他</el-radio>
              </el-radio-group>
            </el-form-item>
            <el-form-item label="立即生效" prop="operation">
              <el-radio-group v-model="salaryForm.operation">
                <el-radio label="0">否</el-radio>
                <el-radio label="1">是</el-radio>
              </el-radio-group>
            </el-form-item>
            <el-form-item label="详情" prop="remark">
              <el-input v-model="salaryForm.remark" type="textarea" :autosize="{ minRows: 10, maxRows: 20 }" placeholder="请输入该薪水详情"></el-input>
            </el-form-item>
            <el-form-item>
              <span style="font-size: 10px; color: #ff2855">*默认不立即生效为下一个月生效</span>
              <div>
                <el-button type="primary" :disabled="isAdd" @click="submit">确 认</el-button>
                <el-button @click="isShowDrawer = false">取 消</el-button>
              </div>
            </el-form-item>
          </el-form>
        </el-drawer>
      </div>
    </div>
  </div>
</template>

<script>
import { getAllSalary, insertSalary, updateSalary, deleteSalary } from '@/api/salary';
import * as permission from '@/utils/permission';
import { validNumber } from '@/utils/validate';
import { validatePassword } from '@/api/user';
export default {
  data() {
    const validateNumber = (rule, value, callback) => {
      validNumber(value) ? callback() : callback(new Error('请输入数字'));
    };
    return {
      pagination: {
        currentPage: 1, //当前页码
        pageSize: 8, //每页显示的记录数
        total: 0
      },
      tableData: [],
      isShowDrawer: false,
      drawerType: '',
      salaryForm: {
        salaryName: '',
        price: '',
        remark: '',
        operation: '0',
        state: '1'
      },
      rules: {
        salaryName: [{ required: true, message: '请输入收费名称', trigger: 'blur' }],
        price: [
          { required: true, message: '请输入收费价格', trigger: 'blur' },
          {
            validator: validateNumber,
            message: '价格必须为数字值',
            trigger: 'blur'
          }
        ],
        operation: [{ required: true, message: '请选择是否立即生效', trigger: 'blur' }],
        remark: [{ required: true, message: '请输入该收费详情', trigger: 'blur' }],
        state: [{ required: true, message: '请选择薪水区别', trigger: 'blur' }]
      }
    };
  },
  created() {
    this.getAllSalary();
  },
  methods: {
    async getAllSalary() {
      try {
        const param = `${this.pagination.currentPage}/${this.pagination.pageSize}`;
        const { data } = await getAllSalary(param);
        this.tableData = data.records;
        this.pagination.currentPage = data.current;
        this.pagination.total = data.total;
        this.pagination.pageSize = data.size;
      } catch (error) {
        console.log(error);
      }
    },
    //换页
    handleCurrentChange(currentPage) {
      this.pagination.currentPage = currentPage;
      this.getAllSalary();
    },
    handleClose(done) {
      this.$confirm('确认关闭？')
        .then(_ => {
          done();
        })
        .catch(_ => {});
    },
    //打开文本
    openDrawer(type, row) {
      this.reset();
      this.drawerType = type;
      // console.log(type);
      this.isShowDrawer = true;
      if (this.drawerType === `edit`) {
        // console.log(row);
        this.salaryForm = {
          salaryName: row.salaryName,
          price: row.price,
          remark: row.remark,
          operation: row.operation,
          salaryId: row.salaryId,
          state: '' + row.state
        };
      }
    },
    //提交
    async submit() {
      try {
        await this.$refs.salaryForm.validate();
        if (this.drawerType === `add`) {
          await insertSalary(this.salaryForm);
          this.$message.success('添加成功');
        }
        if (this.drawerType === `edit`) {
          await updateSalary(this.salaryForm);
          this.$message.success('修改成功');
        }
      } catch (error) {
        console.log(error);
      } finally {
        this.getAllSalary();
        this.reset();
        this.isShowDrawer = false;
      }
    },
    //清空form
    reset() {
      this.salaryForm = {
        salaryName: '',
        price: '',
        remark: '',
        operation: '0',
        state: '1'
      };
    },
    deleteSalary(row) {
      this.$prompt('请输入密码', '删除该收费', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        inputType: 'password',
        inputPattern: /^\w{5,12}$/,
        inputErrorMessage: '密码格式不正确'
      })
        .then(async ({ value }) => {
          try {
            console.log(value);
            await validatePassword({
              password: value,
              username: this.$store.getters.username
            });
            // console.log(1);
            await deleteSalary({ salaryId: row.salaryId });
            // console.log(2);
            this.$message.success('删除成功');
          } catch (error) {
            console.log(error);
          } finally {
            this.getAllSalary();
          }
        })
        .catch(() => {
          this.$message({
            type: 'info',
            message: '取消输入'
          });
        });
    }
  },
  computed: {
    drawerTitle() {
      let title = '默认大标题';
      if (this.drawerType === 'add') title = '添 加 薪 水';
      if (this.drawerType === 'edit') title = '修 改 薪 水';
      return title;
    },
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
    height: 100%;
    width: 100%;
    margin-top: 25px;
    padding: 25px;
    background-color: #fff;
    border-radius: 8px;
    .table {
      .el-table {
        height: 467px;
      }
    }
    .pagination {
      margin-top: 25px;
    }
    .drawer {
      ::v-deep .el-form {
        padding: 0 30px 0 20px;

        .el-form-item {
          margin-bottom: 20px;
        }

        .el-form-item__label {
          font-size: 14px !important;
          padding: 0 15px 0 0 !important;
        }
        .el-select {
          width: 100%;
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
}
</style>
