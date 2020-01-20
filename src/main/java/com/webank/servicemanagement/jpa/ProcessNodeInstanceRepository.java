package com.webank.servicemanagement.jpa;

import org.springframework.data.repository.CrudRepository;

import com.webank.servicemanagement.domain.ProcessNodeInstance;

public interface ProcessNodeInstanceRepository extends CrudRepository<ProcessNodeInstance, String> {
}
