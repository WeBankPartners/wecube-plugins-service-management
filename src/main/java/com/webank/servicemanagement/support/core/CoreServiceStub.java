package com.webank.servicemanagement.support.core;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webank.servicemanagement.commons.AppProperties.ServiceManagementProperties;
import com.webank.servicemanagement.dto.OperationEventResultDto;
import com.webank.servicemanagement.support.core.dto.CallbackRequestDto;
import com.webank.servicemanagement.support.core.dto.CoreProcessDefinitionDto;
import com.webank.servicemanagement.support.core.dto.CoreResponse.DefaultCoreResponse;
import com.webank.servicemanagement.support.core.dto.CoreResponse.GetAllProcessKeysResponse;
import com.webank.servicemanagement.support.core.dto.CoreResponse.GetAllRolesResponse;
import com.webank.servicemanagement.support.core.dto.CoreResponse.GetRootEntitiesResponse;
import com.webank.servicemanagement.support.core.dto.CoreResponse.OperationEventResultResponse;
import com.webank.servicemanagement.support.core.dto.ReportServiceRequest;
import com.webank.servicemanagement.support.core.dto.RolesDataResponse;

@Service
public class CoreServiceStub {
    private static final int NOT_INCLUDE_DRAFT = 0;

    private static final String GET_ALL_ROLES = "/auth/v1/roles";
    private static final String GET_ROLES_BY_USER_NAME = "/auth/v1/users/%s/roles";
    private static final String REPORT_OPERATION_EVENTS = "/platform/v1/operation-events";
    private static final String GET_ALL_PEOCESS_KEYS = "/platform/v1/process/definitions?includeDraft=%d";
    
    private static final String GET_ROOT_ENTITIES = "/platform/v1/process/definitions/process-keys/%s/root-entities";

    @Autowired
    private CoreRestTemplate template;

    @Autowired
    private ServiceManagementProperties smProperties;
    
    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> getRootEntitiesByProcDefKey(String procDefKey){
        Object responseData = template.get(asCoreUrl(GET_ROOT_ENTITIES, procDefKey), GetRootEntitiesResponse.class);
        return (List<Map<String, Object>>)responseData;
    }

    public List<RolesDataResponse> getAllRoles() {
        return template.get(asCoreUrl(GET_ALL_ROLES), GetAllRolesResponse.class);
    }

    public List<RolesDataResponse> getRolesByUserName(String userName) {
        return template.get(asCoreUrl(GET_ROLES_BY_USER_NAME, userName), GetAllRolesResponse.class);
    }

    public Object reportOperationEventsToCore(ReportServiceRequest reportServiceRequest) {
        Object result = template.postForResponse(asCoreUrl(REPORT_OPERATION_EVENTS), reportServiceRequest, OperationEventResultResponse.class);
        return result;
    }

    public Object callback(String callbackUrl, CallbackRequestDto callbackRequest) {
        return template.postForResponse(asCoreUrl(callbackUrl), callbackRequest, DefaultCoreResponse.class);
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
