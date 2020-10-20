package com.webank.servicemanagement.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.webank.servicemanagement.domain.ServiceRequest;

public interface ServiceRequestRepository extends JpaRepository<ServiceRequest, String> {

    @Query("select t from ServiceRequest t where t.procInstId = :procInstId")
    List<ServiceRequest> findAllByProcInstId(@Param("procInstId") String procInstId);
}
