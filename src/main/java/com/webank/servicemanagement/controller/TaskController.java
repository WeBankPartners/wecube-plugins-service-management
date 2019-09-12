package com.webank.servicemanagement.controller;

import static com.webank.servicemanagement.dto.JsonResponse.okay;
import static com.webank.servicemanagement.dto.JsonResponse.okayWithData;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.webank.servicemanagement.domain.Task;
import com.webank.servicemanagement.dto.CreateTaskRequest;
import com.webank.servicemanagement.dto.JsonResponse;
import com.webank.servicemanagement.dto.ProcessTaskRequest;
import com.webank.servicemanagement.dto.QueryRequest;
import com.webank.servicemanagement.dto.UpdateTaskRequest;
import com.webank.servicemanagement.jpa.EntityRepository;
import com.webank.servicemanagement.service.TaskService;

@RestController
@RequestMapping("/tasks")
public class TaskController {

	@Autowired
	TaskService taskService;

	@Autowired
	EntityRepository entityRepository;

	@PostMapping("/create")
	public JsonResponse createTask(@RequestBody CreateTaskRequest createTaskRequest) {
		taskService.createTask(createTaskRequest);
		return okay();
	}

	@GetMapping("/retrieve")
	public JsonResponse getAllTask() {
		return okayWithData(taskService.getAllTask());
	}

	@PostMapping("/takeover")
	public JsonResponse takeover(@RequestBody UpdateTaskRequest takeOverTaskrequest) throws Exception {
		taskService.takeoverTask(takeOverTaskrequest);
		return okay();
	}

	@PostMapping("/process")
	public JsonResponse process(@RequestBody ProcessTaskRequest processTaskRequest) throws Exception {
		taskService.processTask(processTaskRequest);
		return okay();
	}

	@PostMapping("/query")
	public JsonResponse queryTask(@RequestBody QueryRequest queryRequest) throws Exception {
		return okayWithData(entityRepository.query(Task.class, queryRequest));
	}
}
