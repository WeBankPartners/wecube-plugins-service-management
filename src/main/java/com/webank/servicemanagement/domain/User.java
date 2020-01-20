package com.webank.servicemanagement.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "user")
public class User extends BaseUUIDFeaturedEntity {

    public User() {
    }

    @Column(name = "name")
    private String name;

    @Column(name = "role_ids")
    private String roleIds;

}
