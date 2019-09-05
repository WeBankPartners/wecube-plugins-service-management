package com.webank.servicemanagement.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.greaterThan;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.io.IOException;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.webank.servicemanagement.mock.MockCoreServiceStub;
import com.webank.servicemanagement.support.core.dto.RolesDataResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ServiceRequestControllerTest extends AbstractControllerTest {

	@Autowired
	MockCoreServiceStub coreServiceStub;

	@Test
	public void createServiceRequestTest() throws Exception {
		mvc.perform(post("/service-requests/create").contentType(MediaType.APPLICATION_JSON)
				.content("{\r\n" + "  \"description\": \"desc-test\",\r\n" + "  \"emergency\": \"emergency-test\",\r\n"
						+ "  \"name\": \"name-createServiceRequestTest\",\r\n"
						+ "  \"reporter\": \"reporter-test\",\r\n" + "  \"templateId\": 1\r\n" + "}"))
				.andExpect(jsonPath("$.status", is("OK")));

		mvc.perform(get("/service-requests/retrieve").contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.status", is("OK"))).andExpect(jsonPath("$.data.length()", greaterThan(0)))
				.andExpect(jsonPath("$.data[*].name", contains("name-createServiceRequestTest")));
	}

	@Test
	public void TestMockCoreService() throws JsonParseException, JsonMappingException, IOException {
		List<RolesDataResponse> returnDataResponses = coreServiceStub.getAllRoles();
		log.info("return = {}", returnDataResponses);
	}

}
