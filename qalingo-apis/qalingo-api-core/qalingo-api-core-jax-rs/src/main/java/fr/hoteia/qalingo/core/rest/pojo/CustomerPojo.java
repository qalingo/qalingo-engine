/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package fr.hoteia.qalingo.core.rest.pojo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import fr.hoteia.qalingo.core.domain.enumtype.CustomerNetworkOrigin;
import fr.hoteia.qalingo.core.domain.enumtype.CustomerPlatformOrigin;

/**
 * 
 * <p>
 * <a href="CustomerJsonPojo.java.html"><i>View Source</i></a>
 * </p>
 * 
 * @author Denis Gosset <a href="http://www.hoteia.com"><i>Hoteia.com</i></a>
 * @author Fingy
 * 
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomerPojo {

    private Long id;
    private int version;
    private String login;
    private String title;
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private String defaultLocale;
    private boolean active;
    private Date dateCreate;
    private Date dateUpdate;
    private CustomerPlatformOrigin platformOrigin;
    private CustomerNetworkOrigin networkOrigin;

    private Collection<CustomerAddressPojo> addresses = new ArrayList<CustomerAddressPojo>();
    private Collection<CustomerConnectionLogPojo> connectionLogs = new ArrayList<CustomerConnectionLogPojo>();
    private Collection<CustomerMarketAreaPojo> customerMarketAreas = new ArrayList<CustomerMarketAreaPojo>();
    private Collection<CustomerAttributePojo> customerAttributes = new ArrayList<CustomerAttributePojo>();
    private Collection<CustomerGroupPojo> customerGroups = new ArrayList<CustomerGroupPojo>();
    private Collection<CustomerOAuthPojo> oauthAccesses = new HashSet<CustomerOAuthPojo>();

    public CustomerPojo() {}

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(final int version) {
        this.version = version;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(final String login) {
        this.login = login;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(final String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(final String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public String getDefaultLocale() {
        return defaultLocale;
    }

    public void setDefaultLocale(final String defaultLocale) {
        this.defaultLocale = defaultLocale;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(final boolean active) {
        this.active = active;
    }

    public Date getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(final Date dateCreate) {
        this.dateCreate = dateCreate;
    }

    public Date getDateUpdate() {
        return dateUpdate;
    }

    public void setDateUpdate(final Date dateUpdate) {
        this.dateUpdate = dateUpdate;
    }

    public CustomerPlatformOrigin getPlatformOrigin() {
        return platformOrigin;
    }

    public void setPlatformOrigin(final CustomerPlatformOrigin platformOrigin) {
        this.platformOrigin = platformOrigin;
    }

    public CustomerNetworkOrigin getNetworkOrigin() {
        return networkOrigin;
    }

    public void setNetworkOrigin(final CustomerNetworkOrigin networkOrigin) {
        this.networkOrigin = networkOrigin;
    }

    public Collection<CustomerAddressPojo> getAddresses() {
        return addresses;
    }

    public void setAddresses(final Collection<CustomerAddressPojo> addresses) {
        this.addresses = new ArrayList<CustomerAddressPojo>(addresses);
    }

    public Collection<CustomerConnectionLogPojo> getConnectionLogs() {
        return connectionLogs;
    }

    public void setConnectionLogs(final Collection<CustomerConnectionLogPojo> connectionLogs) {
        this.connectionLogs = new ArrayList<CustomerConnectionLogPojo>(connectionLogs);
    }

    public Collection<CustomerMarketAreaPojo> getCustomerMarketAreas() {
        return customerMarketAreas;
    }

    public void setCustomerMarketAreas(final Collection<CustomerMarketAreaPojo> customerMarketAreas) {
        this.customerMarketAreas = new ArrayList<CustomerMarketAreaPojo>(customerMarketAreas);
    }

    public Collection<CustomerAttributePojo> getCustomerAttributes() {
        return customerAttributes;
    }

    public void setCustomerAttributes(final Collection<CustomerAttributePojo> customerAttributes) {
        this.customerAttributes = new ArrayList<CustomerAttributePojo>(customerAttributes);
    }

    public Collection<CustomerGroupPojo> getCustomerGroups() {
        return customerGroups;
    }

    public void setCustomerGroups(final Collection<CustomerGroupPojo> customerGroups) {
        this.customerGroups = new ArrayList<CustomerGroupPojo>(customerGroups);
    }

    public Collection<CustomerOAuthPojo> getOauthAccesses() {
        return oauthAccesses;
    }

    public void setOauthAccesses(final Collection<CustomerOAuthPojo> oauthAccesses) {
        this.oauthAccesses = oauthAccesses;
    }
}