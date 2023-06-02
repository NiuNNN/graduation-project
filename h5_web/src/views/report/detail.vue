<template>
  <div class="container">
    <div class="nav">
      <Header title="公 告 详 情"></Header>
    </div>
    <div class="main">
      <header class="header">
        <h1>{{ article.stem }}</h1>
        <p>
          {{ article.time }} |
          <van-icon name="eye-o" style="padding: 0 5px" />
          {{ article.views }} 浏览量
        </p>
        <p>
          <img :src="article.avatar" alt="" />
          <span>{{ article.name }}</span>
        </p>
      </header>
      <main class="body" v-html="article.content"></main>
    </div>
  </div>
</template>

<script>
import Header from '@/components/utils/HeaderVue.vue';
import { getReportPage, updatePreview } from '@/api/report';
import { targetUrl } from '@/targetUrl';
export default {
  components: {
    Header
  },
  data() {
    return {
      article: {
        avatar: ''
      },
      currentPage: 1, //当前页码
      pageSize: 5 //每页显示的记录数
    };
  },
  async created() {
    this.getDetail();
  },
  methods: {
    async getDetail() {
      try {
        this.$toast.loading();
        await updatePreview({ reportId: this.$route.params.id });
        const param = `${this.currentPage}/${this.pageSize}`;
        const { data } = await getReportPage(param, {
          reportId: this.$route.params.id,
          state: 1
        });
        // console.log(data);
        this.article = data.records[0];
        this.article.avatar = `${targetUrl}/view/Avatar/${this.article.avatar}`;
      } catch (error) {
        console.log(error);
      }
    }
  }
};
</script>

<style lang="less" scoped>
.container {
  .nav {
    height: 60px;
  }
  .main {
    padding: 0 15px;
    .header {
      h1 {
        font-size: 24px;
      }
      p {
        color: #999;
        font-size: 12px;
        display: flex;
        align-items: center;
      }
      img {
        width: 40px;
        height: 40px;
        border-radius: 50%;
        overflow: hidden;
      }
      h1,
      h2,
      h3,
      h4,
      h5,
      img,
      p {
        padding: 5px 5px 5px 0;
      }
    }
  }
}
</style>
