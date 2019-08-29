package com.webank.servicemanagement.commons;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@Data
@ConfigurationProperties(prefix = "service.management")
public class AppProperties {

    @Data
    @ConfigurationProperties(prefix = "service.management.httpclient")
    public class HttpClientProperties {
        private int connectTimeout = 30000;
        private int requestTimeout = 30000;
        private int socketTimeout = 60000;
        private int maxTotalConnections = 50;
        private int poolSizeOfScheduler = 50;
        private int defaultKeepAliveTimeMillis = 20000;
        private int closeIdleConnectionWaitTimeSecs = 30;
    }

}
