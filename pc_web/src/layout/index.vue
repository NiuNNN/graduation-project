<template>
  <div id="app">
    <Container>
      <template #navbar> <Navbar></Navbar> </template>
      <template #sidebar><Sidebar></Sidebar> </template>
      <template #appmain></template>
    </Container>
  </div>
</template>

<script>
import Container from '@/layout/components/container.vue';
import Navbar from '@/layout/components/Navbar/Navbar.vue';
import Sidebar from './components/Sidebar/Sidebar.vue';
export default {
  components: {
    Container,
    Navbar,
    Sidebar
  },
  methods: {},
  data() {
    return {};
  }
};
</script>

<style lang="scss" scoped>
#app {
  width: 100%;
  height: 100%;
}
</style>
