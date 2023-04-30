<template>
  <div class="container">
    <div class="btn">
      <el-button class="cancel">取 消</el-button>
      <el-button class="save" @click="updateHandle">更 新</el-button>
    </div>
    <div class="bg">
      <span class="header">个 人 信 息</span>
      <div class="profile">
        <el-row>
          <el-col :span="12">
            <profile-detail :label="`姓 名`" :value="user.name"></profile-detail>
          </el-col>
          <el-col :span="12">
            <profile-detail :label="`账 号`" :value="user.username"></profile-detail>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <profile-detail :label="`电 话`" :value="user.phone"></profile-detail>
          </el-col>
          <el-col :span="12">
            <profile-detail :label="`性 别`" :value="user.sex"></profile-detail>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <profile-detail :label="`民 族`" :value="user.nation"></profile-detail>
          </el-col>
          <el-col :span="12">
            <profile-detail :label="`生 日`" :value="user.birthday"></profile-detail>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <profile-detail :label="`身 份 证`" :value="user.idcard"></profile-detail>
          </el-col>
          <el-col :span="12">
            <profile-detail :label="`签 订 时 间`" :value="user.sign"></profile-detail>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <profile-detail :label="`过 期 时 间`" :value="user.lose"></profile-detail>
          </el-col>
          <el-col :span="12">
            <profile-detail :label="`签 订 机 构`" :value="user.authority"></profile-detail>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <p>地 址</p>
            <div class="val">{{ user.address }}</div>
          </el-col>
        </el-row>
      </div>
    </div>
  </div>
</template>

<script>
import ProfileDetail from '@/components/utils/ProfileDetail.vue';
export default {
  components: {
    ProfileDetail
  },
  computed: {
    user() {
      return this.$store.getters.user;
    }
  },
  methods: {
    updateHandle() {}
    //获取user数据
  }
};
</script>

<style lang="scss" scoped>
.container {
  width: 100%;
  height: 100%;
  .btn {
    margin-bottom: 25px;
    text-align: right;
    .cancel {
      width: 100px;
      height: 40px;
      border-radius: 27px;
      background-color: #fff;
      font-size: 16px;
      color: #4458fe;
      box-shadow: 0px 4px 16px rgba(179, 192, 231, 0.32);
    }
    .save {
      width: 100px;
      height: 40px;
      border-radius: 27px;
      background: linear-gradient(45deg, #4f8aff 0%, #4b5eff 100%);
      background-blend-mode: normal;
      box-shadow: 0px 4px 16px #b3c0e7;
      color: #fff;
      font-size: 16px;
    }
  }
  .bg {
    margin-bottom: 25px;
    width: 100%;
    height: 100%;
    padding: 25px;
    border-radius: 8px;
    background-color: #fff;
    .header {
      font-size: 16px;
      color: #3a3f63;
    }
    .profile {
      margin-top: 25px;
      p {
        margin: 10px 0;
        font-size: 14px;
        color: #3a3f63;
      }
      .val {
        width: 98%;
        padding: 5px;
        height: 40px;
        line-height: 40px;
        border-radius: 4px;
        font-size: 14px;
        color: #858ebd;
        line-height: 30px;
        border: 1px solid #dae0f2;
      }
    }
  }
}
</style>
