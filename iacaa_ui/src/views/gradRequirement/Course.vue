<template>
  <span>
  <el-form :inline="true" :model="serchForm" class="demo-form-inline" style="height: 50px">
    <el-form-item label="">
      <el-input v-model="serchForm.word" placeholder="名称" clearable></el-input>
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click="getList()">查询</el-button>
    </el-form-item>
  </el-form>
  <el-divider></el-divider>
  <el-table
    ref="multipleTable"
    :data="tableData"
    style="width: 100%"
    height="680"
    tooltip-effect="dark"
    @selection-change="handleSelectionChange">
    <el-table-column
      type="selection"
      width="55">
    </el-table-column>
    <el-table-column
      type="index"
      width="50">
    </el-table-column>
    <el-table-column
      prop="image"
      label="课程编号"
      width="100">
    </el-table-column>
    <el-table-column
      prop="name"
      label="名称"
      width="200">
    </el-table-column>
    <el-table-column
      prop="discribe"
      label="简介"
      width="900">
    </el-table-column>
    <el-table-column label="操作" >
      <template slot-scope="scope">
        <el-button type="primary" @click="handleEditForm(scope.row)">编辑课程目标</el-button>
      </template>
    </el-table-column>
  </el-table>
  <el-dialog
    title="课程目标编辑"
    :visible.sync="dialogVisible"
    width="30%">
    <div>
      <el-form :model="editForm" status-icon ref="ruleForm" class="demo-ruleForm">
        <el-form-item label="课程编号" prop="name">
          <el-input type="text" v-model="editForm.name" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="课程名称" prop="pass">
          <el-input type="text" v-model="editForm.discrible" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="课程目标：" prop="pass">
          <el-button type="primary" round style="" @click="handleAddTarget">添加</el-button>
          <br>
          <span v-for="(item,index) in editForm.courseTasks" type="text" autocomplete="off">
              <el-input type="text" autocomplete="off" v-model="item.discribe" style="width: 91%;margin-top: 10px"></el-input>
              <el-button type="danger" icon="el-icon-delete" circle @click="deleteDiscribe(index)"></el-button>
          </span>
        </el-form-item>
      </el-form>
    </div>
    <div slot="footer" class="dialog-footer">
      <el-button @click="dialogVisible = false">取 消</el-button>
      <el-button type="primary" @click="submitEditForm('editForm')">确 定</el-button>
    </div>
  </el-dialog>
  <el-pagination
    @size-change="handleSizeChange"
    @current-change="handleCurrentChange"
    :current-page="currentPage"
    :page-sizes="[10, 15, 20, 50, 100]"
    :page-size="pageSize"
    layout="total, sizes, prev, pager, next, jumper"
    :total="total">
  </el-pagination>
  </span>
</template>

<script>
export default {
  name: "Course",
  mounted() {
    this.getList()
  },
  data() {
    return {
      dialogVisible: false,
      visible: false,
      tableData: [],
      pageSize: 20,
      total : 0,
      currentPage: 1,
      serchForm: {
        word: ''
      },
      editForm: {
        id: '',
        discrible: '',
        name: '',
        courseTasks: [],
      },
      addForm: {
        discrible: '',
        name: ''
      },
      ids: []
    }
  },methods: {
    getList() {
      const token = localStorage.getItem("token")
      this.loading = true
      this.$axios.post('course/voList',{
        pageNum: this.currentPage,
        pageSize: this.pageSize,
        word: this.serchForm.word
      },{
        headers: {
          token:token
        }
      }).then(res => {
        if (res.data.succ) {
          this.tableData = res.data.data.list
          this.total = res.data.data.total
          this.pageSize = res.data.data.pageSize
          this.currentPage = res.data.data.pageNum
        }
        this.loading = false
      }).catch(() => {
        this.loading = false
      })
    },
    handleSizeChange(val) {
      this.pageSize = val
      this.currentPage = 1
      this.getList()
    },
    handleCurrentChange(val) {
      this.currentPage = val
      this.getList()
    },
    handleDelete(){
      this.loading = true
      this.$axios.post('course/del',{
          ids : this.ids}
        ,{}).then(res => {
        if (res.data.succ) {
          this.$message({
            message: '删除成功',
            type: 'success'
          });
          this.getList()
        }
        this.loading = false
      }).catch(() => {
        this.loading = false
      })
    },
    handleSelectionChange(val) {
      const result = val.map(item => item.id)
      this.ids = result;
    },
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
.el-pagination{
  text-align: right;
}
.el-form{
  text-align: left;
}
.dialog-footer{
  margin-top: 0px;
}
.demo-form-inline{
  margin-left: 30px;
}
.el-main .el-divider--horizontal{
  margin: 0px 0;
}
</style>
