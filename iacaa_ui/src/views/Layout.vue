<template>
    <el-container style="height: 100%">
      <el-header>
        <Header></Header>
      </el-header>
      <el-container>
        <el-aside width="200px">
          <Aside></Aside>
        </el-aside>
        <el-container>
          <el-main>
            <Main></Main>
          </el-main>
          <el-footer style="height: 40px">
            <Footer></Footer>
          </el-footer>
        </el-container>
      </el-container>
    </el-container>
</template>

<script>
import Header from "../components/Header";
import Aside from "../components/Aside";
import Footer from "../components/Footer";
import Main from "../components/Main";

export default {
  name: "Layout.vue",
  components: {Footer,Aside,Header,Main},
  comments:{Footer,Aside,Header,Main},

}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style>
.el-container{
}
.el-header, .el-footer {
  background-color: #081423;
  color: #ffffff;
  text-align: center;
  line-height: 40px;
}

.el-aside {
  background-color: #132842;
  color: #333;
  text-align: center;
  line-height: 200px;
}

.el-main {
  background-color: #ffffff;
  color: #ffffff;
  text-align: center;
  padding: 10px;
}

body > .el-container {
  margin-bottom: 40px;
}

.el-container:nth-child(5) .el-aside,
.el-container:nth-child(6) .el-aside {
  line-height: 260px;
}

.el-container:nth-child(7) .el-aside {
  line-height: 320px;
}


</style>
