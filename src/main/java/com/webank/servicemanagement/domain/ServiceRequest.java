package com.webank.servicemanagement.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "service_request")
public class ServiceRequest {

	@Id
	@GeneratedValue
	private int id;

	public ServiceRequest() {
	}

	public ServiceRequest(ServiceRequestTemplate serviceRequestTemplate, String name, int reporterRoleId,
			String reporter, String reportTime, String emergency, String description, String status) {
		this.serviceRequestTemplate = serviceRequestTemplate;
		this.name = name;
		this.reporterRoleId = reporterRoleId;
		this.reporter = reporter;
		this.reportTime = reportTime;
		this.emergency = emergency;
		this.description = description;
		this.status = status;
	}

	@ManyToOne
	@JoinColumn(name = "template_id")
	private ServiceRequestTemplate serviceRequestTemplate;

	@Column(name = "name")
	private String name;
	@Column(name = "reporter_role_id")
	private int reporterRoleId;
	@Column(name = "reporter")
	private String reporter;
	@Column(name = "report_time")
	private String reportTime;
	@Column(name = "emergency")
	private String emergency;
	@Column(name = "description")
	private String description;

	@OneToOne
	@JoinColumn(name = "attach_file_id")
	private AttachFile attachFile;

	@Column(name = "result")
	private String result;
	@Column(name = "process_instance_id")
	private String ProcessInstanceId;
	@Column(name = "status")
	private String status;
}
