<template>
  <div>
    <el-breadcrumb separator-class="el-icon-arrow-right" style="margin-bottom: 15px;">
      <el-breadcrumb-item :to="{ path: '/Home' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>文章管理</el-breadcrumb-item>
      <el-breadcrumb-item>文章列表</el-breadcrumb-item>
    </el-breadcrumb>
    <el-card>
      <el-table :data="blogData" border style="width: 100%">
        <el-table-column label="编号" width="100px">
          <template slot-scope="scope">
            <span style="margin-left: 10px">{{ scope.row.articleId}}</span>
          </template>
        </el-table-column>
        <el-table-column label="作者Id" width="100px">
          <template slot-scope="scope">
            <span style="margin-left: 10px">{{ scope.row.sysUserId}}</span>
          </template>
        </el-table-column>
        <el-table-column label="文章标题">
          <template slot-scope="scope">
            <span style="margin-left: 10px">{{ scope.row.articleTitle}}</span>
          </template>
        </el-table-column>
        <el-table-column label="类别" width="120px">
          <template slot-scope="scope">
            <!--v-if的使用-->
            <span style="margin-left: 10px" v-if="scope.row.sortId === 1">个人博客</span>
            <span style="margin-left: 10px" v-if="scope.row.sortId === 2">技术心得</span>
          </template>
        </el-table-column>
        <el-table-column label="时间" width="200px">
          <template slot-scope="scope">
            <i class="el-icon-time"></i>
            <span style="margin-left: 10px">{{ scope.row.articleDate}}</span>
          </template>
        </el-table-column>
        <el-table-column label="是否发布" width="100px">
          <template slot-scope="scope">
            <span style="margin-left: 10px">{{ scope.row.publishStatus}}</span>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="300px">
          <template slot-scope="scope">
            <span v-if="scope.row.publishStatus === '否'">
              <el-button size="mini" @click="blogpublish(scope.row.articleId)">发布</el-button>
            </span>

            <el-button size="mini" @click="blogComments(scope.row.articleId,1)">查看评论</el-button>
            <el-button size="mini" type="danger" @click="blogDelete(scope.row.articleId)">删除</el-button>
          </template>
        </el-table-column>

      </el-table>

      <p style="text-align: center;">
        <el-pagination background @current-change=blogspage :hide-on-single-page=value :current-page.sync=currentPage
          :page-size="10" layout="total,prev, pager, next" :total=total>
        </el-pagination>
      </p>
    </el-card>

    <el-dialog title="文章评论" width="80%" :visible.sync="commentsdialog" center :destroy-on-close="true" :close-on-click-modal="false" @closed=closedialog>
      <!-- 测试滚动-->
      <div class="infinite-list-wrapper" style="overflow:auto">
        <el-row >
          <el-col :md="6">
            <div class="comheader">用户名</div>
          </el-col>
          <el-col :md="12">
            <div class="comheader">内容</div>
          </el-col>
          <el-col :md="4">
            <div class="comheader">时间</div>
          </el-col>
          <el-col :md="2">
            <div class="comheader">
              操作
            </div>
          </el-col>
        </el-row>
        <ul class="list" v-infinite-scroll="load" infinite-scroll-disabled="disabled">
          <li v-for="(i,index) in list" class="list-item" :key="i.commentId">
            <el-row class="rowcomment">
              <el-col :md="6">
                <div class="bg-purple">{{ i.commentUser }}</div>
              </el-col>
              <el-col :md="12">
                 <el-popover
                    placement="top-start"
                    title="评论内容"
                    width="300"
                    trigger="hover"
                    >{{ i.commentContent }}
                    <div class="bg-purple" slot="reference">
                    {{ i.commentContent }}
                    </div>
                  </el-popover>
              </el-col>
              <el-col :md="4">
                <div class="bg-purple">{{ i.commentDate}}</div>
              </el-col>
              <el-col :md="2">
                <div class="bg-purple">
                  <el-button size="mini" type="danger" @click="commentDelete(i.commentId,index)">删除</el-button>
                </div>
              </el-col>
            </el-row>
          </li>
        </ul>
        <p v-if="loading" style="text-align: center;">加载中...</p>
        <p v-if="noMore" style="text-align: center;">没有更多了</p>
      </div>
      <span slot="footer" class="dialog-footer">
      </span>
    </el-dialog>
  </div>

