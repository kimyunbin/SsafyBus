import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)
import createPersistedState from "vuex-persistedstate";

import userStore from '@/store/modules/userStore.js'
import liveStore from '@/store/modules/liveStore.js'
import boardStore from '@/store/modules/boardStore.js'

const store = new Vuex.Store({
  plugins: [createPersistedState()],

  modules: {
    // 키: 값 형태로 저장됩니다.
    userStore: userStore,
    liveStore: liveStore,
    boardStore: boardStore,
  }
})

export default store