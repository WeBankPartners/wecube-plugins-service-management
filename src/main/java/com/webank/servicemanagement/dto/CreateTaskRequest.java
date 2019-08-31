package com.webank.servicemanagement.dto;

import lombok.Data;

@Data
public class CreateTaskRequest {
	private int serviceRequestId;
	private String processInstanceId;
	private String callbackUrl;
	private String name;
	private String reporter;
//	private String reportTime;
//	private String inputParameters;
	private String description;
	private String processDefinitionKey;
}
