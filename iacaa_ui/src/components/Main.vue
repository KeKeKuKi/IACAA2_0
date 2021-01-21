<template>
  <span>
    <el-form :inline="true" :model="formInline" class="demo-form-inline">
      <el-form-item label="审批人">
    <el-input v-model="formInline.user" placeholder="审批人"></el-input>
    </el-form-item>
    <el-form-item label="活动区域">
    <el-select v-model="formInline.region" placeholder="活动区域">
      <el-option label="区域一" value="shanghai"></el-option>
      <el-option label="区域二" value="beijing"></el-option>
    </el-select>
    </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="onSubmit">查询</el-button>
      </el-form-item>
    </el-form>
    <el-table
      :data="tableData"
      style="width: 100%"
      height="800">
      <el-table-column
        fixed
        prop="id"
        label="ID"
        width="100">
      </el-table-column>
      <el-table-column
        prop="name"
        label="名称"
        width="400">
      </el-table-column>
      <el-table-column
        prop="editUser.name"
        label="管理教師"
        width="300">
      </el-table-column>
      <el-table-column
        prop="province"
        label="操作">
          <el-button icon="el-icon-search" circle></el-button>
          <el-button type="primary" icon="el-icon-edit" circle></el-button>
          <el-button type="success" icon="el-icon-check" circle></el-button>
          <el-button type="primary" icon="el-icon-search">搜索</el-button>
          <el-button type="primary">上传<i class="el-icon-upload el-icon--right"></i></el-button>
      </el-table-column>
    </el-table>
    <el-pagination
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current-page="pageNum"
      :page-sizes="[10, 15, 20, 50, 100]"
      :page-size="pageSize"
      layout="total, sizes, prev, pager, next, jumper"
      :total="total">
    </el-pagination>
  </span>
</template>

<script>
export default {
  name: "Main",
  mounted() {
    this.getList()
  },
  data() {
    return {
      tableData: [],
      pageNum: 1,
      pageSize: 20,
      total : 0,
      currentPage: 1
    }
  },
  methods:{
    handleOpen(key, keyPath) {
      console.log(key, keyPath);
    },
    handleClose(key, keyPath) {
      console.log(key, keyPath);
    },
    getList() {
      const token = localStorage.getItem("token")
      this.loading = true
      this.$axios.post('course/voList',{
        pageNum: this.pageNum,
        pageSize: this.pageSize
      },{
        headers: {
          token:token
        }
      }).then(res => {
        if (res.data.succ) {
          this.tableData = res.data.data.list
          this.total = res.data.data.total
          this.pageSize = res.data.data.pageSize
          this.pageNum = res.data.data.pageNum
          console.log(this.tableData)
        }
        this.loading = false
      }).catch(() => {
        this.loading = false
      })
    },
    handleSizeChange(val) {
      this.pageSize = val
      console.log(`每页 ${val} 条`);
    },
    handleCurrentChange(val) {
      console.log(`当前页: ${val}`);
    }
  }
}
</script>

<style scoped>
.el-table__header tr,
.el-table__header th {
  padding: 0;
  height: 40px;
  line-height: 50px;
}
.el-table__body tr,
.el-table__body td {
  padding: 0;
  height: 40px;
  line-height: 30px;
}
</style>
