package com.webank.servicemanagement.dto;

import java.util.ArrayList;
import java.util.List;

public class TaskPreviewDto {

    private AttachFileDto attachFile;
    private String description;
    private String emergency;
    private String envType;
    private String requestId;
    private String requestName;
    private String reportRole;
    private String reporter;
    private String reportTime;
    private long requestNo;
    private String status;
    private List<SimpleTaskDto> otherTasks = new ArrayList<SimpleTaskDto>();

    private SimpleTaskDto task;

    public AttachFileDto getAttachFile() {
        return attachFile;
    }

    public void setAttachFile(AttachFileDto attachFile) {
        this.attachFile = attachFile;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEmergency() {
        return emergency;
    }

    public void setEmergency(String emergency) {
        this.emergency = emergency;
    }

    public String getEnvType() {
        return envType;
    }

    public void setEnvType(String envType) {
        this.envType = envType;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getRequestName() {
        return requestName;
    }

    public void setRequestName(String requestName) {
        this.requestName = requestName;
    }

    public String getReportRole() {
        return reportRole;
    }

    public void setReportRole(String reportRole) {
        this.reportRole = reportRole;
    }

    public String getReporter() {
        return reporter;
    }

    public void setReporter(String reporter) {
        this.reporter = reporter;
    }

    public long getRequestNo() {
        return requestNo;
    }

    public void setRequestNo(long requestNo) {
        this.requestNo = requestNo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<SimpleTaskDto> getOtherTasks() {
        return otherTasks;
    }

    public void setOtherTasks(List<SimpleTaskDto> otherTasks) {
        this.otherTasks = otherTasks;
    }

    public SimpleTaskDto getTask() {
        return task;
    }

    public void setTask(SimpleTaskDto task) {
        this.task = task;
    }

    public String getReportTime() {
        return reportTime;
    }

    public void setReportTime(String reportTime) {
        this.reportTime = reportTime;
    }
}
