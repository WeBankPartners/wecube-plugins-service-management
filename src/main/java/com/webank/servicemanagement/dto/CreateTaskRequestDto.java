package com.webank.servicemanagement.dto;

import java.util.List;

public class CreateTaskRequestDto {
    private List<CreateTaskRequestInputDto> inputs;
    private String requestId;

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
}
