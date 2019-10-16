package com.webank.servicemanagement.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "service_pipeline")
public class ServicePipeline {
	@Id
	@GeneratedValue
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "service_catalogue_id")
	private ServiceCatalogue serviceCatalogue;
	@Column(name = "name")
	private String name;
	@Column(name = "description")
	private String description;
	@Column(name = "owner_role_id")
	private int ownerRoleId;
	@Column(name = "status")
	private String status;

	public ServicePipeline() {
	}

	public ServicePipeline(ServiceCatalogue serviceCatalogue, String name, String description, int ownerRoleId,
			String status) {
		this.serviceCatalogue = serviceCatalogue;
		this.name = name;
		this.description = description;
		this.ownerRoleId = ownerRoleId;
		this.status = status;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public ServiceCatalogue getServiceCatalogue() {
		return serviceCatalogue;
	}

	public void setServiceCatalogue(ServiceCatalogue serviceCatalogue) {
		this.serviceCatalogue = serviceCatalogue;
	}

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

	public int getOwnerRoleId() {
		return ownerRoleId;
	}

	public void setOwnerRoleId(int ownerRoleId) {
		this.ownerRoleId = ownerRoleId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
