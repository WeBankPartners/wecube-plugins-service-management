package com.webank.servicemanagement.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Data

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

}
