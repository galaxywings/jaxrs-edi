<template>
  <el-menu default-active="1"
    class="el-menu-vertical-demo"
    router="true"
    @select="handleSelect">
    <el-submenu index="1">
      <template slot="title"><i class="el-icon-message"></i>导航1</template>
      <el-menu-item-group title="分组一">
        <el-menu-item index="1-1">选项1</el-menu-item>
        <el-menu-item index="1-2">选项2</el-menu-item>
      </el-menu-item-group>
      <el-menu-item-group title="分组2">
        <el-menu-item index="1-3">选项3</el-menu-item>
      </el-menu-item-group>
    </el-submenu>
    <el-menu-item index="/auth/index">
      <i class="el-icon-menu"></i>Auth
    </el-menu-item>
    <el-menu-item index="/customer/index">
      <i class="el-icon-setting"></i>客户管理
    </el-menu-item>
    <el-submenu index="/task">
      <template slot="title">
        任务管理
      </template>
      <el-menu-item index="/task/services">Service</el-menu-item>
      <el-menu-item index="/task/processes">Process</el-menu-item>
    </el-submenu>
    <el-menu-item index="/misc">
      <i class="el-icon-setting"></i>杂项
    </el-menu-item>
  </el-menu>
</template>

<script>
</script>

<style>

</style>
