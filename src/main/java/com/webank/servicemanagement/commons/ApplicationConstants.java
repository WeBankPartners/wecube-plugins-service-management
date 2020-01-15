package com.webank.servicemanagement.commons;

public final class ApplicationConstants {

    public static class ApiInfo {
        public final static String API_PREFIX = "/service-mgmt";
        public final static String API_VERSION_V1 = "/v1";
        public final static String API_RESOURCE_SERVICE_REQUEST = "/service-requests";
        public final static String CALLBACK_URL_OF_REPORT_SERVICE_REQUEST = API_VERSION_V1
                + API_RESOURCE_SERVICE_REQUEST + "/done";
    }

}
