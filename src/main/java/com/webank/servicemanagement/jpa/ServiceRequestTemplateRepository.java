package com.webank.servicemanagement.jpa;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.webank.servicemanagement.domain.ServiceRequestTemplate;

public interface ServiceRequestTemplateRepository extends CrudRepository<ServiceRequestTemplate, Integer> {
	List<ServiceRequestTemplate> findAllByStatus(String status);

	List<ServiceRequestTemplate> findAllByServicePipelineId(int servicePipelineId);
}
