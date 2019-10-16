package com.webank.servicemanagement.dto;

public class CreateServiceRequestRequest {
	private int templateId;
	private int roleId;
	private int attachFileId;
	private String name;
	private String reporter;
	private String emergency;
	private String description;

	public int getTemplateId() {
		return templateId;
	}

	public void setTemplateId(int templateId) {
		this.templateId = templateId;
	}

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
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
