package com.webank.servicemanagement.domain;

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
@Table(name = "service_request")
@GenericGenerator(name = "jpa-uuid", strategy = "uuid")
public class ServiceRequest {

    @Id
    @GeneratedValue(generator = "jpa-uuid")
    @Column(name = "id", length = 32)
    private String id;

    private long requestNo;

    public ServiceRequest() {
    }

    public ServiceRequest(ServiceRequestTemplate serviceRequestTemplate, String name, String reporter, String emergency,
            String description, String status, String attachFileId, String envType, String reportRole) {
        this.serviceRequestTemplate = serviceRequestTemplate;
        this.name = name;
        this.reporter = reporter;
        this.emergency = emergency;
        this.description = description;
        this.status = status;
        this.attachFileId = attachFileId;
        this.envType = envType;
        this.reportRole = reportRole;
        long currentTimeMillis = System.currentTimeMillis();
        this.reportTime = new Date(currentTimeMillis);
        this.requestNo = currentTimeMillis;
    }

    @ManyToOne
    @JoinColumn(name = "template_id")
    private ServiceRequestTemplate serviceRequestTemplate;

    @Column(name = "name")
    private String name;
    @Column(name = "reporter")
    private String reporter;
    @Column(name = "report_role")
    private String reportRole;
    @Column(name = "report_time")
    private Date reportTime;
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
    
    //#162
    @Column(name = "root_data_id")
    private String rootDataId;
    
    //#162
    @Column(name = "proc_inst_id")
    private String procInstId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ServiceRequestTemplate getServiceRequestTemplate() {
        return serviceRequestTemplate;
    }

    public void setServiceRequestTemplate(ServiceRequestTemplate serviceRequestTemplate) {
        this.serviceRequestTemplate = serviceRequestTemplate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReporter() {
        return reporter;
    }

    public void setReporter(String reporter) {
        this.reporter = reporter;
    }

    public Date getReportTime() {
        return reportTime;
    }

    public void setReportTime(Date reportTime) {
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

    public String getReportRole() {
        return reportRole;
    }

    public void setReportRole(String reportRole) {
        this.reportRole = reportRole;
    }

    public String getRootDataId() {
        return rootDataId;
    }

    public void setRootDataId(String rootDataId) {
        this.rootDataId = rootDataId;
    }

    public String getProcInstId() {
        return procInstId;
    }

    public void setProcInstId(String procInstId) {
        this.procInstId = procInstId;
    }
}
