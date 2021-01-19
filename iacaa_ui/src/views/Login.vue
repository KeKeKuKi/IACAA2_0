<template>
<div style="position: relative;">
  <el-container>
    <el-header>LOGO</el-header>
    <el-main>
      <form>
        <div style="width: 50%;margin-left: 25%;margin-top: 10%">
          <el-input v-model="input" placeholder="账户"></el-input>
          <br/>
          <br/>
          <br/>
          <el-input v-model="input" placeholder="密码"></el-input>
          <br/>
          <br/>
          <br/>
          <el-button type="primary">登录</el-button>
        </div>
      </form>

    </el-main>
  </el-container>

</div>
</template>

<script>
export default {
  name: "Login"
}
</script>

<style scoped>

</style>
