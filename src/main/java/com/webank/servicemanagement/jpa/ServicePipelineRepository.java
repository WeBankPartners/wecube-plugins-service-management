package com.webank.servicemanagement.jpa;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.webank.servicemanagement.domain.ServicePipeline;

public interface ServicePipelineRepository extends CrudRepository<ServicePipeline, String> {
	List<ServicePipeline> findAllByStatus(String status);

	List<ServicePipeline> findAllByServiceCatalogueIdAndStatus(String serviceCatalogueId,String status);

	List<ServicePipeline> findAllByServiceCatalogueIdAndName(String serviceCatalogueId, String name);
}
