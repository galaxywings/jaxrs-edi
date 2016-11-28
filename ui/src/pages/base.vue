<template>
  <div class="outer-container">
    <el-row>
      <el-col :span="24">
        <main-header></main-header>
      </el-col>
    </el-row>
    <el-row class="content-container">
      <el-col :span="4" class="side-nav-menu">
        <side-nav></side-nav>
      </el-col>
      <el-col :span="20" class="content">
        <router-view></router-view>
      </el-col>
    </el-row>
    <el-row>
      <main-footer></main-footer>
    </el-row>
  </div>
</template>

<script>
import MainFooter from '../components/footer.vue'
import MainHeader from '../components/header.vue'
import SideNav from '../components/side-nav'
export default {
  name: 'base',
  components: {
    MainHeader,
    SideNav,
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

.outer-container
  height: 100%
  background: #efefef

.content-container
  height: 80%

.side-nav-menu
  height: 100%
  border-right: 2px solid #e1e1e1

.content
  padding: 0.5em

.breadcrumb-bar
  padding: 1em
</style>
