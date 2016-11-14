<template>
  <!-- since the id="app" has been defined in index.html already -->
  <router-view></router-view>
</template>

<script>
import Base from './pages/base'
export default {
  name: 'app',
  component: Base
}
</script>
