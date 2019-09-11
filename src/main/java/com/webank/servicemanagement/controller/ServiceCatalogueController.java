package com.webank.servicemanagement.controller;

import static com.webank.servicemanagement.dto.JsonResponse.okay;
import static com.webank.servicemanagement.dto.JsonResponse.okayWithData;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.webank.servicemanagement.dto.CreateServiceCatalogueRequest;
import com.webank.servicemanagement.dto.JsonResponse;
import com.webank.servicemanagement.service.ServiceCatalogueService;

@RestController
@RequestMapping("/service-management/service-catalogues")
public class ServiceCatalogueController {

	@Autowired
	ServiceCatalogueService serviceCatalogueService;

	@PostMapping
	public JsonResponse createServiceCatalogue(@RequestBody CreateServiceCatalogueRequest createServiceCatalogueRequest)
			throws Exception {
		serviceCatalogueService.createServiceCatalogue(createServiceCatalogueRequest);
		return okay();
	}

	@GetMapping("/available")
	public JsonResponse getAllAvailableServiceCatalogues() {
		return okayWithData(serviceCatalogueService.getAllAvailableServiceCatalogue());
	}

}