
// import createPersistedState from "vuex-persistedstate";
import { createInstance } from "@/api/index.js";
import { createInstance2 } from "@/api/index.js";



const boardStore = {
  // plugins: [createPersistedState()],
  namespaced: true,

  state: {
    visitor_info: Array,
    profile_info: Object,
    search_user: Object,
    qboard_info: Array,
  },

  getters: {
    visitor_info(state) {
      return state.visitor_info;
    },
    profile_info(state) {
      return state.profile_info
    },
    search_user(state) {
      return state.search_user
    },
    qboard_info(state) {
      return state.qboard_info
    },
  },

  mutations: {
    GET_VISITORBOOK(state, data) {
      state.visitor_info = data
    },
    GET_PROFILE(state, data) {
      state.profile_info = data
    },
    SEARCH_USER(state, data) {
      state.search_user = data
    },
    GET_QBOARD(state, data) {
      state.qboard_info = data
    },
  },

  actions: { 
    async getVisitorBook(context, date) { // 방명록 조회
      // console.log(date)
      console.log('방명록 조회 들어옴?')
      const instance = createInstance()
      const response = await instance.get(`/guestbook?date=${date}`)
      // console.log(response.data)
      context.commit("GET_VISITORBOOK",response.data)
    },
    async writeVisitorBook(context, content) { // 방명록 쓰기
      console.log(content)
      console.log('방명록 쓰기 들어옴?')
      const instance = createInstance2()
      const response = await instance.post("/guestbook", content)
      console.log(response.data)
      // context.commit("GET_VISITORBOOK",response.data)
    },
    async getProfile(context) { // 프로필 조회
      console.log('프로필 조회 들어옴?')
      const instance = createInstance()
      const response = await instance.get("/users/profile")
      // console.log(response.data)
      context.commit("GET_PROFILE",response.data.user)
    },
    async searchUser(context, name) { // 유저검색 조회
      console.log('유저검색 들어옴?')
      const instance = createInstance()
      const response = await instance.get(`/users/?nickname=${name}`)
      console.log(response.data)
      context.commit("SEARCH_USER",response.data.users)
    },
    async getQBoard(context, userid) { // 유저별 무물 조회
      console.log('유저별 무물 조회?')
      const instance = createInstance()
      const response = await instance.get(`/users/profile/${userid}`)
      console.log(response.data,'무물조회')
      context.commit("GET_QBOARD",response.data)
    },
    async writeQBoard(context, value) { // 무물 쓰기
      const user_id = value.user_id
      const content = value.content
      console.log(user_id, content)
      console.log('무물 질문쓰기 들어옴?')
      const instance = createInstance2()
      const response = await instance.post(`/users/question/${user_id}`, content)
      console.log(response.data)
      // context.commit("GET_VISITORBOOK",response.data)
    },
    async writeQBoardAS(context, value) { // 무물 답변 쓰기
      const user_id = value.user_id
      const answer = value.answer
      const qna_pk = value.qna_pk

      console.log(user_id, answer, qna_pk)
      console.log('무물 답변쓰기 들어옴?')
      const instance = createInstance2()
      const response = await instance.post(`/users/answer/${user_id}/${qna_pk}`, answer)
      console.log(response.data)
      // context.commit("GET_VISITORBOOK",response.data)
    },
  }
}

export default boardStore