<template>
  <div class="pie-container">
    <div id="pie-main"></div>
  </div>
</template>

<script>
export default {
  props: {
    num: {
      type: Object,
      default: () => {
        return {};
      }
    }
  },
  data() {
    return {};
  },
  created() {
    this.setTime();
  },
  methods: {
    setTime() {
      let _this = this;
      setTimeout(function () {
        _this.drawChart();
      }, 1000);
    },
    drawChart() {
      // console.log(this.num);
      // 基于准备好的dom，初始化echarts实例  这个和上面的main对应
      let myChart = this.$echarts.init(document.getElementById('pie-main'));
      // 指定图表的配置项和数据
      let option = {
        tooltip: {
          trigger: 'item'
        },
        legend: {
          top: '5%',
          left: 'center',
          // doesn't perfectly work with our tricks, disable it
          selectedMode: false
        },
        series: [
          {
            name: '房 屋 状 况',
            type: 'pie',
            radius: ['40%', '70%'],
            center: ['50%', '70%'],
            // adjust the start angle
            startAngle: 180,
            label: {
              show: true,
              formatter(param) {
                // correct the percentage
                return param.name + ' (' + param.percent * 2 + '%)';
              }
            },
            data: [
              { value: this.num.userNum, name: '住 户 数 量' },
              { value: this.num.outNum, name: '退 房 数 量' },
              { value: this.num.noneNum, name: '空 房 数 量' },
              {
                // make an record to fill the bottom 50%
                value: this.num.houseNum,
                itemStyle: {
                  // stop the chart from rendering this piece
                  color: 'none',
                  decal: {
                    symbol: 'none'
                  }
                },
                label: {
                  show: false
                }
              }
            ]
          }
        ]
      };
      // console.log(option);
      myChart.setOption(option);
    }
  }
};
</script>

<style lang="scss" scoped>
.pie-container {
  padding-top: 15px;
  width: 100%;
  height: 100%;
  p {
    text-align: center;
    color: #6a74a5;
    font-weight: 600;
    font-size: 14px;
  }
  #pie-main {
    padding-top: 5%;
    width: 100%;
    height: 90%;
  }
}
</style>
