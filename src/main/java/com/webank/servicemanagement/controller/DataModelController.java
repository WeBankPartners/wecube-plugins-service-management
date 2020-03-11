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
import com.webank.servicemanagement.service.AttachFileService;
import com.webank.servicemanagement.service.ServiceCatalogueService;
import com.webank.servicemanagement.service.ServicePipelineService;
import com.webank.servicemanagement.service.ServiceRequestService;
import com.webank.servicemanagement.service.ServiceFormService;
import com.webank.servicemanagement.service.TaskService;

@RestController
@RequestMapping("/entities")
public class DataModelController {

    @Autowired
    ServiceCatalogueService serviceCatalogueService;
    @Autowired
    ServicePipelineService servicePipelineService;
    @Autowired
    ServiceFormService serviceRequestTemplateService;
    @Autowired
    ServiceRequestService serviceRequestService;
    @Autowired
    TaskService taskService;
    @Autowired
    AttachFileService attachFileService;

    public static final String SERVICE_CATALOGUE = "serviceCatalogue";
    public static final String SERVICE_PIPELINE = "servicePipeline";
    public static final String SERVICE_REQUEST_TEMPLATE = "serviceRequestTemplate";
    public static final String SERVICE_REQUEST = "serviceRequest";
    public static final String TASK = "task";
    public static final String ATTACH_FILE = "attachFile";

    @GetMapping("/" + SERVICE_CATALOGUE)
    @ResponseBody
    public JsonResponse retrieveServiceCatalogues(@RequestParam(value = "filter", required = false) String filter,
            @RequestParam(value = "sorting", required = false) String sorting,
            @RequestParam(value = "select", required = false) String select) throws Exception {
        return okayWithData(serviceCatalogueService.getDataWithConditions(filter, sorting, select));
    }

    @PostMapping("/" + SERVICE_CATALOGUE + "/create")
    public JsonResponse createServiceCatalogues(@RequestBody List<Map<String, Object>> request) {
        return okayWithData(serviceCatalogueService.create(request));
    }

    @PostMapping("/" + SERVICE_CATALOGUE + "/update")
    public JsonResponse updateServiceCatalogues(@RequestBody List<Map<String, Object>> request) {
        return okayWithData(serviceCatalogueService.update(request));
    }

    @PostMapping("/" + SERVICE_CATALOGUE + "/delete")
    public JsonResponse deleteServiceCatalogues(@RequestBody List<Map<String, Object>> request) {
        serviceCatalogueService.delete(request);
        return okay();
    }

    @GetMapping("/" + SERVICE_PIPELINE)
    @ResponseBody
    public JsonResponse retrieveServicePipelines(@RequestParam(value = "filter", required = false) String filter,
            @RequestParam(value = "sorting", required = false) String sorting,
            @RequestParam(value = "select", required = false) String select) throws Exception {
        return okayWithData(servicePipelineService.getDataWithConditions(filter, sorting, select));
    }

    @PostMapping("/" + SERVICE_PIPELINE + "/create")
    public JsonResponse createServicePipelines(@RequestBody List<Map<String, Object>> request) {
        return okayWithData(servicePipelineService.create(request));
    }

    @PostMapping("/" + SERVICE_PIPELINE + "/update")
    public JsonResponse updateServicePipelines(@RequestBody List<Map<String, Object>> request) {
        return okayWithData(servicePipelineService.update(request));
    }

    @PostMapping("/" + SERVICE_PIPELINE + "/delete")
    public JsonResponse deleteServicePipelines(@RequestBody List<Map<String, Object>> request) {
        servicePipelineService.delete(request);
        return okay();
    }

    @GetMapping("/" + SERVICE_REQUEST_TEMPLATE)
    @ResponseBody
    public JsonResponse retrieveServiceRequestTemplates(@RequestParam(value = "filter", required = false) String filter,
            @RequestParam(value = "sorting", required = false) String sorting,
            @RequestParam(value = "select", required = false) String select) throws Exception {
        return okayWithData(serviceRequestTemplateService.getDataWithConditions(filter, sorting, select));
    }

