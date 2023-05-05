<template>
  <div class="container-box">
    <div class="main-container">
      <div class="data">
        <h1 class="title">404 Error</h1>
        <p>请检查您输入的URL是否正确或点击</p>
        <p>下面的按钮返回主页。</p>
        <a @click="go()" class="button"> 返 回 主 页 </a>
      </div>

      <div class="ghost-img">
        <img src="@/assets/404/ghost-img.png" alt="" />
        <div class="ghost-shadow"></div>
      </div>
    </div>

    <div class="main-footer">
      <span>@NiuNiu 版权所有</span>
      <span>|</span>
      <span>+86 13924813103</span>
    </div>
  </div>
</template>

<script>
export default {
  methods: {
    go() {
      this.$router.push('/layout');
    }
  }
};
</script>

<style lang="less" scoped>
.container-box {
  display: grid;
  padding: 144px 0 32px;
  height: 100vh;
  color: #232253;
  font-size: 15px;
  font-weight: 500;
  box-sizing: border-box;
}

.main-container {
  display: grid;
  align-content: center;
  row-gap: 40px;
  max-width: 992px;
  margin-left: 24px;
  margin-right: 24px;
}
.data {
  text-align: center;
}

.title {
  font-size: 38px;
  margin: 12px 0;
}

.button {
  display: inline-block;
  margin-top: 32px;
  padding: 12px 24px;
  background-color: #232253;
  color: #fff;
  border-radius: 48px;
  transition: 0.4s;
  text-decoration: none;
}

.button:hover {
  box-shadow: 0 4px 12px rgba(34, 24, 6, 0.2);
}

.ghost-img {
  justify-self: center;

  img {
    width: 230px;
    animation: floaty 1.8s infinite alternate;
  }
}

@keyframes floaty {
  0% {
    transform: translateY(0);
  }
  100% {
    transform: translateY(15px);
  }
}

.ghost-shadow {
  width: 130px;
  height: 24px;
  background-color: rgba(60, 52, 38, 0.16);
  border-radius: 50%;
  margin: 0 auto;
  filter: blur(7px);
  animation: shadow 1.8s infinite alternate;
}

@keyframes shadow {
  0% {
    transform: scale(1, 1);
  }
  100% {
    transform: scale(0.85, 0.85);
  }
}

.main-footer {
  align-self: flex-end;
  display: flex;
  justify-content: center;
  font-size: 12px;
  column-gap: 8px;
}

@media screen and(max-width: 320px) {
  .container-box {
    padding-top: 112px;
  }
}

@media screen and(min-width: 992px) {
  .main-container {
    grid-template-columns: repeat(2, 1fr);
    align-items: center;
    column-gap: 32px;
  }

  .data {
    text-align: initial;
  }

  .ghost-img img {
    width: 400px;
  }

  .ghost-shadow {
    width: 250px;
    height: 40px;
  }
}

@media screen and(min-width: 1024px) {
  .main-container {
    margin-left: auto;
    margin-right: auto;
  }

  .container-box {
    font-size: 16px;
  }

  .title {
    font-size: 80px;
  }

  .main-footer {
    font-size: 13px;
  }
}

@media screen and(min-width: 2048px) {
  .container-box {
    zoom: 1.7;
  }

  .main-container {
    column-gap: 64px;
  }
}

@media screen and(min-width: 3840px) {
  .container-box {
    zoom: 3.1;
  }
}
</style>
