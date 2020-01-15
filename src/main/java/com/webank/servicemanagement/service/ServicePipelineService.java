package com.webank.servicemanagement.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.webank.servicemanagement.domain.ServiceCatalogue;
import com.webank.servicemanagement.domain.ServicePipeline;
import com.webank.servicemanagement.dto.CreateServicePipelineRequest;
import com.webank.servicemanagement.dto.QueryRequest;
import com.webank.servicemanagement.dto.QueryResponse;
import com.webank.servicemanagement.jpa.EntityRepository;
import com.webank.servicemanagement.jpa.ServiceCatalogueRepository;
import com.webank.servicemanagement.jpa.ServicePipelineRepository;
import com.webank.servicemanagement.utils.JsonUtils;

@Service
public class ServicePipelineService {

    @Autowired
    ServicePipelineRepository servicePipelineRepository;
    @Autowired
    ServiceCatalogueRepository ServiceCatalogueRepository;
    @Autowired
    EntityRepository entityRepository;

    private final static String AVAILABLE_STATU_STRING = "active";

    public List<ServicePipeline> getServicePipelineByCatalogueId(String serviceCatalogueId) {
        return servicePipelineRepository.findAllByServiceCatalogueIdAndStatus(serviceCatalogueId,
                AVAILABLE_STATU_STRING);
    }

    public void createServicePipeline(CreateServicePipelineRequest createServicePipelineRequest) throws Exception {

        Optional<ServiceCatalogue> serviceCatalogue = ServiceCatalogueRepository
                .findById(createServicePipelineRequest.getServiceCatalogueId());

        if (!serviceCatalogue.isPresent())
            throw new Exception(String.format("Service catalogue ID [%d] does not exist",
                    createServicePipelineRequest.getServiceCatalogueId()));

        if (servicePipelineRepository
                .findAllByServiceCatalogueIdAndName(createServicePipelineRequest.getServiceCatalogueId(),
                        createServicePipelineRequest.getName())
                .size() > 0)
            throw new Exception(
                    String.format("Service pipeline [%s] already exists", createServicePipelineRequest.getName()));

        ServicePipeline servicePipeline = new ServicePipeline(serviceCatalogue.get(),
                createServicePipelineRequest.getName(), createServicePipelineRequest.getDescription(),
                createServicePipelineRequest.getOwnerRole(), AVAILABLE_STATU_STRING);
        servicePipelineRepository.save(servicePipeline);
    }

    public List<ServicePipeline> create(List<Map<String, Object>> mapList) {
        List<ServicePipeline> servicePipelines = convertToDomainList(mapList);
        Iterable<ServicePipeline> savedServicePipelines = servicePipelineRepository.saveAll(servicePipelines);
        return Lists.newArrayList(savedServicePipelines);
    }

    public List<ServicePipeline> update(List<Map<String, Object>> mapList) {
        List<ServicePipeline> servicePipelines = convertToDomainList(mapList);
        Iterable<ServicePipeline> savedServicePipelines = servicePipelineRepository.saveAll(servicePipelines);
        return Lists.newArrayList(savedServicePipelines);
    }

    public void delete(List<Map<String, Object>> mapList) {
        List<ServicePipeline> servicePipelines = convertToDomainList(mapList);
        servicePipelines.forEach(servicePipeline -> {
            servicePipelineRepository.deleteById(servicePipeline.getId());
        });
    }

    private List<ServicePipeline> convertToDomainList(List<Map<String, Object>> mapList) {
        List<ServicePipeline> servicePipelines = new ArrayList<ServicePipeline>();
        mapList.forEach(map -> {
            servicePipelines.add(JsonUtils.toObject(map, ServicePipeline.class));
        });
        return servicePipelines;
    }

    public List<ServicePipeline> getDataWithConditions(String filter, String sorting, String select) throws Exception {
        QueryResponse<ServicePipeline> response = queryServicePipeline(
                QueryRequest.buildQueryRequest(filter, sorting, select));
        return response.getContents();
    }

    public QueryResponse<ServicePipeline> queryServicePipeline(QueryRequest queryRequest) {
        QueryResponse<ServicePipeline> queryResult;
        try {
            queryResult = entityRepository.query(ServicePipeline.class, queryRequest);
            if (queryResult.getContents().size() == 0) {
                return new QueryResponse<>();
            }
            return queryResult;
        } catch (Exception e) {
            return new QueryResponse<>();
        }
    }
}
