package com.webank.servicemanagement.support.core;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webank.servicemanagement.commons.AppProperties.ServiceManagementProperties;
import com.webank.servicemanagement.support.core.dto.CoreProcessDefinitionDto;
import com.webank.servicemanagement.support.core.dto.CoreResponse.GetAllProcessKeysResponse;
import com.webank.servicemanagement.support.core.dto.CoreResponse.GetAllRolesResponse;
import com.webank.servicemanagement.support.core.dto.CoreResponse.StringCoreResponse;

import com.webank.servicemanagement.support.core.dto.RolesDataResponse;
import com.webank.servicemanagement.support.core.dto.StartWorkflowInstanceRequest;

@Service
public class CoreServiceStub {
    private static final Logger log = LoggerFactory.getLogger(CoreServiceStub.class);
    private static final int NOT_INCLUDE_DRAFT = 0;

    private static final String GET_ALL_ROLES = "/admin/roles";
    private static final String GET_ROLES_BY_USER_NAME = "/admin/users/%s/roles";
    private static final String START_WORKFLOW_INSTANCE = "/process-definition-keys/%s/start";
    private static final String GET_ALL_PEOCESS_KEYS = "/v1/process/definitions?includeDraft=%d";

    @Autowired
    private CoreRestTemplate template;

    @Autowired
    private ServiceManagementProperties smProperties;

    public List<RolesDataResponse> getAllRoles() {
        return template.get(asCoreUrl(GET_ALL_ROLES), GetAllRolesResponse.class);
    }

    public List<RolesDataResponse> getRolesByUserName(String userName) {
        return template.get(asCoreUrl(GET_ROLES_BY_USER_NAME, userName), GetAllRolesResponse.class);
    }

    public String startWorkflowInstanceByProcessDefinitionId(
            StartWorkflowInstanceRequest startWorkflowInstanceRequest) {
        return template.get(asCoreUrl(START_WORKFLOW_INSTANCE, startWorkflowInstanceRequest.getProcessDefinitionId()),
                StringCoreResponse.class);
    }

    private String asCoreUrl(String path, Object... pathVariables) {
        if (pathVariables != null && pathVariables.length > 0) {
            path = String.format(path, pathVariables);
        }
        return smProperties.getWecubeCoreAddress() + path;
    }

    public List<CoreProcessDefinitionDto> getAllProcessDefinitionKeys() {
        return template.get(asCoreUrl(GET_ALL_PEOCESS_KEYS, NOT_INCLUDE_DRAFT), GetAllProcessKeysResponse.class);
    }

}
