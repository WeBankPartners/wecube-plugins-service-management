package com.webank.servicemanagement.domain;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "task")
@GenericGenerator(name = "jpa-uuid", strategy = "uuid")
public class Task {

    @Id
    @GeneratedValue(generator = "jpa-uuid")
    @Column(name = "id", length = 32)
    private String id;

    public Task() {
    }

    public Task(String callbackUrl, String name, String operatorRole, String reporter, Timestamp reportTime,
            String description, String status, String requestId, String callbackParameter) {
        this.callbackUrl = callbackUrl;
        this.name = name;
        this.operatorRole = operatorRole;
        this.reporter = reporter;
        this.reportTime = reportTime;
        this.description = description;
        this.status = status;
        this.requestId = requestId;
        this.callbackParameter = callbackParameter;
    }

    @ManyToOne
    @JoinColumn(name = "service_request_id")
    private ServiceRequest serviceRequest;

    @Column(name = "callback_url")
    private String callbackUrl;

    @Column(name = "name")
    private String name;

    @Column(name = "reporter")
    private String reporter;

    @Column(name = "report_time")
    private Timestamp reportTime;

    @Column(name = "operator_role")
    private String operatorRole;

    @Column(name = "operator")
    private String operator;

    @Column(name = "operate_time")
    private Timestamp operateTime;

    @Column(name = "input_parameters")
    private String inputParameters;

    @Column(name = "description")
    private String description;

    @Column(name = "result")
    private String result;

    @Column(name = "result_message")
    private String resultMessage;

    @Column(name = "status")
    private String status = "active";

    @Column(name = "request_id")
    private String requestId;

    @Column(name = "callback_parameter")
    private String callbackParameter;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ServiceRequest getServiceRequest() {
        return serviceRequest;
    }

    public void setServiceRequest(ServiceRequest serviceRequest) {
        this.serviceRequest = serviceRequest;
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

    public String getReporter() {
        return reporter;
    }

    public void setReporter(String reporter) {
        this.reporter = reporter;
    }

    public String getReportTime() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(reportTime);
    }

    public void setReportTime(Timestamp reportTime) {
        this.reportTime = reportTime;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getOperateTime() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(operateTime);
    }

    public void setOperateTime(Timestamp operateTime) {
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

    public String getOperatorRole() {
        return operatorRole;
    }

    public void setOperatorRole(String operatorRole) {
        this.operatorRole = operatorRole;
    }

    public String getCallbackParameter() {
        return callbackParameter;
    }

    public void setCallbackParameter(String callbackParameter) {
        this.callbackParameter = callbackParameter;
    }
}
