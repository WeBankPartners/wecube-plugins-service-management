package com.webank.servicemanagement.support.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.webank.servicemanagement.dto.JsonResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class CoreRestTemplate {

	@Autowired
	private RestTemplate restTemplate;

	@SuppressWarnings("unchecked")
	public <D, R extends JsonResponse> D get(String targetUrl, Class<R> responseType) {
		log.info("About to call {} ", targetUrl);
		R jsonResponse = restTemplate.getForObject(targetUrl, responseType);
		log.info("Core response: {} ", jsonResponse);
		validateJsonResponse(jsonResponse);
		return (D) jsonResponse.getData();
	}

	public <D, R extends JsonResponse> D postForResponse(String targetUrl, Class<R> responseType) {
		return postForResponse(targetUrl, null, responseType);
	}

	@SuppressWarnings("unchecked")
	public <D, R extends JsonResponse> D postForResponse(String targetUrl, Object postObject, Class<R> responseType) {
		log.info("About to POST {} with postObject {}", targetUrl, postObject);
		R jsonResponse = restTemplate.postForObject(targetUrl, postObject, responseType);
		log.info("Core response: {} ", jsonResponse);
		validateJsonResponse(jsonResponse);
		return (D) jsonResponse.getData();
	}

	private void validateJsonResponse(JsonResponse jsonResponse) {
		validateJsonResponse(jsonResponse, true);
	}

	private void validateJsonResponse(JsonResponse jsonResponse, boolean dataRequired) {
		if (jsonResponse == null) {
			throw new CoreRemoteCallException("Call WeCube-Core failed due to no response.");
		}
		if (!JsonResponse.STATUS_OK.equalsIgnoreCase(jsonResponse.getStatus())) {
			throw new CoreRemoteCallException("Core Error: " + jsonResponse.getMessage(), jsonResponse);
		}
		if (dataRequired && jsonResponse.getData() == null) {
			throw new CoreRemoteCallException("Call WeCube-Core failed due to unexpected empty response.",
					jsonResponse);
		}
	}

}
