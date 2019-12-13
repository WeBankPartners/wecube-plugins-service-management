package com.webank.servicemanagement.dto;

public class WorkflowJsonResponse {
    public final static String STATUS_OK = "0";
    public final static String STATUS_ERROR = "1";

    private String resultCode;
    private String resultMessage;

    public static WorkflowJsonResponse okay() {
        WorkflowJsonResponse result = new WorkflowJsonResponse();
        result.setResultCode(STATUS_OK);
        result.setResultMessage("Success");
        return result;
    }

    public static WorkflowJsonResponse error(String errorMessage) {
        WorkflowJsonResponse result = new WorkflowJsonResponse();
        result.setResultCode(STATUS_ERROR);
        result.setResultMessage(errorMessage);
        return result;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultMessage() {
        return resultMessage;
    }

    public void setResultMessage(String resultMessage) {
        this.resultMessage = resultMessage;
    }
}
