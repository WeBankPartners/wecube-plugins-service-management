package com.webank.servicemanagement.job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.webank.servicemanagement.service.ProcessorService;

@Component
public class ProcessJobsHandler {

    private static final Logger log = LoggerFactory.getLogger(ProcessJobsHandler.class);

    @Autowired
    private ProcessorService processorService;

    @Scheduled(cron = "*/30 * * * * ?")
    public void extractOutstandingOperationEvents() {
        if (log.isInfoEnabled()) {
            log.info("scheduled execution start...");
        }

        try {
            processorService.run();
        } catch (Exception e) {
            log.error("Process jobs processing errors", e);
        }

        if (log.isInfoEnabled()) {
            log.info("scheduled execution end...");
        }
    }

}
