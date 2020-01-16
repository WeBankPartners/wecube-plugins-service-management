package com.webank.servicemanagement.dto;

import com.webank.servicemanagement.domain.Task;

public class UpdateTaskRequest {
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
	private String resultMessage;
	private String status;

	public static Task toDomain(UpdateTaskRequest updateTaskRequest, Task existedTask) {
		Task task = existedTask;
		if (task == null) {
			task = new Task();
		}

		if (updateTaskRequest.getCallbackUrl() != null) {
			task.setCallbackUrl(updateTaskRequest.getCallbackUrl());
		}

		if (updateTaskRequest.getName() != null) {
			task.setName(updateTaskRequest.getName());
		}

		if (updateTaskRequest.getReporter() != null) {
			task.setReporter(updateTaskRequest.getReporter());
		}

		if (updateTaskRequest.getReportTime() != null) {
			task.setReportTime(updateTaskRequest.getReportTime());
		}

		if (updateTaskRequest.getOperator() != null) {
			task.setOperator(updateTaskRequest.getOperator());
		}

		if (updateTaskRequest.getOperateTime() != null) {
			task.setOperateTime(updateTaskRequest.getOperateTime());
		}

		if (updateTaskRequest.getInputParameters() != null) {
			task.setInputParameters(updateTaskRequest.getInputParameters());
		}

		if (updateTaskRequest.getDescription() != null) {
			task.setDescription(updateTaskRequest.getDescription());
		}

		if (updateTaskRequest.getResult() != null) {
			task.setResult(updateTaskRequest.getResult());
		}

		if (updateTaskRequest.getResultMessage() != null) {
			task.setResultMessage(updateTaskRequest.getResultMessage());
		}

		if (updateTaskRequest.getStatus() != null) {
			task.setStatus(updateTaskRequest.getStatus());
		}

		return task;
	}

	public String getProcessInstanceId() {
		return processInstanceId;
	}

	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}

	public String getCallbackUrl() {
		return callbackUrl;
	}

	public void setCallbackUrl(String callbackUrl) {
		this.callbackUrl = callbackUrl;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getProcessDefinitionKey() {
		return processDefinitionKey;
	}

	public void setProcessDefinitionKey(String processDefinitionKey) {
		this.processDefinitionKey = processDefinitionKey;
	}

	public String getReporter() {
		return reporter;
	}

	public void setReporter(String reporter) {
		this.reporter = reporter;
	}

	public String getReportTime() {
		return reportTime;
	}

	public void setReportTime(String reportTime) {
		this.reportTime = reportTime;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getOperateTime() {
		return operateTime;
	}

	public void setOperateTime(String operateTime) {
		this.operateTime = operateTime;
	}

	public String getInputParameters() {
		return inputParameters;
	}

	public void setInputParameters(String inputParameters) {
		this.inputParameters = inputParameters;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getResultMessage() {
		return resultMessage;
	}

	public void setResultMessage(String resultMessage) {
		this.resultMessage = resultMessage;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
