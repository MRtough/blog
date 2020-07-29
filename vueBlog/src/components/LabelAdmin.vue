<template>
  <div>
      <el-breadcrumb separator-class="el-icon-arrow-right" style="margin-bottom: 15px;">
        <el-breadcrumb-item :to="{ path: '/Home' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>标签管理</el-breadcrumb-item>
      </el-breadcrumb>
      <el-card>
        <el-table :data="labelData" border style="width: 100%;margin-top: 15px;">
          <el-table-column label="编号" width="100px">
            <template slot-scope="scope">
              <span style="margin-left: 10px">{{ scope.row.labelId }}</span>
            </template>
          </el-table-column>
          <el-table-column label="标签名">
            <template slot-scope="scope">
              <span style="margin-left: 10px">
               <el-tag>{{ scope.row.labelName }}</el-tag>
              </span>
            </template>
          </el-table-column>
          <el-table-column label="操作" width="150px">
            <template slot-scope="scope">
              <el-button size="mini" type="danger" @click="labelDelete(scope.row.labelId)">删除</el-button>
            </template>
          </el-table-column>
        </el-table>
        <p style="text-align: center;">
          <el-pagination background @current-change=labelpage :hide-on-single-page=value :current-page.sync=currentPage
            :page-size="10" layout="total,prev, pager, next" :total=total>
          </el-pagination>
        </p>
      </el-card>

  </div>
</template>

<script>
  export default {
    name: 'LableAdmin',
    data () {
      return {
        labelData: [],
        total: 0,
        currentPage: 1,
        value: true,
      }
    },
    methods: {
      labelpage(currentPage) {
        const _this = this
        this.$axios.get('/getlabels', {
          params: {
            pageNum: currentPage
          }
        }).then((res) => {
          console.log(res.data)
          _this.labelData = res.data.list
          _this.total = res.data.total
          _this.currentPage = res.data.pageNum
        })
      },
      labelDelete(id) {
        console.log(id);
        const _this = this
        this.$axios.get('/admin/deletelable', {
          params: {
            labelId: id
          }
        }).then((res) => {
          console.log(res.data)
          this.$message({
            message: res.data.msg,
          });
          //刷新当前页
          this.labelpage(this.currentPage)
        })
      }

    },
    mounted() {
      this.labelpage(1)
    }
  }
</script>

<style>
</style>
