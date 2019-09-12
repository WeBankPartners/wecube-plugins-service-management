package com.webank.servicemanagement.support.core.dto;

import lombok.Data;

@Data
public class RolesDataResponse {

	private int roleId;
	private String roleName;
	private String roleType;
	private String description;
}
