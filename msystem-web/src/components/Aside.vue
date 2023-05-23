<template>
    <el-menu
        default-active="2"
        class="el-menu-vertical-demo"
        @open="handleOpen"
        @close="handleClose"
        background-color="#545c64"
        :collapse="isCollapse"
        text-color="#fff"
        active-text-color="#ffd04b">
      <h3>{{ isCollapse? '後台':'岩碩後台管理系統' }}</h3>
      <el-menu-item @click="clickMenu(item)" v-for="item in noChildren" :key="item.name" :index="item.name">
        <i :class="`el-icon-${item.icon}`"></i>
        <span slot="title">{{ item.label }}</span>
      </el-menu-item>

      <el-submenu v-for="item in haschildren" :key="item.label" :index="item.label">
        <template slot="title">
          <i :class="`el-icon-${item.icon}`"></i>
          <span>{{ item.label }}</span>
        </template>
        <el-menu-item-group>
          <el-menu-item @click="clickMenu(subItem)" v-for="subItem in item.children" :key="subItem.path" :index="subItem.path">{{ subItem.label }}</el-menu-item>
        </el-menu-item-group>
      </el-submenu>

    </el-menu>
</template>

<script>
import {mapState} from "vuex";

export default {
  data(){
    return{
    };
  },

  methods: {
    handleOpen(key, keyPath) {
      console.log(key, keyPath);
    },
    handleClose(key, keyPath) {
      console.log(key, keyPath);
    },
    //點擊菜單跳轉頁面
    clickMenu(item) {
      this.$router.push(item.path).catch(err => err);
      //調用添加麵包削方法
      this.$store.commit('menu/breadcrumb',item);
    }
  },

  computed: {
    //映射Vuex中的menuData
    ...mapState('data',['menuData']),
    //沒有子菜單
    noChildren() {
      return this.menuData.filter(item => !item.children);
    },
    //有子菜單
    haschildren() {
      return this.menuData.filter(item => item.children);
    },
    //切換側邊欄收/折
    isCollapse() {
      return this.$store.state.menu.isCollapse
    },

  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.el-menu-vertical-demo:not(.el-menu--collapse) {
  width: 200px;
  min-height: 400px;
}
.el-menu {
  height: 100vh;
  border-right: none;
  text-align: left;
}
h3 {
  color: #fff;
  text-align: center;
  line-height: 48px;
  font-size: 16px;
  font-weight: 400;
}
.el-menu--collapse .el-submenu, .el-menu-item {
  text-align: left;
}
.el-submenu__title {
  text-align: left;
}
.element .style {
  width: auto;
}
</style>
