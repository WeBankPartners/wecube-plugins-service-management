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
@Table(name = "form_custom_item")
@GenericGenerator(name = "jpa-uuid", strategy = "uuid")
public class FormCustomItem {
    @Id
    @GeneratedValue(generator = "jpa-uuid")
    @Column(name = "id", length = 32)
    private String id;

    @Column(name = "service_form_id")
    private String serviceFormId;
    @ManyToOne
    @JoinColumn(name = "service_form_id", insertable = false, updatable = false)
    private ServiceForm serviceForm;

    @Column(name = "name")
    private String name;
    @Column(name = "display_name")
    private String displayName;
    @Column(name = "data_type")
    private String dataType;
    @Column(name = "length")
    private int length;

    public FormCustomItem() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ServiceForm getServiceForm() {
        return serviceForm;
    }

    public void setServiceForm(ServiceForm serviceForm) {
        this.serviceForm = serviceForm;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public FormCustomItem(String serviceFormId, String name, String displayName,
            String dataType, int length) {
        super();
        this.serviceFormId = serviceFormId;
        this.name = name;
        this.displayName = displayName;
        this.dataType = dataType;
        this.length = length;
    }

}
