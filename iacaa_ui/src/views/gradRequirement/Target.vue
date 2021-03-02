<template>
<span>
    <el-form :inline="true" :model="serchForm" class="demo-form-inline">
      <el-form-item label="">
        <el-input v-model="serchForm.word" placeholder="描述" clearable></el-input>
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
        <el-button type="primary" @click="getList()">查询</el-button>
      </el-form-item>
      <span style="float: right;margin-right: 30px">
        <el-form-item>
          <el-button type="success" @click="handleAddForm()">新增</el-button>
        </el-form-item>
        <el-form-item>
          <el-button type="danger" @click="handleDelete()">删除</el-button>
        </el-form-item>
      </span>
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
        label="序号"
        width="50">
      </el-table-column>
      <el-table-column
        prop="year"
        label="年份"
        width="80">
      </el-table-column>
      <el-table-column
        prop="reqName"
        label="支撑毕业要求"
        width="120">
      </el-table-column>
      <el-table-column
        prop="discribe"
        label="描述"
        width="900">
      </el-table-column>
      <el-table-column
        prop="stuGrade"
        label="学生评价成绩"
        width="100">
      </el-table-column>
      <el-table-column
        prop="sysGrade"
        label="系统计算成绩"
        width="100">
      </el-table-column>
      <el-table-column label="操作" >
        <template slot-scope="scope">
          <el-button type="primary" icon="el-icon-edit" circle @click="handleEditForm(scope.row)"></el-button>
      </template>
      </el-table-column>
    </el-table>
    <el-dialog
      title="编辑"
      :visible.sync="dialogVisible"
      width="30%">
      <div>
        <el-form :model="editForm" status-icon ref="ruleForm" label-width="100px" class="demo-ruleForm">
          <el-form-item label="名称" prop="name">
            <el-input type="text" v-model="editForm.name" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="描述" prop="pass">
            <el-input type="text" v-model="editForm.discribe" autocomplete="off"></el-input>
          </el-form-item>
        </el-form>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="submitEditForm('editForm')">确 定</el-button>
      </div>
    </el-dialog>
    <el-dialog
      title="添加"
      :visible.sync="dialogVisible1"
      width="30%">
      <div>
        <el-form :model="addForm" status-icon ref="ruleForm" label-width="100px" class="demo-ruleForm">
          <el-form-item label="名称" prop="name">
            <el-input type="text" v-model="addForm.name" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="描述" prop="pass">
            <el-input type="text" v-model="addForm.discrible" autocomplete="off"></el-input>
          </el-form-item>
        </el-form>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible1 = false">取 消</el-button>
        <el-button type="primary" @click="submitAddForm()">确 定</el-button>
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
  name: "Target",
  mounted() {
    this.getList()
  },
  data() {
    return {
      dialogVisible: false,
      dialogVisible1: false,
      visible: false,
      tableData: [],
      pageSize: 20,
      total : 0,
      currentPage: 1,
      serchForm: {
        word: '',
        year: ''
      },
      editForm: {
        id: '',
        discribe: '',
        name: ''
      },
      addForm: {
        discribe: '',
        name: ''
      },
      ids: []
    }
  },
  methods:{
    handleSelectionChange(val) {
      const result = val.map(item => item.id)
      this.ids = result;
    },
    onSubmit() {
      console.log('submit!');
    },
    getList() {
      const token = localStorage.getItem("token")
      this.loading = true
      this.$axios.post('target/list',{
        pageNum: this.currentPage,
        pageSize: this.pageSize,
        word: this.serchForm.word,
        year: this.serchForm.year
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
    handleClose(done) {
    },
    submitEditForm(){
      this.dialogVisible = false
      this.loading = true
      this.$axios.post('target/update',this.editForm,{}).then(res => {
        if (res.data.succ) {
          this.$message({
            message: '修改成功',
            type: 'success'
          });
          this.getList()
        }
        this.loading = false
      }).catch(() => {
        this.loading = false
      })
    },
    submitAddForm(){
      this.dialogVisible1 = false
      this.loading = true
      this.$axios.post('target/save',this.addForm,{}).then(res => {
        if (res.data.succ) {
          this.$message({
            message: '添加成功',
            type: 'success'
          });
          this.getList()
        }
        this.loading = false
      }).catch(() => {
        this.loading = false
      })
    },
    handleEditForm(record){
      this.dialogVisible = true
      this.editForm.id = record.id
      this.editForm.discrible = record.discrible
      this.editForm.name = record.name
    },
    handleAddForm(){
      this.dialogVisible1 = true
      this.addForm.discrible = ''
      this.addForm.name = ''
    },
    handleDelete(){
      this.loading = true
      this.$axios.post('target/del',{
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
.el-pagination{
  text-align: right;
}
.el-form{
  text-align: left;
  height: 40px;
}
.dialog-footer{
  margin-top: 30px;
}.demo-form-inline{
   margin-left: 50px;
 }
</style>
