<template>
  <div class="container">
    <div class="header">
      <Header title="公 告 信 息"></Header>
    </div>
    <div class="main">
      <van-list v-model="loading" :finished="finished" finished-text="没有更多了" @load="onLoad">
        <article-item v-for="item in list" :key="item.id" :item="item"></article-item>
      </van-list>
    </div>
  </div>
</template>

<script>
import articleItem from '@/components/report/article-item.vue';
import Header from '@/components/utils/HeaderVue.vue';
import { getReportPage } from '@/api/report';
export default {
  components: {
    Header,
    articleItem
  },
  data() {
    return {
      currentPage: 1, //当前页码
      pageSize: 5, //每页显示的记录数
      list: [],
      loading: false, // 默认非加载状态
      finished: false // 默认数据未加载完
    };
  },
  methods: {
    // 触发时机(onLoad会被触发多次！):
    // 1. 一进入页面，如果数据没有撑满整个屏幕  list: [] 需要加载更多，触发load事件
    // 2. 当用户往下滑动，触底时，需要加载更多，触发load事件
    async onLoad() {
      //   console.log("需要加载更多了, 应该要发送请求了...");
      try {
        const param = `${this.currentPage}/${this.pageSize}`;
        const { data } = await getReportPage(param, { state: 1 });
        //   console.log(data);
        // 需要累加数据
        this.list.push(...data.records);
        //   console.log(this.list);
        // 注意：加载完成数据后，需要将loading状态改成false，表示加载已完成
        this.loading = false;
        // 让 当前页 + 1，下一次加载下一页的数据
        this.currentPage++;
        // 如果下一页的页码，已经大于最大页码数，说明数据已经加载完成了, 需要将finished改成true
        if (this.currentPage > data.total) {
          this.finished = true;
        }
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
  .main {
    .bg {
      margin-bottom: 15px;
    }
  }
}
</style>
