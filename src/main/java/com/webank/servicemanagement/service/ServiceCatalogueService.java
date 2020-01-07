package com.webank.servicemanagement.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.webank.servicemanagement.domain.ServiceCatalogue;
import com.webank.servicemanagement.dto.CreateServiceCatalogueRequest;
import com.webank.servicemanagement.dto.QueryRequest;
import com.webank.servicemanagement.dto.QueryResponse;
import com.webank.servicemanagement.jpa.EntityRepository;
import com.webank.servicemanagement.jpa.ServiceCatalogueRepository;
import com.webank.servicemanagement.utils.JsonUtils;

@Service
public class ServiceCatalogueService {
    @Autowired
    EntityRepository entityRepository;

    @Autowired
    ServiceCatalogueRepository serviceCatalogueRepository;

    private final static String AVAILABLE_STATU_STRING = "active";

    public List<ServiceCatalogue> getAllAvailableServiceCatalogue() {
        return serviceCatalogueRepository.findAllByStatus(AVAILABLE_STATU_STRING);
    }

    public void createServiceCatalogue(CreateServiceCatalogueRequest createServiceCatalogueRequest) throws Exception {
        if (serviceCatalogueRepository.findAllByName(createServiceCatalogueRequest.getName()).size() > 0)
            throw new Exception(
                    String.format("Service catalogue [%d] already exists", createServiceCatalogueRequest.getName()));

        ServiceCatalogue serviceCatalogue = new ServiceCatalogue(createServiceCatalogueRequest.getName(),
                createServiceCatalogueRequest.getDescription(), AVAILABLE_STATU_STRING);
        serviceCatalogueRepository.save(serviceCatalogue);
    }

    public List<ServiceCatalogue> create(List<Map<String, Object>> mapList) {
        List<ServiceCatalogue> serviceCatalogues = convertToDomainList(mapList);
        Iterable<ServiceCatalogue> savedServiceCatalogues = serviceCatalogueRepository.saveAll(serviceCatalogues);
        return Lists.newArrayList(savedServiceCatalogues);
    }

    public List<ServiceCatalogue> update(List<Map<String, Object>> mapList) {
        List<ServiceCatalogue> serviceCatalogues = convertToDomainList(mapList);
        Iterable<ServiceCatalogue> savedServiceCatalogues = serviceCatalogueRepository.saveAll(serviceCatalogues);
        return Lists.newArrayList(savedServiceCatalogues);
    }

    public void delete(List<Map<String, Object>> mapList) {
        List<ServiceCatalogue> serviceCatalogues = convertToDomainList(mapList);
        serviceCatalogues.forEach(serviceCatalogue -> {
            serviceCatalogueRepository.deleteById(serviceCatalogue.getId());
        });
    }

    private List<ServiceCatalogue> convertToDomainList(List<Map<String, Object>> mapList) {
        List<ServiceCatalogue> serviceCatalogues = new ArrayList<ServiceCatalogue>();
        mapList.forEach(map -> {
            serviceCatalogues.add(JsonUtils.toObject(map, ServiceCatalogue.class));
        });
        return serviceCatalogues;
    }

    public List<ServiceCatalogue> getDataWithConditions(String filter, String sorting, String select) throws Exception {
        QueryResponse<ServiceCatalogue> response = queryServiceCatalogue(
                QueryRequest.buildQueryRequest(filter, sorting, select));
        return response.getContents();
    }

    public QueryResponse<ServiceCatalogue> queryServiceCatalogue(QueryRequest queryRequest) {
        QueryResponse<ServiceCatalogue> queryResult;
        try {
            queryResult = entityRepository.query(ServiceCatalogue.class, queryRequest);
            if (queryResult.getContents().size() == 0) {
                return new QueryResponse<>();
            }
            return queryResult;
        } catch (Exception e) {
            return new QueryResponse<>();
        }
    }
}
