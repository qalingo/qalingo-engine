package org.hoteia.qalingo.core.pojo.customer;

import java.util.Date;

public class CustomerConnectionLogPojo {

    private Long id;
    private Date loginDate;
    private String app;
    private String host;
    private String address;
    private Long customerId;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public Date getLoginDate() {
        return loginDate;
    }

    public void setLoginDate(final Date loginDate) {
        this.loginDate = loginDate;
    }

    public String getApp() {
        return app;
    }

    public void setApp(final String app) {
        this.app = app;
    }

    public String getHost() {
        return host;
    }

    public void setHost(final String host) {
        this.host = host;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(final String address) {
        this.address = address;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(final Long customerId) {
        this.customerId = customerId;
    }

}
