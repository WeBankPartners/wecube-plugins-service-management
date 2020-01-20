package com.webank.servicemanagement.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "role")
public class Role extends BaseUUIDFeaturedEntity {

    public Role() {
    }

    @Column(name = "name")
    private String name;
}
