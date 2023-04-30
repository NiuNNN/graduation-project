<template>
  <div @click="preview()" :disabled="isGet" style="cursor: pointer">
    <el-card :body-style="{ padding: '5px' }" shadow="hover">
      <img src="@/assets/image/pdfImage.jpg" />
      <div>
        <h3>{{ name }}</h3>
      </div>
    </el-card>
  </div>
</template>

<script>
import * as permission from "@/utils/permission";
import { targetUrl } from "@/targetUrl.js";
export default {
  props: {
    name: {
      type: String,
      default: "合同",
    },
    contractObj: {
      type: Object,
      default: function () {
        return {};
      },
    },
  },
  methods: {
    preview() {
      this.$emit(
        "preview",
        `${targetUrl}/view/Contract/${this.contractObj.url}`
      );
    },
    download() {
      this.$emit("download");
      console.log("download");
    },
    del() {
      this.$emit("del");
      console.log("del");
    },
  },
  computed: {
    isAdd() {
      return permission.isAdd(this.$route.params.type);
    },
    isDel() {
      return permission.isDel(this.$route.params.type);
    },
    isUp() {
      return permission.isUp(this.$route.params.type);
    },
    isPull() {
      return permission.isPull(this.$route.params.type);
    },
    isEdit() {
      return permission.isEdit(this.$route.params.type);
    },
    isGet() {
      return permission.isGet(this.$route.params.type);
    },
  },
};
</script>

<style lang="scss" scoped>
img {
  display: block;
  margin: 0 auto;
  text-align: center;
  width: 100%;
}
h3 {
  text-align: center;
  font-size: 14px;
  font-weight: 400;
  color: #5a607f;
}
</style>
