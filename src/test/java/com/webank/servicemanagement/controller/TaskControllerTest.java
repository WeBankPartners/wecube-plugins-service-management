package com.webank.servicemanagement.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.greaterThan;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.junit.Test;
import org.springframework.http.MediaType;

public class TaskControllerTest extends AbstractControllerTest {

	@Test
	public void createTaskTest() throws Exception {
		mvc.perform(post("/v1/tasks").contentType(MediaType.APPLICATION_JSON).content(
				"{\"callbackUrl\":\"callbackUrl-test\",\"taskName\":\"name-createTaskTest\",\"operator\":\"reporter-test\",\"roleName\":\"roleName\",\"requestId\": \"999\"}"))
				.andExpect(jsonPath("$.status", is("OK")));

		mvc.perform(post("/v1/tasks/query").contentType(MediaType.APPLICATION_JSON)
				.content("{\r\n" + "  \"filters\": [\r\n" + "    {\r\n" + "      \"name\": \"taskName\",\r\n"
						+ "      \"operator\": \"eq\",\r\n" + "      \"value\": \"name-createTaskTest\"\r\n"
						+ "    }\r\n" + "  ]\r\n" + "}"))
				.andExpect(jsonPath("$.status", is("OK"))).andExpect(jsonPath("$.data.length()", greaterThan(0)));
	}
}
