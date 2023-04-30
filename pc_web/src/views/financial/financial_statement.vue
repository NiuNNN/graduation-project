<template>
  <div class="main-container">
    <div class="header-container">
      <h1>财 务 报 表</h1>
    </div>
    <div class="table-container">
      <div class="navbar">
        <span
          :class="{ current: isCurrent == `HouseFinancial` }"
          @click="changeCurrent(`HouseFinancial`)"
          >住 房 报 表</span
        >
        <span
          :class="{ current: isCurrent == `all` }"
          @click="changeCurrent(`all`)"
          >全 部</span
        >
      </div>
      <div class="table">
        <component :is="isCurrent"></component>
      </div>
    </div>
  </div>
</template>
<script>
import HouseFinancial from "@/components/financial/HouseFinancial.vue";
export default {
  components: {
    HouseFinancial,
  },
  data() {
    return {
      isCurrent: "HouseFinancial",
    };
  },
  methods: {
    changeCurrent(name) {
      this.isCurrent = name;
    },
  },
};
</script>

<style lang="scss" scoped>
.main-container {
  .header-container {
    display: flex;
    justify-content: space-between;

    h1 {
      font-size: 24px;
      line-height: 40px;
      color: #3a3f63;
      font-weight: 400;
    }
  }
  .table-container {
    height: 100%;
    width: 100%;
    margin-top: 25px;
    background-color: #fff;
    border-radius: 8px;
    padding: 25px;
    .navbar {
      height: 70px;
      width: 100%;
      padding: 25px 25px 0 25px;
      border-bottom: 1px solid #e6e9f4;

      span {
        display: inline-block;
        margin-right: 15px;
        width: 120px;
        height: 44px;
        text-align: center;
        font-size: 16px;
        color: #5a607f;
        cursor: pointer;

        &:hover {
          color: #4458fe;
        }
      }

      .current {
        color: #4458fe;
        border-radius: 2px;
        border-bottom: 3px solid #4458fe !important;
      }

      .new {
        font-size: 20px !important;
        margin-left: 5px;
        color: #ff2855;
        line-height: 5px;
      }
    }
    .pagination {
      margin-top: 15px;
    }
  }
  .drawer-container {
    width: 90%;
    margin: 0 auto;
    .table {
      margin-bottom: 15px;
      h3 {
        margin-bottom: 5px;
        font-size: 14px;
        font-weight: 400;
      }
      ::v-deep .el-input {
        height: 23px !important;
        .el-input__inner {
          font-size: 12px !important;
          height: 23px !important;
          border: none !important;
        }
      }
    }
  }
}
</style>
