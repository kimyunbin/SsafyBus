
// import createPersistedState from "vuex-persistedstate";
import { createInstance } from "@/api/index.js";
import { createInstance2 } from "@/api/index.js";
import { createInstance3 } from "@/api/index.js";



const boardStore = {
  // plugins: [createPersistedState()],
  namespaced: true,

  state: {
    visitor_info: Array,
    profile_info: Object,
    search_user: Object,
    qboard_info: Array,
    help_info: Object,
    help_one_info: Object,
    edit_info: Object,
    share_info: Array,
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
    help_info(state) {
      return state.help_info
    },
    help_one_info(state) {
      return state.help_one_info
    },
    edit_info(state) {
      return state.edit_info
    },
    share_info(state) {
      return state.share_info
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
    GET_HELPLIST(state, data) {
      state.help_info = data
    },
    GET_HELPITEM(state, data) {
      state.help_one_info = data
    },
    EDIT_HELPITEM(state, data) {
      state.edit_info = data
    },
    GET_SHARELIST(state, data) {
      state.share_info = data
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
      console.log(response.data)
      context.commit("GET_PROFILE",response.data.user.content)
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
    // Help
    async getHelpList(context) { // 헬프게시판 조회
      console.log('헬프게시판 조회 들어옴?')
      const instance = createInstance()
      const response = await instance.get("/help")
      console.log(response.data)
      context.commit("GET_HELPLIST",response.data.help)
    },
    async writeHelpList(context, content) { // 헬프게시판 쓰기
      console.log(content)
      console.log('헬프게시판 쓰기 들어옴?')
      const instance = createInstance2()
      const response = await instance.post("/help", content)
      console.log(response.data)
    },
    async getHelpItem(context, help_pk) { // 헬프게시판 단일조회
      console.log('헬프게시판 단일조회 들어옴?')
      const instance = createInstance()
      const response = await instance.get(`/help/${help_pk}`)
      console.log(response.data)
      context.commit("GET_HELPITEM",response.data)
    },
    async writeComment(context, value) { // 헬프게시판 답글 쓰기
      const help_pk = value.help_pk
      const content = value.content
      console.log(help_pk, content)
      console.log('헬프게시판 답글쓰기 들어옴?')
      const instance = createInstance2()
      const response = await instance.post(`/help/${help_pk}`, content)
      console.log(response.data)
    },    
    editHelp(context, value) { // 헬프게시판 수정할때 데이터
      console.log('헬프게시판 수정정보 들어옴?')
      console.log(value)
      context.commit("EDIT_HELPITEM", value)
    },
    async editHelpItem(context, value) { // 헬프게시판 수정
      console.log('헬프게시판 수정 들어옴?')
      const help_pk = value.help_pk
      const instance = createInstance2()
      const response = await instance.put(`/help/${help_pk}`, value.data)
      console.log(response.data)
    }, 
    async deleteHelpItem(context, help_pk) { // 헬프게시판 삭제
      console.log('헬프게시판 삭제 들어옴?')
      const instance = createInstance2()
      const response = await instance.delete(`/help/${help_pk}`)
      console.log(response.data)
    }, 

    //
    async getShareList(context) { // 공유게시판 조회
      console.log('공유게시판 조회 들어옴?')
      const instance = createInstance2()
      const response = await instance.get("/sharefile")
      console.log(response.data)
      context.commit("GET_SHARELIST",response.data.sharefile)
    },
    async writeShareList(context, data) { // 공유게시판 쓰기
      console.log('공유게시판 쓰기 들어옴?')
      const instance = createInstance3()
      const response = await instance.post("/sharefile", data)
      console.log(response.data)
      // context.commit("GET_SHARELIST",response.data.sharefile)
    },
    async shareDownload(context,path) { // 공유 다운로드
      console.log('공유 다운로드 들어옴?')
      const instance = createInstance()
      const response = await instance.get(`/sharefile/${path}`, {
        responseType: "blob",
    })
      console.log(response)
      return response
    },
  }
}

export default boardStore