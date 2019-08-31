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
import com.webank.servicemanagement.domain.ServiceRequest;
import com.webank.servicemanagement.dto.CompletedServiceRequestRequest;
import com.webank.servicemanagement.dto.CreateServiceRequestRequest;
import com.webank.servicemanagement.dto.DownloadAttachFileResponse;
import com.webank.servicemanagement.jpa.ServiceRequestRepository;
import com.webank.servicemanagement.utils.FileUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ServiceRequestService {

	@Autowired
	ServiceRequestRepository serviceRequestRepository;

	private final static String STATUS_SUBMITTED = "Submitted";
	private final static String STATUS_PROCESSING = "Processing";
	private final static String STATUS_DONE = "Done";

	public void createNewServiceRequest(CreateServiceRequestRequest request) {
		String currentTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		ServiceRequest serviceRequest = new ServiceRequest(request.getTemplateId(), request.getName(),
				request.getReporter(), currentTime, request.getEmergency(), request.getDescription(), STATUS_SUBMITTED);
		serviceRequestRepository.save(serviceRequest);

		// TODO -call WeCube-Core API to start workflow instance, return
		// ProcessInstanceId.
		serviceRequest.setProcessInstanceId("mockProcessInstanceId");

		serviceRequest.setStatus(STATUS_PROCESSING);
		serviceRequestRepository.save(serviceRequest);
	}

	public List<ServiceRequest> getAllServiceRequest() {
		return Lists.newArrayList(serviceRequestRepository.findAll());
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

	public void uploadServiceRequestAttachFile(int serviceRequestId, InputStream attachFileStream,
			String attachFileName) throws Exception {

		String attachFileBase64String = FileUtils.streamToBase64String(attachFileStream);

		ServiceRequest serviceRequest = null;
		Optional<ServiceRequest> serviceRequestResult = serviceRequestRepository.findById(serviceRequestId);
		if (serviceRequestResult.isPresent())
			serviceRequest = serviceRequestResult.get();
		serviceRequest.setAttachFileName(attachFileName);
		serviceRequest.setAttachFile(attachFileBase64String);
		serviceRequestRepository.save(serviceRequest);
	}

	public DownloadAttachFileResponse downloadServiceRequestAttachFile(int serviceRequestId) throws Exception {
		Optional<ServiceRequest> serviceRequestResult = serviceRequestRepository.findById(serviceRequestId);
		if (!serviceRequestResult.isPresent())
			throw new Exception(String.format("The service request ID [%d] not found", serviceRequestId));
		ServiceRequest serviceRequest = serviceRequestResult.get();

		ResponseEntity<byte[]> responseEntity = FileUtils
				.base64StringToFileResponseEntity(serviceRequest.getAttachFileName(), serviceRequest.getAttachFile());
		if (responseEntity == null) {
			throw new Exception("File object not found for service-request-id: " + serviceRequestId);
		}
		return new DownloadAttachFileResponse(responseEntity, serviceRequest.getAttachFileName());

	}

}
