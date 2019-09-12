package com.webank.servicemanagement.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.webank.servicemanagement.commons.AppProperties;
import com.webank.servicemanagement.commons.AppProperties.HttpClientProperties;
import com.webank.servicemanagement.commons.AppProperties.ServiceManagementProperties;

@Configuration
@EnableConfigurationProperties({ AppProperties.class, HttpClientProperties.class, ServiceManagementProperties.class })
@ComponentScan({ "com.webank.servicemanagement.service" })
public class SpringAppConfig {

}
