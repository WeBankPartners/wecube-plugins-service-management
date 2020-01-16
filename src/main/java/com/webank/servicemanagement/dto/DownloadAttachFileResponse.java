package com.webank.servicemanagement.dto;

public class DownloadAttachFileResponse {
	private byte[] fileByteArray;
	private String attachFileName;

	public byte[] getFileByteArray() {
		return fileByteArray;
	}

	public void setFileByteArray(byte[] fileByteArray) {
		this.fileByteArray = fileByteArray;
	}

	public String getAttachFileName() {
		return attachFileName;
	}

	public void setAttachFileName(String attachFileName) {
		this.attachFileName = attachFileName;
	}

	public DownloadAttachFileResponse(byte[] fileByteArray, String attachFileName) {
		super();
		this.fileByteArray = fileByteArray;
		this.attachFileName = attachFileName;
	}

	public DownloadAttachFileResponse() {
		super();
	}

}
