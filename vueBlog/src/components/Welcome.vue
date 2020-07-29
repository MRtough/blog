<template>
  <div>
    welcome:{{user.username}}
    <div id="tt">dasdasda</div>

  </div>
</template>

<script>
  export default {
    name: 'welcome',
    data() {
      return {
        user: {
          username: '',
          userface: '',
        }
      }
    },
    created() {
      if (sessionStorage.getItem("username")) {
        this.user.username = sessionStorage.getItem("username")
        this.user.userface = sessionStorage.getItem("userprofile")
      }
      $(function() {
        $("#tt").hide();
      })

    },
    methods: {

    }

  }
</script>

<style>

</style>
