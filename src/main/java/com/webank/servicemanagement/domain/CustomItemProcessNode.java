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
@Table(name = "custom_item__process_node")
@GenericGenerator(name = "jpa-uuid", strategy = "uuid")
public class CustomItemProcessNode {

    @Id
    @GeneratedValue(generator = "jpa-uuid")
    @Column(name = "id", length = 32)
    private String id;

    @ManyToOne
    @JoinColumn(name = "custom_item_id", insertable = false, updatable = false)
    private FormCustomItem formCustomItem;

    @Column(name = "custom_item_id")
    private String customItemId;

    @Column(name = "process_node_id")
    private String processNodeId;

    @Column(name = "input_type")
    private String inputType;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public FormCustomItem getFormCustomItem() {
        return formCustomItem;
    }

    public void setFormCustomItem(FormCustomItem formCustomItem) {
        this.formCustomItem = formCustomItem;
    }

    public String getProcessNodeId() {
        return processNodeId;
    }

    public void setProcessNodeId(String processNodeId) {
        this.processNodeId = processNodeId;
    }

    public String getInputType() {
        return inputType;
    }

    public void setInputType(String inputType) {
        this.inputType = inputType;
    }

    public CustomItemProcessNode() {
        super();
    }

    public CustomItemProcessNode(String customItemId, String processNodeId, String inputType) {
        super();
        this.customItemId = customItemId;
        this.processNodeId = processNodeId;
        this.inputType = inputType;
    }

}
