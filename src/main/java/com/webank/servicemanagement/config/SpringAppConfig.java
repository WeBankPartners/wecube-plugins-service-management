package com.webank.servicemanagement.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.webank.servicemanagement.commons.AppProperties;

@Configuration
@EnableConfigurationProperties({ AppProperties.class })
@ComponentScan({ "com.webank.servicemanagement.service" })
public class SpringAppConfig {

}
