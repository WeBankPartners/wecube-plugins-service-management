package com.webank.servicemanagement.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data

@Entity
@Table(name = "service_request_template")
public class ServiceRequestTemplate {
	private static final long serialVersionUID = -2952735933715107252L;

	@Id
	@GeneratedValue
	private Integer id;

	@Column(name = "service_pipeline_id")
	private int servicePipelinId;
	@Column(name = "name")
	private String name;
	@Column(name = "process_definition_key")
	private String ProcessDefinitionKey;
	@Column(name = "status")
	private String status;

}
