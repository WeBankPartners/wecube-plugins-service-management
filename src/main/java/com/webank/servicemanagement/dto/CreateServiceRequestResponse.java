package com.webank.servicemanagement.dto;

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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getTemplateId() {
		return templateId;
	}

	public void setTemplateId(int templateId) {
		this.templateId = templateId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public String getEmergency() {
		return emergency;
	}

	public void setEmergency(String emergency) {
		this.emergency = emergency;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAttachFile() {
		return attachFile;
	}

	public void setAttachFile(String attachFile) {
		this.attachFile = attachFile;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getProcessInstanceId() {
		return ProcessInstanceId;
	}

	public void setProcessInstanceId(String processInstanceId) {
		ProcessInstanceId = processInstanceId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public CreateServiceRequestResponse(int id, int templateId, String name, String reporter, String reportTime,
			String emergency, String description, String attachFile, String result, String processInstanceId,
			String status) {
		super();
		this.id = id;
		this.templateId = templateId;
		this.name = name;
		this.reporter = reporter;
		this.reportTime = reportTime;
		this.emergency = emergency;
		this.description = description;
		this.attachFile = attachFile;
		this.result = result;
		ProcessInstanceId = processInstanceId;
		this.status = status;
	}

	public CreateServiceRequestResponse() {
		super();
	}
}
