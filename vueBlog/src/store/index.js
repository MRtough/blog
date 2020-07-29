import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    userid:'',
    username:'',
    userprofile:''
  },
  mutations: {
    // set
    set_userid: (state, userid) => {
      state.userid = userid
      sessionStorage.setItem("userid", userid)
    },
    set_username: (state, username) => {
      state.username = username
      sessionStorage.setItem("username", username)
    },
   set_userprofile: (state, userprofile) => {
      state.userprofile = userprofile
      sessionStorage.setItem("userprofile", userprofile)
    },
    remove_user: (state) => {
      state.username = ''
      state.userprofile = ''
      state.userid=''
      sessionStorage.setItem("userid",'')
      sessionStorage.setItem("username", '')
      sessionStorage.setItem("userprofile",'')
    }

  },
  getters: {
    // get
    getUserid: state => {
      return state.userid
    },
    getUsername: state => {
      return state.username
    },
    getUserface: state => {
      return state.userprofile
    }

  },
  actions: {
  },
  modules: {
  }
})
