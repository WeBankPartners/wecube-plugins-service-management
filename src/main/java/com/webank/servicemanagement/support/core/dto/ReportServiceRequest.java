package com.webank.servicemanagement.support.core.dto;

public class ReportServiceRequest {
    private String eventSeqNo;
    private String eventType;
    private String sourceSubSystem;
    private String operationKey;
    private String operationData;
    private String notifyRequired;
    private String notifyEndpoint;
    private String operationUser;
    private String reportTime;
    private String envType;

    public ReportServiceRequest() {
        super();
    }

    public ReportServiceRequest(String eventSeqNo, String eventType, String sourceSubSystem, String operationKey,
            String operationData, String notifyRequired, String notifyEndpoint, String operationUser, String reportTime,
            String envType) {
        super();
        this.eventSeqNo = eventSeqNo;
        this.eventType = eventType;
        this.sourceSubSystem = sourceSubSystem;
        this.operationKey = operationKey;
        this.operationData = operationData;
        this.notifyRequired = notifyRequired;
        this.notifyEndpoint = notifyEndpoint;
        this.operationUser = operationUser;
        this.reportTime = reportTime;
        this.envType = envType;
    }

    public String getEventSeqNo() {
        return eventSeqNo;
    }

    public void setEventSeqNo(String eventSeqNo) {
        this.eventSeqNo = eventSeqNo;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getSourceSubSystem() {
        return sourceSubSystem;
    }

    public void setSourceSubSystem(String sourceSubSystem) {
        this.sourceSubSystem = sourceSubSystem;
    }

    public String getOperationKey() {
        return operationKey;
    }

    public void setOperationKey(String operationKey) {
        this.operationKey = operationKey;
    }

    public String getOperationData() {
        return operationData;
    }

    public void setOperationData(String operationData) {
        this.operationData = operationData;
    }

    public String getNotifyRequired() {
        return notifyRequired;
    }

    public void setNotifyRequired(String notifyRequired) {
        this.notifyRequired = notifyRequired;
    }

    public String getNotifyEndpoint() {
        return notifyEndpoint;
    }

    public void setNotifyEndpoint(String notifyEndpoint) {
        this.notifyEndpoint = notifyEndpoint;
    }

    public String getOperationUser() {
        return operationUser;
    }

    public void setOperationUser(String operationUser) {
        this.operationUser = operationUser;
    }

    public String getReportTime() {
        return reportTime;
    }

    public void setReportTime(String reportTime) {
        this.reportTime = reportTime;
    }

    public String getEnvType() {
        return envType;
    }

    public void setEnvType(String envType) {
        this.envType = envType;
    }

    @Override
    public String toString() {
        return "ReportServiceRequest [eventSeqNo=" + eventSeqNo + ", eventType=" + eventType + ", sourceSubSystem="
                + sourceSubSystem + ", operationKey=" + operationKey + ", operationData=" + operationData
                + ", notifyRequired=" + notifyRequired + ", notifyEndpoint=" + notifyEndpoint + ", operationUser="
                + operationUser + ", reportTime=" + reportTime + ", envType=" + envType + "]";
    }

}
