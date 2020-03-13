package com.webank.servicemanagement.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.webank.servicemanagement.domain.ServicePipeline;
import com.webank.servicemanagement.commons.ApplicationConstants;
import com.webank.servicemanagement.domain.CustomItemProcessNode;
import com.webank.servicemanagement.domain.FormCustomItem;
import com.webank.servicemanagement.domain.ServiceForm;
import com.webank.servicemanagement.dto.BindNodeInfo;
import com.webank.servicemanagement.dto.CreateServiceFormRequest;
import com.webank.servicemanagement.dto.CustomItem;
import com.webank.servicemanagement.dto.QueryRequest;
import com.webank.servicemanagement.dto.QueryResponse;
import com.webank.servicemanagement.jpa.CustomItemProcessNodeRepository;
import com.webank.servicemanagement.jpa.EntityRepository;
import com.webank.servicemanagement.jpa.FormCustomItemRepository;
import com.webank.servicemanagement.jpa.ServicePipelineRepository;
import com.webank.servicemanagement.jpa.ServiceFormRepository;
import com.webank.servicemanagement.utils.JsonUtils;

@Service
public class ServiceFormService {

    @Autowired
    EntityRepository entityRepository;

    @Autowired
    ServiceFormRepository serviceFormRepository;

    @Autowired
    FormCustomItemRepository formCustomItemRepository;

    @Autowired
    CustomItemProcessNodeRepository customItemProcessNodeRepository;

    @Autowired
    ServicePipelineRepository servicePipelineRepository;

    public List<ServiceForm> getAllAvailableServiceForms() {
        return serviceFormRepository.findAllByStatus(ApplicationConstants.getStatusActive());
    }

    public List<ServiceForm> getServiceFormsByPipelineId(String servicePipelineId) {
        return serviceFormRepository.findAllByServicePipelineId(servicePipelineId);
    }

    public void createServiceForm(CreateServiceFormRequest createServiceFormRequest) throws Exception {
        Optional<ServicePipeline> servicePipelineOptional = servicePipelineRepository
                .findById(createServiceFormRequest.getServicePipelineId());
        if (!servicePipelineOptional.isPresent())
            throw new Exception(String.format("Service Pipeline ID [%d] does not exist",
                    createServiceFormRequest.getServicePipelineId()));

        ServiceForm serviceForm = new ServiceForm(servicePipelineOptional.get(), createServiceFormRequest.getName(),
                createServiceFormRequest.getDescription(), createServiceFormRequest.getProcessDefinitionKey(),
                ApplicationConstants.getStatusActive());

        serviceForm = serviceFormRepository.save(serviceForm);

        for (CustomItem customItem : createServiceFormRequest.getCustomItems()) {
            FormCustomItem formCustomItem = formCustomItemRepository
                    .save(CustomItem.toDomain(customItem, serviceForm.getId()));

            for (BindNodeInfo bindNode : customItem.getBindNodes()) {
                customItemProcessNodeRepository.save(new CustomItemProcessNode(formCustomItem.getId(),
                        bindNode.getNodeId(), bindNode.getInputType()));
            }
        }

    }

    public List<ServiceForm> create(List<Map<String, Object>> mapList) {
        List<ServiceForm> ServiceForms = convertToDomainList(mapList);
        Iterable<ServiceForm> savedServiceForms = serviceFormRepository.saveAll(ServiceForms);
        return Lists.newArrayList(savedServiceForms);
    }

    public List<ServiceForm> update(List<Map<String, Object>> mapList) {
        List<ServiceForm> ServiceForms = convertToDomainList(mapList);
        Iterable<ServiceForm> savedServiceForms = serviceFormRepository.saveAll(ServiceForms);
        return Lists.newArrayList(savedServiceForms);
    }

    public void delete(List<Map<String, Object>> mapList) {
        List<ServiceForm> ServiceForms = convertToDomainList(mapList);
        ServiceForms.forEach(ServiceForm -> {
            serviceFormRepository.deleteById(ServiceForm.getId());
        });
    }

    private List<ServiceForm> convertToDomainList(List<Map<String, Object>> mapList) {
        List<ServiceForm> ServiceForms = new ArrayList<ServiceForm>();
        mapList.forEach(map -> {
            ServiceForms.add(JsonUtils.toObject(map, ServiceForm.class));
        });
        return ServiceForms;
    }

    public List<ServiceForm> getDataWithConditions(String filter, String sorting, String select) throws Exception {
        QueryResponse<ServiceForm> response = queryServiceForm(QueryRequest.buildQueryRequest(filter, sorting, select));
        return response.getContents();
    }

    public QueryResponse<ServiceForm> queryServiceForm(QueryRequest queryRequest) {
        QueryResponse<ServiceForm> queryResult;
        try {
            queryResult = entityRepository.query(ServiceForm.class, queryRequest);
            if (queryResult.getContents().size() == 0) {
                return new QueryResponse<>();
            }
            return queryResult;
        } catch (Exception e) {
            return new QueryResponse<>();
        }
    }
}
