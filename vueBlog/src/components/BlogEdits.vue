<template>
  <div>
    <el-breadcrumb separator-class="el-icon-arrow-right" style="margin-bottom: 15px;">
      <el-breadcrumb-item :to="{ path: '/Home' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>文章管理</el-breadcrumb-item>
      <el-breadcrumb-item>新建博文</el-breadcrumb-item>
    </el-breadcrumb>


    <el-card>
      <div slot="header" class="clearfix">
        <span>博文编辑</span>
      </div>
      <el-form :model="blogForm" :rules="rules" ref="blogForm" label-width="100px" label-position="top" class="demo-ruleForm">
        <el-form-item label="文章标题" prop="blogtitle">
          <el-input v-model="blogForm.blogtitle" maxlength="60" show-word-limit></el-input>
        </el-form-item>
        <el-form-item label="文章分类" prop="blogsort">
          <el-radio-group v-model="blogForm.blogsort">
            <el-radio label="个人博客"></el-radio>
            <el-radio label="技术心得"></el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="上传封面" prop="imageUrl">
          <el-upload class="avatar-uploader" action="https://jsonplaceholder.typicode.com/posts/" :show-file-list="false"
            :auto-upload="false" :on-change="handleChange">
            <img v-if="blogForm.imageUrl" :src="blogForm.imageUrl" class="avatar">
            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
          </el-upload>
        </el-form-item>
        <el-form-item label="文章內容" prop="blogcontent">
          <mavon-editor v-model="blogForm.blogcontent" ref=md @imgAdd="$imgAdd" @imgDel="$imgDel"></mavon-editor>
        </el-form-item>
      </el-form>
      <p style="float: right;">
        <el-button type="success" @click="addBlog('blogForm','否')">存草稿</el-button>
        <el-button type="success" @click="addBlog('blogForm','是')">发布</el-button>
        <el-button type="success" @click="resetForm('blogForm')">重置</el-button>
      </p>
    </el-card>
    <el-dialog title="为文章添加标签..." width="50%" :visible.sync="labledialog" center :close-on-click-modal="false"
      :destroy-on-close="true" @closed=closedialog>
      <div class="box-content">
        <label class="label-content">
          文章标签：
        </label>
        <div class="labels-box">
          <el-tag :key="tag" v-for="tag in dynamicTags" closable :disable-transitions="false" @close="handleClose(tag)">{{tag}}</el-tag>
        </div>
      </div>
      <div class="box-content">
        <label class="label-content">
          可选标签：
        </label>
        <div class="labels-box">
          <el-input v-model="labelinput" @keyup.enter.native="handleInputConfirm" @blur="handleInputConfirm"
            placeholder="Enter键入可添加自定义标签" maxlength="20" show-word-limit></el-input>
          <div class="label-select">
            <span>常用：</span>
            <el-tag :key="labeltag" v-for="labeltag in labelTags" @click.native="tagclick(labeltag)">{{labeltag}}</el-tag>
          </div>
        </div>
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button @click="cancel">取 消</el-button>
        <el-button type="primary" @click="submitLable">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
  export default {
    name: 'BlogEdits',
    data() {
      return {
        blogForm: {
          blogtitle: '',
          blogsort: '',
          imageUrl: '',
          imgfile: '',
          blogcontent: '',
        },
        rules: {
          blogtitle: [{
              required: true,
              message: '请输入文章标题',
              trigger: 'blur'
            },
            {
              min: 1,
              max: 60,
              message: '长度在 1 到 60 个字符',
              trigger: 'blur'
            }
          ],
          blogsort: [{
            required: true,
            message: '请选择分类',
            trigger: 'change'
          }],
          blogcontent: [{
            required: true,
            message: '请填写文章內容',
            trigger: 'blur'
          }],
          imageUrl: [{
            required: true,
            message: '请上传图片'
          }],
        },
        img_file: {},
        userid: '',
        //设置标签模态框数据定义
        labledialog: false,
        backBlogId: '',
        dynamicTags: [],
        labelTags: ['java', '前端', 'vue', 'Spring', '微服务', 'java架构', 'spring boot'],
        labelinput: ''
      }
    },
    methods: {
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
          this.blogForm.imageUrl = URL.createObjectURL(file.raw);
          this.blogForm.imgfile = file.raw;

        }
      },

      // beforeAvatarUpload(file) {
      //   const isJPG = file.type === 'image/jpeg';
      //   const isLt2M = file.size / 1024 / 1024 < 2;

      //   if (!isJPG) {
      //     this.$message.error('上传头像图片只能是 JPG 格式!');
      //   }
      //   if (!isLt2M) {
      //     this.$message.error('上传头像图片大小不能超过 2MB!');
      //   }
      //   return isJPG && isLt2M;
      // },

      //重置表单
      resetForm(formName) {
        if (this.$refs[formName] !== undefined) {
          this.$refs[formName].resetFields();

        }
      },

      noticebox(msg) {
        this.$message(msg);
      },

      // 绑定@imgAdd event
      $imgAdd(pos, $file) {
        // 缓存图片信息 pos初始為1
        console.log(pos)
        this.img_file[pos] = $file;
        console.log(this.img_file.length)
      },
      $imgDel(pos) {
        delete this.img_file[pos];
      },
      //添加文章的事件方法
      addBlog(formName, status) {
        //表单验证
        this.$refs[formName].validate((valid) => {
          if (valid) {
            //this.uploadimg();
            // 第一步.将图片上传到服务器.

            //判断img_file数组是否为空  使用ES6的Object.keys()方法
            //与4方法类似， 是ES6的新方法, 返回值也是对象中属性名组成的数组
            //var data = {};
            // var arr = Object.keys(data);
            //alert(arr.length == 0); //true

            var arr = Object.keys(this.img_file);
            console.log('图片数组大小：' + arr.length);
            //如果文章内容添加了本地图片 先上传文章内图片 再提交表单
            if (arr.length != 0) {
              var formdata = new FormData();
              for (var _img in this.img_file) {
                formdata.append("_img", this.img_file[_img]);
              }
              this.$axios({
                url: '/admin/uploadImgInBlog',
                method: 'post',
                data: formdata,
                headers: {
                  'Content-Type': 'multipart/form-data'
                },
              }).then((res) => {
                /**
                 * 例如：返回数据为 res = [[pos, url], [pos, url]...]
                 * pos 为原图片标志（0）
                 * url 为上传后图片的url地址
                 */
                // 第二步.将返回的url替换到文本原位置![...](0) -> ![...](url)
                console.log(res.data.list)
                const resData = res.data.list
                for (var i = 0; i < resData.length; i++) {
                  // $vm.$img2Url 详情见本页末尾
                  //.将返回的url替换到文本原位置
                  this.$refs.md.$img2Url(i + 1, '/static/' + resData[i]);
                }
                this.$message({
                  message: res.data.msg,
                });
                this.submitBlogForm(status);
              })
            } else {
              //如果文章内容没有添加本地图片 直接提交表单
              this.submitBlogForm(status);
            }
          } else {
            this.$message({
              message: '请按格式填满表单再提交！',
              type: 'warning'
            });
            return false;
          }
        })

      },
      //添加文章的表单
      submitBlogForm(status) {
        var blogform = new FormData();
        blogform.append("userid", this.userid);
        blogform.append("blogtitle", this.blogForm.blogtitle);
        blogform.append("blogsort", this.blogForm.blogsort);
        blogform.append("imgfile", this.blogForm.imgfile);
        blogform.append("blogcontent", this.blogForm.blogcontent);
        blogform.append("blogstatus", status);
        this.$axios({
          url: '/admin/addArticle',
          method: 'post',
          data: blogform,
          headers: {
            'Content-Type': 'multipart/form-data'
          },
        }).then((res) => {
          this.$message({
            message: res.data.msg,
          });
          //文章保存成功后返回文章的ID
          this.backBlogId = res.data.backBlogId
          console.log(this.backBlogId)
          this.resetForm('blogForm');
        })
          this.labledialog = true
      },
      //添加自定义标签视图
      handleInputConfirm() {
        let labelinput = this.labelinput;
        if (this.dynamicTags.indexOf(labelinput) == -1) {
          if (labelinput && this.dynamicTags.length < 3) {
            this.dynamicTags.push(labelinput);
            console.log(this.dynamicTags.length)
          } else {
            this.$message.error('最多添加3个标签！');
          }
        } else {
          this.$message.error('标签已存在！');
        }
        this.labelinput = '';
      },
      //删除标签视图
      handleClose(tag) {
        this.dynamicTags.splice(this.dynamicTags.indexOf(tag), 1);
      },

      //添加推荐常用标签视图
      tagclick(labeltag) {
        console.log(labeltag)
        let _labeltag = labeltag;
        if (this.dynamicTags.indexOf(_labeltag) == -1) {
          if (_labeltag && this.dynamicTags.length < 3) {
            this.dynamicTags.push(_labeltag);
            console.log(this.dynamicTags.length)
          } else {
            this.$message.error('最多添加3个标签！');
          }
        } else {
          this.$message.error('标签已存在！');
        }
      },
      //标签设置对话框关闭后的回调
      closedialog() {
        this.dynamicTags = []
        this.labelinput = ''
      },
      //取消标签设置
      cancel() {
        this.labledialog = false
        this.dynamicTags = []
        this.labelinput = ''
      },
      submitLable() {
        if (this.dynamicTags == '') {
          this.$message.error('请至少选择一个标签！');
        } else {
          var labelform = new FormData();
          labelform.append("labellist", this.dynamicTags);
          labelform.append("blogid", this.backBlogId);
          this.$axios({
            url: '/admin/postlabel',
            method: 'post',
            data: labelform,
            headers: {
              'Content-Type': 'application/x-www-form-urlencoded; charset=utf-8'
            },
          }).then((res) => {
            this.$message({
              message: res.data.msg,
            });
            this.labledialog = false
            this.dynamicTags = []
            this.labelinput = ''
          })
        }

      }


      // uploadimg($e) {
      //   // 第一步.将图片上传到服务器.
      //   var formdata = new FormData();
      //   for (var _img in this.img_file) {
      //     formdata.append("_img", this.img_file[_img]);
      //   }
      //   this.$axios({
      //     url: '/api/uploadImgInBlog',
      //     method: 'post',
      //     data: formdata,
      //     headers: {
      //       'Content-Type': 'multipart/form-data'
      //     },
      //   }).then((res) => {
      //     /**
      //      * 例如：返回数据为 res = [[pos, url], [pos, url]...]
      //      * pos 为原图片标志（0）
      //      * url 为上传后图片的url地址
      //      */
      //     // 第二步.将返回的url替换到文本原位置![...](0) -> ![...](url)
      //     console.log(res.data)
      //     const resData = res.data
      //     for (var i = 0; i < resData.length; i++) {

      //       // $vm.$img2Url 详情见本页末尾

      //       this.$refs.md.$img2Url(i + 1, '/api/' + resData[i]);
      //     }

      //   })
      // },
    },
    created() {
      if (sessionStorage.getItem("userid")) {
        this.userid = sessionStorage.getItem("userid")
        console.log("当前用户ID：" + this.userid)
      }
    },
    mounted() {
      window.scrollTo(0, 0);
    }
  }
</script>

<style>
  .box-content {
    display: flex;
    padding: 10px;
  }

  .label-content {
    white-space: nowrap;
  }

  .labels-box {
    width: 100%;
  }

  .label-select {
    margin-top: 10px;
    min-height: 200px;
    max-width: 100%;
  }

  .el-tag {
    margin-bottom: 10px;
  }

  .el-tag+.el-tag {
    margin-left: 10px;
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
<style scoped>
  .avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 250px;
    height: 178px;
    line-height: 178px;
    text-align: center;
  }

  .avatar {
    width: 250px;
    height: 178px;
    display: block;
  }
</style>
