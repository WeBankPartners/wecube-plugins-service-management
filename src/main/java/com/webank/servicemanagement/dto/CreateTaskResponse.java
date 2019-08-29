package com.webank.servicemanagement.dto;

import lombok.Data;

@Data
public class CreateTaskResponse {
	private int serviceRequestId;
	private String processInstanceId;
	private String callbackUrl;
	private String name;
	private String processDefinitionKey;
	private String reporter;
	private String reportTime;
	private String operator;
	private String operateTime;
	private String inputParameters;
	private String description;
	private String result;
	private String status;
}
