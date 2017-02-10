<template>
  <div>
    <el-breadcrumb separator="/" class="breadcrumb-bar">
      <el-breadcrumb-item :to="{ path: '/' }">Home</el-breadcrumb-item>
      <el-breadcrumb-item :to="{ path: '/customer/customers' }">客户管理</el-breadcrumb-item>
      <el-breadcrumb-item>{{$route.meta.name}}</el-breadcrumb-item>
    </el-breadcrumb>
    <router-view></router-view>
  </div>
</template>
