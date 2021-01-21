<template>
  <span>
    <el-table
      :data="tableData"
      style="width: 100%"
      height="800">
      <el-table-column
        fixed
        prop="id"
        label="ID">
      </el-table-column>
      <el-table-column
        prop="name"
        label="名称">
      </el-table-column>
      <el-table-column
        prop="editUser.name"
        label="管理教師">
      </el-table-column>
      <el-table-column
        prop="province"
        label="操作">
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
