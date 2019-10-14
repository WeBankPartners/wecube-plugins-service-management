package com.webank.servicemanagement.controller;

import static com.webank.servicemanagement.dto.JsonResponse.error;
import static com.webank.servicemanagement.dto.JsonResponse.okay;
import static com.webank.servicemanagement.dto.JsonResponse.okayWithData;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.webank.servicemanagement.dto.CreateTaskRequest;
import com.webank.servicemanagement.dto.JsonResponse;
import com.webank.servicemanagement.dto.ProcessTaskRequest;
import com.webank.servicemanagement.dto.QueryRequest;
import com.webank.servicemanagement.dto.UpdateTaskRequest;
import com.webank.servicemanagement.service.TaskService;

@RestController
@RequestMapping("/service-management/tasks")
public class TaskController {

	@Autowired
	TaskService taskService;

	@PostMapping
	public JsonResponse createTask(@RequestBody CreateTaskRequest createTaskRequest) {
		try {
			taskService.createTask(createTaskRequest);
		} catch (Exception e) {
			return error(e.getMessage());
		}
		return okay();
	}

	@Deprecated
	@GetMapping
	public JsonResponse getAllTask() {
		return okayWithData(taskService.getAllTask());
	}

	@PutMapping("/{task-id}/takeover")
	public JsonResponse takeoverTask(@PathVariable(value = "task-id") int taskId,
			@RequestBody UpdateTaskRequest takeOverTaskrequest) throws Exception {
		try {
			taskService.takeoverTask(taskId, takeOverTaskrequest);
		} catch (Exception e) {
			return error(e.getMessage());
		}
		return okay();
	}

	@PutMapping("/{task-id}/process")
	public JsonResponse processTask(@PathVariable(value = "task-id") int taskId,
			@RequestBody ProcessTaskRequest processTaskRequest) throws Exception {
		try {
			taskService.processTask(taskId, processTaskRequest);
		} catch (Exception e) {
			return error(e.getMessage());
		}
		return okay();
	}

	@PostMapping("/query")
	public JsonResponse queryTask(@RequestBody QueryRequest queryRequest) throws Exception {
		return okayWithData(taskService.queryTask(queryRequest));
	}
}
