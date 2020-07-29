import Vue from 'vue'
import Router from 'vue-router'
import BlogAdmin from "../components/BlogAdmin.vue"
import MessageAdmin from "../components/MessageAdmin.vue"
import LabelAdmin from "../components/LabelAdmin.vue"
import UserAdmin from "../components/UserAdmin.vue"
import Home from "../components/Home.vue"
import Welcome from "../components/Welcome.vue"
import BlogEdits from "../components/BlogEdits.vue"
import Login from "../components/Login.vue"

Vue.use(Router)

export default new Router({
  routes: [{
      path: '/',
      component: Login
    },
    {
      path: '/Home',
      component: Home,
      redirect: '/Welcome',
      meta: {
        requireAuth: true
      },
      children: [{
          path: '/Welcome',
          component: Welcome
        },
        {
          path: '/BlogAdmin',
          component: BlogAdmin
        },
        {
          path: '/BlogEdits',
          component: BlogEdits
        },
        {
          path: '/MessageAdmin',
          component: MessageAdmin
        },
        {
          path: '/LabelAdmin',
          component: LabelAdmin
        },
        {
          path: '/UserAdmin',
          component: UserAdmin
        },
        
      ]
    },

  ]
})
