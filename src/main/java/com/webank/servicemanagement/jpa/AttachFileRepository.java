package com.webank.servicemanagement.jpa;

import org.springframework.data.repository.CrudRepository;

import com.webank.servicemanagement.domain.AttachFile;

public interface AttachFileRepository extends CrudRepository<AttachFile, String> {
}
