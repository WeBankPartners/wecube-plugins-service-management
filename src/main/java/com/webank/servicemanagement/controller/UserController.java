package com.webank.servicemanagement.controller;

import static com.webank.servicemanagement.dto.JsonResponse.okayWithData;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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

	@GetMapping("/current-user/roles")
	public JsonResponse getRolesByCurrentUser(HttpServletRequest httpRequest) {
		return okayWithData(coreServiceStub.getRolesByUserName(httpRequest.getHeader("Current_User")));
	}

}
