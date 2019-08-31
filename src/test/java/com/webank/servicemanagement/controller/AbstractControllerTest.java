package com.webank.servicemanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import com.webank.servicemanagement.DatabaseBasedTest;

@AutoConfigureMockMvc
public abstract class AbstractControllerTest extends DatabaseBasedTest {

	@Autowired
	protected MockMvc mvc;

}
