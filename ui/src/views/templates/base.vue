<template>
  <div class="app">
    <main-header></main-header>
    <div class="main clearfix">
      <el-col :span="24" class="content">
        <router-view></router-view>
      </el-col>
    </div>
    <main-footer></main-footer>
  </div>
</template>
<script>
import MainFooter from './partials/footer.vue'
import MainHeader from './partials//header.vue'
export default {
  name: 'base',
  components: {
    MainHeader,
    MainFooter
  }
}
</script>

<style lang="sass">
html, body
  margin: 0
  padding: 0
  font-family: "Helvetica Neue",Helvetica,"PingFang SC","Hiragino Sans GB","Microsoft YaHei","微软雅黑",Arial,sans-serif
  height: 100%

.app
  height: 100%
  background: #efefef

.side-nav-menu
  height: 100%
  border-right: 2px solid #e1e1e1

.main
  margin-top: -60px
  margin-bottom: -60px
  padding: 60px 0 60px 0
  min-height: 100%

.content
  padding: 0.5em

.breadcrumb-bar
  padding: 1em
</style>
