<template>
  <div class="background">
    <el-form :model="loginForm" :rules="rules" ref="loginForm" label-width="100px" class="login-container">
      <h3 class="login_title">岩碩後台管理系統</h3>
      <el-form-item label="帳號" prop="username">
        <el-input v-model="loginForm.username"></el-input>
      </el-form-item>
      <el-form-item label="密碼" prop="password">
        <el-input type="password" v-model="loginForm.password" @keyup.enter.native="login"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" style="margin-left: 82px;margin-top: 10px;" :plain="true" @click="login">登入</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import {mapActions, mapMutations, mapState} from "vuex";

export default {
  name:'Login',
  data() {
    return {
      loginForm: {
        username: '',
        password:'',
      },
      //驗證輸入資料
      rules: {
        username: [
          { required: true, message: '請輸入帳號', trigger: 'blur' },
        ],
        password: [
          { required: true, message: '請輸入密碼', trigger: 'blur' },
        ],
      },

    }
  },
created() {
    // console.log('login頁面')
},
  methods:{
    ...mapActions('menu',['fetchLogin']),
    ...mapMutations('data',['setMenuData']),
    login(){
      this.fetchLogin(this.loginForm)
          .then((response) => {
              this.$store.commit('data/setMenuData',response.data.role) //根據帳號權設定menuData
            setTimeout(() => {
              this.$router.push(`${response.data.role === '1'?'/home' : '/userhome' }`);
            }, 1000);
          })
          .catch((error) => {
            this.$message.error(error.response.data);
          });
    },
  }
}
</script>

<style scoped>
.background {
  display: flex;
  width: 100%;
  min-height: 100vh;
  background: url("~@/assets/images/login.jpg") center center no-repeat;
  background-size: 100% 100%;
}
.login-container {
  width: 500px;
  height: 350px;
  border: 1px solid #eaeaea;
  margin: 180px auto;
  padding: 35px 35px 15px 35px;
  background-color: #fff;
  border-radius: 15px;
  box-shadow: 0 0 25px #cac6c6;
  box-sizing: border-box;

}
.login-container .login_title {
  text-align: center;
  margin-bottom: 40px;
  color: #505458;
}
.login-container .el-input {
  width: 250px;
}
</style>