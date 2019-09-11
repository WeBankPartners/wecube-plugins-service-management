package com.webank.servicemanagement.controller;

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

import com.webank.servicemanagement.domain.Task;
import com.webank.servicemanagement.dto.CreateTaskRequest;
import com.webank.servicemanagement.dto.JsonResponse;
import com.webank.servicemanagement.dto.ProcessTaskRequest;
import com.webank.servicemanagement.dto.QueryRequest;
import com.webank.servicemanagement.dto.UpdateTaskRequest;
import com.webank.servicemanagement.jpa.EntityRepository;
import com.webank.servicemanagement.service.TaskService;

@RestController
@RequestMapping("/service-management/tasks")
public class TaskController {

	@Autowired
	TaskService taskService;

	@Autowired
	EntityRepository entityRepository;

	@PostMapping
	public JsonResponse createTask(@RequestBody CreateTaskRequest createTaskRequest) {
		taskService.createTask(createTaskRequest);
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
		taskService.takeoverTask(taskId, takeOverTaskrequest);
		return okay();
	}

	@PutMapping("/{task-id}/process")
	public JsonResponse processTask(@PathVariable(value = "task-id") int taskId,
			@RequestBody ProcessTaskRequest processTaskRequest) throws Exception {
		taskService.processTask(taskId, processTaskRequest);
		return okay();
	}

	@PostMapping("/query")
	public JsonResponse queryTask(@RequestBody QueryRequest queryRequest) throws Exception {
		return okayWithData(entityRepository.query(Task.class, queryRequest));
	}
}
