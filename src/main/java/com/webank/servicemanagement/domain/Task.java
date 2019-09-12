package com.webank.servicemanagement.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "task")
public class Task {

	@Id
	@GeneratedValue
	private Integer id;

	public Task() {
	}

	public Task(int serviceRequestId, String processInstanceId, String callbackUrl, String name,
			String processDefinitionKey, String reporter,String reportTime, String description, String status) {
		this.serviceRequestId = serviceRequestId;
		this.processInstanceId = processInstanceId;
		this.callbackUrl = callbackUrl;
		this.name = name;
		this.processDefinitionKey = processDefinitionKey;
		this.reporter = reporter;
		this.reportTime = reportTime;
		this.description = description;
		this.status = status;
	}

	@Column(name = "service_request_id")
	private int serviceRequestId;
	@Column(name = "process_instance_id")
	private String processInstanceId;
	@Column(name = "callback_url")
	private String callbackUrl;
	@Column(name = "name")
	private String name;
	@Column(name = "process_definition_key")
	private String processDefinitionKey;
	@Column(name = "reporter")
	private String reporter;
	@Column(name = "report_time")
	private String reportTime;
	@Column(name = "operator")
	private String operator;
	@Column(name = "operate_time")
	private String operateTime;
	@Column(name = "input_parameters")
	private String inputParameters;
	@Column(name = "description")
	private String description;
	@Column(name = "result")
	private String result;
	@Column(name = "result_message")
	private String resultMessage;
	@Column(name = "status")
	private String status;
}
