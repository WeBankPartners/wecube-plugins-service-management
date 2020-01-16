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

import com.webank.servicemanagement.dto.CreateServicePipelineRequest;
import com.webank.servicemanagement.dto.JsonResponse;
import com.webank.servicemanagement.service.ServicePipelineService;

@RestController
@RequestMapping("/v1/service-pipelines")
public class ServicePipelineController {

	@Autowired
	ServicePipelineService servicePipelineService;

	@PostMapping
	public JsonResponse createServicePipeline(@RequestBody CreateServicePipelineRequest createServicePipelineRequest)
			throws Exception {
		try {
			servicePipelineService.createServicePipeline(createServicePipelineRequest);
		} catch (Exception e) {
			return error(e.getMessage());
		}
		return okay();
	}

	@GetMapping("/service-catalogues/{service-catalogue-id}")
	public JsonResponse getServicePipelineByCatalogueId(
			@PathVariable(value = "service-catalogue-id") String serviceCatalogueId) {
		return okayWithData(servicePipelineService.getServicePipelineByCatalogueId(serviceCatalogueId));
	}

}