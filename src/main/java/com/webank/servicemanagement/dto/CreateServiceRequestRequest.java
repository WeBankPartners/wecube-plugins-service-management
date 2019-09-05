package com.webank.servicemanagement.dto;

import lombok.Data;

@Data
public class CreateServiceRequestRequest {
	private int templateId;
	private int roleId;
	private String name;
	private String reporter;
	private String emergency;
	private String description;
}
