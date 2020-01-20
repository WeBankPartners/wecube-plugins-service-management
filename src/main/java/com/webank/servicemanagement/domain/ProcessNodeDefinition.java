package com.webank.servicemanagement.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "process_node_definition")
public class ProcessNodeDefinition extends BaseUUIDFeaturedEntity {
    public ProcessNodeDefinition() {
    }

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "process_definition_id")
    private ProcessDefinition processDefinition;

    @Column(name = "sequence_no")
    private String sequenceNo;

    public enum NodeType {
        APPROVAL, MANUAL_EXECUTION, AUTO_EXECUTION
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private NodeType Type;

    @Column(name = "handle_role")
    private String handleRole;

    @Column(name = "auto_execution_api_url")
    private String autoExecuteApiUrl;

    @Column(name = "auto_execution_api_request_body_template")
    private String autoExecutionApiRequestBodyTemplate;

}
