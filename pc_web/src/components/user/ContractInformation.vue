<template>
  <div class="table-container">
    <div class="bg">
      <span class="header">合 同 信 息</span>
      <div class="contract">
        <iframe v-if="isShow" frameborder="0" style="width: 100%; height: 500px" :src="pdfSrc"></iframe>
        <el-empty v-else description="获取为空"></el-empty>
      </div>
    </div>
  </div>
</template>

<script>
import { getContractByUserId } from '@/api/contract';
import { targetUrl } from '@/targetUrl.js';
export default {
  data() {
    return {
      pdfSrc: '',
      isShow: false
    };
  },
  created() {
    this.getContractByUserId();
  },
  methods: {
    async getContractByUserId() {
      const { data } = await getContractByUserId({ userId: this.$store.getters.userId });
      if (data != null) {
        this.pdfSrc = `${targetUrl}/view/Contract/${data.url}`;
      }
      // console.log(data);
    }
  },
  watch: {
    pdfSrc: {
      handler(newVal, oldVal) {
        if (newVal != '') {
          this.isShow = true;
        } else {
          this.isShow = false;
        }
      },
      immediate: true
    }
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
    .contract {
      margin-top: 25px;
      height: 500px;
    }
  }
}
</style>
