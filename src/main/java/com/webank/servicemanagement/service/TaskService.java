package com.webank.servicemanagement.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.webank.servicemanagement.domain.Task;
import com.webank.servicemanagement.dto.CreateTaskRequest;
import com.webank.servicemanagement.dto.ProcessTaskRequest;
import com.webank.servicemanagement.dto.UpdateTaskRequest;
import com.webank.servicemanagement.jpa.TaskRepository;

@Service
public class TaskService {

	@Autowired
	TaskRepository taskRepository;

	private final static String STATUS_CREATED = "Created";
	private final static String STATUS_RECEIVED = "Received";
	private final static String STATUS_DONE = "Done";

	public Task createTask(CreateTaskRequest createTaskRequest) {
		Task task = new Task(null, 1, "processInstanceId_test", "http://127.0.0.1/helloworld/test", "test-taksName",
				"processDefinitionKey_test", "haha", "2019-08-29 14:52:38", "hehe", "2019-08-29 14:52:41", "input",
				"description1", null, null, "active");
		taskRepository.save(task);
		return task;
	}

	public List<Task> getAllTask() {
		return Lists.newArrayList(taskRepository.findAll());
	}

	public void takeOverTask(UpdateTaskRequest receiveTaskrequest) throws Exception {
		Task task;
		Optional<Task> taskResult = taskRepository.findById(receiveTaskrequest.getTaskId());
		if (!taskResult.isPresent()) {
			throw new Exception("Can not found the specified task, please check !");
		}
		task = taskResult.get();
		task.setOperator(receiveTaskrequest.getOperator());
		taskRepository.save(task);
	}

	public void processTask(ProcessTaskRequest processTaskRequest) throws Exception {
		Task task;
		Optional<Task> taskResult = taskRepository.findById(processTaskRequest.getTaskId());
		if (!taskResult.isPresent()) {
			throw new Exception("Can not found the specified task, please check !");
		}
		task = taskResult.get();
		task.setResult(processTaskRequest.getResult());
		task.setResultMessage(processTaskRequest.getResultMessage());
		taskRepository.save(task);
	}

}
