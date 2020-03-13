package com.webank.servicemanagement.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.collect.Lists;
import com.webank.servicemanagement.commons.AppProperties.ServiceManagementProperties;
import com.webank.servicemanagement.commons.ApplicationConstants;
import com.webank.servicemanagement.commons.AuthenticationContextHolder;
import com.webank.servicemanagement.commons.ApplicationConstants.ApiInfo;
import com.webank.servicemanagement.domain.AttachFile;
import com.webank.servicemanagement.domain.ServiceTicket;
import com.webank.servicemanagement.domain.ServiceForm;
import com.webank.servicemanagement.dto.CreateServiceTicketRequest;
import com.webank.servicemanagement.dto.DoneServiceRequestRequest;
import com.webank.servicemanagement.dto.DownloadAttachFileResponse;
import com.webank.servicemanagement.dto.QueryRequest;
import com.webank.servicemanagement.dto.QueryResponse;
import com.webank.servicemanagement.dto.Sorting;
import com.webank.servicemanagement.jpa.AttachFileRepository;
import com.webank.servicemanagement.jpa.EntityRepository;
import com.webank.servicemanagement.jpa.ServiceTicketRepository;
import com.webank.servicemanagement.jpa.ServiceFormRepository;
import com.webank.servicemanagement.support.core.CoreServiceStub;
import com.webank.servicemanagement.support.core.dto.ReportServiceRequest;
import com.webank.servicemanagement.support.s3.S3Client;
import com.webank.servicemanagement.utils.JsonUtils;
import com.webank.servicemanagement.utils.SystemUtils;

@Service
public class ServiceTicketService {

    @Autowired
    ServiceTicketRepository serviceTicketRepository;
    @Autowired
    ServiceFormRepository serviceFormRepository;
    @Autowired
    AttachFileRepository attachFileRepository;
    @Autowired
    EntityRepository entityRepository;

    @Autowired
    CoreServiceStub coreServiceStub;
    @Autowired
    ServiceManagementProperties serviceManagementProperties;

    public void createNewServiceTicket(CreateServiceTicketRequest request) throws Exception {
        String currentUserName = AuthenticationContextHolder.getCurrentUsername();
        Optional<ServiceForm> serviceFormOptional = serviceFormRepository.findById(request.getServiceFormId());
        if (!serviceFormOptional.isPresent())
            throw new Exception("Invalid service form ID !");

        String attachFileId = null;
        if (request.getAttachFileId() != null && !request.getAttachFileId().isEmpty()) {
            Optional<AttachFile> attachFileOptional = attachFileRepository.findById(request.getAttachFileId());
            if (!attachFileOptional.isPresent())
                throw new Exception(String.format("Attach file ID [%s] not found", request.getAttachFileId()));
            attachFileId = attachFileOptional.get().getId();
        }
        ServiceForm serviceForm = serviceFormOptional.get();
        ServiceTicket serviceTicket = serviceTicketRepository.save(new ServiceTicket(serviceForm,
                request.getName(), currentUserName, request.getEmergency(), request.getDescription(),
                ApplicationConstants.getStatusSubmitted(), attachFileId, request.getEnvType()));

        ReportServiceRequest reportServiceRequest = new ReportServiceRequest(serviceTicket.getId(),
                serviceForm.getName(), serviceManagementProperties.getSystemCode(),
                serviceForm.getProcessDefinitionKey(), serviceTicket.getId(),
                ApplicationConstants.getIsNotifyRequired(),
                ApiInfo.API_PREFIX + ApiInfo.CALLBACK_URL_OF_REPORT_SERVICE_REQUEST, serviceTicket.getReporter(),
                serviceTicket.getReportTime(), serviceTicket.getEnvType());

        try {
            coreServiceStub.reportOperationEventsToCore(reportServiceRequest);
        } catch (Exception e) {
            serviceTicket.setStatus(ApplicationConstants.getStatusDone());
            serviceTicket.setResult("Report to Core Error: " + e.getMessage());
            serviceTicketRepository.save(serviceTicket);
            throw new Exception("Report to Core Error: " + e.getMessage());
        }

        serviceTicket.setStatus(ApplicationConstants.getStatusProcessing());
        serviceTicketRepository.save(serviceTicket);
    }

    public List<ServiceTicket> getAllServiceRequest() {
        return Lists.newArrayList(serviceTicketRepository.findAll());
    }

    public void doneServiceRequest(DoneServiceRequestRequest completedRequest) throws Exception {
        Optional<ServiceTicket> serviceRequestResult = serviceTicketRepository
                .findById(completedRequest.getServiceRequestId());
        if (!serviceRequestResult.isPresent())
            throw new Exception(
                    String.format("Service ticket [%d] not found", completedRequest.getServiceRequestId()));
        ServiceTicket serviceRequest = serviceRequestResult.get();
        serviceRequest.setResult(completedRequest.getResult());
        serviceRequest.setStatus(ApplicationConstants.getStatusDone());
        serviceTicketRepository.save(serviceRequest);
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
        Optional<ServiceTicket> serviceRequestResult = serviceTicketRepository.findById(serviceRequestId);
        if (!serviceRequestResult.isPresent())
            throw new Exception(String.format("The service ticket ID [%d] not found", serviceRequestId));
        ServiceTicket serviceRequest = serviceRequestResult.get();

        Optional<AttachFile> attachFileOptional = attachFileRepository.findById(serviceRequest.getAttachFileId());
        if (!attachFileOptional.isPresent())
            throw new Exception("This service ticket has no attach file");
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

    public QueryResponse<ServiceTicket> queryServiceRequest(QueryRequest queryRequest) {
        queryRequest.setSorting(new Sorting(false, "reportTime"));

        QueryResponse<ServiceTicket> queryResult;
        try {
            queryResult = entityRepository.query(ServiceTicket.class, queryRequest);
            if (queryResult.getContents().size() == 0) {
                return new QueryResponse<>();
            }
            return queryResult;
        } catch (Exception e) {
            return new QueryResponse<>();
        }
    }

    public List<ServiceTicket> create(List<Map<String, Object>> mapList) {
        List<ServiceTicket> serviceRequests = convertToDomainList(mapList);
        Iterable<ServiceTicket> savedServiceRequests = serviceTicketRepository.saveAll(serviceRequests);
        return Lists.newArrayList(savedServiceRequests);
    }

    public List<ServiceTicket> update(List<Map<String, Object>> mapList) {
        List<ServiceTicket> serviceRequests = convertToDomainList(mapList);
        Iterable<ServiceTicket> savedServiceRequests = serviceTicketRepository.saveAll(serviceRequests);
        return Lists.newArrayList(savedServiceRequests);
    }

    public void delete(List<Map<String, Object>> mapList) {
        List<ServiceTicket> serviceRequests = convertToDomainList(mapList);
        serviceRequests.forEach(serviceRequest -> {
            serviceTicketRepository.deleteById(serviceRequest.getId());
        });
    }

    private List<ServiceTicket> convertToDomainList(List<Map<String, Object>> mapList) {
        List<ServiceTicket> serviceTickets = new ArrayList<ServiceTicket>();
        mapList.forEach(map -> {
            serviceTickets.add(JsonUtils.toObject(map, ServiceTicket.class));
        });
        return serviceTickets;
    }

    public List<ServiceTicket> getDataWithConditions(String filter, String sorting, String select) throws Exception {
        QueryResponse<ServiceTicket> response = queryServiceRequest(
                QueryRequest.buildQueryRequest(filter, sorting, select));
        return response.getContents();
    }

}
