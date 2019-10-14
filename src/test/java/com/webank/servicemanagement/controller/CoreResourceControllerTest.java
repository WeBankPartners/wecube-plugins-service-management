package com.webank.servicemanagement.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.junit.Test;
import org.springframework.http.MediaType;

public class CoreResourceControllerTest extends AbstractControllerTest {

	@Test
	public void getAllRolesTest() throws Exception {
		mvc.perform(get("/service-management/core-resources/roles").contentType(MediaType.APPLICATION_JSON)).andExpect(jsonPath("$.status", is("OK")));
	}
	@Test
	public void getRolesByCurrentUser() throws Exception {
		mvc.perform(get("/service-management/core-resources/users/current-user/roles").contentType(MediaType.APPLICATION_JSON)).andExpect(jsonPath("$.status", is("OK")));
	}
	@Test
	public void createServiceCatalogueTest() throws Exception {
		mvc.perform(get("/service-management/core-resources/workflow/process-definition-keys").contentType(MediaType.APPLICATION_JSON)).andExpect(jsonPath("$.status", is("OK")));
	}

}
