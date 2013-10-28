/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package fr.hoteia.qalingo.core.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.Version;

import fr.hoteia.qalingo.core.domain.enumtype.EnvironmentType;

@Entity
@Table(name="TBO_ENGINE_SESSION")
public class EngineBoSession implements Serializable {

	/**
	 * Generated UID
	 */
	private static final long serialVersionUID = 7002076520560106074L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="ID", nullable=false)
	private Long id;
	
	@Version
	@Column(name="VERSION", nullable=false, columnDefinition="int(11) default 1")
	private int version;
	
	@Column(name="JSESSION_ID")
	private String jSessionId;
	
    @Transient
    private boolean environmentStagingModeEnabled;
    
    @Transient
    private EnvironmentType environmentType;
    
	@Transient 
	private MarketPlace currentMarketPlace;

	@Transient 
	private Market currentMarket;

	@Transient 
	private MarketArea currentMarketArea;

	@Transient 
	private Localization currentMarketLocalization;
	
	@Transient 
	private Retailer currentRetailer;

	@Transient 
	private Localization currentLocalization;

	@Transient 
	private User currentUser;
	
	@Transient 
	private String theme;
	
	@Transient 
	private String device;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATE_CREATE")
	private Date dateCreate;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATE_UPDATE")
	private Date dateUpdate;

	public EngineBoSession(){
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

	public String getjSessionId() {
		return jSessionId;
	}
	
	public void setjSessionId(String jSessionId) {
		this.jSessionId = jSessionId;
	}
	
    public boolean isEnvironmentStagingModeEnabled() {
        return environmentStagingModeEnabled;
    }

    public void setEnvironmentStagingModeEnabled(boolean environmentStagingModeEnabled) {
        this.environmentStagingModeEnabled = environmentStagingModeEnabled;
    }
    
    public EnvironmentType getEnvironmentType() {
        return environmentType;
    }
    
    public void setEnvironmentType(EnvironmentType environmentType) {
        this.environmentType = environmentType;
    }

	public MarketPlace getCurrentMarketPlace() {
		return currentMarketPlace;
	}
	
	public void setCurrentMarketPlace(MarketPlace currentMarketPlace) {
		this.currentMarketPlace = currentMarketPlace;
	}
	
	public Market getCurrentMarket() {
		return currentMarket;
	}
	
	public void setCurrentMarket(Market currentMarket) {
		this.currentMarket = currentMarket;
	}
	
	public MarketArea getCurrentMarketArea() {
		return currentMarketArea;
	}
	
	public void setCurrentMarketArea(MarketArea currentMarketArea) {
		this.currentMarketArea = currentMarketArea;
	}
	
	public Localization getCurrentMarketLocalization() {
		return currentMarketLocalization;
	}
	
	public void setCurrentMarketLocalization(Localization currentMarketLocalization) {
		this.currentMarketLocalization = currentMarketLocalization;
	}
	
	public Retailer getCurrentRetailer() {
		return currentRetailer;
	}
	
	public void setCurrentRetailer(Retailer currentRetailer) {
		this.currentRetailer = currentRetailer;
	}
	
	public Localization getCurrentLocalization() {
		return currentLocalization;
	}
	
	public void setCurrentLocalization(Localization currentLocalization) {
		this.currentLocalization = currentLocalization;
	}
	
	public User getCurrentUser() {
		return currentUser;
	}
	
	public void setCurrentUser(User currentUser) {
		this.currentUser = currentUser;
	}
	
	public String getTheme() {
		return theme;
	}
	
	public void setTheme(String theme) {
		this.theme = theme;
	}
	
	public String getDevice() {
		return device;
	}
	
	public void setDevice(String device) {
		this.device = device;
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

	
}
