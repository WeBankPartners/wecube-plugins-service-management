package com.webank.servicemanagement.support.core;

import com.webank.servicemanagement.dto.JsonResponse;
import com.webank.servicemanagement.support.RemoteCallException;

public class CoreRemoteCallException extends RemoteCallException {

    private static final long serialVersionUID = 1L;
    private transient JsonResponse jsonResponse;

    public CoreRemoteCallException(String message) {
        super(message);
    }

    public CoreRemoteCallException(String message, JsonResponse cmdbResponse) {
        super(message);
        this.jsonResponse = cmdbResponse;
    }

    public CoreRemoteCallException(String message, JsonResponse cmdbResponse, Throwable cause) {
        super(message, cause);
        this.jsonResponse = cmdbResponse;
    }

    public JsonResponse getCmdbResponse() {
        return jsonResponse;
    }

    @Override
    public String getErrorMessage() {
        return String.format("%s (CORE_ERROR_CODE: %s)", this.getMessage(), getStatusCode(jsonResponse));
    }

    @Override
    public Object getErrorData() {
        return jsonResponse == null ? null : jsonResponse.getData();
    }

    private String getStatusCode(JsonResponse jsonResponse) {
        return jsonResponse == null ? null : jsonResponse.getStatus();
    }
}
