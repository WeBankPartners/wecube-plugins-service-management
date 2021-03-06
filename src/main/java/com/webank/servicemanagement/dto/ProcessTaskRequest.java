package com.webank.servicemanagement.dto;

public class ProcessTaskRequest {

    public static final String RESULT_SUCCESSFUL="Successful/Approved";
    public static final String RESULT_FAILED="Failed/Rejected";
    
	private String result;
	private String resultMessage;

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getResultMessage() {
		return resultMessage;
	}

	public void setResultMessage(String resultMessage) {
		this.resultMessage = resultMessage;
	}
}
