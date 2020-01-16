package com.webank.servicemanagement.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "service_pipeline")
@GenericGenerator(name = "jpa-uuid", strategy = "uuid")
public class ServicePipeline {
    @Id
    @GeneratedValue(generator = "jpa-uuid")
    @Column(name = "id", length = 32)
    private String id;

    @ManyToOne
    @JoinColumn(name = "service_catalogue_id")
    private ServiceCatalogue serviceCatalogue;
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "owner_role")
    private String ownerRole;
    @Column(name = "status")
    private String status = "active";

    public ServicePipeline() {
    }

    public ServicePipeline(ServiceCatalogue serviceCatalogue, String name, String description, String ownerRole,
            String status) {
        this.serviceCatalogue = serviceCatalogue;
        this.name = name;
        this.description = description;
        this.ownerRole = ownerRole;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ServiceCatalogue getServiceCatalogue() {
        return serviceCatalogue;
    }

    public void setServiceCatalogue(ServiceCatalogue serviceCatalogue) {
        this.serviceCatalogue = serviceCatalogue;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOwnerRole() {
        return ownerRole;
    }

    public void setOwnerRole(String ownerRole) {
        this.ownerRole = ownerRole;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
