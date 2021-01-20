import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    token:'',
    userName:''
  },
  mutations: {
    SET_TOKEN:(state,token) => {
      state.token = token
      localStorage.setItem("token",token)
    },
    SET_USERNAME:(state,userName) => {
      state.userName = userName
      sessionStorage.setItem("userName",userName)
    },
    REMOVE_INFO:(state) => {
      state.token = ''
      state.userName = ''
      localStorage.setItem("token",'')
      sessionStorage.setItem("userName",'')
    }
  },
  gettes: {

  },
  actions: {

  },
  modules: {

  }
})
