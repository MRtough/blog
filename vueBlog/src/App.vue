<template>
  <div id="app">
    <router-view />


  </div>
</template>

<script>
  export default {
    name: 'App',
    data() {
      return{
      _gap_time: 0,
      _beforeUnload_time: 0,
      }
    },
    mounted() {
      window.addEventListener('beforeunload', e => this.beforeunloadHandler(e))
      window.addEventListener('unload', e => this.unloadHandler(e))
    },
    destroyed() {
      window.removeEventListener('beforeunload', e => this.beforeunloadHandler(e))
      window.removeEventListener('unload', e => this.unloadHandler(e))
    },
    methods: {
      beforeunloadHandler() {
        this._beforeUnload_time = new Date().getTime();
      },
      unloadHandler(e) {
        this._gap_time = new Date().getTime() - this._beforeUnload_time;
        //判断是窗口关闭还是刷新
        if (this._gap_time <= 5) {
          //关闭窗口前，移除用户
          $(function() {
              $.ajax({
                url: '/logout',
                type: 'get',
                async: false, //或false,是否异步
              })
          })
        }
      },
    }
  }
</script>

<style>
  html,body,#app {
    padding: 0;
    height: 100%;
    margin: 0;
  }
</style>
