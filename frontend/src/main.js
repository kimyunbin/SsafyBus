import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from '@/store/index.js'
import vuetify from './plugins/vuetify'
// import Vuetify from 'vuetify'
// import 'vuetify/dist/vuetify.min.css'

// Vue.use(Vuetify)
// import vuetify from './plugins/vuetify';


Vue.config.productionTip = false

new Vue({
  router,

  // vuetify,
  store,

  vuetify,
  render: h => h(App)
}).$mount('#app')
