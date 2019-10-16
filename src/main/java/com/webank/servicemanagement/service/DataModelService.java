package com.webank.servicemanagement.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webank.servicemanagement.domain.ServiceCatalogue;
import com.webank.servicemanagement.domain.ServicePipeline;
import com.webank.servicemanagement.domain.ServiceRequest;
import com.webank.servicemanagement.domain.ServiceRequestTemplate;
import com.webank.servicemanagement.domain.Task;
import com.webank.servicemanagement.dto.UpdateServiceCatalogueRequest;
import com.webank.servicemanagement.dto.UpdateServicePipelineRequest;
import com.webank.servicemanagement.dto.UpdateServiceRequestRequest;
import com.webank.servicemanagement.dto.UpdateServiceRequestTemplateRequest;
import com.webank.servicemanagement.dto.UpdateTaskRequest;
import com.webank.servicemanagement.jpa.ServiceCatalogueRepository;
import com.webank.servicemanagement.jpa.ServicePipelineRepository;
import com.webank.servicemanagement.jpa.ServiceRequestRepository;
import com.webank.servicemanagement.jpa.ServiceRequestTemplateRepository;
import com.webank.servicemanagement.jpa.TaskRepository;

@Service
public class DataModelService {

	@Autowired
	ServiceCatalogueRepository serviceCatalogueRepository;
	@Autowired
	ServicePipelineRepository servicePipelineRepository;
	@Autowired
	ServiceRequestTemplateRepository serviceRequestTemplateRepository;
	@Autowired
	ServiceRequestRepository serviceRequestRepository;
	@Autowired
	TaskRepository taskRepository;

	public ServiceCatalogue getServiceCatalogueById(String serviceCatalogueId) {
		Optional<ServiceCatalogue> serviceCatalogueOptional = serviceCatalogueRepository
				.findById(Integer.valueOf(serviceCatalogueId));
		if (!serviceCatalogueOptional.isPresent())
			return null;
		return serviceCatalogueOptional.get();
	}

	public void updateServiceCatalogue(String serviceCatalogueId, UpdateServiceCatalogueRequest updateServiceCatalogue)
			throws Exception {
		ServiceCatalogue serviceCatalogue = getServiceCatalogueById(serviceCatalogueId);
		if (null == serviceCatalogue)
			throw new Exception(String.format("Service catalogue ID [%s] not found", serviceCatalogueId));

		serviceCatalogueRepository
				.save(UpdateServiceCatalogueRequest.toDomain(updateServiceCatalogue, serviceCatalogue));
	}

	public ServicePipeline getServicePipelineById(String servicePipelineId) {
		Optional<ServicePipeline> servicePipelineOptional = servicePipelineRepository
				.findById(Integer.valueOf(servicePipelineId));
		if (!servicePipelineOptional.isPresent())
			return null;
		return servicePipelineOptional.get();
	}

	public void updateServicePipeline(String servicePipelineId, UpdateServicePipelineRequest updateServicePipeline)
			throws Exception {
		ServicePipeline servicePipeline = getServicePipelineById(servicePipelineId);
		if (null == servicePipeline)
			throw new Exception(String.format("Service pipeline ID [%s] not found", servicePipelineId));

		servicePipelineRepository.save(UpdateServicePipelineRequest.toDomain(updateServicePipeline, servicePipeline));
	}

	public ServiceRequestTemplate getServiceRequestTemplateById(String serviceRequestTemplateId) {
		Optional<ServiceRequestTemplate> serviceRequestTemplateOptional = serviceRequestTemplateRepository
				.findById(Integer.valueOf(serviceRequestTemplateId));
		if (!serviceRequestTemplateOptional.isPresent())
			return null;
		return serviceRequestTemplateOptional.get();
	}

	public void updateServiceRequestTemplate(String serviceRequestTemplateId,
			UpdateServiceRequestTemplateRequest updateServiceRequestTemplate) throws Exception {
		ServiceRequestTemplate serviceRequestTemplate = getServiceRequestTemplateById(serviceRequestTemplateId);
		if (null == serviceRequestTemplate)
			throw new Exception(String.format("Service request template ID [%s] not found", serviceRequestTemplateId));

		serviceRequestTemplateRepository.save(
				UpdateServiceRequestTemplateRequest.toDomain(updateServiceRequestTemplate, serviceRequestTemplate));
	}

	public ServiceRequest getServiceRequestById(String serviceRequestId) {
		Optional<ServiceRequest> serviceRequestOptional = serviceRequestRepository
				.findById(Integer.valueOf(serviceRequestId));
		if (!serviceRequestOptional.isPresent())
			return null;
		return serviceRequestOptional.get();
	}

	public void updateServiceRequest(String serviceRequestId, UpdateServiceRequestRequest updateServiceRequest)
			throws Exception {
		ServiceRequest serviceRequest = getServiceRequestById(serviceRequestId);
		if (null == serviceRequest)
			throw new Exception(String.format("Service request ID [%s] not found", serviceRequestId));

		serviceRequestRepository.save(UpdateServiceRequestRequest.toDomain(updateServiceRequest, serviceRequest));
	}

	public Task getTaskById(String taskId) {
		Optional<Task> taskOptional = taskRepository.findById(Integer.valueOf(taskId));
		if (!taskOptional.isPresent())
			return null;
		return taskOptional.get();
	}

	public void updateTask(String taskId, UpdateTaskRequest updateTask) throws Exception {
		Task task = getTaskById(taskId);
		if (null == task)
			throw new Exception(String.format("Service task ID [%s] not found", taskId));

		taskRepository.save(UpdateTaskRequest.toDomain(updateTask, task));
	}

}
