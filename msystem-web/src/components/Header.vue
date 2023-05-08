<template>
  <div class="header-container">
    <div class="l-content">
      <el-button style="margin-right: 20px" @click="handleMenu" icon="el-icon-menu" size="mini"></el-button>
      <el-breadcrumb separator="/">
        <el-breadcrumb-item
            v-for="item in breadcrumbData"
            :to="{ path: item.path }"
            :key="item.path"
            :class="{ active: isActiveBreadcrumb(item)  }"
        >
          {{ item.label }}
        </el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <div class="r-content">
      <el-dropdown @command="handleClick">
        <span class="el-dropdown-link">
            <img class="user" src="../assets/images/female.png" alt="">
        </span>
        <el-dropdown-menu slot="dropdown">
          <el-dropdown-item>個人中心</el-dropdown-item>
          <el-dropdown-item command="cancel">登出</el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
    </div>
  </div>
</template>

<script>
import {mapState,mapGetters,mapMutations,mapActions} from 'vuex'
import Cookie from 'js-cookie'
export default {

  methods:{
    //調用store裡menu模塊中的handleMenu方法
    ...mapMutations('menu',['handleMenu']),
    handleClick(command) {
      if (command === 'cancel') {
        console.log('登出')
        // 清除localStorage中的token
        localStorage.removeItem('token')
        localStorage.removeItem('role')
        localStorage.removeItem('username')
        localStorage.removeItem('menuData')
        localStorage.removeItem('lastLoginTime')
        localStorage.removeItem('employee')
        //清除cookie中的menu
        if(Cookie){
          Cookie.remove('menu')
        }
        // 跳轉到登入頁面
        this.$router.push('/login')
      }
    },
    //判斷麵包削的當前頁
    isActiveBreadcrumb(item) {
      console.log(this.$route.path);
      return this.$route.path === item.path;
    },
  },
  computed:{
    //映射到store/menu.js中的breadcrumb方法
    ...mapState('menu', ['breadcrumbData']),
  },
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.header-container {
  padding: 0 20px;
  background-color: #333;
  height: 60px;
  display: flex;
  justify-content: space-between;
  align-items: center;
}
.header-container .text {
  color: #fff;
  font-size: 14px;
  margin-left: 10px;
}
.header-container .r-content .user {
  width: 40px;
  height: 40px;
  border-radius: 50%;
}
.header-container .l-content {
  display: flex;
  align-items: center;
}
.header-container .l-content ::v-deep .el-breadcrumb__item .el-breadcrumb__inner {
  font-weight: normal;
}
.header-container .l-content ::v-deep .el-breadcrumb__item .el-breadcrumb__inner.is-link {
  color: #666;
}
.header-container .l-content ::v-deep .el-breadcrumb__item.active .el-breadcrumb__inner {
  color: #ffffff;
}
</style>
