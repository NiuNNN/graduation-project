<template>
  <div class="main-container">
    <div class="header-container">
      <h1>住 户 管 理</h1>
    </div>
    <div>
      <create-user></create-user>
    </div>
    <div class="bg">
      <div class="navbar">
        <span :class="{ current: isCurrent == `in` }" @click="changeCurrent(`in`)">已 入 住 用 户</span>
        <span :class="{ current: isCurrent == `house` }" @click="changeCurrent(`house`)">待 添 加 房 间</span>
        <span :class="{ current: isCurrent == `pay` }" @click="changeCurrent(`pay`)">待 缴 纳 押 金</span>
        <span :class="{ current: isCurrent == `out` }" @click="changeCurrent(`out`)">已 退 房 用 户</span>
      </div>
      <div class="table" v-loading="loading">
        <div class="top">
          <el-form :inline="true" :model="searchForm" class="demo-form-inline" label-width="50px">
            <el-form-item label="姓名">
              <el-input v-model="searchForm.name" placeholder="请输入姓名"></el-input>
            </el-form-item>
            <el-form-item label="账号">
              <el-input v-model="searchForm.username" placeholder="请输入账号"></el-input>
            </el-form-item>
            <el-form-item label="房号" v-if="isCurrent != `house`">
              <el-input v-model="searchForm.houseName" placeholder="请输入房号"></el-input>
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
            <el-table-column prop="houseName" label="房号" width="100" v-if="isCurrent != `house`"> </el-table-column>
            <el-table-column prop="phone" label="手机"> </el-table-column>
            <el-table-column prop="idcard" label="身份证"> </el-table-column>
            <el-table-column prop="addtime" label="添加时间"> </el-table-column>
            <el-table-column label="操作" width="180">
              <template slot-scope="scope">
                <template v-if="isCurrent == `in`">
                  <el-button @click="openDrawer(scope.row)" size="mini" :disabled="isGet" type="primary">查看</el-button>
                </template>
                <template v-if="isCurrent == `house`">
                  <el-button @click="openAddHouseDrawer(scope.row)" size="mini" type="primary" :disabled="isAdd">添加</el-button>
                  <el-button size="mini" type="danger" :disabled="isDel" @click="deleteUser(scope.row)" slot="reference">删除</el-button>
                </template>
                <template v-if="isCurrent == `pay`">
                  <el-button @click="openPayDrawer(scope.row)" size="mini" :disabled="isAdd" type="primary">付款</el-button>
                </template>
                <template v-if="isCurrent == `out`">
                  <el-button @click="openDrawer(scope.row)" size="mini" :disabled="isGet" type="primary">查看</el-button>
                </template>
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

          <!-- 入住用户、退房用户页 -->
          <!-- 住客详细信息 -->
          <div class="drawer">
            <el-drawer title="住客详细信息" :visible.sync="drawer" :destroy-on-close="true" size="50%">
              <drawer-user-detail :tableData="miscellaneousData" :user="user" :isDel="isDel" :isEdit="isEdit" :isOut="isOut" @updateIdcard="updateIdcard" @updateHouse="updateHouse" @updateBasic="updateBasic"></drawer-user-detail>
            </el-drawer>
          </div>
          <!-- 修改详细信息 -->
          <div class="drawer">
            <el-drawer :wrapperClosable="false" :show-close="false" title="修改租客个人信息" :visible.sync="idcardDrawer" :destroy-on-close="true" size="50%">
              <update-user :user="user" @updateUser="updateUser"></update-user>
            </el-drawer>
          </div>
          <!-- 修改租房信息 -->
          <div class="drawer">
            <el-drawer :wrapperClosable="false" :show-close="false" title="修改租房信息" :visible.sync="houseDrawer" :destroy-on-close="true" size="50%">
              <component :is="updateHouseComponent" @afterUpdateHouse="afterUpdateHouse" @changeUpdateHouseComponent="changeUpdateHouseComponent" :user="user" :house="houseData" :miscellaneous="miscellaneousData"></component>
            </el-drawer>
          </div>
          <div class="drawer">
            <el-drawer :wrapperClosable="false" :show-close="false" title="添加杂费信息" :visible.sync="basicDrawer" :destroy-on-close="true" size="50%">
              <update-basic :table-data="miscellaneousData" :user="user" @deleteMiscellaneous="deleteMiscellaneous" @afterUpdateBasic="afterUpdateBasic" :isDel="isDel" :isAdd="isAdd"></update-basic>
            </el-drawer>
          </div>

          <!-- 待添加房间 -->
          <div class="drawer">
            <el-drawer title="办理入住" :wrapperClosable="false" :show-close="false" :visible.sync="addHouseDrawer" :destroy-on-close="true" size="50%">
              <login-house :user="user" @closeAddHouseDrawer="closeAddHouseDrawer"></login-house>
            </el-drawer>
          </div>

          <!-- 待支付 -->
          <div class="drawer">
            <el-drawer title="缴纳押金" :visible.sync="payDrawer" :destroy-on-close="true" size="50%">
              <pay :order="order" @closePayDrawer="closePayDrawer"></pay>
            </el-drawer>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { getUserPage, getNoHouseUserPage } from '@/api/user';