</template>

<script>
  export default {
    name: 'BlogAdmin',
    data() {
      return {
        blogData: [],
        total: 0,
        currentPage: 1,
        value: true,
        blogsort: '',
        commentsdialog: false,

        //查看评论模态框中的数据定义
        count: 1, //起始页数值
        loading: false,
        totalPages: '', //取后端返回内容的总页数
        list: [], //后端返回的数组
        articleId: '',

      }
    },
    computed: {
      noMore() {
        //当起始页数大于总页数时停止加载
        return this.count >= this.totalPages;
      },
      disabled() {
        return this.loading || this.noMore;
      }
    },
    methods: {
      blogspage(currentPage) {
        const _this = this
        this.$axios.get('/allArticle', {
          params: {
            pageNum: currentPage
          }
        }).then((res) => {
          console.log(res.data)
          _this.blogData = res.data.list
          _this.total = res.data.total
          _this.currentPage = res.data.pageNum

        })
      },
      blogDelete(blogid) {
        const _this = this
        this.$axios.get('/admin/deleteArticle', {
          params: {
            articleId: blogid
          }
        }).then((res) => {
          console.log(res.data)
          this.$message({
            message: res.data.msg,
          });
          //刷新当前页
          this.blogspage(this.currentPage)
        })
      },
      blogpublish(blogid) {
        const _this = this
        this.$axios.get('/admin/publishblog', {
          params: {
            articleId: blogid
          }
        }).then((res) => {
          console.log(res.data)
          this.$message({
            message: res.data.msg,
          });
          //刷新当前页
          this.blogspage(this.currentPage)
        })

      },
      //查看评论对话框关闭后的回调
      closedialog() {
        this.list = []
        this.count = 1
      },
      load() {
        //滑到底部时进行加载
        this.loading = true;
        setTimeout(() => {
          this.count += 1; //页数+1
          this.blogComments(this.articleId, this.count); //调用接口，此时页数+1，查询下一页数据
          this.loading = false;
        }, 1000);
      },

      blogComments(blogid, count) {
        this.commentsdialog = true
        this.articleId = blogid
        console.log(this.articleId)
        this.$axios.get('/allComments', {
          params: {
            pageNum: count,
            articleId: this.articleId
          }
        }).then((res) => {
          console.log(res.data)
          this.list = this.list.concat(res.data.pageInfo.list) //因为每次后端返回的都是数组，所以这边把数组拼接到一起
          this.totalPages = res.data.pageInfo.pages

        })
      },
      commentDelete(commentid, index) {
        this.$axios.get('/admin/deleteComment', {
          params: {
            commentId: commentid
          }
        }).then((res) => {
          console.log(res.data)
          this.$message({
            message: res.data.msg,
          });
          //删除dom
          if(res.data.msg==='删除成功!'){
            this.list.splice(index, 1);
          }


        })
      },



    },
    created() {
      this.blogspage(1)

    },
    mounted() {

    }
  }
</script>

<style scoped>
  *{
    box-sizing: border-box;
  }
  .infinite-list-wrapper {
    height: 300px;
  }

  .bg-purple {
    height: 70px;
    line-height: 70px;
    overflow: hidden;
    padding-left: 10px;
  }
  .comheader{
    height: 70px;
    line-height: 70px;
    text-align: center;
    color: #606266;
    font-weight: 700;
  }

  div.rowcomment:hover{
    background-color: #ebeef5;
  }
  .el-col {
    height: 70px;
    border: 1px solid #EBEEF5;
  }

  .list {
    padding: 0;
    margin: 0;
    list-style: none;
  }

  .list-item {
    display: block;
  }
  .el-tooltip{

  }
</style>
