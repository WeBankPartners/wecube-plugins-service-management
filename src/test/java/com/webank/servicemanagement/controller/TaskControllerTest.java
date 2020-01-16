package com.webank.servicemanagement.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.greaterThan;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.webank.servicemanagement.dto.JsonResponse;

public class TaskControllerTest extends AbstractControllerTest {

    @Test
    public void createTaskTest() throws Exception {
        Date before = new Date();

        mvc.perform(post("/v1/tasks").contentType(MediaType.APPLICATION_JSON).content(
                "{\r\n" + 
                "  \"inputs\": [\r\n" + 
                "    {\r\n" + 
                "      \"callbackUrl\": \"callbackUrl-test\",\r\n" + 
                "      \"roleName\": \"roleName\",\r\n" + 
                "      \"taskName\": \"name-createTaskTest\"\r\n" + 
                "    }\r\n" + 
                "  ],\r\n" + 
                "  \"operator\": \"reporter-test\",\r\n" + 
                "  \"requestId\": \"999\"\r\n" + 
                "}"))
                .andExpect(jsonPath("$.resultCode", is("0")));
        Date after = new Date();

        MvcResult result = mvc
                .perform(post("/v1/tasks/query").contentType(MediaType.APPLICATION_JSON)
                        .content("{\r\n" + "  \"filters\": [\r\n" + "    {\r\n" + "      \"name\": \"name\",\r\n"
                                + "      \"operator\": \"eq\",\r\n" + "      \"value\": \"name-createTaskTest\"\r\n"
                                + "    }\r\n" + "  ]\r\n" + "}"))
                .andExpect(jsonPath("$.status", is("OK"))).andExpect(jsonPath("$.data.length()", greaterThan(0)))
                .andReturn();

        JsonResponse jsonResponse = toObject(result.getResponse().getContentAsString(), JsonResponse.class);
        String reportTime = (String) ((Map) (((List) ((Map) jsonResponse.getData()).get("contents")).get(0)))
                .get("reportTime");
        Date reportTimeDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(reportTime);
        assertThat(reportTimeDate.after(before));
        assertThat(reportTimeDate.before(after));
    }

    public static <T> T toObject(String jsonContent, Class<T> clzz) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JavaType javaType = mapper.getTypeFactory().constructType(clzz);
        return mapper.readValue(jsonContent.getBytes(), javaType);
    }

}
