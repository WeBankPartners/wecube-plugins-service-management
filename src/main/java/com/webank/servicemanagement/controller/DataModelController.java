package com.webank.servicemanagement.controller;

import static com.webank.servicemanagement.dto.JsonResponse.okay;
import static com.webank.servicemanagement.dto.JsonResponse.okayWithData;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.webank.servicemanagement.domain.ServiceCatalogue;
import com.webank.servicemanagement.domain.ServicePipeline;
import com.webank.servicemanagement.domain.ServiceRequest;
import com.webank.servicemanagement.domain.ServiceRequestTemplate;
import com.webank.servicemanagement.domain.Task;
import com.webank.servicemanagement.dto.JsonResponse;
import com.webank.servicemanagement.dto.UpdateServiceCatalogueRequest;
import com.webank.servicemanagement.dto.UpdateServicePipelineRequest;
import com.webank.servicemanagement.dto.UpdateServiceRequestRequest;
import com.webank.servicemanagement.dto.UpdateServiceRequestTemplateRequest;
import com.webank.servicemanagement.dto.UpdateTaskRequest;
import com.webank.servicemanagement.service.DataModelService;

@RestController
@RequestMapping("/data-models")
public class DataModelController {

	@Autowired
	DataModelService dataModelService;

	@GetMapping("/service-catalogues/{service-catalogue-id}")
	public JsonResponse getServiceCatalogueById(
			@PathVariable(value = "service-catalogue-id") String serviceCatalogueId) {
		return okayWithData(dataModelService.getServiceCatalogueById(serviceCatalogueId));
	}

	@PutMapping("/service-catalogues/{service-catalogue-id}")
	public JsonResponse updateServiceCatalogueById(
			@PathVariable(value = "service-catalogue-id") String serviceCatalogueId,
			@RequestBody UpdateServiceCatalogueRequest updateServiceCatalogue) throws Exception {
		dataModelService.updateServiceCatalogue(serviceCatalogueId, updateServiceCatalogue);
		return okay();
	}

	@GetMapping("/service-pipelines/{service-pipeline-id}")
	public JsonResponse getServicePipelineById(@PathVariable(value = "service-pipeline-id") String servicePipelineId) {
		return okayWithData(dataModelService.getServicePipelineById(servicePipelineId));
	}

	@PutMapping("/service-pipelines/{service-pipeline-id}")
	public JsonResponse updateServicePipelineById(@PathVariable(value = "service-pipeline-id") String servicePipelineId,
			@RequestBody UpdateServicePipelineRequest updateServicePipeline) throws Exception {
		dataModelService.updateServicePipeline(servicePipelineId, updateServicePipeline);
		return okay();
	}

	@GetMapping("/service-request-templates/{service-request-template-id}")
	public JsonResponse getServiceRequestTemplateById(
			@PathVariable(value = "service-request-template-id") String serviceRequestTemplateId) {
		return okayWithData(dataModelService.getServiceRequestTemplateById(serviceRequestTemplateId));
	}

	@PutMapping("/service-request-templates/{service-request-template-id}")
	public JsonResponse updateServiceRequestTemplateById(
			@PathVariable(value = "service-request-template-id") String serviceRequestTemplateId,
			@RequestBody UpdateServiceRequestTemplateRequest updateServiceRequestTemplate) throws Exception {
		dataModelService.updateServiceRequestTemplate(serviceRequestTemplateId, updateServiceRequestTemplate);
		return okay();
	}

	@GetMapping("/service-requests/{service-request-id}")
	public JsonResponse getServiceRequestById(@PathVariable(value = "service-request-id") String serviceRequestId) {
		return okayWithData(dataModelService.getServiceRequestById(serviceRequestId));
	}

	@PutMapping("/service-requests/{service-request-id}")
	public JsonResponse updateServiceRequestById(@PathVariable(value = "service-request-id") String serviceRequestId,
			@RequestBody UpdateServiceRequestRequest updateServiceRequest) throws Exception {
		dataModelService.updateServiceRequest(serviceRequestId, updateServiceRequest);
		return okay();
	}

	@GetMapping("/tasks/{task-id}")
	public JsonResponse getServiceTaskById(@PathVariable(value = "task-id") String taskId) {
		return okayWithData(dataModelService.getTaskById(taskId));
	}

	@PutMapping("/tasks/{task-id}")
	public JsonResponse updateTaskById(@PathVariable(value = "task-id") String taskId, @RequestBody UpdateTaskRequest updateTask)
			throws Exception {
		dataModelService.updateTask(taskId, updateTask);
		return okay();
	}
}
