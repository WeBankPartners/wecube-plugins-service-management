package com.webank.servicemanagement.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "attach_file")
public class AttachFile {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "attach_file_name")
	private String attachFileName;
	@Column(name = "attach_file")
	private byte[] attachFile;

	public AttachFile() {
	}

	public AttachFile(String attachFileName, byte[] attachFile) {
		this.attachFileName = attachFileName;
		this.attachFile = attachFile;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAttachFileName() {
		return attachFileName;
	}

	public void setAttachFileName(String attachFileName) {
		this.attachFileName = attachFileName;
	}

	public byte[] getAttachFile() {
		return attachFile;
	}

	public void setAttachFile(byte[] attachFile) {
		this.attachFile = attachFile;
	}

}
