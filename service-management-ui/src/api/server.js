import req from "./base";

export const queryServiceRequest = data => req.post(`/service-management/service-requests/query`, data);
export const getAllServiceRequest = () => req.get(`/service-management/service-requests/retrieve`);
export const createServiceRequest = data => req.post(`/service-management/service-requests`, data);
export const updateServiceRequest = data => req.put(`/service-management/service-requests/${data.id}/update`, data);
export const getAllAvailableServiceTemplate = () => req.get(`/service-management/service-request-templates/available`);
export const taskProcess = data => req.put(`/service-management/tasks/${data.taskId}/process`, data);
export const queryTask = data => req.post(`/service-management/tasks/query`, data);
export const taskTakeover = data => req.put(`/service-management/tasks/${data.taskId}/takeover`, data);
export const getCurrentUserRoles = () => req.get(`/service-management/core-resources/users/current-user/roles`);

export const getAllProcessDefinitionKeys = () => req.get(`/service-management/core-resources/workflow/process-definition-keys`);
export const getAllAvailableServiceCatalogues = () => req.get(`/service-management/service-catalogues/available`);
export const getServicePipelineByCatalogueId = id => req.get(`/service-management/service-pipelines/service-catalogues/${id}`);
export const createServiceRequestTemplate = data => req.post(`/service-management/service-request-templates`, data);
export const createServiceCatalogue = data => req.post(`/service-management/service-catalogues`, data);
export const createServicePipeline = data => req.post(`/service-management/service-pipelines`, data);
export const getAllRoles = () => req.get(`/service-management/core-resources/roles`);





