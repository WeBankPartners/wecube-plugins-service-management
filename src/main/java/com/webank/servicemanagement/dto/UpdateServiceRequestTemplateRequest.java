package com.webank.servicemanagement.dto;

import com.webank.servicemanagement.domain.ServicePipeline;
import com.webank.servicemanagement.domain.ServiceRequestTemplate;

public class UpdateServiceRequestTemplateRequest {
	private String name;
	private String description;
	private String processDefinitionKey;
	private String status;

	public static ServiceRequestTemplate toDomain(
			UpdateServiceRequestTemplateRequest updateServiceRequestTemplateRequest,
			ServiceRequestTemplate existedServiceRequestTemplate) {
		ServiceRequestTemplate serviceRequestTemplate = existedServiceRequestTemplate;
		if (serviceRequestTemplate == null) {
			serviceRequestTemplate = new ServiceRequestTemplate();
		}

		if (updateServiceRequestTemplateRequest.getName() != null) {
			serviceRequestTemplate.setName(updateServiceRequestTemplateRequest.getName());
		}

		if (updateServiceRequestTemplateRequest.getDescription() != null) {
			serviceRequestTemplate.setDescription(updateServiceRequestTemplateRequest.getDescription());
		}

		if (updateServiceRequestTemplateRequest.getProcessDefinitionKey() != null) {
			serviceRequestTemplate
					.setProcessDefinitionKey(updateServiceRequestTemplateRequest.getProcessDefinitionKey());
		}

		if (updateServiceRequestTemplateRequest.getStatus() != null) {
			serviceRequestTemplate.setStatus(updateServiceRequestTemplateRequest.getStatus());
		}

		return serviceRequestTemplate;
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
