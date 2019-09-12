package com.webank.servicemanagement.support.core.dto;

import java.util.List;

import com.webank.servicemanagement.dto.JsonResponse;

public class CoreResponse<DATATYPE> extends JsonResponse {

	private String status;
	private String message;
	private DATATYPE data;

	public static class DefaultCoreResponse extends CoreResponse<Object> {
	}

	public static class IntegerCoreResponse extends CoreResponse<Integer> {
	}

	public static class StringCoreResponse extends CoreResponse<String> {
	}

	public static class ListDataResponse extends CoreResponse<List> {
	}
	
	public static class GetAllRolesResponse extends CoreResponse<List<RolesDataResponse>> {
	}
}
