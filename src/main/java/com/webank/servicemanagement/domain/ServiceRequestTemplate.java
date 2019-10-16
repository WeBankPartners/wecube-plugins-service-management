package com.webank.servicemanagement.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "service_request_template")
public class ServiceRequestTemplate {
	@Id
	@GeneratedValue
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "service_pipeline_id")
	private ServicePipeline servicePipeline;

	@Column(name = "name")
	private String name;
	@Column(name = "description")
	private String description;
	@Column(name = "process_definition_key")
	private String processDefinitionKey;
	@Column(name = "status")
	private String status;

	public ServiceRequestTemplate() {
	}

	public ServiceRequestTemplate(ServicePipeline servicePipeline, String name, String description,
			String processDefinitionKey, String status) {
		this.servicePipeline = servicePipeline;
		this.name = name;
		this.description = description;
		this.processDefinitionKey = processDefinitionKey;
		this.status = status;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public ServicePipeline getServicePipeline() {
		return servicePipeline;
	}

	public void setServicePipeline(ServicePipeline servicePipeline) {
		this.servicePipeline = servicePipeline;
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

	public String getProcessDefinitionKey() {
		return processDefinitionKey;
	}

	public void setProcessDefinitionKey(String processDefinitionKey) {
		this.processDefinitionKey = processDefinitionKey;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
