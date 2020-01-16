package com.webank.servicemanagement.support.core.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CallbackRequestDto {
    @JsonProperty(value = "resultCode")
    private String resultCode;
    @JsonProperty(value = "resultMessage")
    private String resultMessage;
    private CallbackRequestResultDataDto results;

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

    public CallbackRequestResultDataDto getResults() {
        return results;
    }

    public void setResults(CallbackRequestResultDataDto results) {
        this.results = results;
    }

    @Override
    public String toString() {
        return "CallbackRequestDto [resultCode=" + resultCode + ", resultMessage=" + resultMessage + ", results="
                + results + "]";
    }

}
