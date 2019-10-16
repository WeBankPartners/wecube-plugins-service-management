package com.webank.servicemanagement.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "service_request")
public class ServiceRequest {

	@Id
	@GeneratedValue
	private int id;

	public ServiceRequest() {
	}

	public ServiceRequest(ServiceRequestTemplate serviceRequestTemplate, String name, int reporterRoleId,
			String reporter, String reportTime, String emergency, String description, String status,
			AttachFile attachFile) {
		this.serviceRequestTemplate = serviceRequestTemplate;
		this.name = name;
		this.reporterRoleId = reporterRoleId;
		this.reporter = reporter;
		this.reportTime = reportTime;
		this.emergency = emergency;
		this.description = description;
		this.status = status;
		this.attachFile = attachFile;
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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public ServiceRequestTemplate getServiceRequestTemplate() {
		return serviceRequestTemplate;
	}

	public void setServiceRequestTemplate(ServiceRequestTemplate serviceRequestTemplate) {
		this.serviceRequestTemplate = serviceRequestTemplate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getReporterRoleId() {
		return reporterRoleId;
	}

	public void setReporterRoleId(int reporterRoleId) {
		this.reporterRoleId = reporterRoleId;
	}

	public String getReporter() {
		return reporter;
	}

	public void setReporter(String reporter) {
		this.reporter = reporter;
	}

	public String getReportTime() {
		return reportTime;
	}

	public void setReportTime(String reportTime) {
		this.reportTime = reportTime;
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

	public AttachFile getAttachFile() {
		return attachFile;
	}

	public void setAttachFile(AttachFile attachFile) {
		this.attachFile = attachFile;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getProcessInstanceId() {
		return ProcessInstanceId;
	}

	public void setProcessInstanceId(String processInstanceId) {
		ProcessInstanceId = processInstanceId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
