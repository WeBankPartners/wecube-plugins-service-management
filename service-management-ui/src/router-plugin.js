import TaskManagement from "./views/Task-management.vue"
import ServiceCatalog from "./views/Service-catalog.vue"

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