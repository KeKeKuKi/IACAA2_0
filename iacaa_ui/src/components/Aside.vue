<template>
    <el-menu
      default-active="2"
      class="el-menu-vertical-demo"
      @open="handleOpen"
      @close="handleClose"
      text-color="#fff"
      active-text-color="#ffd04b"
      background-color="#132842"
      >
      <el-submenu index="1">
        <template slot="title">
          <i class="el-icon-location"></i>
          <span>毕业要求体系</span>
        </template>
        <el-menu-item index="1-1">
          <router-link to="GradRequirementEdit" style="color: white;text-decoration:none">毕业要求</router-link>
        </el-menu-item>
        <el-menu-item index="1-1">
          <router-link to="Target" style="color: white;text-decoration:none">指标点</router-link>
        </el-menu-item>
      </el-submenu>

      <el-submenu index="2">
        <template slot="title">
          <i class="el-icon-location"></i>
          <span>课程体系管理</span>
        </template>
        <el-menu-item index="2-1">
          <router-link to="Course" style="color: white;text-decoration:none">课程目标管理</router-link>
        </el-menu-item>
        <el-menu-item index="2-2">
          <router-link to="CheckLinks" style="color: white;text-decoration:none">考核环节管理</router-link>
        </el-menu-item>
      </el-submenu>


      <el-menu-item index="3">
        <i class="el-icon-open"></i>
        <span slot="title">系统配置</span>
      </el-menu-item>
      <el-menu-item index="4">
        <i class="el-icon-pie-chart"></i>
        <span slot="title">统计分析</span>
      </el-menu-item>
    </el-menu>
</template>

<script>
export default {
  name: "Aside",
  methods:{
    handleOpen(key, keyPath) {
      console.log(key, keyPath);
    },
    handleClose(key, keyPath) {
      console.log(key, keyPath);
    }
  }
}
</script>

<style scoped>
.el-menu{
  border-right: 0;
}
</style>
