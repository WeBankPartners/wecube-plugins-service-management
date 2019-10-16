package com.webank.servicemanagement.utils;

import java.io.IOException;
import java.io.InputStream;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

public class FileUtils {

	public static String streamToBase64String(InputStream inputStream) throws IOException, Exception {
		String base64String = null;
		byte[] attachFileBytes = new byte[inputStream.available()];
		try {
			inputStream.read(attachFileBytes);
			base64String = Base64.encodeBase64String(attachFileBytes);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Encode file meet error");
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
					throw new Exception("Encode file meet error");
				}
			}
		}
		return base64String;
	}

	public static byte[] streamToByteArray(InputStream inputStream) throws IOException, Exception {
		byte[] attachFileBytes = new byte[inputStream.available()];
		try {
			inputStream.read(attachFileBytes);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Encode file meet error");
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
					throw new Exception("Encode file meet error");
				}
			}
		}
		return attachFileBytes;
	}

	public String base64StringToStream(InputStream inputStream) throws IOException, Exception {
		String base64String = null;
		byte[] attachFileBytes = new byte[inputStream.available()];
		try {
			inputStream.read(attachFileBytes);
			base64String = Base64.encodeBase64String(attachFileBytes);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Encode file meet error");
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
					throw new Exception("Encode file meet error");
				}
			}

		}
		return base64String;
	}

	public static ResponseEntity<byte[]> base64StringToFileResponseEntity(String fileName, String base64String) {
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.CONTENT_DISPOSITION, "attchement;filename=" + fileName);
		headers.add("Accept", MediaType.APPLICATION_OCTET_STREAM_VALUE);

		ResponseEntity<byte[]> entity = new ResponseEntity<byte[]>(Base64.decodeBase64(base64String), headers,
				HttpStatus.OK);
		return entity;
	}

	public static ResponseEntity<byte[]> byteArrayToFileResponseEntity(String fileName, byte[] byteArray) {
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.CONTENT_DISPOSITION, "attchement;filename=" + fileName);
		headers.add("Accept", MediaType.APPLICATION_OCTET_STREAM_VALUE);

		ResponseEntity<byte[]> entity = new ResponseEntity<byte[]>(byteArray, headers, HttpStatus.OK);
		return entity;
	}

	public static class FileInfo {
		private String fileName;
		private String fileBase64String;

		public String getFileName() {
			return fileName;
		}

		public void setFileName(String fileName) {
			this.fileName = fileName;
		}

		public String getFileBase64String() {
			return fileBase64String;
		}

		public void setFileBase64String(String fileBase64String) {
			this.fileBase64String = fileBase64String;
		}
	}
}
