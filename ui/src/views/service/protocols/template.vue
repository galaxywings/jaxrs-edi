<template>
  <div>
    <el-breadcrumb separator="/" class="breadcrumb-bar">
      <el-breadcrumb-item :to="{ path: '/' }">Home</el-breadcrumb-item>
      <el-breadcrumb-item :to="{ path: '/service/protocols' }">协议配置</el-breadcrumb-item>
      <el-breadcrumb-item>{{$route.meta.name}}</el-breadcrumb-item>
    </el-breadcrumb>
    <router-view></router-view>
  </div>
</template>
