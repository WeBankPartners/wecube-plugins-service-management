package com.webank.servicemanagement.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webank.servicemanagement.domain.ServiceCatalogue;
import com.webank.servicemanagement.domain.ServicePipeline;
import com.webank.servicemanagement.dto.CreateServicePipelineRequest;
import com.webank.servicemanagement.jpa.ServiceCatalogueRepository;
import com.webank.servicemanagement.jpa.ServicePipelineRepository;

@Service
public class ServicePipelineService {

	@Autowired
	ServicePipelineRepository servicePipelineRepository;
	@Autowired
	ServiceCatalogueRepository ServiceCatalogueRepository;

	private final static String AVAILABLE_STATU_STRING = "active";

	public List<ServicePipeline> getServicePipelineByCatalogueId(int serviceCatalogueId) {
		return servicePipelineRepository.findAllByServiceCatalogueIdAndStatus(serviceCatalogueId,
				AVAILABLE_STATU_STRING);
	}

	public void createServicePipeline(CreateServicePipelineRequest createServicePipelineRequest) throws Exception {

		Optional<ServiceCatalogue> serviceCatalogue = ServiceCatalogueRepository
				.findById(createServicePipelineRequest.getServiceCatalogueId());

		if (!serviceCatalogue.isPresent())
			throw new Exception(String.format("Service catalogue ID [%d] does not exist",
					createServicePipelineRequest.getServiceCatalogueId()));

		if (servicePipelineRepository
				.findAllByServiceCatalogueIdAndName(createServicePipelineRequest.getServiceCatalogueId(),
						createServicePipelineRequest.getName())
				.size() > 0)
			throw new Exception(
					String.format("Service pipeline [%s] already exists", createServicePipelineRequest.getName()));

		ServicePipeline servicePipeline = new ServicePipeline(serviceCatalogue.get(),
				createServicePipelineRequest.getName(), createServicePipelineRequest.getDescription(),
				createServicePipelineRequest.getOwnerRoleId(), AVAILABLE_STATU_STRING);
		servicePipelineRepository.save(servicePipeline);
	}

}
