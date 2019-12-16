package com.webank.servicemanagement.interceptor;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import com.webank.servicemanagement.commons.AppProperties.ServiceManagementProperties;

@Component
public class RestTemplateInterceptor implements ClientHttpRequestInterceptor {
    @Autowired
    private ServiceManagementProperties smProperties;

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
            throws IOException {
        HttpHeaders headers = request.getHeaders();
        headers.add("Authorization", smProperties.getWecubePlatformToken());
        return execution.execute(request, body);
    }
}
