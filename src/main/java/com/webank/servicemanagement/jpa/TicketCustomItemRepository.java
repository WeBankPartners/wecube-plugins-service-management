package com.webank.servicemanagement.jpa;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.webank.servicemanagement.domain.FormCustomItem;

public interface TicketCustomItemRepository extends CrudRepository<FormCustomItem, String> {

    List<FormCustomItem> findAllByServiceFormId(String serviceFormId);

}
