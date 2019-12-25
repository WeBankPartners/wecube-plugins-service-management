package com.webank.servicemanagement.dto;

import java.util.List;

public class WorkflowResultDataJsonResponse {
    private List<Object> outputs;

    public List<Object> getOutputs() {
        return outputs;
    }

    public void setOutputs(List<Object> outputs) {
        this.outputs = outputs;
    }

    public WorkflowResultDataJsonResponse() {
    }

    public WorkflowResultDataJsonResponse(List<Object> outputs) {
        this.outputs = outputs;
    }

}