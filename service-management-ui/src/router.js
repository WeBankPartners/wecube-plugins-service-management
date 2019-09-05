import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

export default new Router({
  mode: 'history',
  base: process.env.BASE_URL,
  routes: [
    {
      path: '/task-management',
      name: 'home',
      component: () => import(/* webpackChunkName: "Task-management" */ './views/Task-management.vue')
    },
    {
      path: '/service-catalog',
      name: 'about',
      component: () => import(/* webpackChunkName: "Service-catalog" */ './views/Service-catalog.vue')
    }
  ]
})
