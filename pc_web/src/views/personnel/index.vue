<template>
  <div class="main-container">
    <div class="header-container">
      <h1>员 工 管 理</h1>
    </div>
    <div>
      <create-user></create-user>
    </div>
    <div class="bg">
      <div class="navbar">
        <span :class="{ current: isCurrent == `in` }" @click="changeCurrent(`in`)">已 入 职 员 工</span>
        <span :class="{ current: isCurrent == `out` }" @click="changeCurrent(`out`)">已 离 职 员 工</span>
      </div>
      <div class="table">
        <div class="top">
          <el-form :inline="true" :model="searchForm" class="demo-form-inline" label-width="50px">
            <el-form-item label="姓名">
              <el-input v-model="searchForm.name" placeholder="请输入姓名"></el-input>
            </el-form-item>
            <el-form-item label="账号">
              <el-input v-model="searchForm.username" placeholder="请输入账号"></el-input>
            </el-form-item>
            <el-form-item label="职位:" prop="roleId">
              <el-select v-model="searchForm.roleId" placeholder="请选择职位">
                <el-option v-for="(item, index) in roleList" :label="item.roleName" :value="item.roleId" :key="index"></el-option>
              </el-select>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" :disabled="isGet" @click="search" style="height: 35px">查询</el-button>
            </el-form-item>
          </el-form>
        </div>
        <div class="bottom">
          <el-table :data="tableData" style="width: 100%">
            <el-table-column prop="name" label="头像" width="100">
              <template slot-scope="scope">
                <el-avatar v-imageerror="defaultImg" :src="`${targetUrl}/view/Avatar/${scope.row.avatar}`"></el-avatar>
              </template>
            </el-table-column>
            <el-table-column prop="username" label="账号"> </el-table-column>
            <el-table-column prop="name" label="姓名" width="100"> </el-table-column>
            <el-table-column prop="roleName" label="职位" width="100"> </el-table-column>
            <el-table-column prop="phone" label="手机"> </el-table-column>
            <el-table-column prop="idcard" label="身份证"> </el-table-column>
            <el-table-column prop="addtime" label="添加时间"> </el-table-column>
            <el-table-column label="操作" width="180">
              <template slot-scope="scope">
                <el-button size="mini" :disabled="isGet" type="primary" @click="openDrawer(scope.row)">查看</el-button>
              </template>
            </el-table-column>
            <template #empty>
              <el-empty :image-size="200"></el-empty>
            </template>
          </el-table>
          <!-- 分页器 -->
          <div class="pagination">
            <el-pagination @current-change="handleCurrentChange" :current-page="pagination.currentPage" :page-size="pagination.pageSize" background layout="prev, pager, next" :total="pagination.total"> </el-pagination>
          </div>
        </div>
        <div class="drawer">
          <el-drawer title="员工详细信息" :visible.sync="drawer" :destroy-on-close="true" size="50%">
            <div class="drawer-bg">
              <div class="main">
                <el-descriptions title=" 员工信息" :column="3">
                  <template slot="extra">
                    <el-button type="primary" size="small" :disabled="isEdit || isOut" @click="updateIdcard">操作</el-button>
                  </template>
                  <el-descriptions-item label="账号">{{ user.username }}</el-descriptions-item>
                  <el-descriptions-item label="姓名">{{ user.name }}</el-descriptions-item>
                  <el-descriptions-item label="性别">{{ user.sex }}</el-descriptions-item>
                  <el-descriptions-item label="民族">{{ user.nation }}</el-descriptions-item>
                  <el-descriptions-item label="生日">{{ user.birthday }}</el-descriptions-item>
                  <el-descriptions-item label="身份证">{{ user.idcard }}</el-descriptions-item>
                  <el-descriptions-item label="手机号">{{ user.phone }}</el-descriptions-item>
                  <el-descriptions-item label="签订时间">{{ user.sign }}</el-descriptions-item>
                  <el-descriptions-item label="过期时间">{{ user.lose }}</el-descriptions-item>
                  <el-descriptions-item label="签订机构">{{ user.authority }}</el-descriptions-item>
                  <el-descriptions-item label="地址">{{ user.address }}</el-descriptions-item>
                </el-descriptions>
              </div>
            </div>
            <div class="drawer-bg">
              <div class="main">
                <el-descriptions title=" 职务信息" :column="4">
                  <template slot="extra">
                    <el-button type="danger" size="small" :disabled="isDel || isOut" @click="leave">离职</el-button>
                  </template>
                  <el-descriptions-item :span="4" label="职务">{{ user.roleName }}</el-descriptions-item>
                  <el-descriptions-item :span="4" label="工作详情">{{ user.roleRemark }}</el-descriptions-item>
                  <el-descriptions-item :span="2" label="入职时间">{{ user.addtime }}</el-descriptions-item>
                  <el-descriptions-item :span="2" label="离职时间">{{ user.deltime }}</el-descriptions-item>
                  <el-descriptions-item v-for="(item, index) in userSalary" :key="index" :label="item.salaryName">{{ item.price }}</el-descriptions-item>
                </el-descriptions>
              </div>
            </div>
          </el-drawer>
        </div>
        <!-- 修改详细信息 -->
        <div class="drawer">
          <el-drawer :wrapperClosable="false" :show-close="false" title="修改员工信息" :visible.sync="idcardDrawer" :destroy-on-close="true" size="50%">
            <update-user :user="user" @updateUser="updateUser"></update-user>
          </el-drawer>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { getStaffPage, validatePassword } from '@/api/user';
