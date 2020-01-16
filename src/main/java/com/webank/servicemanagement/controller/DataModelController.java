package com.webank.servicemanagement.controller;

import static com.webank.servicemanagement.dto.JsonResponse.okay;
import static com.webank.servicemanagement.dto.JsonResponse.okayWithData;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.webank.servicemanagement.dto.JsonResponse;
import com.webank.servicemanagement.service.ServiceCatalogueService;
import com.webank.servicemanagement.service.ServicePipelineService;
import com.webank.servicemanagement.service.ServiceRequestService;
import com.webank.servicemanagement.service.ServiceRequestTemplateService;
import com.webank.servicemanagement.service.TaskService;

@RestController
@RequestMapping("/entities")
public class DataModelController {

    @Autowired
    ServiceCatalogueService serviceCatalogueService;
    @Autowired
    ServicePipelineService servicePipelineService;
    @Autowired
    ServiceRequestTemplateService serviceRequestTemplateService;
    @Autowired
    ServiceRequestService serviceRequestService;
    @Autowired
    TaskService taskService;

    @GetMapping("/service_catalogue")
    @ResponseBody
    public JsonResponse retrieveServiceCatalogues(@RequestParam(value = "filter", required = false) String filter,
            @RequestParam(value = "sorting", required = false) String sorting,
            @RequestParam(value = "select", required = false) String select) throws Exception {
        return okayWithData(serviceCatalogueService.getDataWithConditions(filter, sorting, select));
    }

    @PostMapping("/service_catalogue/create")
    public JsonResponse createServiceCatalogues(@RequestBody List<Map<String, Object>> request) {
        return okayWithData(serviceCatalogueService.create(request));
    }

    @PostMapping("/service_catalogue/update")
    public JsonResponse updateServiceCatalogues(@RequestBody List<Map<String, Object>> request) {
        return okayWithData(serviceCatalogueService.update(request));
    }

    @PostMapping("/service_catalogue/delete")
    public JsonResponse deleteServiceCatalogues(@RequestBody List<Map<String, Object>> request) {
        serviceCatalogueService.delete(request);
        return okay();
    }

    @GetMapping("/service_pipeline")
    @ResponseBody
    public JsonResponse retrieveServicePipelines(@RequestParam(value = "filter", required = false) String filter,
            @RequestParam(value = "sorting", required = false) String sorting,
            @RequestParam(value = "select", required = false) String select) throws Exception {
        return okayWithData(servicePipelineService.getDataWithConditions(filter, sorting, select));
    }

    @PostMapping("/service_pipeline/create")
    public JsonResponse createServicePipelines(@RequestBody List<Map<String, Object>> request) {
        return okayWithData(servicePipelineService.create(request));
    }

    @PostMapping("/service_pipeline/update")
    public JsonResponse updateServicePipelines(@RequestBody List<Map<String, Object>> request) {
        return okayWithData(servicePipelineService.update(request));
    }

    @PostMapping("/service_pipeline/delete")
    public JsonResponse deleteServicePipelines(@RequestBody List<Map<String, Object>> request) {
        servicePipelineService.delete(request);
        return okay();
    }

    @GetMapping("/service_request_template")
    @ResponseBody
    public JsonResponse retrieveServiceRequestTemplates(@RequestParam(value = "filter", required = false) String filter,
            @RequestParam(value = "sorting", required = false) String sorting,
            @RequestParam(value = "select", required = false) String select) throws Exception {
        return okayWithData(serviceRequestTemplateService.getDataWithConditions(filter, sorting, select));
    }

    @PostMapping("/service_request_template/create")
    public JsonResponse createServiceRequestTemplates(@RequestBody List<Map<String, Object>> request) {
        return okayWithData(serviceRequestTemplateService.create(request));
    }

    @PostMapping("/service_request_template/update")
    public JsonResponse updateServiceRequestTemplates(@RequestBody List<Map<String, Object>> request) {
        return okayWithData(serviceRequestTemplateService.update(request));
    }

    @PostMapping("/service_request_template/delete")
    public JsonResponse deleteServiceRequestTemplates(@RequestBody List<Map<String, Object>> request) {
        serviceRequestTemplateService.delete(request);
        return okay();
    }

    @GetMapping("/service_request")
    @ResponseBody
    public JsonResponse retrieveServiceRequests(@RequestParam(value = "filter", required = false) String filter,
            @RequestParam(value = "sorting", required = false) String sorting,
            @RequestParam(value = "select", required = false) String select) throws Exception {
        return okayWithData(serviceRequestService.getDataWithConditions(filter, sorting, select));
    }

    @PostMapping("/service_request/create")
    public JsonResponse createServiceRequests(@RequestBody List<Map<String, Object>> request) {
        return okayWithData(serviceRequestService.create(request));
    }

    @PostMapping("/service_request/update")
    public JsonResponse updateServiceRequests(@RequestBody List<Map<String, Object>> request) {
        return okayWithData(serviceRequestService.update(request));
    }

    @PostMapping("/service_request/delete")
    public JsonResponse deleteServiceRequests(@RequestBody List<Map<String, Object>> request) {
        serviceRequestService.delete(request);
        return okay();
    }

    @GetMapping("/task")
    @ResponseBody
    public JsonResponse retrieveTasks(@RequestParam(value = "filter", required = false) String filter,
            @RequestParam(value = "sorting", required = false) String sorting,
            @RequestParam(value = "select", required = false) String select) throws Exception {
        return okayWithData(taskService.getDataWithConditions(filter, sorting, select));
    }

    @PostMapping("/task/create")
    public JsonResponse createTasks(@RequestBody List<Map<String, Object>> request) {
        return okayWithData(taskService.create(request));
    }

    @PostMapping("/task/update")
    public JsonResponse updateTasks(@RequestBody List<Map<String, Object>> request) {
        return okayWithData(taskService.update(request));
    }

    @PostMapping("/task/delete")
    public JsonResponse deleteTasks(@RequestBody List<Map<String, Object>> request) {
        taskService.delete(request);
        return okay();
    }

}
