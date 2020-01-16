package com.webank.servicemanagement.service;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.collect.Lists;
import com.webank.servicemanagement.commons.AppProperties.ServiceManagementProperties;
import com.webank.servicemanagement.commons.AuthenticationContextHolder;
import com.webank.servicemanagement.commons.ApplicationConstants.ApiInfo;
import com.webank.servicemanagement.domain.AttachFile;
import com.webank.servicemanagement.domain.ServiceRequest;
import com.webank.servicemanagement.domain.ServiceRequestTemplate;
import com.webank.servicemanagement.dto.CreateServiceRequestRequest;
import com.webank.servicemanagement.dto.DoneServiceRequestRequest;
import com.webank.servicemanagement.dto.DownloadAttachFileResponse;
import com.webank.servicemanagement.dto.QueryRequest;
import com.webank.servicemanagement.dto.QueryResponse;
import com.webank.servicemanagement.dto.Sorting;
import com.webank.servicemanagement.jpa.AttachFileRepository;
import com.webank.servicemanagement.jpa.EntityRepository;
import com.webank.servicemanagement.jpa.ServiceRequestRepository;
import com.webank.servicemanagement.jpa.ServiceRequestTemplateRepository;
import com.webank.servicemanagement.support.core.CoreServiceStub;
import com.webank.servicemanagement.support.core.dto.ReportServiceRequest;
import com.webank.servicemanagement.support.s3.S3Client;
import com.webank.servicemanagement.utils.JsonUtils;
import com.webank.servicemanagement.utils.SystemUtils;

@Service
public class ServiceRequestService {
    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    ServiceRequestRepository serviceRequestRepository;
    @Autowired
    ServiceRequestTemplateRepository serviceRequestTemplateRepository;
    @Autowired
    AttachFileRepository attachFileRepository;
    @Autowired
    EntityRepository entityRepository;

    @Autowired
    CoreServiceStub coreServiceStub;
    @Autowired
    ServiceManagementProperties serviceManagementProperties;

    private final static String STATUS_SUBMITTED = "Submitted";
    private final static String STATUS_PROCESSING = "Processing";
    private final static String STATUS_DONE = "Done";

    public void createNewServiceRequest(CreateServiceRequestRequest request) throws Exception {
        String currentUserName = AuthenticationContextHolder.getCurrentUsername();
        String currentTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        Optional<ServiceRequestTemplate> serviceRequestTemplateOptional = serviceRequestTemplateRepository
                .findById(request.getTemplateId());
        if (!serviceRequestTemplateOptional.isPresent())
            throw new Exception("Invalid service request template ID !");

        AttachFile attachFile = null;
        if (request.getAttachFileId() != null && !request.getAttachFileId().isEmpty()) {
            Optional<AttachFile> attachFileOptional = attachFileRepository.findById(request.getAttachFileId());
            if (!attachFileOptional.isPresent())
                throw new Exception(String.format("Attach file ID [%s] not found", request.getAttachFileId()));
            attachFile = attachFileOptional.get();
        }
        ServiceRequestTemplate serviceRequestTemplate = serviceRequestTemplateOptional.get();
        ServiceRequest serviceRequest = serviceRequestRepository.save(new ServiceRequest(serviceRequestTemplate,
                request.getName(), currentUserName, currentTime, request.getEmergency(), request.getDescription(),
                STATUS_SUBMITTED, attachFile, request.getEnvType()));

        ReportServiceRequest reportServiceRequest = new ReportServiceRequest(serviceRequest.getId(),
                serviceRequestTemplate.getName(), serviceManagementProperties.getSystemCode(),
                serviceRequestTemplate.getProcessDefinitionKey(), serviceRequest.getId(), "Y",
                ApiInfo.API_PREFIX + ApiInfo.CALLBACK_URL_OF_REPORT_SERVICE_REQUEST, serviceRequest.getReporter(),
                serviceRequest.getReportTime(), serviceRequest.getEnvType());

        try {
            coreServiceStub.reportOperationEventsToCore(reportServiceRequest);
        } catch (Exception e) {
            serviceRequest.setStatus(STATUS_DONE);
            serviceRequest.setResult("Report to Core Error: " + e.getMessage());
            serviceRequestRepository.save(serviceRequest);
            throw new Exception("Report to Core Error: " + e.getMessage());
        }

        serviceRequest.setStatus(STATUS_PROCESSING);
        serviceRequestRepository.save(serviceRequest);
    }

    public List<ServiceRequest> getAllServiceRequest() {
        return Lists.newArrayList(serviceRequestRepository.findAll());
    }

