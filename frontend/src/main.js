import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from '@/store/index.js'
import Vuetify from 'vuetify'
import 'vuetify/dist/vuetify.min.css'

// Vue.use(BootstrapVue)
Vue.config.productionTip = false
Vue.use(Vuetify);

new Vue({
  router,
  store,
  render: h => h(App),
}).$mount('#app')
