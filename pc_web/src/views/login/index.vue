<template>
  <Index>
    <template>
      <component :is="loginComponent"></component>
    </template>
  </Index>
</template>

<script>
import { mapGetters } from 'vuex';
import Index from '@/components/Login/LoginIndex.vue';
import Login from '@/components/Login/LoginPage.vue';
import Forget from '@/components/Login/ForgetPage.vue';
export default {
  components: {
    Index,
    Login,
    Forget
  },
  data() {
    return {};
  },
  computed: {
    ...mapGetters(['loginComponent'])
  }
};
</script>

<style lang="scss" scoped></style>
