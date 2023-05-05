<template>
  <div class="chart-container">
    <div id="chart-main"></div>
  </div>
</template>

<script>
import { getUserChart } from '@/api/cost';
export default {
  data() {
    return {
      series: []
    };
  },
  mounted() {
    this.drawChart();
  },
  methods: {
    async getUserChart() {
      try {
        const { data } = await getUserChart({ userId: this.$store.getters.userId });
        this.series = data;
        // console.log(this.series);
      } catch (error) {
        console.log(error);
      }
    },
    async drawChart() {
      // 基于准备好的dom，初始化echarts实例  这个和上面的main对应
      // console.log(this.$echarts);
      await this.getUserChart();
      console.log(this.series);
      let myChart = this.$echarts.init(document.getElementById('chart-main'));
      // 指定图表的配置项和数据
      let option = {
        title: {
          text: this.title
        },
        tooltip: {
          trigger: 'axis'
        },
        legend: {
          data: ['用水量(方)', '用电量(度)']
        },
        xAxis: {
          type: 'category',
          data: ['一月', '二月', '三月', '四月', '五月', '六月', '七月', '八月', '九月', '十月', '十一月', '十二月']
        },
        yAxis: {
          type: 'value'
        },
        grid: {
          left: '3%',
          right: '4%',
          bottom: '3%',
          containLabel: true
        },
        series: this.series
      };
      // console.log(option);
      // 使用刚指定的配置项和数据显示图表。
      myChart.setOption(option);
    }
  },
  computed: {
    title() {
      const date = new Date();
      return `${date.getFullYear()}年度`;
    }
  }
};
</script>

<style lang="less" scoped>
.chart-container {
  width: 100%;
  height: 100%;
  #chart-main {
    padding-top: 5%;
    width: 100%;
    height: 90%;
  }
}
</style>
