package com.webank.servicemanagement.dto;

public class DoneServiceRequestRequest {
    private int serviceRequestId;
	private String result;

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

    public int getServiceRequestId() {
        return serviceRequestId;
    }

    public void setServiceRequestId(int serviceRequestId) {
        this.serviceRequestId = serviceRequestId;
    }
}