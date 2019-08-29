package com.webank.servicemanagement.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.webank.servicemanagement.domain.ServiceRequest;
import com.webank.servicemanagement.jpa.ServiceRequestRepository;

@Service
public class ServiceRequestService {

	@Autowired
	ServiceRequestRepository serviceRequestRepository;

	private final static String STATUS_CREATED = "Created";
	private final static String STATUS_RECEIVED = "Received";
	private final static String STATUS_DONE = "Done";

	public void createNewServiceRequest(ServiceRequest serviceRequest) {
		serviceRequestRepository.save(serviceRequest);
	}

	public List<ServiceRequest> getAllServiceRequest() {
		return Lists.newArrayList(serviceRequestRepository.findAll());
	}

	public List<ServiceRequest> completeServiceRequest() {
		return Lists.newArrayList(serviceRequestRepository.findAll());
	}

}
