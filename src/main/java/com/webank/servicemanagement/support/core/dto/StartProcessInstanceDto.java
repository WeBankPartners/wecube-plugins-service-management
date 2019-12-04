package com.webank.servicemanagement.support.core.dto;

public class StartProcessInstanceDto {
    public StartProcessInstanceDto() {
        super();
    }

    private String procDefId;
    private String entityTypeId;
    private String entityDataId;

    public String getProcDefId() {
        return procDefId;
    }

    public void setProcDefId(String procDefId) {
        this.procDefId = procDefId;
    }

    public String getEntityTypeId() {
        return entityTypeId;
    }

    public void setEntityTypeId(String entityTypeId) {
        this.entityTypeId = entityTypeId;
    }

    public String getEntityDataId() {
        return entityDataId;
    }

    public void setEntityDataId(String entityDataId) {
        this.entityDataId = entityDataId;
    }

    public StartProcessInstanceDto(String procDefId, String entityTypeId, String entityDataId) {
        super();
        this.procDefId = procDefId;
        this.entityTypeId = entityTypeId;
        this.entityDataId = entityDataId;
    }
}
