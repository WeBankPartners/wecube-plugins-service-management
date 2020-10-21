package com.webank.servicemanagement.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.webank.servicemanagement.domain.Task;

public interface TaskRepository extends JpaRepository<Task, String> {
    List<Task> findByOperatorRoleIn(List<String> operatorRoles);
    
    @Query("select t from Task t where t.serviceRequest.id = :serviceRequestId")
    List<Task> findAllByServiceRequestId(@Param("serviceRequestId") String serviceRequestId);

}
