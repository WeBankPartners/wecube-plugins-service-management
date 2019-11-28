package com.webank.servicemanagement.support.core;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.webank.servicemanagement.support.core.dto.ProcessDefinitionKeyDataResponse;
import com.webank.servicemanagement.support.core.dto.RolesDataResponse;
import com.webank.servicemanagement.support.core.dto.StartWorkflowInstanceRequest;

import net.sf.json.JSONArray;

@Service
public class MockCoreServiceStub {
	private static final Logger log = LoggerFactory.getLogger(MockCoreServiceStub.class);

	public List<RolesDataResponse> getAllRoles() throws JsonParseException, JsonMappingException, IOException {
		log.info("[Mock] send request [getAllRoles] to wecube_core...");
		String mockDataString = "[\r\n" + "    {\r\n" + "      \"roleId\": 1,\r\n"
				+ "      \"roleName\": \"SUPER_ADMIN\",\r\n" + "      \"roleType\": \"ADMIN\",\r\n"
				+ "      \"description\": \"超级管理员\"\r\n" + "    },\r\n" + "    {\r\n" + "      \"roleId\": 2,\r\n"
				+ "      \"roleName\": \"CMDB_ADMIN\",\r\n" + "      \"roleType\": \"ADMIN\",\r\n"
				+ "      \"description\": \"CMDB管理员\"\r\n" + "    },\r\n" + "    {\r\n" + "      \"roleId\": 3,\r\n"
				+ "      \"roleName\": \"PLUGIN_ADMIN\",\r\n" + "      \"roleType\": \"ADMIN\",\r\n"
				+ "      \"description\": \"插件管理员\"\r\n" + "    },\r\n" + "    {\r\n" + "      \"roleId\": 5,\r\n"
				+ "      \"roleName\": \"NETWORK_ARCHITECT\",\r\n" + "      \"roleType\": \"ADMIN\",\r\n"
				+ "      \"description\": \"基础架构规划-网络\"\r\n" + "    },\r\n" + "    {\r\n" + "      \"roleId\": 6,\r\n"
				+ "      \"roleName\": \"APP_ARCHITECT\",\r\n" + "      \"roleType\": \"ADMIN\",\r\n"
				+ "      \"description\": \"应用架构师\"\r\n" + "    },\r\n" + "    {\r\n" + "      \"roleId\": 8,\r\n"
				+ "      \"roleName\": \"OPS-TEST\",\r\n" + "      \"roleType\": \"ADMIN\",\r\n"
				+ "      \"description\": \"测试环境运维\"\r\n" + "    },\r\n" + "    {\r\n" + "      \"roleId\": 9,\r\n"
				+ "      \"roleName\": \"DEVELOPER\",\r\n" + "      \"roleType\": \"ADMIN\",\r\n"
				+ "      \"description\": \"开发人员\"\r\n" + "    },\r\n" + "    {\r\n" + "      \"roleId\": 10,\r\n"
				+ "      \"roleName\": \"REGULAR\",\r\n" + "      \"roleType\": \"REGULAR\",\r\n"
				+ "      \"description\": \"普通用户\"\r\n" + "    },\r\n" + "    {\r\n" + "      \"roleId\": 11,\r\n"
				+ "      \"roleName\": \"READONLY\",\r\n" + "      \"roleType\": \"READONLY\",\r\n"
				+ "      \"description\": \"只读用户\"\r\n" + "    },\r\n" + "    {\r\n" + "      \"roleId\": 12,\r\n"
				+ "      \"roleName\": \"test3\",\r\n" + "      \"roleType\": \"admin\",\r\n"
				+ "      \"description\": \"test3\"\r\n" + "    }\r\n" + "  ]";

		JSONArray json = JSONArray.fromObject(mockDataString);

		@SuppressWarnings("unchecked")
		List<RolesDataResponse> mockData = (List<RolesDataResponse>) JSONArray.toCollection(json,
				RolesDataResponse.class);

		return mockData;
	}

	public List<RolesDataResponse> getRolesByUserName(String userName) {
		log.info("[Mock] send request [getRolesByUserName] with [{}] to wecube_core...", userName);

		String mockDataString = "[{\"roleId\": 1,\"roleName\": \"SUPER_ADMIN\",\"roleType\":\"ADMIN\",\"description\":\"超级管理员\"},{\"roleId\": 99,\"roleName\": \"TEST_ADMIN\",\"roleType\":\"ADMIN\",\"description\":\"测试管理员\"}]";
		JSONArray json = JSONArray.fromObject(mockDataString);

		@SuppressWarnings("unchecked")
		List<RolesDataResponse> mockData = (List<RolesDataResponse>) JSONArray.toCollection(json,
				RolesDataResponse.class);

		return mockData;
	}

	public String startWorkflowInstanceByProcessDefinitionId(
			StartWorkflowInstanceRequest startWorkflowInstanceRequest) {
		log.info("[Mock] send request [startWorkflowInstanceByProcessDefinitionKey] with [{}] to wecube_core...",
				startWorkflowInstanceRequest.toString());

		String mockDataString = "ThisIsAMockProcessInstanceId";

		return mockDataString;
	}

	public List<String> getAllProcessDefinitionKeys() {
		log.info("[Mock] send request [getAllProcessDefinitionKeys] to wecube_core...");

		String mockDataString = "[{\"processDefinitionKey\": \"key001\"},{\"processDefinitionKey\": \"key002\"}]";
		JSONArray json = JSONArray.fromObject(mockDataString);

		@SuppressWarnings("unchecked")
		List<String> mockData = (List<String>) JSONArray.toCollection(json, ProcessDefinitionKeyDataResponse.class);

		return mockData;
	}

}
