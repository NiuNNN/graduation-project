<template>
  <div class="container">
    <div class="bg">
      <div class="header">
        <span>财 务 信 息</span>
        <div class="financial">
          <el-row>
            <el-col :span="12">
              <profile-detail :label="`职业`" :value="`管理员`"></profile-detail>
            </el-col>
            <el-col :span="12">
              <profile-detail :label="`薪水`" :value="`￥123456`"></profile-detail>
            </el-col>
          </el-row>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import ProfileDetail from '@/components/utils/ProfileDetail.vue';
export default {
  components: {
    ProfileDetail
  }
};
</script>

<style lang="scss" scoped>
.container {
  width: 100%;
  height: 100%;
  .bg {
    margin-top: 65px;
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
    .financial {
      margin-top: 25px;
    }
  }
}
</style>
