package com.webank.servicemanagement.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.webank.servicemanagement.domain.ServiceRequest;
import com.webank.servicemanagement.domain.Task;
import com.webank.servicemanagement.dto.CreateTaskRequest;
import com.webank.servicemanagement.dto.ProcessTaskRequest;
import com.webank.servicemanagement.dto.QueryRequest;
import com.webank.servicemanagement.dto.QueryResponse;
import com.webank.servicemanagement.dto.Sorting;
import com.webank.servicemanagement.dto.UpdateTaskRequest;
import com.webank.servicemanagement.jpa.EntityRepository;
import com.webank.servicemanagement.jpa.ServiceRequestRepository;
import com.webank.servicemanagement.jpa.TaskRepository;
import com.webank.servicemanagement.support.core.CoreRemoteCallException;
import com.webank.servicemanagement.support.core.CoreRestTemplate;
import com.webank.servicemanagement.support.core.CoreServiceStub;
import com.webank.servicemanagement.support.core.dto.CallbackRequestDto;
import com.webank.servicemanagement.support.core.dto.CallbackRequestResultDto;

@Service
public class TaskService {
    private static final Logger log = LoggerFactory.getLogger(TaskService.class);

    @Autowired
    TaskRepository taskRepository;
    @Autowired
    ServiceRequestRepository serviceRequestRepository;
    @Autowired
    EntityRepository entityRepository;
    @Autowired
    CoreServiceStub coreServiceStub;

    private final static String STATUS_PENDING = "Pending";
    private final static String STATUS_PROCESSING = "Processing";
    private final static String STATUS_SUCCESSFUL = "Successful";
    private final static String STATUS_FAILED = "Failed";

    public void createTask(CreateTaskRequest createTaskRequest) throws Exception {
        Task task = new Task(createTaskRequest.getCallbackUrl(), createTaskRequest.getTaskName(),
                createTaskRequest.getRoleName(), createTaskRequest.getOperator(),
                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()), createTaskRequest.getTaskName(),
                STATUS_PENDING, createTaskRequest.getRequestId());
        taskRepository.save(task);
    }

    public List<Task> getAllTask() {
        return Lists.newArrayList(taskRepository.findAll());
    }

    public void takeoverTask(int taskId, UpdateTaskRequest receiveTaskrequest) throws Exception {
        Task task;
        Optional<Task> taskResult = taskRepository.findById(taskId);
        if (!taskResult.isPresent()) {
            throw new Exception("Can not found the specified task, please check !");
        }
        task = taskResult.get();
        task.setOperator(receiveTaskrequest.getOperator());
        task.setStatus(STATUS_PROCESSING);
        taskRepository.save(task);
    }

    public void processTask(int taskId, ProcessTaskRequest processTaskRequest) throws Exception {
        if (!checkResultIsAvailable(processTaskRequest.getResult()))
            throw new Exception(String.format("Result[%s] is invalid", processTaskRequest.getResult()));
        updateTaskByProcessTaskRequest(taskId, processTaskRequest);
    }

    private boolean checkResultIsAvailable(String result) {
        return STATUS_SUCCESSFUL.equals(result) || STATUS_FAILED.equals(result) ? true : false;
    }

    private void updateTaskByProcessTaskRequest(int taskId, ProcessTaskRequest processTaskRequest)
            throws Exception, CoreRemoteCallException {
        Optional<Task> taskResult = taskRepository.findById(taskId);
        if (!taskResult.isPresent())
            throw new Exception("Can not found the specified task, please check !");
        Task task = taskResult.get();

        CallbackRequestDto callbackRequest = new CallbackRequestDto();
        callbackRequest.setResults(new CallbackRequestResultDto(task.getRequestId()));
        callbackRequest.setResultMessage(processTaskRequest.getResultMessage());
        if (processTaskRequest.getResult() == "Successful") {
            callbackRequest.setResultCode("0");
        } else {
            callbackRequest.setResultCode("1");
        }
        
        try {
            coreServiceStub.callback(task.getCallbackUrl(), callbackRequest);
        } catch (CoreRemoteCallException e) {
            log.error(String.format("Callback wecube meet error: %s", e.getMessage()));
            throw new CoreRemoteCallException(String.format("Callback wecube meet error: %s", e.getMessage()));
        }

        task.setOperateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        task.setResult(processTaskRequest.getResult());
        task.setResultMessage(processTaskRequest.getResultMessage());
        task.setStatus(processTaskRequest.getResult());
        taskRepository.save(task);
    }

    public QueryResponse<Task> queryTask(QueryRequest queryRequest) {
        queryRequest.setSorting(new Sorting(false, "reportTime"));

        QueryResponse<Task> queryResult;
        try {
            queryResult = entityRepository.query(Task.class, queryRequest);
            if (queryResult.getContents().size() == 0) {
                return new QueryResponse<>();
            }
            return queryResult;
        } catch (Exception e) {
            return new QueryResponse<>();
        }
    }

}
