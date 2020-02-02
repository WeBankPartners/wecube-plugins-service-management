package com.webank.servicemanagement.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.google.common.collect.Lists;
import com.webank.servicemanagement.domain.AttachFile;
import com.webank.servicemanagement.dto.QueryRequest;
import com.webank.servicemanagement.dto.QueryResponse;
import com.webank.servicemanagement.jpa.EntityRepository;
import com.webank.servicemanagement.jpa.AttachFileRepository;
import com.webank.servicemanagement.utils.JsonUtils;

@Service
public class AttachFileService {
    @Autowired
    AttachFileRepository attachFileRepository;
    @Autowired
    EntityRepository entityRepository;

    public List<AttachFile> create(List<Map<String, Object>> mapList) {
        List<AttachFile> attachFiles = convertToDomainList(mapList);
        Iterable<AttachFile> savedAttachFile = attachFileRepository.saveAll(attachFiles);
        return Lists.newArrayList(savedAttachFile);
    }

    public List<AttachFile> update(List<Map<String, Object>> mapList) {
        List<AttachFile> attachFiles = convertToDomainList(mapList);
        Iterable<AttachFile> savedAttachFile = attachFileRepository.saveAll(attachFiles);
        return Lists.newArrayList(savedAttachFile);
    }

    public void delete(List<Map<String, Object>> mapList) {
        List<AttachFile> attachFiles = convertToDomainList(mapList);
        attachFiles.forEach(attachFile -> {
            attachFileRepository.deleteById(attachFile.getId());
        });
    }

    private List<AttachFile> convertToDomainList(List<Map<String, Object>> mapList) {
        List<AttachFile> attachFiles = new ArrayList<AttachFile>();
        mapList.forEach(map -> {
            attachFiles.add(JsonUtils.toObject(map, AttachFile.class));
        });
        return attachFiles;
    }

    public List<AttachFile> getDataWithConditions(String filter, String sorting, String select) throws Exception {
        QueryResponse<AttachFile> response = queryAttachFile(QueryRequest.buildQueryRequest(filter, sorting, select));
        return response.getContents();
    }

    public QueryResponse<AttachFile> queryAttachFile(QueryRequest queryRequest) {
        QueryResponse<AttachFile> queryResult;
        try {
            queryResult = entityRepository.query(AttachFile.class, queryRequest);
            if (queryResult.getContents().size() == 0) {
                return new QueryResponse<>();
            }
            return queryResult;
        } catch (Exception e) {
            return new QueryResponse<>();
        }
    }
}
