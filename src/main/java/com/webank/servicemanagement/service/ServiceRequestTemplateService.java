package com.webank.servicemanagement.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webank.servicemanagement.domain.ServicePipeline;
import com.webank.servicemanagement.domain.ServiceRequestTemplate;
import com.webank.servicemanagement.dto.CreateServiceRequestTemplateRequest;
import com.webank.servicemanagement.jpa.ServicePipelineRepository;
import com.webank.servicemanagement.jpa.ServiceRequestTemplateRepository;

@Service
public class ServiceRequestTemplateService {

	@Autowired
	ServiceRequestTemplateRepository serviceRequestTemplateRepository;

	@Autowired
	ServicePipelineRepository servicePipelineRepository;

	private final static String AVAILABLE_STATU_STRING = "active";

	public List<ServiceRequestTemplate> getAllAvailableServiceRequestTemplate() {
		return serviceRequestTemplateRepository.findAllByStatus(AVAILABLE_STATU_STRING);
	}

	public List<ServiceRequestTemplate> getServiceRequestTemplateByPipelineId(int servicePipelineId) {
		return serviceRequestTemplateRepository.findAllByServicePipelineId(servicePipelineId);
	}

	public void createServiceRequestTemplate(CreateServiceRequestTemplateRequest createServiceRequestTemplateRequest)
			throws Exception {
		Optional<ServicePipeline> servicePipelineOptional = servicePipelineRepository
				.findById(createServiceRequestTemplateRequest.getServicePipelineId());
		if (!servicePipelineOptional.isPresent())
			throw new Exception(String.format("Service Pipeline ID [%d] does not exist",
					createServiceRequestTemplateRequest.getServicePipelineId()));

		ServiceRequestTemplate serviceRequestTemplate = new ServiceRequestTemplate(servicePipelineOptional.get(),
				createServiceRequestTemplateRequest.getName(), createServiceRequestTemplateRequest.getDescription(),
				createServiceRequestTemplateRequest.getProcessDefinitionKey(), AVAILABLE_STATU_STRING);

		serviceRequestTemplateRepository.save(serviceRequestTemplate);
	}

}
