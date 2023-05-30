<template>
  <div class="main-container">
    <div class="header-container">
      <h1>职 位 管 理</h1>
      <el-button class="add" @click="addRole(`add`)">添 加 职 位</el-button>
    </div>
    <div class="table-container">
      <div class="table">
        <el-table :data="tableData" style="width: 100%">
          <el-table-column prop="id" label="编号" width="80"> </el-table-column>
          <el-table-column prop="roleName" label="职位" width="80"> </el-table-column>
          <el-table-column label="主要职务">
            <template slot-scope="scope">
              <div class="remark" :data-title="scope.row.remark">{{ scope.row.remark }}</div>
            </template>
          </el-table-column>
          <el-table-column label="操作" width="220">
            <template slot-scope="scope">
              <el-button size="mini" @click="addRole(`edit`, scope.row)" :disabled="scope.row.roleId === 2">编 辑</el-button>
              <el-button type="primary" size="mini" @click="authorise(scope.row)">授 权</el-button>
              <template v-if="scope.row.state == 1">
                <el-button type="danger" size="mini" :disabled="scope.row.roleId === 2" @click="changeRoleState(scope.row.roleId, 0)">删 除</el-button>
              </template>
              <template v-else>
                <el-button type="warning" size="mini" @click="changeRoleState(scope.row.roleId, 1)">恢 复</el-button>
              </template>
            </template>
          </el-table-column>
        </el-table>
      </div>
      <div class="dialog">
        <el-dialog title="权限管理" :visible.sync="dialogVisible" width="40%" :before-close="handleClose">
          <tree-transfer ref="transfer" :title="title" :from_data="fromData" :to_data="toData" :defaultProps="{ label: 'label' }" @add-btn="add" @remove-btn="remove" :mode="mode" height="400px" :transferOpenNode="false"> </tree-transfer>
          <span slot="footer" class="dialog-footer">
            <el-button @click="close">确定</el-button>
          </span>
        </el-dialog>
      </div>
      <div class="pagination">
        <el-pagination @current-change="handleCurrentChange" :current-page="pagination.currentPage" :page-size="pagination.pageSize" background layout="prev, pager, next" :total="pagination.total"> </el-pagination>
      </div>
      <div class="drawer">
        <el-drawer :title="drawTitle" :visible.sync="drawer" :before-close="handleClose">
          <el-form ref="roleForm" :model="form" label-width="100px" :rules="rules">
            <el-form-item label="职位名称" prop="roleName">
              <el-input v-model="form.roleName" placeholder="请输入职位名称"></el-input>
            </el-form-item>
            <el-form-item label="主要职务" prop="remark">
              <el-input v-model="form.remark" type="textarea" :autosize="{ minRows: 10, maxRows: 20 }" placeholder="请输入主要职务信息"></el-input>
            </el-form-item>
            <el-form-item label="正式期工资" prop="baseSalary">
              <el-select v-model="form.baseSalary" placeholder="请选择正式期工资">
                <el-option v-for="(item, index) in baseSalary" :key="index" :label="item.price" :value="item.salaryId"></el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="试用期工资" prop="probationSalary">
              <el-select v-model="form.probationSalary" placeholder="请选择试用期工资">
                <el-option v-for="(item, index) in probationSalary" :key="index" :label="item.price" :value="item.salaryId"></el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="其他工资待遇">
              <el-select v-model="form.elseSalary" placeholder="请选择其他工资待遇" multiple>
                <el-option v-for="(item, index) in elseSalary" :key="index" :label="item.salaryName" :value="item.salaryId"></el-option>
              </el-select>
            </el-form-item>
            <el-form-item>
              <div>
                <el-button type="primary" @click="submit">确 认</el-button>
                <el-button @click="drawer = false">取 消</el-button>
              </div>
            </el-form-item>
          </el-form>
        </el-drawer>
      </div>
    </div>
  </div>
