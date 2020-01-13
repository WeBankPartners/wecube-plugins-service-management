package com.webank.servicemanagement.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "attach_file")
@GenericGenerator(name = "jpa-uuid", strategy = "uuid")
public class AttachFile {

    @Id
    @GeneratedValue(generator = "jpa-uuid")
    @Column(name = "id", length = 32)
    private String id;

    @Column(name = "attach_file_name")
    private String attachFileName;
    @Column(name = "s3_url")
    private String s3Url;

    public AttachFile() {
    }

    public AttachFile(String attachFileName, String s3Url) {
        this.attachFileName = attachFileName;
        this.s3Url = s3Url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAttachFileName() {
        return attachFileName;
    }

    public void setAttachFileName(String attachFileName) {
        this.attachFileName = attachFileName;
    }

    public String getS3Url() {
        return s3Url;
    }

    public void setS3Url(String s3Url) {
        this.s3Url = s3Url;
    }

}
