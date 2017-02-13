<template>
  <div>
    <el-breadcrumb separator="/" class="breadcrumb-bar">
      <el-breadcrumb-item :to="{ path: '/' }">Home</el-breadcrumb-item>
      <el-breadcrumb-item :to="{ path: '/file/files' }">文件管理</el-breadcrumb-item>
      <el-breadcrumb-item>{{$route.meta.name}}</el-breadcrumb-item>
    </el-breadcrumb>
    <router-view></router-view>
  </div>
</template>