    @PostMapping("/" + SERVICE_REQUEST_TEMPLATE + "/create")
    public JsonResponse createServiceRequestTemplates(@RequestBody List<Map<String, Object>> request) {
        return okayWithData(serviceRequestTemplateService.create(request));
    }

    @PostMapping("/" + SERVICE_REQUEST_TEMPLATE + "/update")
    public JsonResponse updateServiceRequestTemplates(@RequestBody List<Map<String, Object>> request) {
        return okayWithData(serviceRequestTemplateService.update(request));
    }

    @PostMapping("/" + SERVICE_REQUEST_TEMPLATE + "/delete")
    public JsonResponse deleteServiceRequestTemplates(@RequestBody List<Map<String, Object>> request) {
        serviceRequestTemplateService.delete(request);
        return okay();
    }

    @GetMapping("/" + SERVICE_REQUEST)
    @ResponseBody
    public JsonResponse retrieveServiceRequests(@RequestParam(value = "filter", required = false) String filter,
            @RequestParam(value = "sorting", required = false) String sorting,
            @RequestParam(value = "select", required = false) String select) throws Exception {
        return okayWithData(serviceRequestService.getDataWithConditions(filter, sorting, select));
    }

    @PostMapping("/" + SERVICE_REQUEST + "/create")
    public JsonResponse createServiceRequests(@RequestBody List<Map<String, Object>> request) {
        return okayWithData(serviceRequestService.create(request));
    }

    @PostMapping("/" + SERVICE_REQUEST + "/update")
    public JsonResponse updateServiceRequests(@RequestBody List<Map<String, Object>> request) {
        return okayWithData(serviceRequestService.update(request));
    }

    @PostMapping("/" + SERVICE_REQUEST + "/delete")
    public JsonResponse deleteServiceRequests(@RequestBody List<Map<String, Object>> request) {
        serviceRequestService.delete(request);
        return okay();
    }

    @GetMapping("/" + TASK)
    @ResponseBody
    public JsonResponse retrieveTasks(@RequestParam(value = "filter", required = false) String filter,
            @RequestParam(value = "sorting", required = false) String sorting,
            @RequestParam(value = "select", required = false) String select) throws Exception {
        return okayWithData(taskService.getDataWithConditions(filter, sorting, select));
    }

    @PostMapping("/" + TASK + "/create")
    public JsonResponse createTasks(@RequestBody List<Map<String, Object>> request) {
        return okayWithData(taskService.create(request));
    }

    @PostMapping("/" + TASK + "/update")
    public JsonResponse updateTasks(@RequestBody List<Map<String, Object>> request) {
        return okayWithData(taskService.update(request));
    }

    @PostMapping("/" + TASK + "/delete")
    public JsonResponse deleteTasks(@RequestBody List<Map<String, Object>> request) {
        taskService.delete(request);
        return okay();
    }

    @GetMapping("/" + ATTACH_FILE)
    @ResponseBody
    public JsonResponse retrieveAttachFiles(@RequestParam(value = "filter", required = false) String filter,
            @RequestParam(value = "sorting", required = false) String sorting,
            @RequestParam(value = "select", required = false) String select) throws Exception {
        return okayWithData(attachFileService.getDataWithConditions(filter, sorting, select));
    }

    @PostMapping("/" + ATTACH_FILE + "/create")
    public JsonResponse createAttachFiles(@RequestBody List<Map<String, Object>> request) {
        return okayWithData(attachFileService.create(request));
    }

    @PostMapping("/" + ATTACH_FILE + "/update")
    public JsonResponse updateAttachFiles(@RequestBody List<Map<String, Object>> request) {
        return okayWithData(attachFileService.update(request));
    }

    @PostMapping("/" + ATTACH_FILE + "/delete")
    public JsonResponse deleteAttachFiles(@RequestBody List<Map<String, Object>> request) {
        attachFileService.delete(request);
        return okay();
    }

}
