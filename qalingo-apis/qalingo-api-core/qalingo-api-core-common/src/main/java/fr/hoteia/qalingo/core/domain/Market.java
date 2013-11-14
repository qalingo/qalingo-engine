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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

@Entity
@Table(name="TECO_MARKET")
public class Market implements Serializable {

	/**
	 * Generated UID
	 */
	private static final long serialVersionUID = 5759002146568820577L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="ID", nullable=false)
	private Long id;
	
	@Version
	@Column(name="VERSION", nullable=false, columnDefinition="int(11) default 1")
	private int version;
	
	@Column(name="NAME")
	private String name;

	@Column(name="DESCRIPTION")
	private String description;
	
	@Column(name="IS_DEFAULT", nullable=false, columnDefinition="tinyint(1) default 0")
	private boolean isDefault;
	
	@Column(name="CODE")
	private String code;
	
	@Column(name="THEME")
	private String theme;
	
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="MARKETPLACE_ID", insertable=false, updatable=false)
	private MarketPlace marketPlace;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name="MARKET_ID")
	private Set<MarketArea> marketAreas = new HashSet<MarketArea>(); 
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATE_CREATE")
	private Date dateCreate;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATE_UPDATE")
	private Date dateUpdate;

	public Market(){
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
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public boolean isDefault() {
		return isDefault;
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
	
	public MarketPlace getMarketPlace() {
		return marketPlace;
	}
	
	public void setMarketPlace(MarketPlace marketPlace) {
		this.marketPlace = marketPlace;
	}
	
	public Set<MarketArea> getMarketAreas() {
		return marketAreas;
	}
	
	public MarketArea getMarketArea(String marketAreaCode) {
		MarketArea marketAreaToReturn = null;
		Set<MarketArea> marketAreas = getMarketAreas();
		if(marketAreas != null
				&& marketAreas.size() > 0){
			for (Iterator<MarketArea> iterator = marketAreas.iterator(); iterator.hasNext();) {
				MarketArea marketArea = (MarketArea) iterator.next();
				if(marketArea.getCode().equalsIgnoreCase(marketAreaCode)){
					marketAreaToReturn = marketArea;
				}
			}
		}
		return marketAreaToReturn;
	}
	
	public void setMarketAreas(Set<MarketArea> marketAreas) {
		this.marketAreas = marketAreas;
	}
	
	public MarketArea getDefaultMarketArea() {
		MarketArea defaultMarketArea = null;
		Set<MarketArea> marketAreas = getMarketAreas();
		if(marketAreas != null
				&& marketAreas.size() > 0){
			for (Iterator<MarketArea> iterator = marketAreas.iterator(); iterator.hasNext();) {
				MarketArea marketArea = (MarketArea) iterator.next();
				if(marketArea.isDefault()){
					defaultMarketArea = marketArea;
				}
			}
			if(defaultMarketArea == null){
				Iterator<MarketArea> iterator = marketAreas.iterator();
				defaultMarketArea = (MarketArea) iterator.next();
			}
		}
		return defaultMarketArea;
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
		result = prime * result
				+ ((marketAreas == null) ? 0 : marketAreas.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		Market other = (Market) obj;
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
		if (marketAreas == null) {
			if (other.marketAreas != null)
				return false;
		} else if (!marketAreas.equals(other.marketAreas))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (version != other.version)
			return false;
		return true;
	}

	@Override
    public String toString() {
	    return "Market [id=" + id + ", version=" + version + ", name=" + name + ", description=" + description + ", isDefault=" + isDefault + ", code=" + code + ", theme=" + theme + ", marketPlace="
	            + marketPlace + ", marketAreas=" + marketAreas + ", dateCreate=" + dateCreate + ", dateUpdate=" + dateUpdate + "]";
    }

}