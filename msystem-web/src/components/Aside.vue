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
      menudata:[
        {
          path: '/',
          name: 'home',
          label: '首页',
          icon: 's-home',
          url: 'Home/Home',
          meta:{role: ['1', '2']}
        },
        {
          path: '/lineprocess',
          name: 'userlineprocess',
          label: '線材生產',
          icon: 'video-play',
          url: 'management/lineprocess',
          meta:{role: ['2']}
        },
        {
          path: '/prepayroll',
          name: 'prepayroll',
          label: '當月薪資試算',
          icon: 'user',
          url: 'prepayroll/prepayroll',
          meta:{role: ['2']}
        },
        {
          path: '/payroll',
          name: 'user',
          label: '薪資紀錄',
          icon: 'user',
          url: 'payroll/payroll',
          meta:{role: ['2']}
        },
        {
          label: '線材進出貨管理',
          icon: 'location',
          meta:{role: ['1']},
          children: [
            {
              path: '/linepurchase',
              name: 'linepurchase',
              label: '線材進貨',
              icon: 'setting',
              url: 'management/linepurchase',
              meta:{role: ['1']}
            },
            {
              path: '/lineshipping',
              name: 'lineshipping',
              label: '線材出貨',
              icon: 'setting',
              url: 'management/lineshipping',
              meta:{role: ['1']}
            },
            {
              path: '/lineprocess',
              name: 'lineprocess',
              label: '線材生產',
              icon: 'setting',
              url: 'management/lineprocess',
              meta:{role: ['1']}
            },
            {
              path: '/linereturn',
              name: 'linereturn',
              label: '線材退貨',
              icon: 'setting',
              url: 'management/linereturn',
              meta:{role: ['1']}
            },
            {
              path: '/linemanagement',
              name: 'linemanagement',
              label: '線材管理',
              icon: 'setting',
              url: 'management/linemanagement',
              meta:{role: ['1']}
            },
            ]
        },
        {
          label: '人員管理',
          icon: 'location',
          meta:{role: ['1']},
          children: [
            {
              path: '/payrollmanagement',
              name: 'payrollmanagement',
              label: '薪資管理',
              icon: 'setting',
              url: 'management/payrollmanagement',
              meta:{role: ['1']}
            },
            {
              path: '/workermanagement',
              name: 'workermanagement',
              label: '人員管理',
              icon: 'setting',
              url: 'management/workermanagement',
              meta:{role: ['1']}
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
