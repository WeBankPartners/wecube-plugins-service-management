package com.webank.servicemanagement.jpa;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.webank.servicemanagement.domain.ProcessInstance;

public interface ProcessInstanceRepository extends CrudRepository<ProcessInstance, String> {
    List<ProcessInstance> findAllByStatus(ProcessInstance.Status inProgress);
    
}
