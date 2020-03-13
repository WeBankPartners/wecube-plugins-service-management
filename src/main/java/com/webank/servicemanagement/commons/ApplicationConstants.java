package com.webank.servicemanagement.commons;

public final class ApplicationConstants {

    private final static String STATUS_ACTIVE = "active";
    private final static String STATUS_SUBMITTED = "Submitted";
    private final static String STATUS_PROCESSING = "Processing";
    private final static String STATUS_DONE = "Done";
    private final static String IS_NOTIFY_REQUIRED = "Y";

    public static class ApiInfo {
        public final static String API_PREFIX = "/service-mgmt";
        public final static String API_VERSION_V1 = "/v1";
        public final static String API_RESOURCE_SERVICE_REQUEST = "/service-requests";
        public final static String CALLBACK_URL_OF_REPORT_SERVICE_REQUEST = API_VERSION_V1
                + API_RESOURCE_SERVICE_REQUEST + "/done";
    }

    public static class CommonItemTitle {
        public final static String name = "title";
        public final static String displayName = "Title/标题";
        public final static String dataType = "String";
        public final static int length = 32;
    }

    public static class CommonItemDescription {
        public final static String name = "description";
        public final static String displayName = "Description/描述";
        public final static String dataType = "String";
        public final static int length = 255;
    }

    public static class CommonItemEmergency {
        public final static String name = "emergency";
        public final static String displayName = "Emergency/紧急度";
        public final static String dataType = "String";
        public final static int length = 32;
    }

    public static class CommonItemEnvType {
        public final static String name = "envType";
        public final static String displayName = "Env/环境类型";
        public final static String dataType = "String";
        public final static int length = 32;
    }

    class CommonItem {
        private String name;
        private String displayName;
        private String dataType;
        private int length;

        public CommonItem() {
        }

        public CommonItem(String name, String displayName, String dataType, int length) {
            super();
            this.name = name;
            this.displayName = displayName;
            this.dataType = dataType;
            this.length = length;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDisplayName() {
            return displayName;
        }

        public void setDisplayName(String displayName) {
            this.displayName = displayName;
        }

        public String getDataType() {
            return dataType;
        }

        public void setDataType(String dataType) {
            this.dataType = dataType;
        }

        public int getLength() {
            return length;
        }

        public void setLength(int length) {
            this.length = length;
        }
    }

    public static String getStatusActive() {
        return STATUS_ACTIVE;
    }

    public static String getStatusSubmitted() {
        return STATUS_SUBMITTED;
    }

    public static String getStatusProcessing() {
        return STATUS_PROCESSING;
    }

    public static String getStatusDone() {
        return STATUS_DONE;
    }

    public static String getIsNotifyRequired() {
        return IS_NOTIFY_REQUIRED;
    }

}
