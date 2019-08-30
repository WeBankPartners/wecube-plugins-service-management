package com.webank.servicemanagement.controller;

import static com.webank.servicemanagement.dto.JsonResponse.okayWithData;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.webank.servicemanagement.domain.ServiceRequest;
import com.webank.servicemanagement.dto.CreateServiceRequestRequest;
import com.webank.servicemanagement.dto.CreateServiceRequestResponse;
import com.webank.servicemanagement.dto.JsonResponse;
import com.webank.servicemanagement.dto.QueryRequest;
import com.webank.servicemanagement.jpa.EntityRepository;
import com.webank.servicemanagement.service.ServiceRequestService;

@RestController
@RequestMapping("/service-request")
public class ServiceRequestController {

	@Autowired
	ServiceRequestService serviceRequestService;

	@Autowired
	EntityRepository entityRepository;

	@PostMapping("/create")
	public JsonResponse createServiceRequest(@RequestBody CreateServiceRequestRequest rquest) {
		CreateServiceRequestResponse response = new CreateServiceRequestResponse(1, 1, "service001", "reportor",
				"report_time", "normal", "description", "attach_file", null, "process_instance_id", "Created");
		return okayWithData(response);
	}

	@GetMapping("/retrieve")
	public JsonResponse getAllServiceRequest() {
		return okayWithData(serviceRequestService.getAllServiceRequest());
	}

	@PostMapping("/query")
	public JsonResponse queryServiceRequest(@RequestBody QueryRequest queryRequest) throws Exception {
		return okayWithData(entityRepository.query(ServiceRequest.class, queryRequest));
	}
}
