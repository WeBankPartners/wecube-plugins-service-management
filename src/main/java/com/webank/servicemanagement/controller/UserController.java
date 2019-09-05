package com.webank.servicemanagement.controller;

import static com.webank.servicemanagement.dto.JsonResponse.okayWithData;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.webank.servicemanagement.commons.AppProperties;
import com.webank.servicemanagement.dto.JsonResponse;
import com.webank.servicemanagement.jpa.EntityRepository;
import com.webank.servicemanagement.mock.MockCoreServiceStub;
import com.webank.servicemanagement.service.ServiceRequestService;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	ServiceRequestService serviceRequestService;
	@Autowired
	EntityRepository entityRepository;
	@Autowired
	AppProperties appProperties;
	@Autowired
	MockCoreServiceStub coreServiceStub;

	@GetMapping("/{user-name}/roles")
	public JsonResponse createServiceRequest(@PathVariable(value = "user-name") String userName) {
		return okayWithData(coreServiceStub.getRolesByUserName(userName));
	}

}
