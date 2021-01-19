<template>
  <el-calendar v-model="value">
  </el-calendar>
</template>

<script>
export default {
  data() {
    return {
      value: new Date()
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style>
</style>
