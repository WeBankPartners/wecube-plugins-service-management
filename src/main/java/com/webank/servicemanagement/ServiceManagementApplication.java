package com.webank.servicemanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import com.webank.servicemanagement.config.SpringAppConfig;
import com.webank.servicemanagement.config.SpringWebConfig;

@SpringBootApplication
public class ServiceManagementApplication extends AbstractAnnotationConfigDispatcherServletInitializer{

	public static void main(String[] args) {
		SpringApplication.run(ServiceManagementApplication.class, args);
	}
	
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[] { SpringAppConfig.class };
    }

	@Override
	protected Class<?>[] getServletConfigClasses() {
        return new Class[] { SpringWebConfig.class };
	}

	@Override
	protected String[] getServletMappings() {
		return new String[] {"/" };
	}

}
