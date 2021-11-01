// import axios from 'axios'
// const BASE_URL = process.env.VUE_APP_BASE_URL

// import createPersistedState from "vuex-persistedstate";
// import jwt_decode from "jwt-decode";

import { createInstance } from "@/api/index.js";
// import { createInstance2 } from "@/api/index.js";



const userStore = {
  // plugins: [createPersistedState()],
  namespaced: true,

  state: {
    user_info: null,
    token: '',
  },

  getters: {
    user_info(state) {
      return state.user_info;
    },
    token(state) {
      return state.token;
    },
  },

  mutations: {
    TOKEN(state, token) {
      state.token = token
    },
    USER_INFO(state, data) {
      state.user_info = data
    }
  },

  actions: { 
    async signUp(context, signup_info) {
      console.log('회원가입 들어옴?')
      const instance = createInstance()
      const response = await instance.post("/users", signup_info)
      console.log(response.data)
      // context.commit("SIGNUP",response.data.status)
    },
    async login(context, login_info) {
      console.log('로그인 들어옴?')
      const instance = createInstance()
      const response = await instance.post("/users/login", login_info)
      console.log(response.data)

      const token = response.data.token
      localStorage.setItem('token', token)

      const users = response.data.users
      // const data = JSON.stringify(users) 
      localStorage.setItem('userInfo', users)

      context.commit("TOKEN", token)
      context.commit("USER_INFO", users)
    },
    async idCheck(context, id) {
      console.log('중복체크 들어옴?')
      const instance = createInstance()
      const response = await instance.post("/users/check", id)
      console.log(response)
      // context.commit("SIGNUP",response.data.status)
    },
  }
}

export default userStore