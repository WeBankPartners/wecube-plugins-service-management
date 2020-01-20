package com.webank.servicemanagement.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.webank.servicemanagement.jpa.EntityRepository;
import com.webank.servicemanagement.jpa.ProcessInstanceRepository;
import com.webank.servicemanagement.domain.ProcessInstance;

@Service
public class ProcessorService {
    @Autowired
    ProcessInstanceRepository processInstanceRepository;
    @Autowired
    EntityRepository entityRepository;

    private static final Logger log = LoggerFactory.getLogger(ProcessorService.class);

    public void run() {
        List<ProcessInstance> instances = processInstanceRepository.findAllByStatus(ProcessInstance.Status.IN_PROGRESS);
    }
}