</template>

<script>
import { getSalaryMap } from '@/api/salary';
import { updatePermsByRoleId, selectElsePermsByRoleId, selectPermsByRoleId, getAllRole, deletePermsByRoleId, changeRoleState, insertRole, updateRole, getRoleSalary } from '@/api/role';
import treeTransfer from 'el-tree-transfer'; // 引入
export default {
  components: {
    treeTransfer
  },
  async created() {
    await this.getAllRole();
    await this.getSalaryMap();
  },
  data() {
    return {
      drawer: false,
      tableData: [],
      dialogVisible: false,
      title: ['源权限', '已有权限'],
      mode: 'transfer', // transfer addressList
      fromData: [],
      toData: [],
      isCurrent: '',
      keys: [],
      form: {
        roleName: '',
        remark: '',
        baseSalary: '',
        probationSalary: '',
        elseSalary: []
      },
      edit: true,
      drawTitle: '',
      rules: {
        roleName: [{ required: true, message: '请输入职位名称', trigger: 'blur' }],
        remark: [{ required: true, message: '请输入主要职务信息', trigger: 'blur' }],
        baseSalary: [{ required: true, message: '请选择薪水信息', trigger: 'change' }],
        probationSalary: [{ required: true, message: '请选择薪水信息', trigger: 'change' }]
      },
      change: 0,
      pagination: {
        currentPage: 1, //当前页码
        pageSize: 7, //每页显示的记录数
        total: 0
      },
      baseSalary: [],
      probationSalary: [],
      elseSalary: []
    };
  },
  methods: {
    async getSalaryMap() {
      try {
        const { data } = await getSalaryMap();
        // console.log(data);
        this.baseSalary = data.baseSalary;
        this.probationSalary = data.probationSalary;
        this.elseSalary = data.elseSalary;
      } catch (error) {
        console.log(error);
      }
    },
    close() {
      this.dialogVisible = false;
      if (this.change > 0) {
        this.$message.success('修改成功');
      }
      this.change = 0;
    },
    //打开弹窗
    authorise(row) {
      this.isCurrent = row.roleId;
      this.dialogVisible = true;

      // console.log(this.$refs.transfer);
      this.keys = [];
      // console.log(row);
      this.selectPermsByRoleId(row.roleId);
      this.selectElsePermsByRoleId(row.roleId);
    },
    //获取全部职位
    async getAllRole() {
      try {
        const param = `${this.pagination.currentPage}/${this.pagination.pageSize}`;
        const { data } = await getAllRole(param);
        // console.log(data);
        this.tableData = data.records;
        this.pagination.currentPage = data.current;
        this.pagination.total = data.total;
        this.pagination.pageSize = data.size;
      } catch (error) {
        console.log(error);
      }
    },
    //获取已有权限
    async selectPermsByRoleId(roleId) {
      try {
        const { data } = await selectPermsByRoleId(roleId);
        this.toData = data;
        // console.log(data);
      } catch (error) {
        console.log(error);
      }
    },
    //获取未有权限
    async selectElsePermsByRoleId(roleId) {
      try {
        const { data } = await selectElsePermsByRoleId(roleId);
        this.fromData = data;
        // console.log(data);
      } catch (error) {
        console.log(error);
      }
    },
    //添加权限
    async addPermission(roleId) {
      try {
        const param = {
          roleId: roleId,
          keys: JSON.stringify(this.keys)
        };
        const res = await updatePermsByRoleId(param);
        // console.log(param);
      } catch (error) {
        console.log(error);
      }
    },
    //删除权限
    async removePermission(roleId) {
      try {
        const param = {
          roleId: roleId,
          keys: JSON.stringify(this.keys)
        };
        const res = await deletePermsByRoleId(param);
        // console.log(param);
      } catch (error) {
        console.log(error);
      }
    },
    //关闭对话框
    handleClose(done) {
      this.$confirm('确认关闭？')
        .then(_ => {
          done();
        })
        .catch(_ => {});
    },
    // 切换模式 现有树形穿梭框模式transfer 和通讯录模式addressList
    changeMode() {
      if (this.mode == 'transfer') {
        this.mode = 'addressList';
      } else {
        this.mode = 'transfer';
      }
    },
    // 监听穿梭框组件添加
    async add(fromData, toData, obj) {
      console.log('obj:', obj.keys);
      this.keys = obj.keys;
      try {
        const res = await this.addPermission(this.isCurrent);
        this.selectPermsByRoleId(this.isCurrent);
        console.log(res);
        this.change++;
      } catch (error) {
        console.log(error);
      }
    },
    // 监听穿梭框组件移除
    async remove(fromData, toData, obj) {
      console.log('obj:', obj.keys);
      this.keys = obj.keys;
      try {
        const res = await this.removePermission(this.isCurrent);
        this.selectElsePermsByRoleId(this.isCurrent);
        console.log(res);
        this.change++;
      } catch (error) {
        console.log(error);
      }
    },
    //修改职务状态
    async changeRoleState(roleId, state) {
      try {
        await changeRoleState(roleId, { state });
        this.$message.success('修改成功');
        this.getAllRole();
      } catch (error) {
        console.log(error);
      }
    },
    //添加职位
    async addRole(operation, row) {
      this.form = {
        roleName: '',
        remark: '',
        baseSalary: '',
        probationSalary: '',
        elseSalary: []
      };
      if (operation === `edit`) {
        this.edit = true;
        this.drawTitle = '修改职务';
        // console.log(row.roleId);
        const { data } = await getRoleSalary({ roleId: row.roleId });
        // console.log(data);
        let arr = [];
        if (data.else != '' || data.else != null) {
          arr = JSON.parse(data.else);
        }
        this.form = { ...row, baseSalary: JSON.parse(data.base), probationSalary: JSON.parse(data.probation), elseSalary: arr };
        console.log(this.form);
      }
      if (operation === `add`) {
        this.drawTitle = `添加职位`;
        this.edit = false;
        // console.log(this.form);
      }

      this.drawer = true;
    },
    //添加、修改
    async submit() {
      if (this.edit) {
        //修改
        this.$refs.roleForm.validate(async isOK => {
          if (isOK) {
            try {
              await updateRole({ ...this.form, base: this.form.baseSalary, probation: this.form.probationSalary, arrList: JSON.stringify(this.form.elseSalary) });
              this.$message.success('修改成功');
              this.getAllRole();
              this.drawer = false;
            } catch (error) {
              console.log(error);
            }
          } else {
            this.$message.error('请正确输入职位信息');
          }
        });
      } else {
        //添加
        this.$refs.roleForm.validate(async isOK => {
          if (isOK) {
            try {
              // console.log(this.form);
              let arrList = [this.form.baseSalary, this.form.probationSalary, ...this.form.elseSalary];
              // console.log(arrList);
              await insertRole({ ...this.form, arrList: JSON.stringify(arrList) });
              this.$message.success('添加成功');
              this.getAllRole();
              this.drawer = false;
            } catch (error) {
              console.log(error);
            }
          } else {
            this.$message.error('请正确输入职位信息');
          }
        });
      }
    },
    handleCurrentChange(currentPage) {
      this.pagination.currentPage = currentPage;
      this.getAllRole();
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
      width: 115px;
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
    height: 600px;
    width: 100%;
    margin-top: 25px;
    background-color: #fff;
    border-radius: 8px;
    padding: 25px;
    .pagination {
      margin-top: 15px;
    }
  }
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
::v-deep.wl-transfer {
  .transfer-title {
    .el-checkbox {
      display: none;
    }
  }
}
.remark {
  white-space: nowrap;
  overflow: hidden;
  position: relative;
  text-overflow: ellipsis;
}
</style>
