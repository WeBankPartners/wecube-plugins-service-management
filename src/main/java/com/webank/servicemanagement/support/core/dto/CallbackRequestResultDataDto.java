package com.webank.servicemanagement.support.core.dto;

import java.util.List;

public class CallbackRequestResultDataDto {
    private String requestId;
    private List<CallbackRequestOutputsDto> outputs;

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public List<CallbackRequestOutputsDto> getOutputs() {
        return outputs;
    }

    public void setOutputs(List<CallbackRequestOutputsDto> outputs) {
        this.outputs = outputs;
    }

    @Override
    public String toString() {
        return "CallbackRequestResultDataDto [requestId=" + requestId + ", outputs=" + outputs + "]";
    }

}
