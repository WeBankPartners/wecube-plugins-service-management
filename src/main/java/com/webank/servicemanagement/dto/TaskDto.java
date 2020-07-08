package com.webank.servicemanagement.dto;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.webank.servicemanagement.domain.Task;
import com.webank.servicemanagement.service.TaskService;
import com.webank.servicemanagement.utils.DateUtils;
import com.webank.servicemanagement.utils.JsonUtils;

public class TaskDto {

    private static final Logger log = LoggerFactory.getLogger(TaskService.class);

    private String id;

    public TaskDto() {
    }

    private String serviceRequestId;

    private String callbackUrl;

    private String name;

    private String reporter;

    private String reportTime;

    private String operatorRole;

    private String operator;

    private String operateTime;

    private String inputParameters;

    private String description;

    private String result;

    private String resultMessage;

    private String status;

    private String requestId;

    private String callbackParameter;

    private List<String> allowedOptions;

    private String overTime;

    private String dueDate;

    public static TaskDto fromDomain(Task task) {
        List<String> allowedOptionList = new ArrayList<String>();
        try {
            allowedOptionList = JsonUtils.toObject(task.getAllowedOptions(), allowedOptionList.getClass());
        } catch (IOException e) {
            e.printStackTrace();
            log.error(String.format("Parse 'allowedOptions' meet error: %s",e.getMessage()));
            return new TaskDto();
        }

        TaskDto taskDto = new TaskDto(task.getId(),
                task.getServiceRequest() == null ? null : task.getServiceRequest().getId(), task.getCallbackUrl(),
                task.getName(), task.getReporter(), DateUtils.formatDateToString(task.getReportTime()),
                task.getOperatorRole(), task.getOperator(), DateUtils.formatDateToString(task.getOperateTime()),
                task.getInputParameters(), task.getDescription(), task.getResult(), task.getResultMessage(),
                task.getStatus(), task.getRequestId(), task.getCallbackParameter(), allowedOptionList,
                DateUtils.formatDateToString(task.getOverTime()), task.getDueDate());

        return taskDto;
    }

    public TaskDto(String id, String serviceRequestId, String callbackUrl, String name, String reporter,
            String reportTime, String operatorRole, String operator, String operateTime, String inputParameters,
            String description, String result, String resultMessage, String status, String requestId,
            String callbackParameter, List<String> allowedOptions, String overTime, String dueDate) {
        super();
        this.id = id;
        this.serviceRequestId = serviceRequestId;
        this.callbackUrl = callbackUrl;
        this.name = name;
        this.reporter = reporter;
        this.reportTime = reportTime;
        this.operatorRole = operatorRole;
        this.operator = operator;
        this.operateTime = operateTime;
        this.inputParameters = inputParameters;
        this.description = description;
        this.result = result;
        this.resultMessage = resultMessage;
        this.status = status;
        this.requestId = requestId;
        this.callbackParameter = callbackParameter;
        this.setAllowedOptions(allowedOptions);
        this.overTime = overTime;
        this.dueDate = dueDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getServiceRequestId() {
        return serviceRequestId;
    }

    public void setServiceRequestId(String serviceRequestId) {
        this.serviceRequestId = serviceRequestId;
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
        return reportTime;
    }

    public void setReportTime(String reportTime) {
        this.reportTime = reportTime;
    }

    public String getOperatorRole() {
        return operatorRole;
    }

    public void setOperatorRole(String operatorRole) {
        this.operatorRole = operatorRole;
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

    public String getCallbackParameter() {
        return callbackParameter;
    }

    public void setCallbackParameter(String callbackParameter) {
        this.callbackParameter = callbackParameter;
    }

    public List<String> getAllowedOptions() {
        return allowedOptions;
    }

    public void setAllowedOptions(List<String> allowedOptions) {
        this.allowedOptions = allowedOptions;
    }

    public String getDueDate() { return dueDate; }

    public void setDueDate(String dueDate) { this.dueDate = dueDate; }

    public String getOverTime() { return overTime; }

    public void setOverTime(String overTime) { this.overTime = overTime; }
}
