package com.webank.servicemanagement.service;

import java.text.SimpleDateFormat;
import java.util.Date;
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

	private final static String STATUS_PENDING = "Pending";
	private final static String STATUS_PROCESSING = "Processing";
	private final static String STATUS_SUCCESSFUL = "Successful";
	private final static String STATUS_FAILED = "Failed";

	public void createTask(CreateTaskRequest createTaskRequest) {
		String currentTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		Task task = new Task(createTaskRequest.getServiceRequestId(), createTaskRequest.getProcessInstanceId(),
				createTaskRequest.getCallbackUrl(), createTaskRequest.getName(),
				createTaskRequest.getProcessDefinitionKey(), createTaskRequest.getReporter(), currentTime,
				createTaskRequest.getDescription(), STATUS_PENDING);
		taskRepository.save(task);
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
		task.setOperateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		task.setResult(processTaskRequest.getResult());
		task.setResultMessage(processTaskRequest.getResultMessage());
		task.setStatus(processTaskRequest.getResult());
		taskRepository.save(task);
	}

}
