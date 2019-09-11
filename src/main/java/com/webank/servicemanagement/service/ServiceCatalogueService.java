package com.webank.servicemanagement.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webank.servicemanagement.domain.ServiceCatalogue;
import com.webank.servicemanagement.dto.CreateServiceCatalogueRequest;
import com.webank.servicemanagement.jpa.ServiceCatalogueRepository;

@Service
public class ServiceCatalogueService {

	@Autowired
	ServiceCatalogueRepository serviceCatalogueRepository;

	private final static String AVAILABLE_STATU_STRING = "active";

	public List<ServiceCatalogue> getAllAvailableServiceCatalogue() {
		return serviceCatalogueRepository.findAllByStatus(AVAILABLE_STATU_STRING);
	}

	public void createServiceCatalogue(CreateServiceCatalogueRequest createServiceCatalogueRequest) throws Exception {
		if (serviceCatalogueRepository.findAllByName(createServiceCatalogueRequest.getName()).size() > 0)
			throw new Exception(
					String.format("Service catalogue [%s] already exists", createServiceCatalogueRequest.getName()));

		ServiceCatalogue serviceCatalogue = new ServiceCatalogue(createServiceCatalogueRequest.getName(),
				createServiceCatalogueRequest.getDescription(), AVAILABLE_STATU_STRING);
		serviceCatalogueRepository.save(serviceCatalogue);
	}

}
