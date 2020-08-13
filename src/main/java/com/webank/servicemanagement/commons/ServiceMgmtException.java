package com.webank.servicemanagement.commons;

public class ServiceMgmtException extends RuntimeException {

    /**
     * 
     */
    private static final long serialVersionUID = -1397731977791044934L;

    private String errorCode;
    private String messageKey;
    private boolean applyMessage = false;
    private Object[] args;

    public ServiceMgmtException(String message) {
        super(message);
        this.applyMessage = true;
    }

    public ServiceMgmtException(String message, Throwable cause) {
        super(message, cause);
        this.applyMessage = true;
    }

    public ServiceMgmtException(String errorCode, String message, Object... objects) {
        this(errorCode, null, message, null, false, objects);
    }

    private ServiceMgmtException(String errorCode, String messageKey, String message, Throwable cause,
            boolean applyMessage, Object... objects) {
        super(message, cause);
        this.errorCode = errorCode;
        this.messageKey = messageKey;
        if (objects != null && (this.args == null)) {
            this.args = new Object[objects.length];
            int index = 0;
            for (Object object : objects) {
                this.args[index] = object;
                index++;
            }
        }
        this.applyMessage = applyMessage;
    }

    public ServiceMgmtException withErrorCode(String errorCode) {
        this.errorCode = errorCode;
        return this;
    }

    public ServiceMgmtException withErrorCode(String errorCode, Object... objects) {
        this.errorCode = errorCode;
        if (objects != null && (this.args == null)) {
            this.args = new Object[objects.length];
            int index = 0;
            for (Object object : objects) {
                this.args[index] = object;
                index++;
            }
        }
        return this;
    }

    public ServiceMgmtException withErrorCodeAndArgs(String errorCode, Object[] objects) {
        this.errorCode = errorCode;
        if (objects != null && (this.args == null)) {
            this.args = new Object[objects.length];
            int index = 0;
            for (Object object : objects) {
                this.args[index] = object;
                index++;
            }
        }
        return this;
    }

    public ServiceMgmtException withMessageKey(String msgKey) {
        this.messageKey = msgKey;
        return this;
    }

    public ServiceMgmtException withMessageKey(String msgKey, Object... objects) {
        this.messageKey = msgKey;
        if (objects != null && (this.args == null)) {
            this.args = new Object[objects.length];
            int index = 0;
            for (Object object : objects) {
                this.args[index] = object;
                index++;
            }
        }

        return this;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getMessageKey() {
        return messageKey;
    }

    public boolean isApplyMessage() {
        return applyMessage;
    }

    public Object[] getArgs() {
        return args;
    }
}
