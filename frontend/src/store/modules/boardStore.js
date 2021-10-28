
import createPersistedState from "vuex-persistedstate";
import { createInstance } from "@/api/index.js";
import { createInstance2 } from "@/api/index.js";



const boardStore = {
  plugins: [createPersistedState()],
  namespaced: true,

  state: {
    visitor_info: Array,
  },

  getters: {
    visitor_info(state) {
      return state.visitor_info;
    },
  },

  mutations: {
    GET_VISITORBOOK(state, data) {
      state.visitor_info = data

    }
  },

  actions: { 
    async getVisitorBook(context, date) {
      // console.log(date)
      console.log('방명록 조회 들어옴?')
      const instance = createInstance()
      const response = await instance.get(`/guestbook?date=${date}`)
      // console.log(response.data)
      context.commit("GET_VISITORBOOK",response.data)
    },
    async writeVisitorBook(context, content) {
      console.log(content)
      console.log('방명록 쓰기 들어옴?')
      const instance = createInstance2()
      const response = await instance.post("/guestbook", content)
      console.log(response.data)
      // context.commit("GET_VISITORBOOK",response.data)
    },
  }
}

export default boardStore