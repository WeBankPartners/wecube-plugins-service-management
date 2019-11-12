package com.webank.servicemanagement.controller;

import static com.webank.servicemanagement.dto.JsonResponse.error;
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
@RequestMapping("/v1/service-catalogues")
public class ServiceCatalogueController {

	@Autowired
	ServiceCatalogueService serviceCatalogueService;

	@PostMapping
	public JsonResponse createServiceCatalogue(@RequestBody CreateServiceCatalogueRequest createServiceCatalogueRequest)
			throws Exception {
		try {
		serviceCatalogueService.createServiceCatalogue(createServiceCatalogueRequest);
		}catch (Exception e) {
			return error(e.getMessage());
		}
		return okay();
	}

	@GetMapping("/available")
	public JsonResponse getAllAvailableServiceCatalogues() {
		return okayWithData(serviceCatalogueService.getAllAvailableServiceCatalogue());
	}

}