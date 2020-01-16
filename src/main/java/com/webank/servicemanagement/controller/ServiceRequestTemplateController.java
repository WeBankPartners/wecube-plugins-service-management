package com.webank.servicemanagement.controller;

import static com.webank.servicemanagement.dto.JsonResponse.error;
import static com.webank.servicemanagement.dto.JsonResponse.okay;
import static com.webank.servicemanagement.dto.JsonResponse.okayWithData;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.webank.servicemanagement.dto.CreateServiceRequestTemplateRequest;
import com.webank.servicemanagement.dto.JsonResponse;
import com.webank.servicemanagement.service.ServiceRequestTemplateService;

@RestController
@RequestMapping("/v1/service-request-templates")
public class ServiceRequestTemplateController {

	@Autowired
	ServiceRequestTemplateService serviceRequestTemplateService;

	@PostMapping
	public JsonResponse createServiceRequestTemplate(
			@RequestBody CreateServiceRequestTemplateRequest createServiceRequestTemplateRequest) throws Exception {
		try {
			serviceRequestTemplateService.createServiceRequestTemplate(createServiceRequestTemplateRequest);
		} catch (Exception e) {
			return error(e.getMessage());
		}
		return okay();
	}

	@Deprecated
	@GetMapping("/available")
	public JsonResponse getAllAvailableServiceTemplate() {
		return okayWithData(serviceRequestTemplateService.getAllAvailableServiceRequestTemplate());
	}

	@GetMapping("/service-pipelines/{service-pipeline-id}")
	public JsonResponse getServiceRequestTemplateByPipelineId(
			@PathVariable(value = "service-pipeline-id") String servicePipelinId) {
		return okayWithData(serviceRequestTemplateService.getServiceRequestTemplateByPipelineId(servicePipelinId));
	}

}