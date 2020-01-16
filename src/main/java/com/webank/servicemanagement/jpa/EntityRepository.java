package com.webank.servicemanagement.jpa;

import com.webank.servicemanagement.dto.QueryRequest;
import com.webank.servicemanagement.dto.QueryResponse;

public interface EntityRepository {
	<T> QueryResponse<T> query(Class<T> domainClzz, QueryRequest ciRequest) throws Exception;
}
