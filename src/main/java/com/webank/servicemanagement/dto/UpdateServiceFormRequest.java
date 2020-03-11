package com.webank.servicemanagement.dto;

import com.webank.servicemanagement.domain.ServiceForm;

public class UpdateServiceFormRequest {
	private String name;
	private String description;
	private String processDefinitionKey;
	private String status;

	public static ServiceForm toDomain(
			UpdateServiceFormRequest updateServiceFormRequest,
			ServiceForm existedServiceForm) {
		ServiceForm serviceForm = existedServiceForm;
		if (serviceForm == null) {
			serviceForm = new ServiceForm();
		}

		if (updateServiceFormRequest.getName() != null) {
			serviceForm.setName(updateServiceFormRequest.getName());
		}

		if (updateServiceFormRequest.getDescription() != null) {
			serviceForm.setDescription(updateServiceFormRequest.getDescription());
		}

		if (updateServiceFormRequest.getProcessDefinitionKey() != null) {
			serviceForm
					.setProcessDefinitionKey(updateServiceFormRequest.getProcessDefinitionKey());
		}

		if (updateServiceFormRequest.getStatus() != null) {
			serviceForm.setStatus(updateServiceFormRequest.getStatus());
		}

		return serviceForm;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getProcessDefinitionKey() {
		return processDefinitionKey;
	}

	public void setProcessDefinitionKey(String processDefinitionKey) {
		this.processDefinitionKey = processDefinitionKey;
	}
}
