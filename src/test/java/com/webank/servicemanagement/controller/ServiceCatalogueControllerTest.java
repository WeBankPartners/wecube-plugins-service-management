package com.webank.servicemanagement.controller;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.greaterThan;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.junit.Test;
import org.springframework.http.MediaType;

public class ServiceCatalogueControllerTest extends AbstractControllerTest {

	@Test
	public void createServiceCatalogueTest() throws Exception {
		mvc.perform(post("/v1/service-catalogues").contentType(MediaType.APPLICATION_JSON)
				.content("{\"name\":\"test-service-catalogue\",\"description\": \"test-description\"}"))
				.andExpect(jsonPath("$.status", is("OK")));

		mvc.perform(get("/v1/service-catalogues/available").contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.status", is("OK"))).andExpect(jsonPath("$.data.length()", greaterThan(0)))
				.andExpect(jsonPath("$.data[*].name", hasItem("test-service-catalogue")));
	}

	@Test
	public void createServiceCatalogueWhenNameAlreadyExistsTest() throws Exception {
		mvc.perform(post("/v1/service-catalogues").contentType(MediaType.APPLICATION_JSON)
				.content("{\"name\":\"catalogue001\",\"description\": \"test-description\"}"))
				.andExpect(jsonPath("$.status", is("ERROR")));
	}
}
