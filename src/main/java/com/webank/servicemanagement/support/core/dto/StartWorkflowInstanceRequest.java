package com.webank.servicemanagement.support.core.dto;

public class StartWorkflowInstanceRequest {
	private String processDefinitionKey;

	public String getProcessDefinitionKey() {
		return processDefinitionKey;
	}

	public void setProcessDefinitionKey(String processDefinitionKey) {
		this.processDefinitionKey = processDefinitionKey;
	}

	public StartWorkflowInstanceRequest() {
		super();
	}

	public StartWorkflowInstanceRequest(String processDefinitionKey) {
		super();
		this.processDefinitionKey = processDefinitionKey;
	}
}
