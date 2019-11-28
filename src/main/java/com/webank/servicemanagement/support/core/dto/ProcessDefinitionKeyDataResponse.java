package com.webank.servicemanagement.support.core.dto;

public class ProcessDefinitionKeyDataResponse {
    private String processDefinitionKey;

    public ProcessDefinitionKeyDataResponse(String processDefinitionKey) {
        super();
        this.processDefinitionKey = processDefinitionKey;
    }

    public ProcessDefinitionKeyDataResponse() {
    }

    public String getProcessDefinitionKey() {
        return processDefinitionKey;
    }

    public void setProcessDefinitionKey(String processDefinitionKey) {
        this.processDefinitionKey = processDefinitionKey;
    }
}
