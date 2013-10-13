package fr.hoteia.qalingo.core.rest.pojo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

public class CustomerGroupPojo {

    private Long id;
    private int version;
    private String name;
    private String description;
    private String code;
    private Date dateCreate;
    private Date dateUpdate;
    private Collection<CustomerRolePojo> customerRoles;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Date getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(Date dateCreate) {
        this.dateCreate = dateCreate;
    }

    public Date getDateUpdate() {
        return dateUpdate;
    }

    public void setDateUpdate(Date dateUpdate) {
        this.dateUpdate = dateUpdate;
    }

    public Collection<CustomerRolePojo> getCustomerRoles() {
        return customerRoles;
    }

    public void setCustomerRoles(Collection<CustomerRolePojo> customerRoles) {
        this.customerRoles = new ArrayList<CustomerRolePojo>(customerRoles);
    }
}
