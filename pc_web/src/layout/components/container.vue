<template>
  <div class="container">
    <el-container>
      <el-header>
        <slot name="navbar"></slot>
      </el-header>
      <el-container>
        <el-aside width="250px">
          <slot name="sidebar"></slot>
        </el-aside>
        <el-main>
          <router-view></router-view>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script>
export default {};
</script>
<style lang="scss" scoped>
@import '@/styles/container.scss';
</style>
