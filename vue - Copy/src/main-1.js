import Vue from 'vue'
import App from './App.vue'
import router from './router/index'
import store from './store/index'
import axios from 'axios'
import Vuesax from 'vuesax'

import 'vuesax/dist/vuesax.css' //Vuesax styles
//  Vue.use(Vuesax);

Vue.use(Vuesax, {colors: {
  primary:'#ff4d6d',
  success:'#9b5de5',
  danger:'rgb(242, 19, 93)',
  warning:'#f3df4d',
  black:'#8338ec'
}});

Vue.config.productionTip = false

axios.defaults.baseURL = process.env.VUE_APP_REMOTE_API;

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
