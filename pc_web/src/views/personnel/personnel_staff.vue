<template>
  <div class="main-container">
    <div class="header-container">
      <h1>员 工 管 理</h1>
    </div>
    <div class="table-container">
      <div class="navbar">
        <span :class="{ current: isCurrent == `Staff` }" @click="change(`Staff`)">员 工 总 览</span>
        <template v-if="roleId">
          <span :class="{ current: isCurrent == `Permission` }" @click="change(`Permission`)">管 理 员</span>
        </template>
      </div>
      <div>
        <div class="table">
          <component :is="isCurrent"></component>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import Permission from '@/components/personal/permission.vue';
import Staff from '@/components/personal/staff.vue';
export default {
  components: {
    Permission,
    Staff
  },
  data() {
    return {
      isCurrent: 'Staff'
    };
  },
  methods: {
    change(name) {
      this.isCurrent = name;
    }
  },
  computed: {
    roleId() {
      return this.$store.getters.roleId === 2 ? true : false;
    }
  }
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
    }
    .table {
      padding: 25px;
    }
  }
}
</style>
