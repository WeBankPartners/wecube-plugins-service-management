package com.webank.servicemanagement.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.collect.Lists;
import com.webank.servicemanagement.commons.AppProperties.ServiceManagementProperties;
import com.webank.servicemanagement.commons.ApplicationConstants.ApiInfo;
import com.webank.servicemanagement.commons.AuthenticationContextHolder;
import com.webank.servicemanagement.commons.ServiceMgmtException;
import com.webank.servicemanagement.domain.AttachFile;
import com.webank.servicemanagement.domain.ServiceRequest;
import com.webank.servicemanagement.domain.ServiceRequestTemplate;
import com.webank.servicemanagement.dto.CreateServiceRequestRequest;
import com.webank.servicemanagement.dto.DoneServiceRequestRequest;
import com.webank.servicemanagement.dto.DownloadAttachFileResponse;
import com.webank.servicemanagement.dto.QueryRequest;
import com.webank.servicemanagement.dto.QueryResponse;
import com.webank.servicemanagement.dto.ServiceRequestDto;
import com.webank.servicemanagement.dto.Sorting;
import com.webank.servicemanagement.jpa.AttachFileRepository;
import com.webank.servicemanagement.jpa.EntityRepository;
import com.webank.servicemanagement.jpa.ServiceRequestRepository;
import com.webank.servicemanagement.jpa.ServiceRequestTemplateRepository;
import com.webank.servicemanagement.support.core.CoreServiceStub;
import com.webank.servicemanagement.support.core.dto.ReportServiceRequest;
import com.webank.servicemanagement.support.s3.S3Client;
import com.webank.servicemanagement.utils.DateUtils;
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
    private final static String IS_NOTIFY_REQUIRED = "Y";

    public void createNewServiceRequest(CreateServiceRequestRequest request) throws Exception {
        if(StringUtils.isBlank(request.getEnvType())){
            throw new Exception("The envType is required!");
        }
        String currentUserName = AuthenticationContextHolder.getCurrentUsername();
        Optional<ServiceRequestTemplate> serviceRequestTemplateOptional = serviceRequestTemplateRepository
                .findById(request.getTemplateId());
        if (!serviceRequestTemplateOptional.isPresent()) {
            throw new ServiceMgmtException("3004", "Invalid service request template ID !");
        }

        String attachFileId = null;
        if (request.getAttachFileId() != null && !request.getAttachFileId().isEmpty()) {
            Optional<AttachFile> attachFileOptional = attachFileRepository.findById(request.getAttachFileId());
            if (!attachFileOptional.isPresent()) {
                String msg = String.format("Attach file ID [%s] not found", request.getAttachFileId());
                throw new ServiceMgmtException("3005", msg, request.getAttachFileId());
            }
            attachFileId = attachFileOptional.get().getId();
        }
        ServiceRequestTemplate serviceRequestTemplate = serviceRequestTemplateOptional.get();
        ServiceRequest serviceRequest = serviceRequestRepository.save(new ServiceRequest(serviceRequestTemplate,
                request.getName(), currentUserName, request.getEmergency(), request.getDescription(), STATUS_SUBMITTED,
                attachFileId, request.getEnvType(), request.getRoleName()));

        ReportServiceRequest reportServiceRequest = new ReportServiceRequest(serviceRequest.getId(),
                serviceRequestTemplate.getName(), serviceManagementProperties.getSystemCode(),
                serviceRequestTemplate.getProcessDefinitionKey(), serviceRequest.getId(), IS_NOTIFY_REQUIRED,
                ApiInfo.API_PREFIX + ApiInfo.CALLBACK_URL_OF_REPORT_SERVICE_REQUEST, serviceRequest.getReporter(),
                DateUtils.formatDateToString(serviceRequest.getReportTime()), serviceRequest.getEnvType());

        try {
            coreServiceStub.reportOperationEventsToCore(reportServiceRequest);
        } catch (Exception e) {
            serviceRequest.setStatus(STATUS_DONE);
            serviceRequest.setResult("Report to Core Error: " + e.getMessage());
            serviceRequestRepository.save(serviceRequest);
            String msg = String.format("Failed to report to platform caused by:%s.", e.getMessage());
            throw new ServiceMgmtException("3006", msg, e.getMessage());
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
        if (!serviceRequestResult.isPresent()) {
            String msg = String.format("Service Request [%s] not found", completedRequest.getServiceRequestId());
            throw new ServiceMgmtException("3007", msg, completedRequest.getServiceRequestId());
        }
        ServiceRequest serviceRequest = serviceRequestResult.get();
        serviceRequest.setResult(completedRequest.getResult());
        serviceRequest.setStatus(STATUS_DONE);
        serviceRequestRepository.save(serviceRequest);
    }

    public String uploadServiceRequestAttachFile(MultipartFile attachFile) throws Exception {
        if (attachFile.isEmpty()) {
            throw new ServiceMgmtException("3008", "Empty file!");
        }

        String fileExtension = FilenameUtils.getExtension(attachFile.getOriginalFilename());
        if (!fileExtension.equals("xlsx") && !fileExtension.equals("xls")) {
            throw new ServiceMgmtException("3009", "Only support Excel file");
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
        if (!serviceRequestResult.isPresent()){
            String msg = String.format("The service request ID [%s] not found", serviceRequestId);
            throw new ServiceMgmtException("3010", msg, serviceRequestId);
        }
        ServiceRequest serviceRequest = serviceRequestResult.get();

        Optional<AttachFile> attachFileOptional = attachFileRepository.findById(serviceRequest.getAttachFileId());
        if (!attachFileOptional.isPresent()){
            throw new ServiceMgmtException("3011", "This service request has no attach file");
        }
        AttachFile attachFile = attachFileOptional.get();
        String fileName = attachFile.getAttachFileName();

        String tempDownloadFilePath = SystemUtils.getTempFolderPath() + fileName;
        File downloadFile = new File(tempDownloadFilePath);

        new S3Client(serviceManagementProperties).downFile(fileName, tempDownloadFilePath);
        DownloadAttachFileResponse response = new DownloadAttachFileResponse(
                FileUtils.readFileToByteArray(downloadFile), fileName);

        FileUtils.forceDelete(downloadFile);
        return response;
    }

    public QueryResponse<ServiceRequestDto> queryServiceRequestByCurrentRolesOrderByReportTimeDesc(
            QueryRequest queryRequest) {
        if (queryRequest.getSorting() == null || queryRequest.getSorting().getField() == null) {
            queryRequest.setSorting(new Sorting(false, "reportTime"));
        }
        Set<String> currentRoles = AuthenticationContextHolder.getCurrentUserRoles();
        log.info("currentRoles={}", currentRoles);
        if (currentRoles != null && !currentRoles.isEmpty()) {
            queryRequest.addInFilter("reportRole", new ArrayList<>(currentRoles));
        }

        List<ServiceRequestDto> queryResultDtos;
        try {
            QueryResponse<ServiceRequest> queryResult = entityRepository.query(ServiceRequest.class, queryRequest);
            if (queryResult.getContents().size() == 0) {
                return new QueryResponse<>();
            }

            queryResultDtos = Lists.transform(queryResult.getContents(),
                    x -> ServiceRequestDto.fromDomain(x));
            return new QueryResponse<>(queryResult.getPageInfo(), queryResultDtos);
        } catch (Exception e) {
            log.error("Query service_request met error: {}", e.getMessage());
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

    public List<ServiceRequestDto> getDataWithConditions(String filter, String sorting, String select)
            throws Exception {
        QueryResponse<ServiceRequestDto> response = queryServiceRequestByCurrentRolesOrderByReportTimeDesc(
                QueryRequest.buildQueryRequest(filter, sorting, select));
        return response.getContents();
    }

}
