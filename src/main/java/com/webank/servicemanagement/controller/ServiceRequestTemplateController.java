package com.webank.servicemanagement.controller;

import static com.webank.servicemanagement.dto.JsonResponse.okayWithData;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.webank.servicemanagement.dto.JsonResponse;
import com.webank.servicemanagement.service.ServiceRequestTemplateService;

@RestController
@RequestMapping("/service-request-templates")
public class ServiceRequestTemplateController {

	@Autowired
	ServiceRequestTemplateService serviceRequestTemplateService;

//	@PostMapping("/create")
	public JsonResponse create() {
		return okayWithData("TODO");
	}

//	@GetMapping("/retrieve")
	public JsonResponse getAllServiceRequestTemplate() {
		return okayWithData(serviceRequestTemplateService.getAllServiceRequestTemplate());
	}

	@GetMapping("/available")
	public JsonResponse getAllAvailableServiceTemplate() {
		return okayWithData(serviceRequestTemplateService.getAllAvailableServiceRequestTemplate());
	}
}
