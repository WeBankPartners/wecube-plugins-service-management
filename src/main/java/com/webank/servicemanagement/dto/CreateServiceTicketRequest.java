package com.webank.servicemanagement.dto;

public class CreateServiceTicketRequest {
    private String serviceFormId;
    private String roleId;
    private String attachFileId;
    private String name;
    private String emergency;
    private String description;
    private String envType;

    public String getServiceFormId() {
        return serviceFormId;
    }

    public void setServiceFormId(String serviceFormId) {
        this.serviceFormId = serviceFormId;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getAttachFileId() {
        return attachFileId;
    }

    public void setAttachFileId(String attachFileId) {
        this.attachFileId = attachFileId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmergency() {
        return emergency;
    }

    public void setEmergency(String emergency) {
        this.emergency = emergency;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEnvType() {
        return envType;
    }

    public void setEnvType(String envType) {
        this.envType = envType;
    }
}