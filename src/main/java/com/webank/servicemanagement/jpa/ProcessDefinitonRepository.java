package com.webank.servicemanagement.jpa;

import org.springframework.data.repository.CrudRepository;

import com.webank.servicemanagement.domain.ProcessDefinition;

public interface ProcessDefinitonRepository extends CrudRepository<ProcessDefinition, String> {
}
