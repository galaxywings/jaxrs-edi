<template>
  <el-menu default-active="1"
    :router="true">
    <el-menu-item index="/auth">
      <i class="el-icon-setting"></i>Auth
    </el-menu-item>
    <el-submenu index="/customers">
      <template slot="title"><i class="el-icon-setting"></i>客户管理</template>
      <el-menu-item index="/customers/create">新建客户</el-menu-item>
      <el-menu-item index="/customers">客户列表</el-menu-item>
    </el-submenu>
    <el-submenu index="/task">
      <template slot="title"><i class="el-icon-setting"></i>任务管理</template>
      <el-menu-item-group title="Service">
        <el-menu-item index="/task/services">Service列表</el-menu-item>
      </el-menu-item-group>
      <el-menu-item-group title="Process">
        <el-menu-item index="/task/processes/new">新建Process</el-menu-item>
        <el-menu-item index="/task/processes">Process列表</el-menu-item>
      </el-menu-item-group>
    </el-submenu>
    <el-menu-item index="/misc">
      <i class="el-icon-setting"></i>杂项
    </el-menu-item>
  </el-menu>
</template>

<script>
</script>
