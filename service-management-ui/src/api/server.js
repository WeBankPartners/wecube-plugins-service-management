import req from "./base";
const request = window.request ? window.request : req
export const queryServiceRequest = data => request.post(`/service-management/service-requests/query`, data);
export const getAllServiceRequest = () => request.get(`/service-management/service-requests/retrieve`);
export const createServiceRequest = data => request.post(`/service-management/service-requests`, data);
export const updateServiceRequest = data => request.put(`/service-management/service-requests/${data.id}/update`, data);
export const getAllAvailableServiceTemplate = () => request.get(`/service-management/service-request-templates/available`);
export const taskProcess = data => request.put(`/service-management/tasks/${data.taskId}/process`, data);
export const queryTask = data => request.post(`/service-management/tasks/query`, data);
export const taskTakeover = data => request.put(`/service-management/tasks/${data.taskId}/takeover`, data);
export const getCurrentUserRoles = () => request.get(`/service-management/core-resources/users/current-user/roles`);

export const getAllProcessDefinitionKeys = () => request.get(`/service-management/core-resources/workflow/process-definition-keys`);
export const getAllAvailableServiceCatalogues = () => request.get(`/service-management/service-catalogues/available`);
export const getServicePipelineByCatalogueId = id => request.get(`/service-management/service-pipelines/service-catalogues/${id}`);
export const createServiceRequestTemplate = data => request.post(`/service-management/service-request-templates`, data);
export const createServiceCatalogue = data => request.post(`/service-management/service-catalogues`, data);
export const createServicePipeline = data => request.post(`/service-management/service-pipelines`, data);
export const getAllRoles = () => request.get(`/service-management/core-resources/roles`);





