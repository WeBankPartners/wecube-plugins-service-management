package com.webank.servicemanagement.jpa;

import org.springframework.data.repository.CrudRepository;

import com.webank.servicemanagement.domain.ServiceTicket;

public interface ServiceTicketRepository extends CrudRepository<ServiceTicket, String> {
}
