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

import com.webank.servicemanagement.dto.CreateServiceFormRequest;
import com.webank.servicemanagement.dto.JsonResponse;
import com.webank.servicemanagement.service.ServiceFormService;

@RestController
@RequestMapping("/v1/service-forms")
public class ServiceFormController {

	@Autowired
	ServiceFormService serviceFormService;

	@PostMapping
	public JsonResponse createServiceForm(
			@RequestBody CreateServiceFormRequest createServiceFormRequest) throws Exception {
		try {
			serviceFormService.createServiceForm(createServiceFormRequest);
		} catch (Exception e) {
			return error(e.getMessage());
		}
		return okay();
	}

	@GetMapping("/available")
	public JsonResponse getAllAvailableServiceForms() {
		return okayWithData(serviceFormService.getAllAvailableServiceForms());
	}

	@GetMapping("/service-pipelines/{service-pipeline-id}")
	public JsonResponse getServiceFormsByPipelineId(
			@PathVariable(value = "service-pipeline-id") String servicePipelinId) {
		return okayWithData(serviceFormService.getServiceFormsByPipelineId(servicePipelinId));
	}

}