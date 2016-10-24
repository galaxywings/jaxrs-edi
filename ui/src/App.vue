<template>
  <div id="app">
    <el-row>
      <el-col :span="24">
        <main-header></main-header>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="4">
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
import MainFooter from './components/footer.vue'
import MainHeader from './components/header.vue'
import SideNav from './components/side-nav'
export default {
  name: 'app',
  components: {
    MainHeader,
    SideNav,
    MainFooter
  }
}
</script>

<style lang="sass">
body
  margin: 0
  font-family: "Helvetica Neue",Helvetica,"PingFang SC","Hiragino Sans GB","Microsoft YaHei","微软雅黑",Arial,sans-serif

.content
  padding: 10px
  background: #efefef
</style>
