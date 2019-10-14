package com.webank.servicemanagement.dto;

import lombok.Data;

@Data
public class CreateServiceRequestTemplateRequest {
	private String name;
	private String description;
	private int servicePipelineId;
	private String processDefinitionKey;
}
