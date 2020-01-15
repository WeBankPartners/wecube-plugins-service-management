package com.webank.servicemanagement.jpa;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.webank.servicemanagement.domain.ServiceCatalogue;

public interface ServiceCatalogueRepository extends CrudRepository<ServiceCatalogue, String> {
	List<ServiceCatalogue> findAllByStatus(String status);

	List<ServiceCatalogue> findAllByName(String name);
}
