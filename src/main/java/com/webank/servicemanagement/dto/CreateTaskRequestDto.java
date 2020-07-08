package com.webank.servicemanagement.dto;

import java.util.List;

public class CreateTaskRequestDto {
    private List<CreateTaskRequestInputDto> inputs;
    private String requestId;
    private List<String> allowedOptions;
    private String dueDate;


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

    public List<String> getAllowedOptions() {
        return allowedOptions;
    }

    public void setAllowedOptions(List<String> allowedOptions) {
        this.allowedOptions = allowedOptions;
    }

    public String getDueDate() { return dueDate; }

    public void setDueDate(String dueDate) { this.dueDate = dueDate; }
}
