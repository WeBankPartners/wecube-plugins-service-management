package com.webank.servicemanagement.controller;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.greaterThan;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.junit.Test;
import org.springframework.http.MediaType;

public class ServicePipelineControllerTest extends AbstractControllerTest {

	@Test
	public void createServicePipelineTest() throws Exception {
		mvc.perform(post("/service-management/service-pipelines").contentType(MediaType.APPLICATION_JSON).content(
				"{\"name\":\"test-service-pipeline\",\"description\": \"test-description\",\"serviceCatalogueId\":999,\"ownerRoleId\":999}"))
				.andExpect(jsonPath("$.status", is("OK")));

		mvc.perform(get("/service-management/service-pipelines//service-catalogues/999").contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.status", is("OK"))).andExpect(jsonPath("$.data.length()", greaterThan(0)))
				.andExpect(jsonPath("$.data[*].name", hasItem("test-service-pipeline")));
	}

}
