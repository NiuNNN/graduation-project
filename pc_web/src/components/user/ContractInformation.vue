<template>
  <div class="table-container">
    <div class="bg">
      <span class="header">合 同 信 息</span>
      <iframe frameborder="0" style="width: 100%; height: 500px" :src="pdfSrc"></iframe>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      pdfSrc: ''
    };
  }
};
</script>
<style lang="scss" scoped>
.table-container {
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
  }
}
</style>
