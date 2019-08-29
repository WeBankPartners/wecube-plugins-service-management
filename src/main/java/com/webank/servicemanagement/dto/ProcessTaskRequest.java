package com.webank.servicemanagement.dto;

import lombok.Data;

@Data
public class ProcessTaskRequest {
	private Integer taskId;
	private String result;
	private String resultMessage;
}
