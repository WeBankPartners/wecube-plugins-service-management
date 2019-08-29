package com.webank.servicemanagement.controller;

import static com.webank.servicemanagement.dto.JsonResponse.okay;
import static com.webank.servicemanagement.dto.JsonResponse.okayWithData;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.webank.servicemanagement.dto.CreateTaskRequest;
import com.webank.servicemanagement.dto.JsonResponse;
import com.webank.servicemanagement.dto.UpdateTaskRequest;
import com.webank.servicemanagement.service.TaskService;

@RestController
@RequestMapping("/task")
public class TaskController {

	@Autowired
	TaskService taskService;

	@PostMapping("/create")
	public JsonResponse createTask() {
		return okayWithData(taskService.createTask(new CreateTaskRequest()));
	}

	@GetMapping("/retrieve")
	public JsonResponse getAllTask() {
		return okayWithData(taskService.getAllTask());
	}

	@PostMapping("/takeover")
	public JsonResponse takeover(@RequestBody UpdateTaskRequest receiveTaskrequest) {
		taskService.receiveTask(receiveTaskrequest);
		return okay();
	}

	@PostMapping("/process")
	public JsonResponse process(@RequestBody UpdateTaskRequest receiveTaskrequest) {
		taskService.receiveTask(receiveTaskrequest);
		return okay();
	}
}
