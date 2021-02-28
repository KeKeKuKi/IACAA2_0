<template>
<span>
    <el-form :inline="true" :model="formInline" class="demo-form-inline">
      <el-form-item label="名称">
        <el-input v-model="formInline.user" placeholder="名称"></el-input>
      </el-form-item>
      <el-form-item label="年份">
        <el-select v-model="formInline.region" placeholder="年份">
          <el-option label="2018" value="2018"></el-option>
          <el-option label="2019" value="2019"></el-option>
          <el-option label="2020" value="2020"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="onSubmit">查询</el-button>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="handleAddForm()">新增</el-button>
      </el-form-item>
    </el-form>
    <el-divider></el-divider>
    <el-table
      :data="tableData"
      style="width: 100%"
      height="680">
      <el-table-column
        fixed
        prop="id"
        label="ID"
        width="100">
      </el-table-column>
      <el-table-column
        prop="name"
        label="名称"
        width="200">
      </el-table-column>
      <el-table-column
        prop="discrible"
        label="描述"
        width="900">
      </el-table-column>
      <el-table-column label="操作" >
        <template slot-scope="scope">
          <el-button type="primary" icon="el-icon-edit" circle @click="handleEditForm(scope.row)"></el-button>
<!--          <el-button type="success" icon="el-icon-check" circle></el-button>-->
<!--          <el-button type="primary" icon="el-icon-search">搜索</el-button>-->
<!--          <el-button type="primary">上传<i class="el-icon-upload el-icon&#45;&#45;right"></i></el-button>-->
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
            <el-input type="text" v-model="editForm.discrible" autocomplete="off"></el-input>
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
  name: "GradRequirementEdit",
  mounted() {
    this.getList()
  },
  data() {
    return {
      dialogVisible: false,
      dialogVisible1: false,
      visible: false,
      tableData: [],
      pageNum: 1,
      pageSize: 20,
      total : 0,
      currentPage: 1,
      formInline: {
        user: '',
        region: ''
      },
      editForm: {
        id: '',
        discrible: '',
        name: ''
      },
      addForm: {
        discrible: '',
        name: ''
      },
    }
  },
  methods:{
    onSubmit() {
      console.log('submit!');
    },
    getList() {
      const token = localStorage.getItem("token")
      this.loading = true
      this.$axios.post('gradRequirement/list',{
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
        }
        this.loading = false
      }).catch(() => {
        this.loading = false
      })
    },
    handleSizeChange(val) {
      this.pageSize = val
    },
    handleCurrentChange(val) {
    },
    handleClose(done) {
    },
    submitEditForm(){
      this.dialogVisible = false
      this.loading = true
      this.$axios.post('gradRequirement/update',this.editForm,{}).then(res => {
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
      this.$axios.post('gradRequirement/save',this.addForm,{}).then(res => {
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
}
</style>
