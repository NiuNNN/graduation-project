<template>
  <div class="container">
    <img src="@/assets/logo.png" alt="" />
    <span>PRHMS</span>
  </div>
</template>

<script>
export default {};
</script>

<style lang="less" scoped>
.container {
  width: 100%;
  height: 60px;
  display: flex;
  align-items: center;
  background-color: #fff;
  padding-left: 10px;
  img {
    height: 48px;
  }
  span {
    color: #232253;
    margin-left: 5px;
    height: 60px;
    line-height: 75px;
    font-weight: 700;
    font-size: 18px;
  }
}
</style>
