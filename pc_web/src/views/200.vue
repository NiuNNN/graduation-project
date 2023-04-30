<template>
  <div>
    <el-result icon="success" title="缴费成功" :subTitle="title">
      <template slot="extra">
        <el-button type="primary" size="medium" @click="close">返回</el-button>
      </template>
    </el-result>
  </div>
</template>

<script>
export default {
  data() {
    return {
      count: "", //倒计时时间
      isShowTimeHeader: 1, //三秒后需要改变的值
      timer: "",
      title: "",
    };
  },
  created() {
    this.countDown();
  },
  methods: {
    close() {
      window.close();
    },
    countDown() {
      const TIME_COUNT = 3;
      if (!this.timer) {
        this.count = TIME_COUNT;
        this.timer = setInterval(() => {
          if (this.count > 1 && this.count <= TIME_COUNT) {
            //限制倒计时区间
            this.count--;
            this.title = `即将返回主页面，${this.count}秒...`;
          } else {
            clearInterval(this.timer); //删除定时器
            this.timer = null;
            //三秒后关闭（三秒后需要进行的操作）
            this.isShowTimeHeader = 0;
            this.close();
          }
        }, 1000);
      }
    },
  },
};
</script>

<style></style>
