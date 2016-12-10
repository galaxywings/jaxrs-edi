<template>
  <div>
  <el-row :gutter="10">
    <el-col :span="6">
      <div class="panel mini-box" @click="$router.push('/customers')">
        <span class="btn-icon-round" style="background:#20A0FF">
          <icon name="address-card" scale="3"></icon>
        </span>
        <span>Customers</span>
      </div>
    </el-col>
    <el-col :span="6">
      <div class="panel mini-box"  @click="$router.push('/task/services')">
        <span class="btn-icon-round" style="background:#F7BA2A">
          <icon name="cogs" scale="3"></icon>
        </span>
        <span>Services</span>
      </div>
    </el-col>
    <el-col :span="6">
      <div class="panel mini-box"  @click="$router.push('/users')">
        <span class="btn-icon-round" style="background:#13CE66">
          <icon name="group" scale="3"></icon>
        </span>
        <span>Users</span>
      </div>
    </el-col>

    <el-col :span="6">
      <div class="panel mini-box">
        <span class="btn-icon-round" style="background:#FF4949">
          <icon name="bar-chart" scale="3"></icon>
        </span>
        <span>Reports</span>
      </div>
    </el-col>
  </el-row>
  </div>
</template>

<script>
import Icon from 'vue-awesome'
export default {
  components: {
    Icon
  }
}
</script>

<style lang="sass" scoped>
  body
    margin: 0px
  .el-row
    margin-top: 10px
    margin-botton: 10px

  .btn-icon-round
    display: inline-block
    text-align: center
    border-radius: 50%
    height: 50px
    width: 50px
    padding: 15px
    box-sizing: content-box
  .fa-icon
    color: #fff
  .panel
    box-shadow: 0 0 3px rgba(0,0,0,0.1)
    background-color: #fff
    border: 1px solid transparent
    box-shadow: 0 1px 1px rgba(0,0,0,0.05)
  .mini-box
    min-height: 120px
    padding: 25px
</style>

