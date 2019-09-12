package com.webank.servicemanagement.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.webank.servicemanagement.domain.ServiceRequestTemplate;
import com.webank.servicemanagement.jpa.ServiceRequestTemplateRepository;

@Service
public class ServiceRequestTemplateService {

	@Autowired
	ServiceRequestTemplateRepository serviceRequestTemplateRepository;

	private final static String AVAILABLE_STATU_STRING = "active";

	public List<ServiceRequestTemplate> getAllAvailableServiceRequestTemplate() {
		return Lists.newArrayList(serviceRequestTemplateRepository.findAllByStatus(AVAILABLE_STATU_STRING));
	}

	public List<ServiceRequestTemplate> getAllServiceRequestTemplate() {
		return Lists.newArrayList(serviceRequestTemplateRepository.findAll());
	}

}
