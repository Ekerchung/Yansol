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
          <el-menu-item @click.once="clickMenu(subItem)" v-for="subItem in item.children" :key="subItem.path" :index="subItem.path">{{ subItem.label }}</el-menu-item>
        </el-menu-item-group>
      </el-submenu>

    </el-menu>
</template>

<script>
export default {
  name: 'HelloWorld',
  props: {
    msg: String
  },
  data(){
    return{
      menudata:[
        {
          path: '/',
          name: 'home',
          label: '首页',
          icon: 's-home',
          url: 'Home/Home'
        },
        {
          path: '/lineprocess',
          name: 'userlineprocess',
          label: '線材生產',
          icon: 'video-play',
          url: 'management/lineprocess'
        },
        {
          path: '/prepayroll',
          name: 'prepayroll',
          label: '當月薪資試算',
          icon: 'user',
          url: 'prepayroll/prepayroll'
        },
        {
          path: '/payroll',
          name: 'user',
          label: '薪資紀錄',
          icon: 'user',
          url: 'payroll/payroll'
        },
        {
          label: '線材進出貨管理',
          icon: 'location',
          children: [
            {
              path: '/linepurchase',
              name: 'linepurchase',
              label: '線材進貨',
              icon: 'setting',
              url: 'management/linepurchase'
            },
            {
              path: '/lineshipping',
              name: 'lineshipping',
              label: '線材出貨',
              icon: 'setting',
              url: 'management/lineshipping'
            },
            {
              path: '/lineprocess',
              name: 'lineprocess',
              label: '線材生產',
              icon: 'setting',
              url: 'management/lineprocess'
            },
            {
              path: '/linereturn',
              name: 'linereturn',
              label: '線材退貨',
              icon: 'setting',
              url: 'management/linereturn'
            },
            {
              path: '/linemanagement',
              name: 'linemanagement',
              label: '線材管理',
              icon: 'setting',
              url: 'management/linemanagement'
            },
            ]
        },
        {
          label: '人員管理',
          icon: 'location',
          children: [
            {
              path: '/payrollmanagement',
              name: 'payrollmanagement',
              label: '薪資管理',
              icon: 'setting',
              url: 'management/payrollmanagement'
            },
            {
              path: '/workermanagement',
              name: 'workermanagement',
              label: '人員管理',
              icon: 'setting',
              url: 'management/workermanagement'
            }
          ]
        }
      ]
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
      // console.log(item)
      this.$router.push(item.path).catch(err => err);
    }
  },

  computed: {
    //沒有子菜單
    noChildren(){
      return this.menudata.filter(item => !item.children);
    },
    //有子菜單
    haschildren(){
      return this.menudata.filter(item => item.children);
    },

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
