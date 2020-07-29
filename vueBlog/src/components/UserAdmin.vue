<template>
  <div>
    <el-breadcrumb separator-class="el-icon-arrow-right" style="margin-bottom: 15px;">
      <el-breadcrumb-item :to="{ path: '/Home' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>用户管理</el-breadcrumb-item>
    </el-breadcrumb>
    <el-card>
      <el-button type="primary" icon="el-icon-plus" @click="userDialog">添加用户</el-button>
      <el-table :data="userData" border style="width: 100%;margin-top: 15px;">
        <el-table-column label="编号" width="100px">
          <template slot-scope="scope">
            <span style="margin-left: 10px">{{ scope.row.id }}</span>
          </template>
        </el-table-column>
        <el-table-column label="头像" width="100px">
          <template slot-scope="scope">
            <span style="margin-left: 10px">
              <el-avatar :src="'/static/'+scope.row.profile"></el-avatar>
            </span>
          </template>
        </el-table-column>
        <el-table-column label="用户名">
          <template slot-scope="scope">
            <span style="margin-left: 10px">{{ scope.row.username }}</span>
          </template>
        </el-table-column>
        <el-table-column label="用户角色" width="300px">
          <template slot-scope="scope">
            <span style="margin-left: 10px">
              <el-tag v-for="item in scope.row.roles" :key="item.id" :type="item.type" effect="plain">
                <span v-if="item.roleName==='ROLE_admin'">管理员</span>
                <span v-if="item.roleName==='ROLE_user'">普通用户</span>
              </el-tag>
            </span>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="100px">
          <template slot-scope="scope">
            <el-button size="mini" type="danger" @click="userDelete(scope.row.id)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <p style="text-align: center;">
        <el-pagination background @current-change=userpage :hide-on-single-page=value :current-page.sync=currentPage
          :page-size="10" layout="total,prev, pager, next" :total=total>
        </el-pagination>
      </p>
    </el-card>
    <el-dialog title="添加用户" width="60%" :visible.sync="postuserdialog" center :destroy-on-close="true" :close-on-click-modal="false" :show-close="false">
      <el-form :model="userForm" :rules="rules" ref="userForm" label-position="left" label-width="100px">
        <el-form-item label="上传头像" prop="imageUrl">
          <el-upload class="avatar-uploader" action="https://jsonplaceholder.typicode.com/posts/" :show-file-list="false"
            :auto-upload="false" :on-change="handleChange">
            <img v-if="userForm.imageUrl" :src="userForm.imageUrl" class="avatar">
            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
          </el-upload>
        </el-form-item>
        <el-form-item label="用户名" prop="username">
          <el-input type="text" v-model="userForm.username" maxlength="20"  show-word-limit placeholder="账号"></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input type="password" v-model="userForm.password" maxlength="20" show-word-limit placeholder="密码"></el-input>
        </el-form-item>
        <el-form-item label="重复密码" prop="repassword">
          <el-input type="password" v-model="userForm.repassword" maxlength="20" show-word-limit placeholder="确认密码"></el-input>
        </el-form-item>
        <el-form-item label="用户类型" prop="userrole">
          <el-radio-group v-model="userForm.userrole">
            <el-radio label="管理员"></el-radio>
            <el-radio label="普通用户"></el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="cancel">取 消</el-button>
        <el-button type="primary" @click="addUser('userForm')">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
  export default {
    name: 'UserAdmin',
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
          }],
          repassword: [{
            required: true,
            message: '请输入重复密码',
            trigger: 'blur'
          }],
          userrole: [{
            required: true,
            message: '请选择用户类型',
            trigger: 'blur'
          }],
          imageUrl: [{
            required: true,
            message: '请上传图片'
          }],
        },
        userForm: {
          username: '',
          password: '',
          repassword: '',
          userrole: '',
          imageUrl: '',
          imgfile: '',
        },
        userData: [],
        total: 0,
        currentPage: 1,
        value: true,
        postuserdialog: false,
      }
    },
    computed: {

    },
    methods: {
      userpage(currentPage) {
        const _this = this
        this.$axios.get('/admin/getusers', {
          params: {
            pageNum: currentPage
          }
        }).then((res) => {
          console.log(res.data)
          _this.userData = res.data.list
          _this.total = res.data.total
          _this.currentPage = res.data.pageNum
        })
      },
      userDelete(id) {
        console.log(id);
        const _this = this
        this.$axios.get('/admin/deleteUser', {
          params: {
            userId: id
          }
        }).then((res) => {
          console.log(res.data)
          this.$message({
            message: res.data.msg,
          });
          //刷新当前页
          this.userpage(this.currentPage)
        })
      },

      userDialog() {
        this.postuserdialog = true
      },
      cancel() {
        this.resetForm('userForm');
        this.postuserdialog = false
      },
      addUser(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            if (this.userForm.password===this.userForm.repassword) {
                 var userform = new FormData();
                 userform.append("username", this.userForm.username);
                 userform.append("password", this.userForm.password);
                 userform.append("imgfile", this.userForm.imgfile);
                 userform.append("userrole", this.userForm.userrole);
                 this.$axios({
                   url: '/admin/addUser',
                   method: 'post',
                   data: userform,
                   headers: {
                     'Content-Type': 'multipart/form-data'
                   },
                 }).then((res) => {
                   this.$message({
                     message: res.data.msg,
                   });
                   this.resetForm('userForm');
                   this.postuserdialog = false
                   //刷新当前页
                   this.userpage(this.currentPage)
                 })
            } else{
              this.$message({
                message: '密码不一致！',
                type: 'warning'
              });
            }
          } else {
            this.$message({
              message: '请按格式填满表单再提交！',
              type: 'warning'
            });
            return false;
          }
        });
      },
      //重置表单
      resetForm(formName) {
        if (this.$refs[formName] !== undefined) {
          this.$refs[formName].resetFields();
        }
      },
      handleChange(file, fileList) {
        console.log(file.name);
        const ftype = file.raw.type;
        console.log(ftype);
        const isIMG =
          file.raw.type === 'image/jpg' ||
          file.raw.type === 'image/jpeg' ||
          file.raw.type === 'image/png';
        const isLt2M = file.size / 1024 / 1024 < 2;
        if (!isIMG) {
          this.$message.error('上传图片只能是JPG、JPGE或PNG格式!');
        } else if (!isLt2M) {
          this.$message.error('上传图片大小不能超过 2MB!');
        } else {
          this.userForm.imageUrl = URL.createObjectURL(file.raw);
          this.userForm.imgfile = file.raw;

        }
      },
    },
    mounted() {
      this.userpage(1)
    }
  }
</script>

<style>
  .el-tag+.el-tag {
    margin-left: 10px;
  }

  .el-avatar {
    width: 50px;
    height: 50px;
  }

  .el-avatar>img {
    width: 50px;
    height: 50px;
  }
.avatar-uploader .el-upload {
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
  }

  .avatar-uploader .el-upload:hover {
    border-color: #409EFF;
  }

</style>
<style  scoped>


  .avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 178px;
    height: 178px;
    line-height: 178px;
    text-align: center;
  }

  .avatar {
    width: 178px;
    height: 178px;
    display: block;
  }
</style>
