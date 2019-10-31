import Vue from 'vue'
import Router from 'vue-router'
import TaskManagement from "./views/Task-management.vue"
import ServiceCatalog from "./views/Service-catalog.vue"
 

// Vue.use(Router)

// export default new Router({
//   routes: [
//     {
//       path: '/task-management',
//       name: 'TaskManagement',
//       component: TaskManagement
//     },
//     {
//       path: '/service-catalog',
//       name: 'ServiceCatalog',
//       component: ServiceCatalog
//     }
//   ]
// })

export default [
  {
    path: '/task-management',
    name: 'TaskManagement',
    component: TaskManagement
  },
  {
    path: '/service-catalog',
    name: 'ServiceCatalog',
    component: ServiceCatalog
  }
]
