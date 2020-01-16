package com.webank.servicemanagement.controller;

import static com.webank.servicemanagement.dto.JsonResponse.okayWithData;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.webank.servicemanagement.commons.AuthenticationContextHolder;
import com.webank.servicemanagement.dto.JsonResponse;
import com.webank.servicemanagement.support.core.CoreServiceStub;

@RestController
@RequestMapping("/v1/core-resources")
public class CoreResourceController {
    @Autowired
    CoreServiceStub coreServiceStub;

    @GetMapping("/users/current-user/roles")
    public JsonResponse getRolesByCurrentUser(HttpServletRequest httpRequest) {
        String currentUserName = AuthenticationContextHolder.getCurrentUsername();
        return okayWithData(coreServiceStub.getRolesByUserName(currentUserName));
    }

    @GetMapping("/roles")
    public JsonResponse getAllRoles() throws JsonParseException, JsonMappingException, IOException {
        return okayWithData(coreServiceStub.getAllRoles());
    }

    @GetMapping("/workflow/process-definition-keys")
    public JsonResponse getAllProcessDefinitionKeys() {
        return okayWithData(coreServiceStub.getAllProcessDefinitionKeys());
    }
}
