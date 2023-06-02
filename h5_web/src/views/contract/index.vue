<template>
  <div class="container">
    <div class="header">
      <Header title="合同信息" path="/"></Header>
    </div>
    <div class="main">
      <iframe frameborder="0" style="width: 100%; height: 607px" :src="pdfSrc"></iframe>
      <van-empty description="未查找到合同信息" />
    </div>
  </div>
</template>

<script>
import { getContract } from '@/api/contract';
import { targetUrl } from '@/targetUrl';
import Header from '@/components/utils/HeaderVue.vue';
export default {
  components: {
    Header
  },
  data() {
    return {
      pdfSrc: ''
    };
  },
  created() {
    this.getContract();
  },
  methods: {
    async getContract() {
      try {
        const { data } = await getContract({
          userId: this.$store.getters.userId
        });
        this.pdfSrc = `http://niuniu66.natapp1.cc/pdfjs/web/viewer.html?file=${encodeURIComponent(`${targetUrl}/view/Contract/${data.url}`)}`;
        // console.log(this.pdfSrc);
      } catch (error) {
        console.log(error);
      }
    }
  }
};
</script>

<style lang="less" scoped>
.container {
  .header {
    height: 60px;
  }
}
</style>
