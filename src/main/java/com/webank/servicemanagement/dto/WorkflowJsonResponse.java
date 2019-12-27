package com.webank.servicemanagement.dto;

import java.util.List;

import com.webank.servicemanagement.dto.WorkflowResultDataJsonResponse.WorkflowResultDataOutputJsonResponse;

public class WorkflowJsonResponse {
    public final static String STATUS_OK = "0";
    public final static String STATUS_ERROR = "1";

    private String resultCode;
    private String resultMessage;
    private WorkflowResultDataJsonResponse<?> results;

    public static WorkflowJsonResponse okay() {
        WorkflowJsonResponse result = new WorkflowJsonResponse();
        result.setResultCode(STATUS_OK);
        result.setResultMessage("Success");
        return result;
    }

    public static WorkflowJsonResponse okayWithData(List<WorkflowResultDataOutputJsonResponse> tasks) {
        WorkflowJsonResponse result = new WorkflowJsonResponse();
        result.setResultCode(STATUS_OK);
        result.setResultMessage("Success");
        result.setResults(new WorkflowResultDataJsonResponse(tasks));
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

    public WorkflowResultDataJsonResponse<?> getResults() {
        return results;
    }

    public void setResults(WorkflowResultDataJsonResponse<?> results) {
        this.results = results;
    }
}
