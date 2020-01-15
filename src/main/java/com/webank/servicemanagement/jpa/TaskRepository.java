package com.webank.servicemanagement.jpa;

import org.springframework.data.repository.CrudRepository;

import com.webank.servicemanagement.domain.Task;

public interface TaskRepository extends CrudRepository<Task, String> {
}
