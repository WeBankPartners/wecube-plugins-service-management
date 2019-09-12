package com.webank.servicemanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateServiceRequestResponse {
	private int id;
	private int templateId;
	private String name;
	private String reporter;
	private String reportTime;
	private String emergency;
	private String description;
	private String attachFile;
	private String result;
	private String ProcessInstanceId;
	private String status;
}
