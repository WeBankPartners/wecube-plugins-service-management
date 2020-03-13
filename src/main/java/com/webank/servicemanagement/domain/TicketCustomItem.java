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
@Table(name = "ticket_custom_item")
@GenericGenerator(name = "jpa-uuid", strategy = "uuid")
public class TicketCustomItem {
    @Id
    @GeneratedValue(generator = "jpa-uuid")
    @Column(name = "id", length = 32)
    private String id;

    @ManyToOne
    @JoinColumn(name = "service_ticket_id", insertable = false, updatable = false)
    private ServiceTicket serviceTicket;
    @Column(name = "service_ticket_id")
    private String serviceTicketId;

    @ManyToOne
    @JoinColumn(name = "form_custom_item_id", insertable = false, updatable = false)
    private FormCustomItem formCustomItem;
    @Column(name = "form_custom_item_id")
    private String formCustomItemId;

    @Column(name = "value")
    private String value;

    public TicketCustomItem() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ServiceTicket getServiceTicket() {
        return serviceTicket;
    }

    public void setServiceTicket(ServiceTicket serviceTicket) {
        this.serviceTicket = serviceTicket;
    }

    public String getServiceTicketId() {
        return serviceTicketId;
    }

    public void setServiceTicketId(String serviceTicketId) {
        this.serviceTicketId = serviceTicketId;
    }

    public FormCustomItem getFormCustomItem() {
        return formCustomItem;
    }

    public void setFormCustomItem(FormCustomItem formCustomItem) {
        this.formCustomItem = formCustomItem;
    }

    public String getFormCustomItemId() {
        return formCustomItemId;
    }

    public void setFormCustomItemId(String formCustomItemId) {
        this.formCustomItemId = formCustomItemId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public TicketCustomItem(String serviceTicketId, String formCustomItemId, String value) {
        super();
        this.serviceTicketId = serviceTicketId;
        this.formCustomItemId = formCustomItemId;
        this.value = value;
    }

}
