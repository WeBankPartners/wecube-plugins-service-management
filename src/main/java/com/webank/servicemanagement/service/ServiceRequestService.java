package com.webank.servicemanagement.service;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
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
import com.webank.servicemanagement.mock.MockCoreServiceStub;
import com.webank.servicemanagement.support.core.dto.StartWorkflowInstanceRequest;
import com.webank.servicemanagement.utils.FileUtils;

@Service
public class ServiceRequestService {

	@Autowired
	ServiceRequestRepository serviceRequestRepository;
	@Autowired
	ServiceRequestTemplateRepository serviceRequestTemplateRepository;
	@Autowired
	AttachFileRepository attachFileRepository;
	@Autowired
	EntityRepository entityRepository;

	// TODO - modify "MockCoreServiceStub" to "CoreServiceStub" when Core API is
	// ready
	@Autowired
	MockCoreServiceStub coreServiceStub;

	private final static String STATUS_SUBMITTED = "Submitted";
	private final static String STATUS_PROCESSING = "Processing";
	private final static String STATUS_DONE = "Done";

	public void createNewServiceRequest(String currentUserName, CreateServiceRequestRequest request) throws Exception {
		String currentTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		Optional<ServiceRequestTemplate> serviceRequestTemplate = serviceRequestTemplateRepository
				.findById(request.getTemplateId());
		if (!serviceRequestTemplate.isPresent())
			throw new Exception("Invalid service request template ID !");

		AttachFile attachFile = null;
		if (request.getAttachFileId() != 0) {
			Optional<AttachFile> attachFileOptional = attachFileRepository.findById(request.getAttachFileId());
			if (!attachFileOptional.isPresent())
				throw new Exception(String.format("Attach file ID [%d] not found", request.getAttachFileId()));
			attachFile = attachFileOptional.get();
		}
		ServiceRequest serviceRequest = new ServiceRequest(serviceRequestTemplate.get(), request.getName(),
				request.getRoleId(), request.getReporter(), currentTime, request.getEmergency(),
				request.getDescription(), STATUS_SUBMITTED, attachFile);
		serviceRequestRepository.save(serviceRequest);

		StartWorkflowInstanceRequest startWorkflowInstanceRequest = new StartWorkflowInstanceRequest(
				serviceRequestTemplate.get().getProcessDefinitionKey());
		serviceRequest.setProcessInstanceId(
				coreServiceStub.startWorkflowInstanceByProcessDefinitionKey(startWorkflowInstanceRequest));

		serviceRequest.setStatus(STATUS_PROCESSING);
		serviceRequestRepository.save(serviceRequest);
	}

	public List<ServiceRequest> getAllServiceRequest() {
		return Lists.newArrayList(serviceRequestRepository.findAll());
	}

	public void doneServiceRequest(int serviceRequestId, DoneServiceRequestRequest completedRequest) throws Exception {
		Optional<ServiceRequest> serviceRequestResult = serviceRequestRepository.findById(serviceRequestId);
		if (!serviceRequestResult.isPresent())
			throw new Exception(String.format("Service Request [%d] not found", serviceRequestId));
		ServiceRequest serviceRequest = serviceRequestResult.get();
		serviceRequest.setResult(completedRequest.getResult());
		serviceRequest.setStatus(STATUS_DONE);
		serviceRequestRepository.save(serviceRequest);
	}

	public int uploadServiceRequestAttachFile(InputStream attachFileStream, String attachFileName) throws Exception {
		AttachFile attachFileObject = new AttachFile(attachFileName, FileUtils.streamToByteArray(attachFileStream));
		attachFileRepository.save(attachFileObject);

		return attachFileObject.getId();
	}

	public DownloadAttachFileResponse downloadServiceRequestAttachFile(int serviceRequestId) throws Exception {
		Optional<ServiceRequest> serviceRequestResult = serviceRequestRepository.findById(serviceRequestId);
		if (!serviceRequestResult.isPresent())
			throw new Exception(String.format("The service request ID [%d] not found", serviceRequestId));
		ServiceRequest serviceRequest = serviceRequestResult.get();

		ResponseEntity<byte[]> responseEntity = FileUtils.byteArrayToFileResponseEntity(
				serviceRequest.getAttachFile().getAttachFileName(), serviceRequest.getAttachFile().getAttachFile());
		if (responseEntity == null) {
			throw new Exception("File object not found for service-request-id: " + serviceRequestId);
		}
		return new DownloadAttachFileResponse(responseEntity, serviceRequest.getAttachFile().getAttachFileName());

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

}
