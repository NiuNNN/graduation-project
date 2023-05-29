<template>
  <div class="container">
    <div class="bg">
      <div class="header">
        <span>财 务 信 息</span>
        <div class="financial">
          <el-row>
            <el-col :span="12">
              <profile-detail :label="`职位`" :value="role.roleName"></profile-detail>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="12">
              <profile-detail label="基础工资" :value="base"></profile-detail>
            </el-col>
            <el-col :span="12">
              <profile-detail label="实习工资" :value="probation"></profile-detail>
            </el-col>
            <el-col :span="12" v-for="(item, index) in elseSalary" :key="index">
              <profile-detail :label="item.salaryName" :value="item.price"></profile-detail>
            </el-col>
          </el-row>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import ProfileDetail from '@/components/utils/ProfileDetail.vue';
import { getUserSalaryByUserId } from '@/api/salary';
import { getUserRole } from '@/api/role';
export default {
  components: {
    ProfileDetail
  },
  data() {
    return {
      role: {},
      baseSalary: [],
      probationSalary: [],
      elseSalary: [],
      base: '0.00',
      probation: '0.00'
    };
  },
  created() {
    this.getAll();
  },
  methods: {
    async getAll() {
      try {
        const { data } = await getUserSalaryByUserId({ userId: this.$store.getters.userId });
        // console.log(data);
        this.baseSalary = data.baseSalary;
        this.probationSalary = data.probationSalary;
        this.elseSalary = data.elseSalary;
        if (this.baseSalary.length > 0) {
          this.base = this.baseSalary[0].price;
          this.probation = this.probationSalary[0].price;
        }
        const { data: role } = await getUserRole({ userId: this.$store.getters.userId });
        this.role = role;
      } catch (error) {
        console.log(error);
      }
    }
  }
};
</script>

<style lang="scss" scoped>
.container {
  width: 100%;
  height: 100%;
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
    .financial {
      margin-top: 25px;
    }
  }
}
</style>
