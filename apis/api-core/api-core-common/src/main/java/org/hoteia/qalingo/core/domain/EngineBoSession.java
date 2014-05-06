/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.domain;

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

import org.hoteia.qalingo.core.domain.enumtype.EnvironmentType;

@Entity
@Table(name="TBO_ENGINE_SESSION")
public class EngineBoSession extends AbstractEngineSession {

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
	
    @Column(name = "ENGINE_SESSION_GUID")
    private String engineSessionGuid;
    
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
	private Localization currentMarketAreaLocalization;
	
	@Transient 
	private Retailer currentMarketAreaRetailer;

    @Transient
    private CurrencyReferential currentMarketAreaCurrency;
    
	@Transient 
	private Localization currentBackofficeLocalization;

    @Transient
    private User currentUser;

    @Transient
    private Company currentCompany;

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
	
	public String getEngineSessionGuid() {
        return engineSessionGuid;
    }
	
	public void setEngineSessionGuid(String engineSessionGuid) {
        this.engineSessionGuid = engineSessionGuid;
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
	
	public void setCurrentMarketPlace(MarketPlace marketPlace) {
		this.currentMarketPlace = marketPlace;
	}
	
	public Market getCurrentMarket() {
		return currentMarket;
	}
	
	public void setCurrentMarket(Market market) {
		this.currentMarket = market;
	}
	
	public MarketArea getCurrentMarketArea() {
		return currentMarketArea;
	}
	
	public void setCurrentMarketArea(MarketArea marketArea) {
		this.currentMarketArea = marketArea;
	}
	
	public Localization getCurrentMarketAreaLocalization() {
		return currentMarketAreaLocalization;
	}
	
	public void setCurrentMarketAreaLocalization(Localization localization) {
		this.currentMarketAreaLocalization = localization;
	}
	
	public Retailer getCurrentMarketAreaRetailer() {
		return currentMarketAreaRetailer;
	}
	
	public void setCurrentMarketAreaRetailer(Retailer retailer) {
		this.currentMarketAreaRetailer = retailer;
	}
	
	public CurrencyReferential getCurrentMarketAreaCurrency() {
        return currentMarketAreaCurrency;
    }

    public void setCurrentMarketAreaCurrency(CurrencyReferential currentMarketAreaCurrency) {
        this.currentMarketAreaCurrency = currentMarketAreaCurrency;
    }

    public Localization getCurrentBackofficeLocalization() {
		return currentBackofficeLocalization;
	}
	
	public void setCurrentBackofficeLocalization(Localization localization) {
		this.currentBackofficeLocalization = localization;
	}
	
	public User getCurrentUser() {
		return currentUser;
	}
	
	public void setCurrentUser(User user) {
		this.currentUser = user;
	}
	
	public Company getCurrentCompany() {
        return currentCompany;
    }
	
	public void setCurrentCompany(Company currentCompany) {
        this.currentCompany = currentCompany;
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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((jSessionId == null) ? 0 : jSessionId.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        EngineBoSession other = (EngineBoSession) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (jSessionId == null) {
            if (other.jSessionId != null)
                return false;
        } else if (!jSessionId.equals(other.jSessionId))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "EngineBoSession [id=" + id + ", version=" + version + ", jSessionId=" + jSessionId + ", engineSessionGuid=" + engineSessionGuid + ", environmentStagingModeEnabled="
                + environmentStagingModeEnabled + ", environmentType=" + environmentType + ", theme=" + theme + ", device=" + device + ", dateCreate=" + dateCreate + ", dateUpdate=" + dateUpdate
                + "]";
    }
	
}