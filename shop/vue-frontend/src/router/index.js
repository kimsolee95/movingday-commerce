import { LayoutPlugin } from 'bootstrap-vue'
import Vue from 'vue'
import VueRouter from 'vue-router'
import HomeView from '../views/HomeView.vue'
import LoginView from "../views/common/LoginView.vue"
import SignUpCompanyView from "../views/common/SignUpCompanyView.vue"
import CompanyLoginView from "../views/common/CompanyLoginView.vue"
import ManagingProductView from "../views/company/ManagingProductView.vue"

import { createRouter, createWebHistory } from 'vue-router'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'home',
    component: HomeView
  },
  {
    path: '/about',
    name: 'about',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import(/* webpackChunkName: "about" */ '../views/AboutView.vue')
  },
  {
    path: '/login',
    name: 'login',
    component: LoginView
  },
  {
    path: '/sign-up/company',
    name: 'signup',
    component: SignUpCompanyView
  },
  {
    path: '/login/company',
    name: 'loginCompany',
    component: CompanyLoginView
  },
  {
    path: '/company/service-products',
    name: 'managingServiceProducts',
    component: ManagingProductView
  }
]

const router = new VueRouter({
  routes
})

// const router = createRouter({
//   history: createWebHistory(process.env.BASE_URL),
//   routes
// })

export default router
