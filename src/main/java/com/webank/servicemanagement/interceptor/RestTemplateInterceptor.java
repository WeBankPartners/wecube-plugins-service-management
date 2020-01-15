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
import com.webank.servicemanagement.commons.AuthenticationContextHolder;

@Component
public class RestTemplateInterceptor implements ClientHttpRequestInterceptor {
    @Autowired
    private ServiceManagementProperties smProperties;

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
            throws IOException {
        HttpHeaders headers = request.getHeaders();
        if (AuthenticationContextHolder.getCurrentUser() != null
                && !AuthenticationContextHolder.getCurrentUser().getToken().isEmpty()) {
            headers.add("Authorization", AuthenticationContextHolder.getCurrentUser().getToken());
        }
        return execution.execute(request, body);
    }
}
