package com.webank.servicemanagement.jpa;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.webank.servicemanagement.domain.ServiceRequestTemplate;

public interface ServiceRequestTemplateRepository extends CrudRepository<ServiceRequestTemplate, Integer> {
//	@Query("SELECT template FROM request_template template WHERE template.status=:status")
	List<ServiceRequestTemplate> findAllByStatus(String status);
}
