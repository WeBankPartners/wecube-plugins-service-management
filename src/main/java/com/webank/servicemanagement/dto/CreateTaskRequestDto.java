package com.webank.servicemanagement.dto;

import java.util.List;

public class CreateTaskRequestDto {
    private List<CreateTaskRequestInputDto> inputs;
    private String requestId;
    private String operator;

    public List<CreateTaskRequestInputDto> getInputs() {
        return inputs;
    }

    public void setInputs(List<CreateTaskRequestInputDto> inputs) {
        this.inputs = inputs;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }
}
