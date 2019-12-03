package com.webank.servicemanagement.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "task")
public class Task {

    @Id
    @GeneratedValue
    private Integer id;

    public Task() {
    }

    public Task(ServiceRequest serviceRequest, String processInstanceId, String callbackUrl, String name,
            String processDefinitionKey, String reporter, String reportTime, String description, String status,
            String requestId) {
        this.serviceRequest = serviceRequest;
        this.processInstanceId = processInstanceId;
        this.callbackUrl = callbackUrl;
        this.name = name;
        this.processDefinitionKey = processDefinitionKey;
        this.reporter = reporter;
        this.reportTime = reportTime;
        this.description = description;
        this.status = status;
        this.requestId = requestId;
    }

    @ManyToOne
    @JoinColumn(name = "service_request_id")
    private ServiceRequest serviceRequest;

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

    @Column(name = "requeset_id")
    private String requestId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ServiceRequest getServiceRequest() {
        return serviceRequest;
    }

    public void setServiceRequest(ServiceRequest serviceRequest) {
        this.serviceRequest = serviceRequest;
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public String getCallbackUrl() {
        return callbackUrl;
    }

    public void setCallbackUrl(String callbackUrl) {
        this.callbackUrl = callbackUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProcessDefinitionKey() {
        return processDefinitionKey;
    }

    public void setProcessDefinitionKey(String processDefinitionKey) {
        this.processDefinitionKey = processDefinitionKey;
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

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getOperateTime() {
        return operateTime;
    }

    public void setOperateTime(String operateTime) {
        this.operateTime = operateTime;
    }

    public String getInputParameters() {
        return inputParameters;
    }

    public void setInputParameters(String inputParameters) {
        this.inputParameters = inputParameters;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getResultMessage() {
        return resultMessage;
    }

    public void setResultMessage(String resultMessage) {
        this.resultMessage = resultMessage;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }
}
