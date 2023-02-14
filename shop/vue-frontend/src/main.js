import '@babel/polyfill'
import 'mutationobserver-shim'
import Vue from 'vue'
import './plugins/bootstrap-vue'
import './plugins/bootstrap-vue'
import './plugins/bootstrap-vue'

// import './plugins/axios'
import axios from './utils/customaxios.js' //인터셉터 axios 보내기 전에 헤더에 jwt setting해주는 로직 추가

import App from './App.vue'
import router from './router'
import store from './store'

import { BootstrapVue, IconsPlugin } from 'bootstrap-vue'
import 'bootstrap/dist/css/bootstrap.min.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'

Vue.use(BootstrapVue)
Vue.use(IconsPlugin)
Vue.config.productionTip = false

Vue.prototype.$axios = axios; //전역으로 $axios 사용 (인터셉터가 항상 적용)

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
