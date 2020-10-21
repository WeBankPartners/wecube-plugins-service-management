package com.webank.servicemanagement.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.webank.servicemanagement.domain.ServiceRequestTemplate;

public interface ServiceRequestTemplateRepository extends JpaRepository<ServiceRequestTemplate, String> {
	List<ServiceRequestTemplate> findAllByStatus(String status);

	List<ServiceRequestTemplate> findAllByServicePipelineId(String servicePipelineId);
}
