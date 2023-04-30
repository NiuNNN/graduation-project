<template>
  <div class="login-container">
    <div class="banner"></div>
    <div class="form-container">
      <div class="phone clearfix">
        <span>Contact number</span>
        <span>+86 13924813103</span>
      </div>
      <slot></slot>
    </div>
  </div>
</template>

<script>
export default {};
</script>

<style lang="scss" scoped>
@import "@/styles/index.scss";
.login-container {
  display: flex;
  width: 1440px / $rootSize;
  height: 900px / $rootSize;
  box-shadow: 0px 4px 16px #b3c0e7;
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  border: 1px solid #dae0f2;
  border-radius: 30px;
  .banner {
    position: relative;
    width: 480px / $rootSize;
    height: 900px / $rootSize;
    background: url("@/assets/image/Banner.png") no-repeat;
    background-size: cover;
  }
  .form-container {
    position: relative;
    width: 920px / $rootSize;
    height: 900px / $rootSize;
    background-color: #fff;
    .phone {
      margin-top: 15px / $rootSize;
      text-align: right;
      font-size: 16px / $rootSize;
      span {
        margin-left: 5px / $rootSize;
      }
    }
  }
}
</style>
