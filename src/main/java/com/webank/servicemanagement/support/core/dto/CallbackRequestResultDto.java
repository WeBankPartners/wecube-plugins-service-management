package com.webank.servicemanagement.support.core.dto;

public class CallbackRequestResultDto {
    public static final String ERROR_CODE_SUCCESSFUL = "0";
    public static final String ERROR_CODE_FAILED = "1";

    private String requestId;
    private String errorCode;
    private String errorMessage;
    private String comment;
    private String callbackParameter;

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

    public CallbackRequestResultDto() {
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public CallbackRequestResultDto(String requestId, String errorCode, String errorMessage, String comment,
            String callbackParameter) {
        super();
        this.requestId = requestId;
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.comment = comment;
        this.callbackParameter = callbackParameter;
    }

    public String getCallbackParameter() {
        return callbackParameter;
    }

    public void setCallbackParameter(String callbackParameter) {
        this.callbackParameter = callbackParameter;
    }
}
