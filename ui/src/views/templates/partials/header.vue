<template>
  <el-menu theme="dark" default-active="1" mode="horizontal" :router="true">
    <el-menu-item index="/">{{$t('global.title')}}</el-menu-item>
    <el-submenu index="/customers">
      <template slot="title"><i class="el-icon-setting"></i>客户管理</template>
      <el-menu-item index="/customers/create">新建客户</el-menu-item>
      <el-menu-item index="/customers">客户列表</el-menu-item>
    </el-submenu>
    <el-submenu index="/task">
      <template slot="title"><i class="el-icon-setting"></i>任务管理</template>
      <el-menu-item-group title="Process">
        <el-menu-item index="/task/processes/new">新建Process</el-menu-item>
        <el-menu-item index="/task/processes">Process列表</el-menu-item>
      </el-menu-item-group>
    </el-submenu>
    <el-submenu index="/service">
      <template slot="title"><i class="el-icon-setting"></i>服务管理</template>
      <el-menu-item-group title="Schema">
        <el-menu-item index="/service/generics-schemas">通用Schema列表</el-menu-item>
      </el-menu-item-group>
      <el-menu-item-group title="FTP">
        <el-menu-item index="/service/ftp/new">新建FTP</el-menu-item>
        <el-menu-item index="/service/ftp">FTP列表</el-menu-item>
      </el-menu-item-group>
      <el-menu-item-group title="协议">
        <el-menu-item index="/service/protocols/new">新建协议</el-menu-item>
        <el-menu-item index="/service/protocols">协议列表</el-menu-item>
      </el-menu-item-group>
    </el-submenu>
    <el-submenu  index="/misc">
      <template slot="title"><i class="el-icon-setting"></i>杂项</template>
      <el-menu-item index="/misc/files">文件列表</el-menu-item>
    </el-submenu>
    <el-menu-item index="/auth">
      <i class="el-icon-setting"></i>Auth
    </el-menu-item>
  </el-menu>
</template>

<script>
</script>

<style>
</style>
