<template>
<span>
    <el-form :inline="true" :model="serchForm" class="demo-form-inline" style="height: 50px">
      <el-form-item label="">
        <el-input v-model="serchForm.word" placeholder="名称/描述" clearable></el-input>
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
          <el-button type="success" @click="">下载导入模板</el-button>
        </el-form-item>
        <el-form-item>
          <el-button type="success" @click="">导入年度配置</el-button>
        </el-form-item>
        <el-form-item>
          <el-button type="warning" @click="handleAddForm()">新增</el-button>
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
        width="50">
      </el-table-column>
      <el-table-column
        prop="year"
        label="年份"
        width="80">
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
      title="毕业要求编辑"
      :visible.sync="dialogVisible"
      width="30%">
      <div>
        <el-form :model="editForm" status-icon ref="ruleForm" class="demo-ruleForm">
          <el-form-item label="名称" prop="name">
            <el-input type="text" v-model="editForm.name" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="描述" prop="pass">
            <el-input type="text" v-model="editForm.discrible" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="指标点：" prop="pass">
            <el-button type="primary" round style="" @click="handleAddTarget">添加</el-button>
            <br>
            <span v-for="(item,index) in editForm.targets" type="text" autocomplete="off">
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
    <el-dialog
      title="添加年度毕业要求"
      :visible.sync="dialogVisible1"
      width="30%">
      <div>
        <el-form :model="addForm" status-icon ref="ruleForm" class="demo-ruleForm">
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
      pageSize: 20,
      total : 0,
      currentPage: 1,
      serchForm: {
        word: '',
        year: ''
      },
      editForm: {
        id: '',
        discrible: '',
        name: '',
        targets: [],
      },
      addForm: {
        discrible: '',
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
      this.$axios.post('gradRequirement/list',{
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
      this.$axios.post('target/list',{
          reqId : record.id}
        ,{}).then(res => {
        if (res.data.succ) {
          this.editForm.targets = res.data.data
        }
        this.loading = false
      }).catch(() => {
        this.loading = false
      })
    },
    handleAddForm(){
      this.dialogVisible1 = true
      this.addForm.discrible = ''
      this.addForm.name = ''
    },
    handleDelete(){
      this.loading = true
      this.$axios.post('gradRequirement/del',{
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
    handleAddTarget(){
      this.editForm.targets.push({discribe: '',reqId: this.editForm.id})
    },
    deleteDiscribe(index){
      this.editForm.targets.splice(index,1)
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
}
.dialog-footer{
  margin-top: 0px;
}
.demo-form-inline{
  margin-left: 50px;
}
.el-main .el-divider--horizontal{
  margin: 0px 0;
}
</style>
