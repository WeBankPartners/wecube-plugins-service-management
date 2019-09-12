package com.webank.servicemanagement.dto;

import lombok.Data;

@Data
public class UpdateTaskRequest {
	private Integer taskId;
	private String operator;
}
