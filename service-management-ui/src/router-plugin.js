import TaskManagement from "./views/Task-management.vue"
import ServiceCatalog from "./views/Service-catalog.vue"

export default [
    {
      path: '/service-mgmt/task-management',
      name: 'TaskManagement',
      component: TaskManagement
    },
    {
      path: '/service-mgmt/service-catalog',
      name: 'ServiceCatalog',
      component: ServiceCatalog
    }
  ]