import req from "./base";

export const queryServiceRequest = data => req.post(`/service-requests/query`, data);
export const getAllServiceRequest = () => req.get(`/service-requests/retrieve`);
export const createServiceRequest = data => req.post(`/service-requests/create`, data);
export const updateServiceRequest = data => req.put(`/service-requests/${data.id}/update`, data);
export const getAllAvailableServiceTemplate = () => req.get(`/service-request-templates/available`);
export const taskProcess = data => req.post(`/tasks/process`, data);
export const queryTask = data => req.post(`/tasks/query`, data);
export const taskTakeover = data => req.post(`/tasks/takeover`, data);
export const getCurrentUserRoles = () => req.get(`/users/current-user/roles`);





