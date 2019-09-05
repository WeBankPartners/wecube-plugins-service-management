package com.webank.servicemanagement.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "attach_file")
public class AttachFile {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "attach_file_name")
	private String attachFileName;
	@Column(name = "attach_file")
	private String attachFile;

	public AttachFile() {
	}

	public AttachFile(String attachFileName, String attachFile) {
		this.attachFileName = attachFileName;
		this.attachFile = attachFile;
	}

}
