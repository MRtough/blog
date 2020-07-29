import router from "./router";

// 路由判断登录 根据路由配置文件的参数
router.beforeEach((to, from, next) => {

  if (to.matched.some(record => record.meta.requireAuth)) { // 判断该路由是否需要登录权限

    const username = sessionStorage.getItem("username")
    
    if (username) { //
      if (to.path === '/') {
      } else {
        next()
      }
    } else {
      alert("请登录！")
      next({
        path: '/'
      })
    }
  } else {
    next()
  }
})
