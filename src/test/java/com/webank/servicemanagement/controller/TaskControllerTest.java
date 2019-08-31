package com.webank.servicemanagement.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.greaterThan;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.junit.Test;
import org.springframework.http.MediaType;

public class TaskControllerTest extends AbstractControllerTest {

	@Test
	public void createTaskTest() throws Exception {
		mvc.perform(post("/tasks/create").contentType(MediaType.APPLICATION_JSON).content(
				"{\"callbackUrl\":\"callbackUrl-test\",\"description\":\"description-test\",\"name\":\"name-createTaskTest\",\"processDefinitionKey\":\"processDefinitionKey-test\",\"processInstanceId\":\"processInstanceId-test\",\"reporter\":\"reporter-test\",\"serviceRequestId\": 1}"))
				.andExpect(jsonPath("$.status", is("OK")));

		mvc.perform(get("/tasks/retrieve").contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.status", is("OK")))
				.andExpect(jsonPath("$.data.length()", greaterThan(0)))
				.andExpect(jsonPath("$.data[*].name", contains("name-createTaskTest")));
	}

}
