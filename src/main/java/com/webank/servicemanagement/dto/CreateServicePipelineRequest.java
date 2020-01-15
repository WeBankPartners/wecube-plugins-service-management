package com.webank.servicemanagement.dto;

public class CreateServicePipelineRequest {
    private String name;
    private String description;
    private String serviceCatalogueId;
    private String ownerRole;

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

    public String getServiceCatalogueId() {
        return serviceCatalogueId;
    }

    public void setServiceCatalogueId(String serviceCatalogueId) {
        this.serviceCatalogueId = serviceCatalogueId;
    }

    public String getOwnerRole() {
        return ownerRole;
    }

    public void setOwnerRole(String ownerRole) {
        this.ownerRole = ownerRole;
    }
}
