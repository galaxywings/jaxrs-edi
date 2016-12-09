<template>
  <!-- since the id="app" has been defined in index.html already -->
  <router-view></router-view>
</template>

<script>
export default {
  name: 'app'
}
</script>

<style lang="sass">
  $icon-font-path: "../node_modules/bootstrap-sass/assets/fonts/bootstrap/"
  @import '../node_modules/bootstrap-sass/assets/stylesheets/_bootstrap'
</style>
