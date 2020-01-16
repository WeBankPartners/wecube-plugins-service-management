package com.webank.servicemanagement.support.core.dto;

public class StartWorkflowInstanceRequest {
	private String processDefinitionId;

	public String getProcessDefinitionId() {
		return processDefinitionId;
	}

	public void setProcessDefinitionId(String processDefinitionId) {
		this.processDefinitionId = processDefinitionId;
	}

	public StartWorkflowInstanceRequest() {
		super();
	}

	public StartWorkflowInstanceRequest(String processDefinitionId) {
		super();
		this.processDefinitionId = processDefinitionId;
	}
}
