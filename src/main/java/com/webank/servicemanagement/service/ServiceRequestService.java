package com.webank.servicemanagement.service;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.webank.servicemanagement.domain.AttachFile;
import com.webank.servicemanagement.domain.ServiceRequest;
import com.webank.servicemanagement.domain.ServiceRequestTemplate;
import com.webank.servicemanagement.dto.CompletedServiceRequestRequest;
import com.webank.servicemanagement.dto.CreateServiceRequestRequest;
import com.webank.servicemanagement.dto.DownloadAttachFileResponse;
import com.webank.servicemanagement.jpa.AttachFileRepository;
import com.webank.servicemanagement.jpa.ServiceRequestRepository;
import com.webank.servicemanagement.jpa.ServiceRequestTemplateRepository;
import com.webank.servicemanagement.mock.MockCoreServiceStub;
import com.webank.servicemanagement.support.core.dto.StartWorkflowInstanceRequest;
import com.webank.servicemanagement.utils.FileUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ServiceRequestService {

	@Autowired
	ServiceRequestRepository serviceRequestRepository;
	@Autowired
	ServiceRequestTemplateRepository serviceRequestTemplateRepository;
	@Autowired
	AttachFileRepository attachFileRepository;

	// TODO - modify "MockCoreServiceStub" to "CoreServiceStub" when Core API is
	// ready
	@Autowired
	MockCoreServiceStub coreServiceStub;

	private final static String STATUS_SUBMITTED = "Submitted";
	private final static String STATUS_PROCESSING = "Processing";
	private final static String STATUS_DONE = "Done";

	public void createNewServiceRequest(String currentUserName, CreateServiceRequestRequest request) throws Exception {

		checkUserAuthority(currentUserName, request.getTemplateId());

		String currentTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		Optional<ServiceRequestTemplate> serviceRequestTemplate = serviceRequestTemplateRepository
				.findById(request.getTemplateId());
		if (!serviceRequestTemplate.isPresent())
			throw new Exception("Invalid service request template ID !");
		ServiceRequest serviceRequest = new ServiceRequest(serviceRequestTemplate.get(), request.getName(),
				request.getRoleId(), request.getReporter(), currentTime, request.getEmergency(),
				request.getDescription(), STATUS_SUBMITTED);
		serviceRequestRepository.save(serviceRequest);

		StartWorkflowInstanceRequest startWorkflowInstanceRequest = new StartWorkflowInstanceRequest(
				serviceRequestTemplate.get().getProcessDefinitionKey());
		serviceRequest.setProcessInstanceId(
				coreServiceStub.startWorkflowInstanceByProcessDefinitionKey(startWorkflowInstanceRequest));

		serviceRequest.setStatus(STATUS_PROCESSING);
		serviceRequestRepository.save(serviceRequest);
	}

	private void checkUserAuthority(String userName, int serviceTemplateId) {
		// TODO - check user whether has authority to use the specified
		// template ID
	}

	public List<ServiceRequest> getAllServiceRequest() {
		return Lists.newArrayList(serviceRequestRepository.findAll());
	}

	public List<ServiceRequest> getServiceRequestByUserName(String currentUserName) {

		// TODO - getServiceRequestByUserName
		return Lists.newArrayList();
	}

	public void completedServiceRequest(int serviceRequestId, CompletedServiceRequestRequest completedRequest)
			throws Exception {
		Optional<ServiceRequest> serviceRequestResult = serviceRequestRepository.findById(serviceRequestId);
		if (!serviceRequestResult.isPresent())
			throw new Exception(String.format("Service Request [%d] not found", serviceRequestId));
		ServiceRequest serviceRequest = serviceRequestResult.get();
		serviceRequest.setResult(completedRequest.getResult());
		serviceRequest.setStatus(STATUS_DONE);
		serviceRequestRepository.save(serviceRequest);
	}

	public int uploadServiceRequestAttachFile(InputStream attachFileStream, String attachFileName) throws Exception {

		String attachFileBase64String = FileUtils.streamToBase64String(attachFileStream);

		AttachFile attachFileObject = new AttachFile(attachFileName, attachFileBase64String);
		attachFileRepository.save(attachFileObject);

		return attachFileObject.getId();
	}

	public DownloadAttachFileResponse downloadServiceRequestAttachFile(int serviceRequestId) throws Exception {
		Optional<ServiceRequest> serviceRequestResult = serviceRequestRepository.findById(serviceRequestId);
		if (!serviceRequestResult.isPresent())
			throw new Exception(String.format("The service request ID [%d] not found", serviceRequestId));
		ServiceRequest serviceRequest = serviceRequestResult.get();

		ResponseEntity<byte[]> responseEntity = FileUtils.base64StringToFileResponseEntity(
				serviceRequest.getAttachFile().getAttachFileName(), serviceRequest.getAttachFile().getAttachFile());
		if (responseEntity == null) {
			throw new Exception("File object not found for service-request-id: " + serviceRequestId);
		}
		return new DownloadAttachFileResponse(responseEntity, serviceRequest.getAttachFile().getAttachFileName());

	}

}
