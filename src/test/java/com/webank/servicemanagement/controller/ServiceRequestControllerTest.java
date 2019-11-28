package com.webank.servicemanagement.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.greaterThan;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;

import com.webank.servicemanagement.support.core.MockCoreServiceStub;

@ActiveProfiles("test")
public class ServiceRequestControllerTest extends AbstractControllerTest {

	@Autowired
	MockCoreServiceStub coreServiceStub;

	@Test
	public void createServiceRequestTest() throws Exception {
		mvc.perform(post("/v1/service-requests").contentType(MediaType.APPLICATION_JSON)
				.content("{" + "  \"description\": \"desc-test\",\"emergency\": \"emergency-test\","
						+ "  \"name\": \"name-createServiceRequestTest\"," + "  \"reporter\": \"reporter-test\","
						+ "\"templateId\": 999," + "\"attachFileId\":999 } "))
				.andExpect(jsonPath("$.status", is("OK")));

		mvc.perform(post("/v1/service-requests/query").contentType(MediaType.APPLICATION_JSON)
				.content("{\r\n" + "  \"filters\": [\r\n" + "    {\r\n" + "      \"name\": \"name\",\r\n"
						+ "      \"operator\": \"eq\",\r\n" + "      \"value\": \"name-createServiceRequestTest\"\r\n"
						+ "    }\r\n" + "  ]\r\n" + "}"))
				.andExpect(jsonPath("$.status", is("OK"))).andExpect(jsonPath("$.data.length()", greaterThan(0)));
	}

	@Test
	public void createServiceRequestWhenTemplateIdDoesNotExistsTest() throws Exception {
		mvc.perform(post("/v1/service-requests").contentType(MediaType.APPLICATION_JSON)
				.content("{" + "  \"description\": \"desc-test\",\"emergency\": \"emergency-test\","
						+ "  \"name\": \"name-createServiceRequestTest\"," + "  \"reporter\": \"reporter-test\","
						+ "\"templateId\": 111," + "\"attachFileId\":999 } "))
				.andExpect(jsonPath("$.status", is("ERROR")));
	}

	@Test
	public void createServiceRequestWhenAttachFileIdDoesNotExistsTest() throws Exception {
		mvc.perform(post("/v1/service-requests").contentType(MediaType.APPLICATION_JSON)
				.content("{" + "  \"description\": \"desc-test\",\"emergency\": \"emergency-test\","
						+ "  \"name\": \"name-createServiceRequestTest\"," + "  \"reporter\": \"reporter-test\","
						+ "\"templateId\": 999," + "\"attachFileId\":111 } "))
				.andExpect(jsonPath("$.status", is("ERROR")));
	}

	@Test
	public void updateServiceRequestWithValidIdTest() throws Exception {
		mvc.perform(put("/v1/service-requests/done").contentType(MediaType.APPLICATION_JSON)
				.content("{\"result\": \"Approved\",\"serviceRequestId\": 999}")).andExpect(jsonPath("$.status", is("OK")));
	}

	@Test
	public void updateServiceRequestWithInvalidIdTest() throws Exception {
		mvc.perform(put("/v1/service-requests/done").contentType(MediaType.APPLICATION_JSON)
				.content("{\"result\": \"Approved\",\"serviceRequestId\": 998}")).andExpect(jsonPath("$.status", is("ERROR")));
	}
}
