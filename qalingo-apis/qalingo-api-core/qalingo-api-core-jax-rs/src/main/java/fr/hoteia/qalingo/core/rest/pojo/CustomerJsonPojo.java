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
import java.util.Date;
import java.util.List;

import org.codehaus.jackson.annotate.JsonAnySetter;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import fr.hoteia.qalingo.core.domain.CustomerAddress;
import fr.hoteia.qalingo.core.domain.CustomerAttribute;
import fr.hoteia.qalingo.core.domain.CustomerConnectionLog;
import fr.hoteia.qalingo.core.domain.CustomerGroup;
import fr.hoteia.qalingo.core.domain.CustomerMarketArea;
import fr.hoteia.qalingo.core.json.pojo.AbstractJsonPojo;

/**
 *
 * <p>
 * <a href="CustomerJsonPojo.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Denis Gosset <a href="http://www.hoteia.com"><i>Hoteia.com</i></a>
 * 
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class CustomerJsonPojo extends AbstractJsonPojo {
	
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
	
	private List<CustomerAddress> addresses = new ArrayList<CustomerAddress>(); 
	private List<CustomerConnectionLog> connectionLogs = new ArrayList<CustomerConnectionLog>(); 
	private List<CustomerMarketArea> customerMarketAreas = new ArrayList<CustomerMarketArea>(); 
	private List<CustomerAttribute> customerAttributes = new ArrayList<CustomerAttribute>(); 
	private List<CustomerGroup> customerGroups = new ArrayList<CustomerGroup>(); 
	
	public CustomerJsonPojo() {
	}
	
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

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDefaultLocale() {
		return defaultLocale;
	}

	public void setDefaultLocale(String defaultLocale) {
		this.defaultLocale = defaultLocale;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
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

	public List<CustomerAddress> getAddresses() {
		return addresses;
	}

	public void setAddresses(List<CustomerAddress> addresses) {
		this.addresses = addresses;
	}

	public List<CustomerConnectionLog> getConnectionLogs() {
		return connectionLogs;
	}

	public void setConnectionLogs(List<CustomerConnectionLog> connectionLogs) {
		this.connectionLogs = connectionLogs;
	}

	public List<CustomerMarketArea> getCustomerMarketAreas() {
		return customerMarketAreas;
	}

	public void setCustomerMarketAreas(List<CustomerMarketArea> customerMarketAreas) {
		this.customerMarketAreas = customerMarketAreas;
	}

	public List<CustomerAttribute> getCustomerAttributes() {
		return customerAttributes;
	}

	public void setCustomerAttributes(List<CustomerAttribute> customerAttributes) {
		this.customerAttributes = customerAttributes;
	}

	public List<CustomerGroup> getCustomerGroups() {
		return customerGroups;
	}

	public void setCustomerGroups(List<CustomerGroup> customerGroups) {
		this.customerGroups = customerGroups;
	}

	@JsonAnySetter
	@Override
	public void handleUnknown(String key, Object value) {
		super.handleUnknown(key, value);
	}
}