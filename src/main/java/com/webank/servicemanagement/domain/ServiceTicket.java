package com.webank.servicemanagement.domain;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "service_ticket")
@GenericGenerator(name = "jpa-uuid", strategy = "uuid")
public class ServiceTicket {

    @Id
    @GeneratedValue(generator = "jpa-uuid")
    @Column(name = "id", length = 32)
    private String id;

    private long requestNo;

    @ManyToOne
    @JoinColumn(name = "service_form_id", insertable = false, updatable = false)
    private ServiceForm serviceForm;
    @Column(name = "service_form_id")
    private String serviceFormId;

    @Column(name = "title")
    private String title;
    @Column(name = "emergency")
    private String emergency;
    @Column(name = "description")
    private String description;

    @Column(name = "attach_file_id")
    private String attachFileId;

    @Column(name = "result")
    private String result;
    @Column(name = "status")
    private String status = "active";
    @Column(name = "env_type")
    private String envType;

    @Column(name = "reporter")
    private String reporter;
    @Column(name = "report_time")
    private String reportTime;

    public ServiceTicket() {
    }

    public ServiceTicket(ServiceForm serviceForm, String title, String reporter, String emergency,
            String description, String status, String attachFileId, String envType) {
        this.serviceForm = serviceForm;
        this.title = title;
        this.reporter = reporter;
        this.emergency = emergency;
        this.description = description;
        this.status = status;
        this.attachFileId = attachFileId;
        this.envType = envType;
        long currentTimeMillis = System.currentTimeMillis();
        this.reportTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(currentTimeMillis));
        this.requestNo = currentTimeMillis;
    }
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ServiceForm getServiceForm() {
        return serviceForm;
    }

    public void setServiceForm(ServiceForm serviceForm) {
        this.serviceForm = serviceForm;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getReporter() {
        return reporter;
    }

    public void setReporter(String reporter) {
        this.reporter = reporter;
    }

    public String getReportTime() {
        return reportTime;
    }

    public void setReportTime(String reportTime) {
        this.reportTime = reportTime;
    }

    public String getEmergency() {
        return emergency;
    }

    public void setEmergency(String emergency) {
        this.emergency = emergency;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEnvType() {
        return envType;
    }

    public void setEnvType(String envType) {
        this.envType = envType;
    }

    public String getAttachFileId() {
        return attachFileId;
    }

    public void setAttachFileId(String attachFileId) {
        this.attachFileId = attachFileId;
    }

    public long getRequestNo() {
        return requestNo;
    }

    public void setRequestNo(long requestNo) {
        this.requestNo = requestNo;
    }
}