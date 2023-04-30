<template>
  <div class="profile-detail">
    <p>{{ label }}</p>
    <div class="val">{{ value }}</div>
  </div>
</template>

<script>
export default {
  props: {
    label: String,
    value: String
  }
};
</script>

<style lang="scss" scoped>
.profile-detail {
  p {
    margin: 10px 0;
    font-size: 14px;
    color: #3a3f63;
  }
  .val {
    width: 95%;
    padding: 5px;
    height: 40px;
    line-height: 40px;
    border-radius: 4px;
    font-size: 14px;
    color: #858ebd;
    line-height: 30px;
    border: 1px solid #dae0f2;
  }
}
</style>
