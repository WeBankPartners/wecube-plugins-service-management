package com.webank.servicemanagement.jpa;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.webank.servicemanagement.domain.ServicePipeline;

public interface ServicePipelineRepository extends CrudRepository<ServicePipeline, Integer> {
	List<ServicePipeline> findAllByStatus(String status);

	List<ServicePipeline> findAllByServiceCatalogueIdAndStatus(int serviceCatalogueId,String status);

	List<ServicePipeline> findAllByServiceCatalogueIdAndName(int serviceCatalogueId, String name);
}
