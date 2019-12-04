package com.webank.servicemanagement.support.core.dto;

public class CallbackRequestResultDto {

    private String requestId;

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    @Override
    public String toString() {
        return "CallbackRequestResultDto [requestId=" + requestId + "]";
    }

    public CallbackRequestResultDto(String requestId) {
        this.requestId = requestId;
    }

    public CallbackRequestResultDto() {
    }
}
