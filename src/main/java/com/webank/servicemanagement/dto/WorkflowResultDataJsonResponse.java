package com.webank.servicemanagement.dto;

import java.util.List;

public class WorkflowResultDataJsonResponse<DATATYPE> {
    private List<WorkflowResultDataOutputJsonResponse<DATATYPE>> outputs;

    public List<WorkflowResultDataOutputJsonResponse<DATATYPE>> getOutputs() {
        return outputs;
    }

    public void setOutputs(List<WorkflowResultDataOutputJsonResponse<DATATYPE>> outputs) {
        this.outputs = outputs;
    }

    public WorkflowResultDataJsonResponse() {
    }

    public WorkflowResultDataJsonResponse(List<WorkflowResultDataOutputJsonResponse<DATATYPE>> outputs) {
        this.outputs = outputs;
    }

    public static class WorkflowResultDataOutputJsonResponse<DATATYPE> {
        public final static String STATUS_OK = "0";
        public final static String STATUS_ERROR = "1";
        private String callbackParameter;
        private String errorCode;
        private String errorMessage;
        private DATATYPE output;

        public String getCallbackParameter() {
            return callbackParameter;
        }

        public void setCallbackParameter(String callbackParameter) {
            this.callbackParameter = callbackParameter;
        }

        public String getErrorCode() {
            return errorCode;
        }

        public void setErrorCode(String errorCode) {
            this.errorCode = errorCode;
        }

        public String getErrorMessage() {
            return errorMessage;
        }

        public void setErrorMessage(String errorMessage) {
            this.errorMessage = errorMessage;
        }

        public DATATYPE getOutput() {
            return output;
        }

        public void setOutput(Object output) {
            this.output = (DATATYPE) output;
        }

        public static WorkflowResultDataOutputJsonResponse<?> withError(String callbackParameter, String errorMessage) {
            WorkflowResultDataOutputJsonResponse<?> output = new WorkflowResultDataOutputJsonResponse();
            output.setCallbackParameter(callbackParameter);
            output.setErrorCode(STATUS_ERROR);
            output.setErrorMessage(errorMessage);
            return output;
        }

        public static WorkflowResultDataOutputJsonResponse<?> okay(String callbackParameter,
                Object pluginResultObject) {
            WorkflowResultDataOutputJsonResponse<?> output = new WorkflowResultDataOutputJsonResponse();
            output.setCallbackParameter(callbackParameter);
            output.setErrorCode(STATUS_OK);
            output.setOutput(pluginResultObject);
            return output;
        }

    }
}