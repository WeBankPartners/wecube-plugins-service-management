package com.webank.servicemanagement.dto;

import org.springframework.http.ResponseEntity;

public class DownloadAttachFileResponse {
	private ResponseEntity<byte[]> fileResponseEntity;
	private String attachFileName;

	public ResponseEntity<byte[]> getFileResponseEntity() {
		return fileResponseEntity;
	}

	public void setFileResponseEntity(ResponseEntity<byte[]> fileResponseEntity) {
		this.fileResponseEntity = fileResponseEntity;
	}

	public String getAttachFileName() {
		return attachFileName;
	}

	public void setAttachFileName(String attachFileName) {
		this.attachFileName = attachFileName;
	}

	public DownloadAttachFileResponse(ResponseEntity<byte[]> fileResponseEntity, String attachFileName) {
		super();
		this.fileResponseEntity = fileResponseEntity;
		this.attachFileName = attachFileName;
	}

	public DownloadAttachFileResponse() {
		super();
	}

}
