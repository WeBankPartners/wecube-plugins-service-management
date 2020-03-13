package com.webank.servicemanagement.dto;

import java.util.List;

import com.webank.servicemanagement.domain.FormCustomItem;

public class CustomItem {
    private String name;
    private String displayName;
    private String dataType;
    private int length;
    private List<BindNodeInfo> bindNodes;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public List<BindNodeInfo> getBindNodes() {
        return bindNodes;
    }

    public void setBindNodes(List<BindNodeInfo> bindNodes) {
        this.bindNodes = bindNodes;
    }

    public static FormCustomItem toDomain(CustomItem customItem, String serviceFormId) {
        return new FormCustomItem(serviceFormId, customItem.getName(), customItem.getDisplayName(),
                customItem.getDataType(), customItem.getLength());
    }
}
