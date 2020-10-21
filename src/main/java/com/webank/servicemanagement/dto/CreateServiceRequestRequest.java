package com.webank.servicemanagement.dto;

public class CreateServiceRequestRequest {
    private String templateId;
    private String roleName;
    private String attachFileId;
    private String name;
    private String emergency;
    private String description;
    private String envType;
    
    //#162
    private String rootDataId;

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
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

    public String getRootDataId() {
        return rootDataId;
    }

    public void setRootDataId(String rootDataId) {
        this.rootDataId = rootDataId;
    }
    
    
}