import CreateUser from '@/components/personal/CreateUser.vue';
import * as permission from '@/utils/permission';
import { targetUrl } from '@/targetUrl';
import { getRole, getUserSalary, leaveRole } from '@/api/role';
import UpdateUser from '@/components/house/UpdateUser.vue';
export default {
  components: {
    CreateUser,
    UpdateUser
  },
  data() {
    return {
      defaultImg: require('@/assets/image/avatar_default.png'),
      isCurrent: 'in',
      targetUrl,
      searchForm: {
        name: '',
        username: '',
        roleId: ''
      },
      tableData: [],
      pagination: {
        currentPage: 1, //当前页码
        pageSize: 6, //每页显示的记录数
        total: 0
      },
      user: {},
      roleList: [],
      obj: {},
      drawer: false,
      userSalary: [],
      idcardDrawer: false
    };
  },
  async created() {
    await this.getRole();
    await this.getStaffPage();
  },
  methods: {
    async getRole() {
      try {
        const { data } = await getRole();
        this.roleList = data;
      } catch (error) {
        console.log(error);
      }
    },
    reset() {
      this.pagination = {
        currentPage: 1, //当前页码
        pageSize: 8, //每页显示的记录数
        total: 0
      };
      this.searchForm = {
        name: '',
        username: '',
        roleId: ''
      };
    },
    //切换
    async changeCurrent(name) {
      this.isCurrent = name;
      this.reset();
    },
    //换页
    async handleCurrentChange(currentPage) {
      this.pagination.currentPage = currentPage;
      await this.getStaffPage();
    },
    async search() {
      await this.getStaffPage();
      this.searchForm = {
        name: '',
        username: '',
        roleId: ''
      };
    },
    //修改完身份信息回调
    updateUser(e) {
      // console.log(e);
      this.user = e;
      this.getStaffPage();
      this.idcardDrawer = false;
    },
    //按需查询用户信息
    async getStaffPage() {
      try {
        // console.log('被调用');
        const param = `${this.pagination.currentPage}/${this.pagination.pageSize}`;
        // console.log({ ...this.searchForm, ...this.obj });
        const { data } = await getStaffPage(param, { ...this.searchForm, ...this.obj });
        this.tableData = data.records;
        this.pagination.currentPage = data.current;
        this.pagination.total = data.total;
        this.pagination.pageSize = data.size;
      } catch (error) {
        console.log(error);
      }
    },
    //打开抽屉
    async openDrawer(row) {
      try {
        // console.log(row);
        this.user = row;
        const { data } = await getUserSalary({ roleId: row.roleId });
        this.userSalary = data;
        this.drawer = true;
      } catch (error) {
        console.log(error);
      }
    },
    updateIdcard() {
      this.idcardDrawer = true;
    },
    leave() {
      this.$prompt('请输入密码', '员工离职', {
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
            await leaveRole(this.user);
            // console.log(this.user);
            this.$message.success('员工离职成功...');
            this.drawer = false;
          } catch (error) {
            console.log(error);
          } finally {
            this.getStaffPage();
          }
        })
        .catch(() => {
          this.$message({
            type: 'info',
            message: '取消编辑'
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
    isOut() {
      return this.isCurrent == `out` ? true : false;
    }
  },
  watch: {
    isCurrent: {
      async handler(newVal, oldVal) {
        if (newVal == `in`) {
          this.obj = { state: 1 };
        }
        if (newVal == `out`) {
          this.obj = { state: 0 };
        }
        await this.getStaffPage();
      },
      immediate: true
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
  .bg {
    height: 100%;
    width: 100%;
    margin-top: 25px;
    background-color: #fff;
    border-radius: 8px;
    .navbar {
      height: 70px;
      width: 100%;
      padding: 25px 25px 0 25px;
      border-bottom: 1px solid #e6e9f4;

      span {
        display: inline-block;
        margin-right: 15px;
        width: 120px;
        height: 44px;
        text-align: center;
        font-size: 16px;
        color: #5a607f;
        cursor: pointer;

        &:hover {
          color: #4458fe;
        }
      }

      .current {
        color: #4458fe;
        border-radius: 2px;
        border-bottom: 3px solid #4458fe !important;
      }
    }
    .table {
      padding: 25px;
      .top {
        padding-bottom: 15px;
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
      .bottom {
        .el-table {
          height: 467px;
        }
        .pagination {
          margin-top: 15px;
        }
      }
    }
  }
  .drawer {
    .drawer-bg {
      width: 100%;
      padding: 25px;
      .main {
        padding: 10px;
        box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
      }
    }
  }
}
</style>
