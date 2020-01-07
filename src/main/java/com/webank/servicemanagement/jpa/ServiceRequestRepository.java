package com.webank.servicemanagement.jpa;

import org.springframework.data.repository.CrudRepository;

import com.webank.servicemanagement.domain.ServiceRequest;

public interface ServiceRequestRepository extends CrudRepository<ServiceRequest, String> {
}
