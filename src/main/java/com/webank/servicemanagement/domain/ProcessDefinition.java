package com.webank.servicemanagement.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "process_definition")
public class ProcessDefinition extends BaseUUIDFeaturedEntity {

    public ProcessDefinition() {
    }

    public enum Status {
        ACTIVE, INACTIVE;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status Status;

    @JsonManagedReference
    @OneToMany(mappedBy = "processDefinition", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<ProcessNodeDefinition> processNodeDefinitionList;

    @Column(name = "name")
    private String name;

}
