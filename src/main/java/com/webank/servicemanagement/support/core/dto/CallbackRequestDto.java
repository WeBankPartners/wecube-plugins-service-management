package com.webank.servicemanagement.support.core.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CallbackRequestDto {
    @JsonProperty(value = "resultCode")
    private String resultCode;
    @JsonProperty(value = "resultMessage")
    private String resultMessage;
    private List<CallbackRequestResultDto> results;

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

    public List<CallbackRequestResultDto> getResults() {
        return results;
    }

    public void setResults(List<CallbackRequestResultDto> results) {
        this.results = results;
    }

}
