package com.webank.servicemanagement.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "service_request")
public class ServiceRequest {

	@Id
	@GeneratedValue
	private int id;

	@Column(name = "template_id")
	private int templateId;
	@Column(name = "name")
	private String name;
	@Column(name = "reporter")
	private String reporter;
	@Column(name = "report_time")
	private String reportTime;
	@Column(name = "emergency")
	private String emergency;
	@Column(name = "description")
	private String description;
	@Column(name = "attach_file")
	private String attachFile;
	@Column(name = "result")
	private String result;
	@Column(name = "process_instance_id")
	private String ProcessInstanceId;
	@Column(name = "status")
	private String status;
}