import CreateUser from '@/components/house/CreateUser.vue';
import * as permission from '@/utils/permission';
import { targetUrl } from '@/targetUrl';
import DrawerUserDetail from '@/components/house/DrawerUserDetail.vue';
import { getBasicByUserId } from '@/api/basic';
import { changeUserState } from '@/api/user';
import { getOrderByRentId } from '@/api/order';
import UpdateUser from '@/components/house/UpdateUser.vue';
import UpdateHouse from '@/components/house/UpdateHouse.vue';
import UpdateContract from '@/components/house/UpdateContract.vue';
import UpdateBasic from '@/components/house/UpdateBasic.vue';
import LoginHouse from '@/components/house/LoginHouse.vue';
import Pay from '@/components/financial/Pay.vue';
export default {
  components: {
    CreateUser,
    DrawerUserDetail,
    UpdateUser,
    UpdateHouse,
    UpdateContract,
    UpdateBasic,
    LoginHouse,
    Pay
  },
  data() {
    return {
      defaultImg: require('@/assets/image/avatar_default.png'),
      isCurrent: 'in',
      targetUrl,
      searchForm: {
        name: '',
        username: '',
        houseName: ''
      },
      tableData: [],
      pagination: {
        currentPage: 1, //当前页码
        pageSize: 6, //每页显示的记录数
        total: 0
      },
      loading: true,
      drawer: false,
      miscellaneousData: [],
      user: {},
      idcardDrawer: false,
      houseDrawer: false,
      updateHouseComponent: `UpdateHouse`,
      houseData: [],
      basicDrawer: false,
      addHouseDrawer: false,
      payDrawer: false,
      order: {}
    };
  },
  created() {
    this.getUserPage();
  },
  methods: {
    reset() {
      this.pagination = {
        currentPage: 1, //当前页码
        pageSize: 8, //每页显示的记录数
        total: 0
      };
      this.searchForm = {
        name: '',
        username: '',
        houseName: ''
      };
    },
    //切换
    async changeCurrent(name) {
      this.loading = true;
      this.isCurrent = name;
      this.reset();
      if (name == `in` || name == `out` || name == `pay`) {
        this.getUserPage();
      }
      if (name == `house`) {
        this.getNoHouseUserPage();
      }
    },
    //换页
    handleCurrentChange(currentPage) {
      this.pagination.currentPage = currentPage;
      if (this.isCurrent == `house`) {
        this.getNoHouseUserPage();
      } else {
        this.getUserPage();
      }
    },
    //已入住、退房、缴纳押金用户
    async getUserPage() {
      try {
        this.loading = true;
        let state = this.isCurrent != `out` ? '1' : '0';
        let orderState = this.isCurrent == `pay` ? '0' : '1';
        const param = `${this.pagination.currentPage}/${this.pagination.pageSize}`;
        const { data } = await getUserPage(param, {
          ...this.searchForm,
          state,
          orderState
        });
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
    //待添加房间用户
    async getNoHouseUserPage() {
      try {
        this.loading = true;
        const param = `${this.pagination.currentPage}/${this.pagination.pageSize}`;
        const { data } = await getNoHouseUserPage(param, this.searchForm);
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
    //搜索
    search() {
      this.loading = true;
      if (this.isCurrent == `in` || this.isCurrent == `out` || this.isCurrent == `pay`) {
        this.getUserPage();
      }
      if (this.isCurrent == `house`) {
        this.getNoHouseUserPage();
      }
    },
    //打开详情侧边栏
    openDrawer(row) {
      // console.log(row);
      this.user = row;
      this.getBasicByUserId(row);
      this.drawer = true;
    },
    //获取用户杂费
    async getBasicByUserId(row) {
      try {
        const { data } = await getBasicByUserId({ userId: row.userId });
        this.miscellaneousData = data;
      } catch (error) {
        console.log(error);
      }
    },
    //删除杂费回调
    deleteMiscellaneous() {
      this.getBasicByUserId(this.user);
    },
    //更新身份信息
    updateIdcard(e) {
      // console.log(e);
      this.user = e;
      this.idcardDrawer = true;
    },
    //修改完身份信息回调
    updateUser(e) {
      // console.log(e);
      this.user = e;
      this.getUserPage();
      this.idcardDrawer = false;
    },
    //修改住房信息
    updateHouse(e) {
      console.log(e);
      this.user = e;
      this.houseDrawer = true;
    },
    //修改房间回调
    afterUpdateHouse(e) {
      console.log(e);
      this.updateHouseComponent = `UpdateHouse`;
      this.houseData = {};
      this.user = e;
      this.getUserPage();
      this.houseDrawer = false;
    },
    //改变组件并传值
    changeUpdateHouseComponent(e) {
      console.log(e);
      this.updateHouseComponent = e.component;
      this.houseData = e.houseData;
    },
    updateBasic(e) {
      this.user = e;
      this.basicDrawer = true;
    },
    //修改杂费信息回调
    afterUpdateBasic(e) {
      this.miscellaneousData = e;
      this.basicDrawer = false;
    },
    //未添加房间页
    deleteUser(row) {
      this.$confirm('此操作将永久删除该用户, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(async () => {
          try {
            console.log(row);
            await changeUserState({ state: 2, userId: row.userId });
            this.$message.success('删除成功');
          } catch (error) {
            console.log(error);
          } finally {
            this.getNoHouseUserPage();
          }
        })
        .catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          });
        });
    },
    //打开添加房间页
    openAddHouseDrawer(row) {
      this.user = row;
      this.addHouseDrawer = true;
      // console.log(row);
    },
    //关闭弹窗
    closeAddHouseDrawer() {
      this.addHouseDrawer = false;
      this.getNoHouseUserPage();
    },
    //支付
    async openPayDrawer(row) {
      try {
        const { data } = await getOrderByRentId({ rentId: row.rentId });
        this.order = data;
        this.payDrawer = true;
      } catch (error) {
        console.log(error);
      }
    },
    //关闭支付弹窗
    closePayDrawer() {
      this.getUserPage();
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
    isOut() {
      return this.isCurrent == `out` ? true : false;
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
}
</style>
