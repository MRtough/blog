<template>
  <div>

    <el-breadcrumb separator-class="el-icon-arrow-right" style="margin-bottom: 15px;">
      <el-breadcrumb-item :to="{ path: '/Home' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>留言管理</el-breadcrumb-item>
    </el-breadcrumb>
    <el-card>
      <el-table :data="msgData" border style="width: 100%">
        <el-table-column label="编号" width="100px">
          <template slot-scope="scope">

            <span style="margin-left: 10px">{{ scope.row.messageId }}</span>
          </template>
        </el-table-column>
        <el-table-column label="匿名用户名" width="200px">
          <template slot-scope="scope">

            <span style="margin-left: 10px">{{ scope.row.messageUser }}</span>
          </template>
        </el-table-column>
        <el-table-column label="内容">
          <template slot-scope="scope">

            <span style="margin-left: 10px">{{ scope.row.messageContent }}</span>
          </template>
        </el-table-column>
        <el-table-column label="时间" width="200px">
          <template slot-scope="scope">
            <i class="el-icon-time"></i>
            <span style="margin-left: 10px">{{ scope.row.messageData }}</span>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="100px">
          <template slot-scope="scope">
            <el-button size="mini" type="danger" @click="msgDelete(scope.row.messageId)">删除</el-button>
          </template>
        </el-table-column>

      </el-table>
      <p style="text-align: center;">
        <el-pagination background @current-change=messagespage :hide-on-single-page=value :current-page.sync=currentPage
          :page-size="10" layout="total,prev, pager, next" :total=total>
        </el-pagination>
      </p>
    </el-card>
  </div>
</template>

<script>
  export default {
    name: 'MessageAdmin',
    data() {
      return {
        msgData: [],
        total: 0,
        currentPage: 1,
        value: true,

      }
    },
    methods: {
      messagespage(currentPage) {
        const _this = this
        this.$axios.get('/getallmessage', {
          params: {
            pageNum: currentPage
          }
        }).then((res) => {
          console.log(res.data)
          _this.msgData = res.data.list
          _this.total = res.data.total
          _this.currentPage=res.data.pageNum

        })
      },
      msgDelete(msgid) {
        console.log(msgid);
        const _this = this
        this.$axios.get('/admin/deleteMsg', {
          params: {
            messageId: msgid
          }
        }).then((res) => {
          console.log(res.data)
          this.$message({
            message: res.data.msg,
          });
          //刷新当前页
          this.messagespage(this.currentPage)
        })
      }
    },
    created() {

    },
    mounted() {
      this.messagespage(1)
    }

  }
</script>

<style>
</style>
