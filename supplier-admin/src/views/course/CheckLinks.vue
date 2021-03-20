<template>
  <span style="width: 100%">
    <el-form :inline="true" :model="serchForm" class="demo-form-inline" style="height: 50px">
      <el-form-item label="">
        <el-input v-model="serchForm.word" placeholder="课程目标描述" clearable></el-input>
      </el-form-item>
      <el-form-item label="">
        <el-select v-model="serchForm.year" placeholder="年份" clearable>
          <el-option label="2016" value="2016"></el-option>
          <el-option label="2017" value="2017"></el-option>
          <el-option label="2018" value="2018"></el-option>
          <el-option label="2019" value="2019"></el-option>
          <el-option label="2020" value="2020"></el-option>
          <el-option label="2021" value="2021"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="getReqList">查询</el-button>
      </el-form-item>
    </el-form>
  </span>
</template>

<script>
export default {
  name: "CheckLinks"
}
</script>

<style scoped>

</style>
