package com.webank.servicemanagement.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.webank.servicemanagement.domain.ServiceRequestTemplate;
import com.webank.servicemanagement.domain.Task;
import com.webank.servicemanagement.dto.CreateTaskRequestDto;
import com.webank.servicemanagement.dto.CreateTaskRequestInputDto;
import com.webank.servicemanagement.dto.ProcessTaskRequest;
import com.webank.servicemanagement.dto.QueryRequest;
import com.webank.servicemanagement.dto.QueryResponse;
import com.webank.servicemanagement.dto.Sorting;
import com.webank.servicemanagement.dto.UpdateTaskRequest;
import com.webank.servicemanagement.dto.WorkflowResultDataJsonResponse.WorkflowResultDataOutputJsonResponse;
import com.webank.servicemanagement.jpa.EntityRepository;
import com.webank.servicemanagement.jpa.ServiceRequestRepository;
import com.webank.servicemanagement.jpa.TaskRepository;
import com.webank.servicemanagement.support.core.CoreRemoteCallException;
import com.webank.servicemanagement.support.core.CoreServiceStub;
import com.webank.servicemanagement.support.core.dto.CallbackRequestDto;
import com.webank.servicemanagement.support.core.dto.CallbackRequestOutputsDto;
import com.webank.servicemanagement.support.core.dto.CallbackRequestResultDataDto;
import com.webank.servicemanagement.utils.JsonUtils;

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

    @SuppressWarnings("rawtypes")
    public List<WorkflowResultDataOutputJsonResponse> createTask(CreateTaskRequestDto createTaskRequest)
            throws Exception {
        List<WorkflowResultDataOutputJsonResponse> savedTasks = new ArrayList<WorkflowResultDataOutputJsonResponse>();
        List<CreateTaskRequestInputDto> inputs = createTaskRequest.getInputs();
        for (CreateTaskRequestInputDto input : inputs) {
            Task task = new Task(input.getCallbackUrl(), input.getTaskName(), input.getRoleName(),
                    createTaskRequest.getOperator(), new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()),
                    input.getTaskName(), STATUS_PENDING, createTaskRequest.getRequestId(),
                    input.getCallbackParameter());
            Task savedTask = taskRepository.save(task);
            WorkflowResultDataOutputJsonResponse<?> taskResult = WorkflowResultDataOutputJsonResponse
                    .okay(input.getCallbackParameter(), savedTask);
            savedTasks.add(taskResult);
        }

        return savedTasks;
    }

    public List<Task> getAllTask() {
        return Lists.newArrayList(taskRepository.findAll());
    }

    public void takeoverTask(String taskId, UpdateTaskRequest receiveTaskrequest) throws Exception {
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

    public void processTask(String taskId, ProcessTaskRequest processTaskRequest) throws Exception {
        if (!checkResultIsAvailable(processTaskRequest.getResult()))
            throw new Exception(String.format("Result[%s] is invalid, Only support 'Successful' and 'Failed'",
                    processTaskRequest.getResult()));
        updateTaskByProcessTaskRequest(taskId, processTaskRequest);
    }

    private boolean checkResultIsAvailable(String result) {
        return STATUS_SUCCESSFUL.equals(result) || STATUS_FAILED.equals(result);
    }

    private void updateTaskByProcessTaskRequest(String taskId, ProcessTaskRequest processTaskRequest)
            throws Exception, CoreRemoteCallException {
        Optional<Task> taskResult = taskRepository.findById(taskId);
        if (!taskResult.isPresent())
            throw new Exception("Can not found the specified task, please check !");
        Task task = taskResult.get();

        String errorCode = processTaskRequest.getResult().equals(STATUS_SUCCESSFUL)
                ? CallbackRequestOutputsDto.ERROR_CODE_SUCCESSFUL
                : CallbackRequestOutputsDto.ERROR_CODE_FAILED;

        CallbackRequestDto callbackRequest = new CallbackRequestDto();
        CallbackRequestResultDataDto callbackRequestResultDataDto = new CallbackRequestResultDataDto();
        callbackRequestResultDataDto.setRequestId(task.getRequestId());
        callbackRequestResultDataDto.setOutputs(
                Lists.newArrayList(new CallbackRequestOutputsDto(errorCode, processTaskRequest.getResultMessage(),
                        processTaskRequest.getResultMessage(), task.getCallbackParameter())));

        callbackRequest.setResults(callbackRequestResultDataDto);
        callbackRequest.setResultMessage(processTaskRequest.getResultMessage());
        if (ProcessTaskRequest.RESULT_SUCCESSFUL.equals(processTaskRequest.getResult())) {
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

    public List<Task> create(List<Map<String, Object>> mapList) {
        List<Task> tasks = convertToDomainList(mapList);
        Iterable<Task> savedTask = taskRepository.saveAll(tasks);
        return Lists.newArrayList(savedTask);
    }

    public List<Task> update(List<Map<String, Object>> mapList) {
        List<Task> tasks = convertToDomainList(mapList);
        Iterable<Task> savedTask = taskRepository.saveAll(tasks);
        return Lists.newArrayList(savedTask);
    }

    public void delete(List<Map<String, Object>> mapList) {
        List<Task> tasks = convertToDomainList(mapList);
        tasks.forEach(task -> {
            taskRepository.deleteById(task.getId());
        });
    }

    private List<Task> convertToDomainList(List<Map<String, Object>> mapList) {
        List<Task> tasks = new ArrayList<Task>();
        mapList.forEach(map -> {
            tasks.add(JsonUtils.toObject(map, Task.class));
        });
        return tasks;
    }

    public List<Task> getDataWithConditions(String filter, String sorting, String select) throws Exception {
        QueryResponse<Task> response = queryTask(QueryRequest.buildQueryRequest(filter, sorting, select));
        return response.getContents();
    }

}
