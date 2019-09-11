package com.webank.servicemanagement.dto;

import lombok.Data;

@Data
public class CreateServicePipelineRequest {
	private String name;
	private String description;
	private int serviceCatalogueId;
	private int ownerRoleId;
}
