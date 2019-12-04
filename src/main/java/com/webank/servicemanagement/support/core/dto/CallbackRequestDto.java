package com.webank.servicemanagement.support.core.dto;

public class CallbackRequestDto {
    private String resultCode;
    private String resultMessage;
    private CallbackRequestResultDto results;

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

    @Override
    public String toString() {
        return "CallbackRequestDto [resultCode=" + resultCode + ", resultMessage=" + resultMessage + ", results="
                + results + "]";
    }

    public CallbackRequestResultDto getResults() {
        return results;
    }

    public void setResults(CallbackRequestResultDto results) {
        this.results = results;
    }
}
