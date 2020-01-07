package com.webank.servicemanagement.dto;

public class CreateServiceRequestRequest {
	private String templateId;
	private String roleId;
	private int attachFileId;
	private String name;
	private String reporter;
	private String emergency;
	private String description;

	public String getTemplateId() {
		return templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public int getAttachFileId() {
		return attachFileId;
	}

	public void setAttachFileId(int attachFileId) {
		this.attachFileId = attachFileId;
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
}
