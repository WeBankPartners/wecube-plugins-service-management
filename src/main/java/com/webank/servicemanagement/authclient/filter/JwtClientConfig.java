package com.webank.servicemanagement.authclient.filter;

public class JwtClientConfig {
    private String signingKey;

    public String getSigningKey() {
        return signingKey;
    }

    public void setSigningKey(String signingKey) {
        this.signingKey = signingKey;
    }

}
