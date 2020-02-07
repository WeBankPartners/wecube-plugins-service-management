import Vue from 'vue'
import Router from 'vue-router'
 

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'home',
      redirect: '/task-management',
      component: () => import("./index.vue"),
      children: [
        {
          path: '/task-management',
          name: 'TaskManagement',
          component: () => import("./views/Task-management.vue")
        },
        {
          path: '/service-catalog',
          name: 'ServiceCatalog',
          component: () => import("./views/Service-catalog.vue")
        }
      ]
    }
  ]
})

