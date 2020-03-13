package com.webank.servicemanagement.controller;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.greaterThan;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.junit.Test;
import org.springframework.http.MediaType;

public class ServiceFormControllerTest extends AbstractControllerTest {

	@Test
	public void createServiceRequestTemplateTest() throws Exception {
		mvc.perform(
				post("/v1/service-request-templates").contentType(MediaType.APPLICATION_JSON).content(
						"{\"name\":\"test-service-templates\",\"description\": \"test-description\",\"servicePipelineId\":999,\"processDefinitionId\":999}"))
				.andExpect(jsonPath("$.status", is("OK")));

		mvc.perform(
				get("/v1/service-request-templates/available").contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.status", is("OK"))).andExpect(jsonPath("$.data.length()", greaterThan(0)))
				.andExpect(jsonPath("$.data[*].name", hasItem("test-service-templates")));
	}

	@Test
	public void createServiceRequestTemplateWhenCatalogueIdAndNameAlreadyExistTest() throws Exception {
		mvc.perform(
				post("/v1/service-request-templates").contentType(MediaType.APPLICATION_JSON).content(
						"{\"name\":\"test-service-templates\",\"description\": \"test-description\",\"servicePipelineId\":111,\"processDefinitionId\":999}"))
				.andExpect(jsonPath("$.status", is("ERROR")));
	}

}
