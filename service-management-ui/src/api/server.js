import req from "./base";

export const queryServiceRequest = data => req.post(`/api/service-management/v1/service-requests/query`, data);
export const getAllServiceRequest = () => req.get(`/api/service-management/v1/service-requests/retrieve`);
export const createServiceRequest = data => req.post(`/api/service-management/v1/service-requests`, data);
export const updateServiceRequest = data => req.put(`/api/service-management/v1/service-requests/${data.id}/update`, data);
export const getAllAvailableServiceTemplate = () => req.get(`/api/service-management/v1/service-request-templates/available`);
export const taskProcess = data => req.put(`/api/service-management/v1/tasks/${data.taskId}/process`, data);
export const queryTask = data => req.post(`/api/service-management/v1/tasks/query`, data);
export const taskTakeover = data => req.put(`/api/service-management/v1/tasks/${data.taskId}/takeover`, data);
export const getCurrentUserRoles = () => req.get(`/api/service-management/v1/core-resources/users/current-user/roles`);

export const getAllProcessDefinitionKeys = () => req.get(`/api/service-management/v1/core-resources/workflow/process-definition-keys`);
export const getAllAvailableServiceCatalogues = () => req.get(`/api/service-management/v1/service-catalogues/available`);
export const getServicePipelineByCatalogueId = id => req.get(`/api/service-management/v1/service-pipelines/service-catalogues/${id}`);
export const createServiceRequestTemplate = data => req.post(`/api/service-management/v1/service-request-templates`, data);
export const createServiceCatalogue = data => req.post(`/api/service-management/v1/service-catalogues`, data);
export const createServicePipeline = data => req.post(`/api/service-management/v1/service-pipelines`, data);
export const getAllRoles = () => req.get(`/api/service-management/v1/core-resources/roles`);





