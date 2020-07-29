<template>

  <el-container class="content">
    <el-header>
      <div>
        <img src="../assets/管理.png" />
        <span class="title_">博客后台管理系统</span>
      </div>
      <div>
        <span>{{user.username}}</span>
        <el-dropdown @command="handleCommand">
          <span class="el-dropdown-link">
            <el-avatar :src="'/static/'+user.userface"></el-avatar>
            <i class="el-icon-arrow-down el-icon--right"></i>
          </span>
          <el-dropdown-menu slot="dropdown">
            <el-dropdown-item command="b">安全退出</el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
      </div>
    </el-header>
    <el-container>
      <el-aside width="200px">

        <el-menu router background-color="#545c64" text-color="#fff" active-text-color="#1989fa">
          <el-submenu index="1">
            <template slot="title">
              <i class="el-icon-document"></i>
              <span>文章管理</span>
            </template>
            <el-menu-item-group>

              <el-menu-item index="/BlogAdmin">
                <i class="el-icon-menu"></i>
                <span>文章列表</span>
              </el-menu-item>
              <el-menu-item index="/BlogEdits">
                <i class="el-icon-menu"></i>
                <span>编辑新文章</span>
              </el-menu-item>
            </el-menu-item-group>
          </el-submenu>
          <el-menu-item index="/MessageAdmin">
            <i class="el-icon-message"></i>
            <span slot="title">留言管理</span>
          </el-menu-item>
          <el-menu-item index="/LabelAdmin">
            <i class="el-icon-star-on"></i>
            <span slot="title">标签管理</span>
          </el-menu-item>
          <el-menu-item index="/UserAdmin">
            <i class="el-icon-user"></i>
            <span slot="title">用户管理</span>
          </el-menu-item>

        </el-menu>
      </el-aside>
      <el-main>
        <router-view></router-view>
      </el-main>
    </el-container>
  </el-container>

</template>

<script>
  export default {
    name: 'Home',
    data() {
      return {
        user: {
          username: '',
          userface: '',
        }

      }
    },
    methods: {
      handleCommand(command) {
        if (command == 'a') {
        } else if (command == 'b') {
          this.loginout()
        }
      },
      loginout() {
        const _this = this
        this.$axios.get('/logout').then((res) => {
          _this.$store.commit('remove_user')
          _this.$router.push('/')
        });
      }

    },
    created() {
      if (sessionStorage.getItem("username")) {
        this.user.username = sessionStorage.getItem("username")
        this.user.userface = sessionStorage.getItem("userprofile")
      }
    }

  }
</script>

<style>
  .content {
    height: 100%;
  }

  .el-menu {
    border-right: 0;
  }

  .el-header {
    background-color: rgb(84, 92, 100);
    display: flex;
    justify-content: space-between;
    align-items: center;
    color: #FFFFFF;
    font-size: 20px;
  }

  .el-header img {
    height: 60px;
  }

  .el-header div {
    display: flex;
    align-items: center;
  }

  .title_ {
    margin-left: 20px;
  }

  .el-avatar {
    width: 50px;
    height: 50px;
  }

  .el-avatar>img {
    width: 50px;
    height: 50px;
  }

  .el-dropdown-link {
    cursor: pointer;
    color: #409EFF;
    width: 100px;
    text-align: center;
  }

  .el-dropdown-menu {
    text-align: center;
  }

  .el-aside {
    background-color: rgb(84, 92, 100);
    color: #333;
  }

  .el-main {
    background-color: #E9EEF3;
    color: #333;
  }
</style>
