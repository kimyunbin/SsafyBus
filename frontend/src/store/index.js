import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

import userStore from '@/store/modules/userStore.js'
import liveStore from '@/store/modules/liveStore.js'
import helpStore from '@/store/modules/helpStore.js'

const store = new Vuex.Store({
  modules: {
    // 키: 값 형태로 저장됩니다.
    userStore: userStore,
    liveStore: liveStore,
    helpStore: helpStore,
  }
})

export default store