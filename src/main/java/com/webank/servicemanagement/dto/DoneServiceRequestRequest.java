package com.webank.servicemanagement.dto;

import com.fasterxml.jackson.annotation.JsonAlias;

public class DoneServiceRequestRequest {

    @JsonAlias("eventSeqNo")
    private String serviceRequestId;

    @JsonAlias("status")
    private String result;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getServiceRequestId() {
        return serviceRequestId;
    }

    public void setServiceRequestId(String serviceRequestId) {
        this.serviceRequestId = serviceRequestId;
    }

    private String eventType;
    private String sourceSubSystem;

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

    @Override
    public String toString() {
        return "DoneServiceRequestRequest [serviceRequestId=" + serviceRequestId + ", result=" + result + ", eventType="
                + eventType + ", sourceSubSystem=" + sourceSubSystem + "]";
    }

}