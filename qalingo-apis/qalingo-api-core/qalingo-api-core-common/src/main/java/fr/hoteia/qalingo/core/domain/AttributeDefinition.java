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
import javax.persistence.Version;

@Entity
@Table(name="TECO_ATTRIBUTE_DEFINITION")
public class AttributeDefinition implements Serializable {

	/**
	 * Generated UID
	 */
	private static final long serialVersionUID = -9192631267286418164L;
	
	// TODO : ENUM this
	public static int ATTRIBUTE_TYPE_STRING = 1;
	public static int ATTRIBUTE_TYPE_DOUBLE = 2;
	public static int ATTRIBUTE_TYPE_FLOAT = 3;
	public static int ATTRIBUTE_TYPE_INTEGER = 4;
	public static int ATTRIBUTE_TYPE_BLOB = 5;
	public static int ATTRIBUTE_TYPE_BOOLEAN = 6;

	public static int OBJECT_TYPE_PRODUCT_CATEGORY = 1;
	public static int OBJECT_TYPE_PRODUCT_MARKETING = 2;
	public static int OBJECT_TYPE_PRODUCT_SKU = 3;
	public static int OBJECT_TYPE_CUSTOMER = 4;
	public static int OBJECT_TYPE_STORE = 5;
	public static int OBJECT_TYPE_PAYMENT_GATEWAY = 6;
	public static int OBJECT_TYPE_RULE_REFERENTIAL = 7;
	public static int OBJECT_TYPE_MARKET_AREA = 8;
	
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
	
	@Column(name="CODE")
	private String code;
	
	@Column(name="ATTRIBUTE_TYPE")
	private int attributeType;
	
	@Column(name="OBJECT_TYPE")
	private int objectType;
	
	@Column(name="LOCALIZABLE", nullable=false, columnDefinition="tinyint(1) default 0")
	private boolean localizable;
	
	@Column(name="GLOBAL", nullable=false, columnDefinition="tinyint(1) default 1")
	private boolean global;
	
	@Column(name="MULTI_VALUE", nullable=false, columnDefinition="tinyint(1) default 0")
	private boolean multiValue;
	
	@Column(name="PLANNED", nullable=false, columnDefinition="tinyint(1) default 0")
	private boolean planned;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATE_CREATE")
	private Date dateCreate;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATE_UPDATE")
	private Date dateUpdate;

	public AttributeDefinition() {
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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public int getAttributeType() {
		return attributeType;
	}

	public void setAttributeType(int attributeType) {
		this.attributeType = attributeType;
	}

	public int getObjectType() {
		return objectType;
	}

	public void setObjectType(int objectType) {
		this.objectType = objectType;
	}

	public boolean isLocalizable() {
		return localizable;
	}

	public void setLocalizable(boolean localizable) {
		this.localizable = localizable;
	}

	public boolean isGlobal() {
		return global;
	}

	public void setGlobal(boolean global) {
		this.global = global;
	}

	public boolean isMultiValue() {
		return multiValue;
	}

	public void setMultiValue(boolean multiValue) {
		this.multiValue = multiValue;
	}

	public boolean isPlanned() {
		return planned;
	}

	public void setPlanned(boolean planned) {
		this.planned = planned;
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
		result = prime * result + attributeType;
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result
				+ ((dateCreate == null) ? 0 : dateCreate.hashCode());
		result = prime * result
				+ ((dateUpdate == null) ? 0 : dateUpdate.hashCode());
		result = prime * result
				+ ((description == null) ? 0 : description.hashCode());
		result = prime * result + (global ? 1231 : 1237);
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + (localizable ? 1231 : 1237);
		result = prime * result + (multiValue ? 1231 : 1237);
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + objectType;
		result = prime * result + (planned ? 1231 : 1237);
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
		AttributeDefinition other = (AttributeDefinition) obj;
		if (attributeType != other.attributeType)
			return false;
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
		if (global != other.global)
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (localizable != other.localizable)
			return false;
		if (multiValue != other.multiValue)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (objectType != other.objectType)
			return false;
		if (planned != other.planned)
			return false;
		if (version != other.version)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "AttributeDefinition [id=" + id + ", version=" + version
				+ ", name=" + name + ", description=" + description + ", code="
				+ code + ", attributeType=" + attributeType + ", objectType="
				+ objectType + ", localizable=" + localizable + ", global="
				+ global + ", multiValue=" + multiValue + ", planned="
				+ planned + ", dateCreate=" + dateCreate + ", dateUpdate="
				+ dateUpdate + "]";
	}

}