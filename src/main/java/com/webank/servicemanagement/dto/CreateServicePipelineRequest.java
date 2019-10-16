package com.webank.servicemanagement.dto;

public class CreateServicePipelineRequest {
	private String name;
	private String description;
	private int serviceCatalogueId;
	private int ownerRoleId;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getServiceCatalogueId() {
		return serviceCatalogueId;
	}

	public void setServiceCatalogueId(int serviceCatalogueId) {
		this.serviceCatalogueId = serviceCatalogueId;
	}

	public int getOwnerRoleId() {
		return ownerRoleId;
	}

	public void setOwnerRoleId(int ownerRoleId) {
		this.ownerRoleId = ownerRoleId;
	}
}
