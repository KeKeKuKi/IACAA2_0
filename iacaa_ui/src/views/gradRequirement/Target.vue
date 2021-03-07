<template>
<span style="width: 100%">
    <el-form :inline="true" :model="serchForm" class="demo-form-inline" style="height: 50px">
      <el-form-item label="">
        <el-input v-model="serchForm.word" placeholder="标题/描述" clearable></el-input>
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
    <el-divider></el-divider>
    <el-table
      ref="multipleTable"
      :data="tableData"
      style="width: 100%"
      height="750"
      tooltip-effect="dark"
      @selection-change="handleSelectionChange">
      <el-table-column
        type="selection"
        width="55">
      </el-table-column>
      <el-table-column
        type="index"
        label="序号"
        width="100">
      </el-table-column>
      <el-table-column
        prop="year"
        label="年份"
        width="100">
      </el-table-column>
      <el-table-column
        prop="name"
        label="毕业要求"
        width="400">
      </el-table-column>
      <el-table-column prop="targets" type="expand" label="指标点" width="1000">
        <template slot-scope="scope">
          <el-table :data="scope.row.targets" stripe>
            <el-table-column
              prop="discribe"
              label="指标点描述">
            </el-table-column>
            <el-table-column label="操作" >
              <template slot-scope="scope1">
                <el-button type="primary" icon="el-icon-edit" circle @click="handleTargetEditForm(scope1.row,scope.row.name)"></el-button>
              </template>
            </el-table-column>
          </el-table>
        </template>
      </el-table-column>
    </el-table>
    <el-dialog
      title="指标点支撑编辑"
      :visible.sync="dialogVisible"
      width="30%">
      <div>
        <el-form :model="targetEditForm" status-icon ref="ruleForm" class="demo-ruleForm">
          <el-form-item label="毕业要求" prop="name">
            <el-input type="text" v-model="targetEditForm.reqName" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="指标点" prop="pass">
            <el-input type="text" v-model="targetEditForm.target" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="支撑课程：" prop="pass">
              <el-button type="primary" round style="" @click="handleAddTarget">添加</el-button>
              <br>
              <span v-for="(item,index) in targetEditForm.courseTargets" type="text" autocomplete="off">
                <el-select v-model="item.course.id" placeholder="选择课程" clearable filterable style="width: 60%;margin-top: 10px">
                  <el-option v-for="(item1,index1) in courses" :key="index1" :label="item1.name" :value="item1.id"></el-option>
                </el-select>
                <el-input type="text" autocomplete="off" v-model="item.mix" style="width: 30%;margin-top: 10px"></el-input>
                <el-button type="danger" icon="el-icon-delete" circle @click="deleteDiscribe(index)"></el-button>
              </span>
          </el-form-item>
        </el-form>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="submitTargetEditForm()">确 定</el-button>
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
    this.getReqList()
    this.getCourseList()
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
      courses: [],
      ids: [],
      targetEditForm:{
        id:'',
        reqName:'',
        target:'',
        courseTargets: [],
      }


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
    getCourseList(){
      this.$axios.post('course/list',{
      },{
      }).then(res => {
        if (res.data.succ) {
          this.courses = res.data.data
        }
      }).catch(() => {
      })
    },
    getReqList() {
      this.$axios.post('gradRequirement/voList',{
        pageNum: this.currentPage,
        pageSize: this.pageSize,
        word: this.serchForm.word,
        year: this.serchForm.year
      },{
      }).then(res => {
        if (res.data.succ) {
          this.tableData = res.data.data.list
          this.total = res.data.data.total
          this.pageSize = res.data.data.pageSize
          this.currentPage = res.data.data.pageNum
        }
      }).catch(() => {
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
    handleTargetEditForm(record,req){
      this.dialogVisible = true
      this.editForm.id = record.id
      this.editForm.discrible = record.discrible
      this.editForm.name = record.name
      this.$axios.post('courseTarget/voList',{
          targetId : record.id}
        ,{}).then(res => {
        if (res.data.succ) {
          this.targetEditForm.id = record.id
          this.targetEditForm.reqName = req
          this.targetEditForm.target = record.discribe
          this.targetEditForm.courseTargets = res.data.data
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
    handleAddTarget(targetId){
      this.targetEditForm.courseTargets.push({course:{id: ''},target:{id: this.targetEditForm.id},mix: ''})
    },
    deleteDiscribe(index){
      this.targetEditForm.courseTargets.splice(index,1)
    },
    submitTargetEditForm(){
      this.loading = true
      this.$axios.post('courseTarget/saveOrUpdate',this.targetEditForm.courseTargets,{}).then(res => {
        if (res.data.succ) {
          this.getReqList()
          this.dialogVisible = false;
          this.$message({
            message: '修改成功',
            type: 'success'
          });
        }
        this.loading = false
      }).catch((e) => {
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
