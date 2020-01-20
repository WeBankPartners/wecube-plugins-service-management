package com.webank.servicemanagement.jpa;

import org.springframework.data.repository.CrudRepository;

import com.webank.servicemanagement.domain.ProcessNodeDefinition;

public interface ProcessNodeDefinitonRepository extends CrudRepository<ProcessNodeDefinition, String> {
}
