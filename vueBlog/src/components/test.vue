<template>
  <div>
    用户管理
    <!-- 测试滚动-->
    <div class="infinite-list-wrapper" style="overflow:auto">
      <ul class="list" v-infinite-scroll="load" infinite-scroll-disabled="disabled">
        <li v-for="(i,index) in list" class="list-item" :key="i.commentId">
          <el-row :gutter="3">
            <el-col :md="4">
              <div class="grid-content bg-purple">{{ i.commentUser }}</div>
            </el-col>
            <el-col :md="8">
              <div class="grid-content bg-purple">{{ i.commentContent }}</div>
            </el-col>
            <el-col :md="6">
              <div class="grid-content bg-purple">{{ i.commentDate}}</div>
            </el-col>
            <el-col :md="6">
              <div class="grid-content bg-purple">
                <el-button size="mini" type="danger" @click="commentDelete(i.commentId,index)">删除</el-button>
              </div>
            </el-col>
          </el-row>
        </li>
      </ul>
      <p v-if="loading">加载中...</p>
      <p v-if="noMore">没有更多了</p>
    </div>
    <el-button size="mini" @click="blogComments(146,1)">查看评论</el-button>
  </div>
</template>

<script>
  export default {
    name: 'UserAdmin',
    data() {
      return {
        count: 1,
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
        return this.loading || this.noMore
      }
    },
    methods: {
      load() {
        this.loading = true
        setTimeout(() => {
          this.count += 1
          this.loading = false
          this.blogComments(this.articleId, this.count); //调用接口，此时页数+1，查询下一页数据
        }, 1000)
      },
      blogComments(blogid, count) {
        this.commentsdialog = true
        this.articleId = blogid
        console.log(this.articleId)
        this.$axios.get('/api/allComments', {
          params: {
            pageNum: count,
            articleId: this.articleId
          }
        }).then((res) => {
          console.log(res.data)
          this.list = this.list.concat(res.data.pageInfo.list) //因为每次后端返回的都是数组，所以这边把数组拼接到一起
          this.totalPages = res.data.pageInfo.pages
          this.loading = false;


        })

      },
      commentDelete(commentid, index) {
        this.$axios.get('/api/deleteComment', {
          params: {
            commentId: commentid
          }
        }).then((res) => {
          console.log(res.data)
          this.$message({
            message: res.data.msg,
            type: 'success'
          });
          //删除dom
          this.list.splice(index, 1);

        })
      },
    }
  }
</script>

<style>
  .infinite-list-wrapper {
    height: 300px;
  }

  .list {
    list-style: none;
  }

  .list-item {

    height: 80px;
  }
</style>
