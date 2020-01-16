package com.webank.servicemanagement.dto;

import com.webank.servicemanagement.domain.ServicePipeline;

public class UpdateServicePipelineRequest {
    private String name;
    private String description;
    private String ownerRole;
    private String status;

    public static ServicePipeline toDomain(UpdateServicePipelineRequest updateServicePipelineRequest,
            ServicePipeline existedServicePipeline) {
        ServicePipeline servicePipeline = existedServicePipeline;
        if (servicePipeline == null) {
            servicePipeline = new ServicePipeline();
        }

        if (updateServicePipelineRequest.getName() != null) {
            servicePipeline.setName(updateServicePipelineRequest.getName());
        }

        if (updateServicePipelineRequest.getDescription() != null) {
            servicePipeline.setDescription(updateServicePipelineRequest.getDescription());
        }

        if (updateServicePipelineRequest.getOwnerRole() != null) {
            servicePipeline.setOwnerRole(updateServicePipelineRequest.getOwnerRole());
        }

        if (updateServicePipelineRequest.getStatus() != null) {
            servicePipeline.setStatus(updateServicePipelineRequest.getStatus());
        }

        return servicePipeline;
    }

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

    public String getOwnerRole() {
        return ownerRole;
    }

    public void setOwnerRole(String ownerRole) {
        this.ownerRole = ownerRole;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
