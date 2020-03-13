package com.webank.servicemanagement.dto;

import com.webank.servicemanagement.domain.ServiceTicket;

public class UpdateServiceRequestRequest {
    private String name;
    private String reporterRoleId;
    private String reporter;
    private String reportTime;
    private String emergency;
    private String description;
    private String result;
    private String ProcessInstanceId;
    private String status;

    public static ServiceTicket toDomain(UpdateServiceRequestRequest updateServiceRequestRequest,
            ServiceTicket existedServiceRequest) {
        ServiceTicket serviceRequest = existedServiceRequest;
        if (serviceRequest == null) {
            serviceRequest = new ServiceTicket();
        }

        if (updateServiceRequestRequest.getName() != null) {
            serviceRequest.setTitle(updateServiceRequestRequest.getName());
        }

        if (updateServiceRequestRequest.getReporter() != null) {
            serviceRequest.setReporter(updateServiceRequestRequest.getReporter());
        }

        if (updateServiceRequestRequest.getReportTime() != null) {
            serviceRequest.setReportTime(updateServiceRequestRequest.getReportTime());
        }

        if (updateServiceRequestRequest.getEmergency() != null) {
            serviceRequest.setEmergency(updateServiceRequestRequest.getEmergency());
        }

        if (updateServiceRequestRequest.getDescription() != null) {
            serviceRequest.setDescription(updateServiceRequestRequest.getDescription());
        }

        if (updateServiceRequestRequest.getResult() != null) {
            serviceRequest.setResult(updateServiceRequestRequest.getResult());
        }

        if (updateServiceRequestRequest.getStatus() != null) {
            serviceRequest.setStatus(updateServiceRequestRequest.getStatus());
        }

        return serviceRequest;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReporterRoleId() {
        return reporterRoleId;
    }

    public void setReporterRoleId(String reporterRoleId) {
        this.reporterRoleId = reporterRoleId;
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

    public String getProcessInstanceId() {
        return ProcessInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        ProcessInstanceId = processInstanceId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
