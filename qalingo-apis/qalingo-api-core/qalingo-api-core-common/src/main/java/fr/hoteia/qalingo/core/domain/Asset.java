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
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import fr.hoteia.qalingo.core.domain.enumtype.AssetScope;
import fr.hoteia.qalingo.core.domain.enumtype.AssetType;
import fr.hoteia.qalingo.core.domain.enumtype.ImageSize;

@Entity
@Table(name="TECO_ASSET")
public class Asset implements Serializable {

	/**
	 * Generated UID
	 */
	private static final long serialVersionUID = -6464078723126013413L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="ID", nullable=false)
	private Long id;
	
	@Version
	@Column(name="VERSION", nullable=false, columnDefinition="int(11) default 1")
	private int version;
	
	@Column(name="NAME")
	private String name;

	@Column(name="CODE")
	private String code;
	
	@Column(name="DESCRIPTION")
	private String description;
	
	@Column(name="PATH")
	private String path;
	
	@Column(name="SCOPE")
	@Enumerated(EnumType.STRING) 
	private AssetScope scope;
	
	@Column(name="TYPE")
	@Enumerated(EnumType.STRING) 
	private AssetType type;
	
	@Column(name="SIZE")
	@Enumerated(EnumType.STRING) 
	private ImageSize size;

	@Column(name="FILE_SIZE")
	private Long fileSize;

	@Column(name="IS_DEFAULT", nullable=false, columnDefinition="tinyint(1) default 0")
	private boolean isDefault;
	
	@Column(name="IS_GLOBAL", nullable=false, columnDefinition="tinyint(1) default 0")
	private boolean isGlobal;

	@Column(name="ORDERING", nullable=false, columnDefinition="int(11) default 0")
	private Integer ordering;
	
	@Column(name="MARKET_AREA_ID")
	private Long marketAreaId;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATE_CREATE")
	private Date dateCreate;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATE_UPDATE")
	private Date dateUpdate;

	public Asset(){
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
	
	public String getCode() {
		return code;
	}
	
	public void setCode(String code) {
		this.code = code;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getPath() {
		return path;
	}
	
	public void setPath(String path) {
		this.path = path;
	}
	
	public AssetScope getScope() {
		return scope;
	}
	
	public void setScope(AssetScope scope) {
		this.scope = scope;
	}
	
	public AssetType getType() {
		return type;
	}
	
	public void setType(AssetType type) {
		this.type = type;
	}
	
	public ImageSize getSize() {
		return size;
	}
	
	public void setSize(ImageSize size) {
		this.size = size;
	}
	
	public Long getFileSize() {
		return fileSize;
	}
	
	public void setFileSize(Long fileSize) {
		this.fileSize = fileSize;
	}
	
	public boolean isDefault() {
		return isDefault;
	}

	public void setDefault(boolean isDefault) {
		this.isDefault = isDefault;
	}

	public boolean isGlobal() {
		return isGlobal;
	}

	public void setGlobal(boolean isGlobal) {
		this.isGlobal = isGlobal;
	}
	
	public Integer getOrdering() {
		return ordering;
	}
	
	public void setOrdering(Integer ordering) {
		this.ordering = ordering;
	}
	
	public Long getMarketAreaId() {
		return marketAreaId;
	}

	public void setMarketAreaId(Long marketAreaId) {
		this.marketAreaId = marketAreaId;
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
		result = prime * result + (isGlobal ? 1231 : 1237);
		result = prime * result
				+ ((marketAreaId == null) ? 0 : marketAreaId.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result
				+ ((ordering == null) ? 0 : ordering.hashCode());
		result = prime * result + ((path == null) ? 0 : path.hashCode());
		result = prime * result + ((size == null) ? 0 : size.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		Asset other = (Asset) obj;
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
		if (isGlobal != other.isGlobal)
			return false;
		if (marketAreaId == null) {
			if (other.marketAreaId != null)
				return false;
		} else if (!marketAreaId.equals(other.marketAreaId))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (ordering == null) {
			if (other.ordering != null)
				return false;
		} else if (!ordering.equals(other.ordering))
			return false;
		if (path == null) {
			if (other.path != null)
				return false;
		} else if (!path.equals(other.path))
			return false;
		if (size != other.size)
			return false;
		if (type != other.type)
			return false;
		if (version != other.version)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ProductMarketingAsset [id=" + id + ", version=" + version
				+ ", name=" + name + ", code=" + code + ", description="
				+ description + ", path=" + path + ", type=" + type + ", size="
				+ size + ", isDefault=" + isDefault + ", isGlobal=" + isGlobal
				+ ", ordering=" + ordering + ", marketAreaId=" + marketAreaId
				+ ", dateCreate=" + dateCreate + ", dateUpdate=" + dateUpdate
				+ "]";
	}

}