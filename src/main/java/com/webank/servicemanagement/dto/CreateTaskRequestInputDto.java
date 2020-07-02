package com.webank.servicemanagement.dto;

import java.util.Date;

public class CreateTaskRequestInputDto {
    private String callbackUrl;
    private String taskName;
    private String taskDescription;
    private String roleName;
    private String callbackParameter;
    private String reporter;
    private String expectedResolveDuration; //Format: "3" - means 3 days
    private Date expectedResolveTime; //Format: "yyyy-MM-dd HH:mm:ss"

    public String getCallbackUrl() {
        return callbackUrl;
    }

    public void setCallbackUrl(String callbackUrl) {
        this.callbackUrl = callbackUrl;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getCallbackParameter() {
        return callbackParameter;
    }

    public void setCallbackParameter(String callbackParameter) {
        this.callbackParameter = callbackParameter;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public String getReporter() {
        return reporter;
    }

    public void setReporter(String reporter) {
        this.reporter = reporter;
    }

    public String getExpectedResolveDuration() {
        return expectedResolveDuration;
    }

    public void setExpectedResolveDuration(String expectedResolveDuration) {
        this.expectedResolveDuration = expectedResolveDuration;
    }

    public Date getExpectedResolveTime() {
        return expectedResolveTime;
    }

    public void setExpectedResolveTime(Date expectedResolveTime) {
        this.expectedResolveTime = expectedResolveTime;
    }
}
