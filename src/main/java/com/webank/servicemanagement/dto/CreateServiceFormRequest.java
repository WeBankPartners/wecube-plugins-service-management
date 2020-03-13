package com.webank.servicemanagement.dto;

import java.util.List;

public class CreateServiceFormRequest {
    private String name;
    private String description;
    private String servicePipelineId;
    private String processDefinitionKey;

    private List<CustomItem> customItems;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getServicePipelineId() {
        return servicePipelineId;
    }

    public void setServicePipelineId(String servicePipelineId) {
        this.servicePipelineId = servicePipelineId;
    }

    public String getProcessDefinitionKey() {
        return processDefinitionKey;
    }

    public void setProcessDefinitionKey(String processDefinitionKey) {
        this.processDefinitionKey = processDefinitionKey;
    }

    public List<CustomItem> getCustomItems() {
        return customItems;
    }

    public void setCustomItems(List<CustomItem> customItems) {
        this.customItems = customItems;
    }
}
