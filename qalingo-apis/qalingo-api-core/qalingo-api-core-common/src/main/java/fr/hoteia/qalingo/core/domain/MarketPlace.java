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
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

@Entity
@Table(name="TECO_MARKETPLACE")
public class MarketPlace implements Serializable {

	/**
	 * Generated UID
	 */
	private static final long serialVersionUID = -8834213059142444939L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="ID", nullable=false)
	private Long id;
	
	@Version
	@Column(name="VERSION", nullable=false, columnDefinition="int(11) default 1")
	private int version;
	
	@Column(name="NAME")
	private String name;

	@Column(name="PATH")
	private String path;

	@Column(name="DESCRIPTION")
	private String description;
	
	@Column(name="IS_DEFAULT", nullable=false, columnDefinition="tinyint(1) default 0")
	private boolean isDefault;
	
	@Column(name="CODE")
	private String code;
	
	@Column(name="THEME")
	private String theme;
	
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name="MASTER_CATALOG_ID")
	private CatalogMaster masterCatalog;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name="MARKETPLACE_ID")
	private Set<Market> markets = new HashSet<Market>(); 
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATE_CREATE")
	private Date dateCreate;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATE_UPDATE")
	private Date dateUpdate;

	public MarketPlace(){
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public boolean isDefault() {
		return isDefault;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public void setDefault(boolean isDefault) {
		this.isDefault = isDefault;
	}

	public String getCode() {
		return code;
	}
	
	public void setCode(String code) {
		this.code = code;
	}
	
	public String getTheme() {
		return theme;
	}
	
	public void setTheme(String theme) {
		this.theme = theme;
	}
	
	public CatalogMaster getMasterCatalog() {
		return masterCatalog;
	}
	
	public void setMasterCatalog(CatalogMaster masterCatalog) {
		this.masterCatalog = masterCatalog;
	}
	
	public Set<Market> getMarkets() {
		return markets;
	}
	
	public Market getDefaultMarket() {
		Market defaultMarket = null;
		Set<Market> markets = getMarkets();
		if(markets != null
				&& markets.size() > 0){
			for (Iterator<Market> iterator = markets.iterator(); iterator.hasNext();) {
				Market market = (Market) iterator.next();
				if(market.isDefault()){
					defaultMarket = market;
				}
			}
			if(defaultMarket == null){
				Iterator<Market> iterator = markets.iterator();
				defaultMarket = (Market) iterator.next();
			}
		}
		return defaultMarket;
	}
	
	public Market getMarket(String marketCode) {
		Market marketToReturn = null;
		Set<Market> markets = getMarkets();
		if(markets != null
				&& markets.size() > 0){
			for (Iterator<Market> iterator = markets.iterator(); iterator.hasNext();) {
				Market market = (Market) iterator.next();
				if(market.getCode().equalsIgnoreCase(marketCode)){
					marketToReturn = market;
				}
			}
		}
		return marketToReturn;
	}
	
	public void setMarkets(Set<Market> markets) {
		this.markets = markets;
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
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result
				+ ((dateCreate == null) ? 0 : dateCreate.hashCode());
		result = prime * result
				+ ((dateUpdate == null) ? 0 : dateUpdate.hashCode());
		result = prime * result
				+ ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + (isDefault ? 1231 : 1237);
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((path == null) ? 0 : path.hashCode());
		result = prime * result + version;
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
		MarketPlace other = (MarketPlace) obj;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		if (dateCreate == null) {
			if (other.dateCreate != null)
				return false;
		} else if (!dateCreate.equals(other.dateCreate))
			return false;
		if (dateUpdate == null) {
			if (other.dateUpdate != null)
				return false;
		} else if (!dateUpdate.equals(other.dateUpdate))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (isDefault != other.isDefault)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (path == null) {
			if (other.path != null)
				return false;
		} else if (!path.equals(other.path))
			return false;
		if (version != other.version)
			return false;
		return true;
	}

	@Override
    public String toString() {
	    return "MarketPlace [id=" + id + ", version=" + version + ", name=" + name + ", path=" + path + ", description=" + description + ", isDefault=" + isDefault + ", code=" + code + ", theme="
	            + theme + ", dateCreate=" + dateCreate + ", dateUpdate=" + dateUpdate + "]";
    }
	
}