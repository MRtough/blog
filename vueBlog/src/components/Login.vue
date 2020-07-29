<template>
  <el-form :model="loginForm" :rules="rules" ref="loginForm" class="login-container" label-position="left" label-width="0px"
    v-loading="loading">
    <h3 class="login_title">系统登录</h3>
    <el-form-item prop="username">
      <el-input type="text" v-model="loginForm.username" auto-complete="off" placeholder="账号"></el-input>
    </el-form-item>
    <el-form-item prop="password">
      <el-input type="password" v-model="loginForm.password" auto-complete="off" placeholder="密码"></el-input>
    </el-form-item>
    <el-checkbox class="login_remember" v-model="checked" label-position="left">记住密码</el-checkbox>
    <el-form-item style="width: 100%">
      <el-button type="primary" @click.native.prevent="submitClick('loginForm')" style="width: 100%">登录</el-button>
    </el-form-item>
  </el-form>
</template>
<script>
  export default {
    data() {
      return {
        rules: {
          username: [{
            required: true,
            message: '请输入用户名',
            trigger: 'blur'
          }],
          password: [{
            required: true,
            message: '请输入密码',
            trigger: 'blur'
          }]
        },
        checked: true,
        loginForm: {
          username: '',
          password: ''
        },
        loading: false
      }
    },
    methods: {
      submitClick(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            var _this = this;
            this.loading = true;
            var loginform = new FormData();
            loginform.append("username", this.loginForm.username);
            loginform.append("password", this.loginForm.password);
            this.$axios({
              url: '/login',
              method: 'post',
              data: loginform,
              headers: {
                'Content-Type': 'application/x-www-form-urlencoded; charset=utf-8'
              },
            }).then(resp => {
              _this.loading = false;
              //成功
              var json = resp.data[0];
              if (json == 'success') {
                //记住密码
                if (this.checked) {
                  localStorage.setItem("username", this.loginForm.username);
                  localStorage.setItem("password", this.loginForm.password);
                } else {
                  localStorage.removeItem("username");
                  localStorage.removeItem("password");
                }
                _this.$store.commit('set_userid', resp.data[1].id)
                _this.$store.commit('set_username', resp.data[1].username)
                _this.$store.commit('set_userprofile', resp.data[1].profile)
                _this.$router.replace({
                  path: '/Home'
                });
              } else {
                _this.$alert('登录失败!', '失败!');
              }
            }, resp => {
              _this.loading = false;
              _this.$alert('找不到服务器⊙﹏⊙∥!', '失败!');
            });
          } else {
            this.$message({
              message: '请输入用户名和密码！',
              type: 'warning'
            });
            return false;
          }
        })
      }
    },
    created() {
      this.loginForm.username=localStorage.getItem("username")
      this.loginForm.password=localStorage.getItem("password")
    }
  }
</script>
<style>
  .login-container {
    border-radius: 15px;
    background-clip: padding-box;
    margin: 180px auto;
    width: 350px;
    padding: 35px 35px 15px 35px;
    background: #fff;
    border: 1px solid #eaeaea;
    box-shadow: 0 0 25px #cac6c6;
  }

  .login_title {
    margin: 0px auto 40px auto;
    text-align: center;
    color: #505458;
  }

  .login_remember {
    margin: 0px 0px 35px 0px;
    text-align: center;
  }
</style>