    public void doneServiceRequest(DoneServiceRequestRequest completedRequest) throws Exception {
        Optional<ServiceRequest> serviceRequestResult = serviceRequestRepository
                .findById(completedRequest.getServiceRequestId());
        if (!serviceRequestResult.isPresent())
            throw new Exception(
                    String.format("Service Request [%d] not found", completedRequest.getServiceRequestId()));
        ServiceRequest serviceRequest = serviceRequestResult.get();
        serviceRequest.setResult(completedRequest.getResult());
        serviceRequest.setStatus(STATUS_DONE);
        serviceRequestRepository.save(serviceRequest);
    }

    public String uploadServiceRequestAttachFile(MultipartFile attachFile) throws Exception {
        if (attachFile.isEmpty()) {
            throw new Exception("Empty file!");
        }

        String fileExtension = FilenameUtils.getExtension(attachFile.getOriginalFilename());
        if (!fileExtension.equals("xlsx") && !fileExtension.equals("xls")) {
            throw new IllegalArgumentException("Only support Excel file");
        }

        String tmpFileName = String.valueOf(System.currentTimeMillis());
        File tempUploadFile = new File(SystemUtils.getTempFolderPath() + tmpFileName);
        attachFile.transferTo(tempUploadFile);

        String uploadFileName = FilenameUtils.getBaseName(attachFile.getOriginalFilename()) + "-" + tmpFileName + "."
                + fileExtension;

        String s3Url = new S3Client(serviceManagementProperties).uploadFile(uploadFileName, tempUploadFile);
        AttachFile attachFileObject = new AttachFile(uploadFileName, s3Url,
                serviceManagementProperties.getS3DefaultBucket(), uploadFileName);
        attachFileRepository.save(attachFileObject);

        FileUtils.forceDelete(tempUploadFile);
        return attachFileObject.getId();
    }

    public DownloadAttachFileResponse downloadServiceRequestAttachFile(String serviceRequestId) throws Exception {
        Optional<ServiceRequest> serviceRequestResult = serviceRequestRepository.findById(serviceRequestId);
        if (!serviceRequestResult.isPresent())
            throw new Exception(String.format("The service request ID [%d] not found", serviceRequestId));
        ServiceRequest serviceRequest = serviceRequestResult.get();

        String fileName = serviceRequest.getAttachFile().getAttachFileName();

        String tempDownloadFilePath = SystemUtils.getTempFolderPath() + fileName;
        File downloadFile = new File(tempDownloadFilePath);

        new S3Client(serviceManagementProperties).downFile(serviceRequest.getAttachFile().getAttachFileName(),
                tempDownloadFilePath);
        DownloadAttachFileResponse response = new DownloadAttachFileResponse(
                FileUtils.readFileToByteArray(downloadFile), serviceRequest.getAttachFile().getAttachFileName());

        FileUtils.forceDelete(downloadFile);
        return response;
    }

    public QueryResponse<ServiceRequest> queryServiceRequest(QueryRequest queryRequest) {
        queryRequest.setSorting(new Sorting(false, "reportTime"));

        QueryResponse<ServiceRequest> queryResult;
        try {
            queryResult = entityRepository.query(ServiceRequest.class, queryRequest);
            if (queryResult.getContents().size() == 0) {
                return new QueryResponse<>();
            }
            return queryResult;
        } catch (Exception e) {
            return new QueryResponse<>();
        }
    }

    public List<ServiceRequest> create(List<Map<String, Object>> mapList) {
        List<ServiceRequest> serviceRequests = convertToDomainList(mapList);
        Iterable<ServiceRequest> savedServiceRequests = serviceRequestRepository.saveAll(serviceRequests);
        return Lists.newArrayList(savedServiceRequests);
    }

    public List<ServiceRequest> update(List<Map<String, Object>> mapList) {
        List<ServiceRequest> serviceRequests = convertToDomainList(mapList);
        Iterable<ServiceRequest> savedServiceRequests = serviceRequestRepository.saveAll(serviceRequests);
        return Lists.newArrayList(savedServiceRequests);
    }

    public void delete(List<Map<String, Object>> mapList) {
        List<ServiceRequest> serviceRequests = convertToDomainList(mapList);
        serviceRequests.forEach(serviceRequest -> {
            serviceRequestRepository.deleteById(serviceRequest.getId());
        });
    }

    private List<ServiceRequest> convertToDomainList(List<Map<String, Object>> mapList) {
        List<ServiceRequest> serviceRequests = new ArrayList<ServiceRequest>();
        mapList.forEach(map -> {
            serviceRequests.add(JsonUtils.toObject(map, ServiceRequest.class));
        });
        return serviceRequests;
    }

    public List<ServiceRequest> getDataWithConditions(String filter, String sorting, String select) throws Exception {
        QueryResponse<ServiceRequest> response = queryServiceRequest(
                QueryRequest.buildQueryRequest(filter, sorting, select));
        return response.getContents();
    }

}
