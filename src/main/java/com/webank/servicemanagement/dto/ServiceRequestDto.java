package com.webank.servicemanagement.dto;

import com.webank.servicemanagement.domain.ServiceRequest;
import com.webank.servicemanagement.utils.DateUtils;

public class ServiceRequestDto {

    private String id;
    private long requestNo;
    private String name;
    private String reporter;
    private String reportRole;
    private String reportTime;
    private String emergency;
    private String description;
    private String attachFileId;
    private String result;
    private String status;
    private String envType;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public long getRequestNo() {
        return requestNo;
    }

    public void setRequestNo(long requestNo) {
        this.requestNo = requestNo;
    }

    public String getAttachFileId() {return attachFileId;}

    public void setAttachFileId(String attachFileId) {this.attachFileId = attachFileId;}

    public String getReportRole() {return reportRole;}

    public void setReportRole(String reportRole) {this.reportRole = reportRole;}

    public String getEnvType() {return envType;}

    public void setEnvType(String envType) {this.envType = envType;}

    public static ServiceRequestDto fromDomain(ServiceRequest serviceRequest){
        ServiceRequestDto serviceRequestDto = new ServiceRequestDto();
        serviceRequestDto.setId(serviceRequest.getId());
        serviceRequestDto.setRequestNo(serviceRequest.getRequestNo());
        serviceRequestDto.setName(serviceRequest.getName());
        serviceRequestDto.setReporter(serviceRequest.getReporter());
        serviceRequestDto.setReportTime(DateUtils.formatDateToString(serviceRequest.getReportTime()));
        serviceRequestDto.setEmergency(serviceRequest.getEmergency());
        serviceRequestDto.setDescription(serviceRequest.getDescription());
        serviceRequestDto.setAttachFileId(serviceRequest.getAttachFileId());
        serviceRequestDto.setResult(serviceRequest.getResult());
        serviceRequestDto.setStatus(serviceRequest.getStatus());
        serviceRequestDto.setEnvType(serviceRequest.getEnvType());
        return serviceRequestDto;
    }
}
