<template>
  <div class="container">
    <div class="bg" style="padding-top: 5px">
      <div class="main">
        <el-descriptions title=" 租客信息" :column="3">
          <template slot="extra">
            <el-button
              type="primary"
              size="small"
              @click="updateIdcard"
              :disabled="isEdit || isOut"
              >操作</el-button
            >
          </template>
          <el-descriptions-item label="账号">{{
            user.username
          }}</el-descriptions-item>
          <el-descriptions-item label="姓名">{{
            user.name
          }}</el-descriptions-item>
          <el-descriptions-item label="性别">{{
            user.sex
          }}</el-descriptions-item>
          <el-descriptions-item label="民族">{{
            user.nation
          }}</el-descriptions-item>
          <el-descriptions-item label="生日">{{
            user.birthday
          }}</el-descriptions-item>
          <el-descriptions-item label="身份证">{{
            user.idcard
          }}</el-descriptions-item>
          <el-descriptions-item label="手机号">{{
            user.phone
          }}</el-descriptions-item>
          <el-descriptions-item label="签订时间">{{
            user.sign
          }}</el-descriptions-item>
          <el-descriptions-item label="过期时间">{{
            user.lose
          }}</el-descriptions-item>
          <el-descriptions-item label="签订机构">{{
            user.authority
          }}</el-descriptions-item>
          <el-descriptions-item label="地址">{{
            user.address
          }}</el-descriptions-item>
        </el-descriptions>
      </div>
    </div>
    <div class="bg">
      <div class="main">
        <el-descriptions title=" 租房信息" :column="3">
          <template slot="extra">
            <el-button
              type="primary"
              size="small"
              @click="updateHouse"
              :disabled="isEdit || isOut"
              >操作</el-button
            >
          </template>
          <el-descriptions-item label="房号">{{
            user.houseName
          }}</el-descriptions-item>
          <el-descriptions-item label="房型">{{
            user.styleName
          }}</el-descriptions-item>
          <el-descriptions-item label="面积(㎡)">{{
            user.area
          }}</el-descriptions-item>
          <el-descriptions-item label="阳台">{{
            user.balcony
          }}</el-descriptions-item>
          <el-descriptions-item label="租金(月/元)">{{
            user.price
          }}</el-descriptions-item>
          <el-descriptions-item label="登记时间">{{
            user.loginRent
          }}</el-descriptions-item>
          <el-descriptions-item label="离开时间">{{
            user.logoutRent
          }}</el-descriptions-item>
        </el-descriptions>
      </div>
    </div>
    <div class="bg">
      <div class="main">
        <el-descriptions title=" 杂费信息">
          <template slot="extra">
            <el-button
              type="primary"
              size="small"
              @click="updateBasic"
              :disabled="isEdit || isOut"
              >操作</el-button
            >
          </template></el-descriptions
        >
        <el-table
          :data="tableData"
          stripe
          style="width: 100%"
          height="250"
          size="small"
        >
          <el-table-column prop="baseName" label="收费名称"> </el-table-column>
          <el-table-column prop="price" label="价格(月/元)" width="120">
          </el-table-column>
          <el-table-column prop="remark" label="收费详情" width="280">
          </el-table-column>
          <el-table-column prop="time" label="录入时间"> </el-table-column>
          <template #empty>
            <el-empty :image-size="80"></el-empty>
          </template>
        </el-table>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    user: {
      type: Object,
      default: () => {
        return {};
      },
    },
    tableData: {
      type: Array,
      default: () => {
        return [];
      },
    },
    isEdit: {
      type: Boolean,
      default: true,
    },
    isDel: {
      type: Boolean,
      default: true,
    },
    isOut: {
      type: Boolean,
      default: true,
    },
  },
  methods: {
    // 更新身份信息
    updateIdcard() {
      this.$emit(`updateIdcard`, this.user);
    },
    //修改租房信息
    updateHouse() {
      this.$emit(`updateHouse`, this.user);
    },
    //修改杂费信息
    updateBasic() {
      this.$emit(`updateBasic`, this.user);
    },
  },
};
</script>

<style lang="scss" scoped>
.container {
  .bg {
    width: 100%;
    padding: 25px 25px 0 25px;
    .main {
      padding: 10px;
      box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
    }
  }
}
</style>
