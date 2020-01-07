package com.webank.servicemanagement.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.webank.servicemanagement.domain.ServicePipeline;
import com.webank.servicemanagement.domain.ServiceRequest;
import com.webank.servicemanagement.domain.ServiceRequestTemplate;
import com.webank.servicemanagement.dto.CreateServiceRequestTemplateRequest;
import com.webank.servicemanagement.dto.QueryRequest;
import com.webank.servicemanagement.dto.QueryResponse;
import com.webank.servicemanagement.jpa.EntityRepository;
import com.webank.servicemanagement.jpa.ServicePipelineRepository;
import com.webank.servicemanagement.jpa.ServiceRequestTemplateRepository;
import com.webank.servicemanagement.utils.JsonUtils;

@Service
public class ServiceRequestTemplateService {

    @Autowired
    EntityRepository entityRepository;

    @Autowired
    ServiceRequestTemplateRepository serviceRequestTemplateRepository;

    @Autowired
    ServicePipelineRepository servicePipelineRepository;

    private final static String AVAILABLE_STATU_STRING = "active";

    public List<ServiceRequestTemplate> getAllAvailableServiceRequestTemplate() {
        return serviceRequestTemplateRepository.findAllByStatus(AVAILABLE_STATU_STRING);
    }

    public List<ServiceRequestTemplate> getServiceRequestTemplateByPipelineId(String servicePipelineId) {
        return serviceRequestTemplateRepository.findAllByServicePipelineId(servicePipelineId);
    }

    public void createServiceRequestTemplate(CreateServiceRequestTemplateRequest createServiceRequestTemplateRequest)
            throws Exception {
        Optional<ServicePipeline> servicePipelineOptional = servicePipelineRepository
                .findById(createServiceRequestTemplateRequest.getServicePipelineId());
        if (!servicePipelineOptional.isPresent())
            throw new Exception(String.format("Service Pipeline ID [%d] does not exist",
                    createServiceRequestTemplateRequest.getServicePipelineId()));

        ServiceRequestTemplate serviceRequestTemplate = new ServiceRequestTemplate(servicePipelineOptional.get(),
                createServiceRequestTemplateRequest.getName(), createServiceRequestTemplateRequest.getDescription(),
                createServiceRequestTemplateRequest.getProcessDefinitionKey(), AVAILABLE_STATU_STRING);

        serviceRequestTemplateRepository.save(serviceRequestTemplate);
    }

    public List<ServiceRequestTemplate> create(List<Map<String, Object>> mapList) {
        List<ServiceRequestTemplate> ServiceRequestTemplates = convertToDomainList(mapList);
        Iterable<ServiceRequestTemplate> savedServiceRequestTemplates = serviceRequestTemplateRepository
                .saveAll(ServiceRequestTemplates);
        return Lists.newArrayList(savedServiceRequestTemplates);
    }

    public List<ServiceRequestTemplate> update(List<Map<String, Object>> mapList) {
        List<ServiceRequestTemplate> ServiceRequestTemplates = convertToDomainList(mapList);
        Iterable<ServiceRequestTemplate> savedServiceRequestTemplates = serviceRequestTemplateRepository
                .saveAll(ServiceRequestTemplates);
        return Lists.newArrayList(savedServiceRequestTemplates);
    }

    public void delete(List<Map<String, Object>> mapList) {
        List<ServiceRequestTemplate> ServiceRequestTemplates = convertToDomainList(mapList);
        ServiceRequestTemplates.forEach(ServiceRequestTemplate -> {
            serviceRequestTemplateRepository.deleteById(ServiceRequestTemplate.getId());
        });
    }

    private List<ServiceRequestTemplate> convertToDomainList(List<Map<String, Object>> mapList) {
        List<ServiceRequestTemplate> ServiceRequestTemplates = new ArrayList<ServiceRequestTemplate>();
        mapList.forEach(map -> {
            ServiceRequestTemplates.add(JsonUtils.toObject(map, ServiceRequestTemplate.class));
        });
        return ServiceRequestTemplates;
    }

    public List<ServiceRequestTemplate> getDataWithConditions(String filter, String sorting, String select)
            throws Exception {
        QueryResponse<ServiceRequestTemplate> response = queryServiceRequestTemplate(
                QueryRequest.buildQueryRequest(filter, sorting, select));
        return response.getContents();
    }

    public QueryResponse<ServiceRequestTemplate> queryServiceRequestTemplate(QueryRequest queryRequest) {
        QueryResponse<ServiceRequestTemplate> queryResult;
        try {
            queryResult = entityRepository.query(ServiceRequestTemplate.class, queryRequest);
            if (queryResult.getContents().size() == 0) {
                return new QueryResponse<>();
            }
            return queryResult;
        } catch (Exception e) {
            return new QueryResponse<>();
        }
    }
}
