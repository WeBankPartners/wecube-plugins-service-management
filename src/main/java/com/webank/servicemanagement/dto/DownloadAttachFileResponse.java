package com.webank.servicemanagement.dto;

import org.springframework.http.ResponseEntity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DownloadAttachFileResponse {
	private ResponseEntity<byte[]> fileResponseEntity;
	private String attachFileName;
}
