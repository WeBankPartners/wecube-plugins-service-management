package com.webank.servicemanagement.dto;

public class CreateServiceRequestTemplateRequest {
	private String name;
	private String description;
	private int servicePipelineId;
	private String processDefinitionKey;

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

	public int getServicePipelineId() {
		return servicePipelineId;
	}

	public void setServicePipelineId(int servicePipelineId) {
		this.servicePipelineId = servicePipelineId;
	}

	public String getProcessDefinitionKey() {
		return processDefinitionKey;
	}

	public void setProcessDefinitionKey(String processDefinitionKey) {
		this.processDefinitionKey = processDefinitionKey;
	}
}
