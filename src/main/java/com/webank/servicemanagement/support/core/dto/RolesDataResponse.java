package com.webank.servicemanagement.support.core.dto;

import com.fasterxml.jackson.annotation.JsonAlias;

public class RolesDataResponse {

    @JsonAlias("id")
    private int roleId;
    @JsonAlias("name")
    private String roleName;
    @JsonAlias("displayName")
    private String description;

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "RolesDataResponse [roleId=" + roleId + ", roleName=" + roleName + ", description=" + description + "]";
    }
}
