package com.webank.servicemanagement.dto;

public class BindNodeInfo {
    private String nodeId;
    private String inputType;
    public String getNodeId() {
        return nodeId;
    }
    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }
    public String getInputType() {
        return inputType;
    }
    public void setInputType(String inputType) {
        this.inputType = inputType;
    }
    public BindNodeInfo() {
        super();
    }
    public BindNodeInfo(String nodeId, String inputType) {
        super();
        this.nodeId = nodeId;
        this.inputType = inputType;
    }
}
