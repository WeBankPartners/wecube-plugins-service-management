package com.webank.servicemanagement.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data

@Entity
@Table(name = "service_catalogue")
public class ServiceCatalogue {

	@Id
	@GeneratedValue
	private Integer id;

	@Column(name = "name")
	private String name;
	@Column(name = "description")
	private String description;
	@Column(name = "status")
	private String status;

	public ServiceCatalogue() {
	}

	public ServiceCatalogue(String name, String description, String status) {
		this.name = name;
		this.description = description;
		this.status = status;
	}
}
