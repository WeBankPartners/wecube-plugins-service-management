package com.webank.servicemanagement.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "process_node_instance")
public class ProcessNodeInstance extends BaseUUIDFeaturedEntity {
    public ProcessNodeInstance() {
    }

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "process_instance_id")
    private ProcessInstance processInstance;

    @JsonBackReference
    @OneToOne
    @JoinColumn(name = "process_node_definiton_id")
    private ProcessNodeDefinition processNodeDefinition;

    @Column(name = "approver")
    private String approver;
    
    public enum Result {
        IN_PROGRESS, DONE
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "execution_result")
    private Result executionResult;

}
