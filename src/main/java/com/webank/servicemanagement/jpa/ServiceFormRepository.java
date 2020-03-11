package com.webank.servicemanagement.jpa;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.webank.servicemanagement.domain.ServiceForm;

public interface ServiceFormRepository extends CrudRepository<ServiceForm, String> {
	List<ServiceForm> findAllByStatus(String status);

	List<ServiceForm> findAllByServicePipelineId(String servicePipelineId);
}
