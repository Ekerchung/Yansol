<template>
  <div id="main">
    <el-container>
      <el-aside width="auto">
        <Aside/>
      </el-aside>
      <el-container>
        <el-header>
          <Header/>
        </el-header>
        <el-main>
          <router-view></router-view>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script>
import Aside from '../components/Aside'
import Header from '../components/Header'

export default {
  name: 'Main',
  components: {
    Aside,
    Header,
  }
}
</script>

<style>
#main {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  /*margin-top: 60px;*/
}

body,html{
  padding: 0;
  margin: 0;
}

.el-header{
  padding: 0;
}
</style>
