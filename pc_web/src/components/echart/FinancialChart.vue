<template>
  <div class="chart-container">
    <p>{{ title }}</p>
    <div id="chart-main"></div>
  </div>
</template>

<script>
import { getFinancialChart } from '@/api/financial';
export default {
  data() {
    return {
      source: []
    };
  },
  mounted() {
    this.drawChart();
  },
  methods: {
    async getFinancialChart() {
      try {
        const { data } = await getFinancialChart();
        this.source = data;
        // console.log(data);
      } catch (error) {
        console.log(error);
      }
    },
    async drawChart() {
      // 基于准备好的dom，初始化echarts实例  这个和上面的main对应
      // console.log(this.$echarts);
      await this.getFinancialChart();
      // console.log(this.source);
      let myChart = this.$echarts.init(document.getElementById('chart-main'));
      // 指定图表的配置项和数据
      let option = {
        legend: {},
        tooltip: {
          trigger: 'axis',
          showContent: false
        },
        dataset: {
          source: this.source
        },
        xAxis: { type: 'category' },
        yAxis: { gridIndex: 0 },
        grid: { top: '55%' },
        series: [
          {
            type: 'line',
            smooth: true,
            seriesLayoutBy: 'row',
            emphasis: { focus: 'series' }
          },
          {
            type: 'line',
            smooth: true,
            seriesLayoutBy: 'row',
            emphasis: { focus: 'series' }
          },
          {
            type: 'line',
            smooth: true,
            seriesLayoutBy: 'row',
            emphasis: { focus: 'series' }
          },
          {
            type: 'line',
            smooth: true,
            seriesLayoutBy: 'row',
            emphasis: { focus: 'series' }
          },
          {
            type: 'line',
            smooth: true,
            seriesLayoutBy: 'row',
            emphasis: { focus: 'series' }
          },
          {
            type: 'line',
            smooth: true,
            seriesLayoutBy: 'row',
            emphasis: { focus: 'series' }
          },
          {
            type: 'line',
            smooth: true,
            seriesLayoutBy: 'row',
            emphasis: { focus: 'series' }
          },
          {
            type: 'line',
            smooth: true,
            seriesLayoutBy: 'row',
            emphasis: { focus: 'series' }
          },
          {
            type: 'line',
            smooth: true,
            seriesLayoutBy: 'row',
            emphasis: { focus: 'series' }
          },
          {
            type: 'pie',
            id: 'pie',
            radius: '30%',
            center: ['50%', '30%'],
            emphasis: {
              focus: 'self'
            },
            label: {
              formatter: '{b}: {@4} ({d}%)'
            },
            encode: {
              itemName: 'Financial',
              value: '4',
              tooltip: '4'
            }
          }
        ]
      };
      // console.log(option);
      // 使用刚指定的配置项和数据显示图表。
      myChart.on('updateAxisPointer', function (event) {
        const xAxisInfo = event.axesInfo[0];
        if (xAxisInfo) {
          const dimension = xAxisInfo.value + 1;
          myChart.setOption({
            series: {
              id: 'pie',
              label: {
                formatter: '{b}: {@[' + dimension + ']} ({d}%)'
              },
              encode: {
                value: dimension,
                tooltip: dimension
              }
            }
          });
        }
      });
      myChart.setOption(option);
    }
  },
  computed: {
    title() {
      const date = new Date();
      return `${date.getFullYear()}年 财务状况`;
    }
  }
};
</script>

<style lang="scss" scoped>
.chart-container {
  padding-top: 15px;
  width: 100%;
  height: 100%;
  p {
    text-align: center;
    color: #6a74a5;
    font-weight: 600;
    font-size: 14px;
  }
  #chart-main {
    padding-top: 5%;
    width: 100%;
    height: 90%;
  }
}
</style>
