import req from "./base";
const request = window.request ? window.request : req
export const queryServiceRequest = data => request.post(`/service-mgmt/v1/service-requests/query`, data);
export const getAllServiceRequest = () => request.get(`/service-mgmt/v1/service-requests/retrieve`);
export const createServiceRequest = data => request.post(`/service-mgmt/v1/service-requests`, data);
export const updateServiceRequest = data => request.put(`/service-mgmt/v1/service-requests/${data.id}/update`, data);
export const getAllAvailableServiceTemplate = () => request.get(`/service-mgmt/v1/service-request-templates/available`);
export const taskProcess = data => request.put(`/service-mgmt/v1/tasks/${data.taskId}/process`, data);
export const queryTask = data => request.post(`/service-mgmt/v1/tasks/query`, data);
export const taskTakeover = data => request.put(`/service-mgmt/v1/tasks/${data.taskId}/takeover`, data);
export const getCurrentUserRoles = () => request.get(`/service-mgmt/v1/core-resources/users/current-user/roles`);

export const getAllProcessDefinitionKeys = () => request.get(`/service-mgmt/v1/core-resources/workflow/process-definition-keys`);
export const getAllAvailableServiceCatalogues = () => request.get(`/service-mgmt/v1/service-catalogues/available`);
export const getServicePipelineByCatalogueId = id => request.get(`/service-mgmt/v1/service-pipelines/service-catalogues/${id}`);
export const createServiceRequestTemplate = data => request.post(`/service-mgmt/v1/service-request-templates`, data);
export const createServiceCatalogue = data => request.post(`/service-mgmt/v1/service-catalogues`, data);
export const createServicePipeline = data => request.post(`/service-mgmt/v1/service-pipelines`, data);
export const getAllRoles = () => request.get(`/service-mgmt/v1/core-resources/roles`);





