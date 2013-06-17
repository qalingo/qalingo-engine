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

import java.util.Arrays;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

@Entity
@Table(name="TECO_CATALOG_MASTER_CATEGORY_ATTRIBUTE")
public class CatalogCategoryMasterAttribute extends AbstractAttribute {

	/**
	 * Generated UID
	 */
	private static final long serialVersionUID = -4742739155651149445L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="ID", nullable=false)
	private Long id;
	
	@Version
	@Column(name="VERSION", nullable=false, columnDefinition="int(11) default 1")
	private int version;
	
	@ManyToOne
    @JoinColumn(name="ATTRIBUTE_DEFINITION_ID")
	private AttributeDefinition attributeDefinition;
	
	@Column(name="STRING_VALUE")
	private String stringValue;
	
	@Column(name="INTEGER_VALUE")
	private Integer integerValue;
	
	@Column(name="DOUBLE_VALUE")
	private Double doubleValue;
	
	@Column(name="FLOAT_VALUE")
	private Float floatValue;
	
	@Column(name="BLOB_VALUE")
	@Lob
	private byte[] blobValue;
	
	@Column(name="BOOLEAN_VALUE")
	private Boolean booleanValue;
	
	@Column(name="LOCALIZATION_CODE")
	private String localizationCode;
	
	@Column(name="IS_GLOBAL", nullable=false, columnDefinition="tinyint(1) default 0")
	private boolean isGlobal;
	
	@Column(name="ORDERING", nullable=false, columnDefinition="int(11) default 0")
	private Integer ordering;
	
	@Column(name="MARKET_AREA_ID")
	private Long marketAreaId;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="START_DATE")
	private Date startDate;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="END_DATE")
	private Date endDate;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATE_CREATE")
	private Date dateCreate;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATE_UPDATE")
	private Date dateUpdate;

	public CatalogCategoryMasterAttribute() {
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

	public AttributeDefinition getAttributeDefinition() {
		return attributeDefinition;
	}

	public void setAttributeDefinition(AttributeDefinition attributeDefinition) {
		this.attributeDefinition = attributeDefinition;
	}

	public String getStringValue() {
		return stringValue;
	}

	public void setStringValue(String stringValue) {
		this.stringValue = stringValue;
	}

	public Integer getIntegerValue() {
		return integerValue;
	}

	public void setIntegerValue(Integer integerValue) {
		this.integerValue = integerValue;
	}

	public Double getDoubleValue() {
		return doubleValue;
	}

	public void setDoubleValue(Double doubleValue) {
		this.doubleValue = doubleValue;
	}

	public Float getFloatValue() {
		return floatValue;
	}

	public void setFloatValue(Float floatValue) {
		this.floatValue = floatValue;
	}

	public byte[] getBlobValue() {
		return blobValue;
	}

	public void setBlobValue(byte[] blobValue) {
		this.blobValue = blobValue;
	}

	public Boolean getBooleanValue() {
		return booleanValue;
	}
	
	public void setBooleanValue(Boolean booleanValue) {
		this.booleanValue = booleanValue;
	}

	public String getLocalizationCode() {
		return localizationCode;
	}

	public void setLocalizationCode(String localizationCode) {
		this.localizationCode = localizationCode;
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

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
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
	
	public String getValueAsString() {
		if(attributeDefinition.getAttributeType() == AttributeDefinition.ATTRIBUTE_TYPE_STRING){
			if(getStringValue() != null){
				return getStringValue();
			}
		} else if(attributeDefinition.getAttributeType() == AttributeDefinition.ATTRIBUTE_TYPE_DOUBLE){
			if(getDoubleValue() != null){
				return getDoubleValue().toString();
			}
		} else if(attributeDefinition.getAttributeType() == AttributeDefinition.ATTRIBUTE_TYPE_FLOAT){
			if(getFloatValue() != null){
				return getFloatValue().toString();
			}
		} else if(attributeDefinition.getAttributeType() == AttributeDefinition.ATTRIBUTE_TYPE_INTEGER){
			if(getIntegerValue() != null){
				return getIntegerValue().toString();
			}
		} else if(attributeDefinition.getAttributeType() == AttributeDefinition.ATTRIBUTE_TYPE_BLOB){
			if(getBlobValue() != null){
				return getBlobValue().toString();
			}
		} else if(attributeDefinition.getAttributeType() == AttributeDefinition.ATTRIBUTE_TYPE_BOOLEAN){
			if(getBooleanValue() != null){
				return getBooleanValue().toString();
			}
		}
		return null;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(blobValue);
		result = prime * result
				+ ((booleanValue == null) ? 0 : booleanValue.hashCode());
		result = prime * result
				+ ((dateCreate == null) ? 0 : dateCreate.hashCode());
		result = prime * result
				+ ((dateUpdate == null) ? 0 : dateUpdate.hashCode());
		result = prime * result
				+ ((doubleValue == null) ? 0 : doubleValue.hashCode());
		result = prime * result + ((endDate == null) ? 0 : endDate.hashCode());
		result = prime * result
				+ ((floatValue == null) ? 0 : floatValue.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((integerValue == null) ? 0 : integerValue.hashCode());
		result = prime
				* result
				+ ((localizationCode == null) ? 0 : localizationCode.hashCode());
		result = prime * result
				+ ((startDate == null) ? 0 : startDate.hashCode());
		result = prime * result
				+ ((stringValue == null) ? 0 : stringValue.hashCode());
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
		CatalogCategoryMasterAttribute other = (CatalogCategoryMasterAttribute) obj;
		if (!Arrays.equals(blobValue, other.blobValue))
			return false;
		if (booleanValue == null) {
			if (other.booleanValue != null)
				return false;
		} else if (!booleanValue.equals(other.booleanValue))
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
		if (doubleValue == null) {
			if (other.doubleValue != null)
				return false;
		} else if (!doubleValue.equals(other.doubleValue))
			return false;
		if (endDate == null) {
			if (other.endDate != null)
				return false;
		} else if (!endDate.equals(other.endDate))
			return false;
		if (floatValue == null) {
			if (other.floatValue != null)
				return false;
		} else if (!floatValue.equals(other.floatValue))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (integerValue == null) {
			if (other.integerValue != null)
				return false;
		} else if (!integerValue.equals(other.integerValue))
			return false;
		if (localizationCode == null) {
			if (other.localizationCode != null)
				return false;
		} else if (!localizationCode.equals(other.localizationCode))
			return false;
		if (startDate == null) {
			if (other.startDate != null)
				return false;
		} else if (!startDate.equals(other.startDate))
			return false;
		if (stringValue == null) {
			if (other.stringValue != null)
				return false;
		} else if (!stringValue.equals(other.stringValue))
			return false;
		if (version != other.version)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ProductCategoryAttribute [id=" + id + ", version=" + version
				+ ", stringValue=" + stringValue + ", integerValue="
				+ integerValue + ", doubleValue=" + doubleValue
				+ ", floatValue=" + floatValue + ", blobValue="
				+ Arrays.toString(blobValue) + ", booleanValue=" + booleanValue
				+ ", localizationCode=" + localizationCode + ", startDate=" + startDate + ", endDate="
				+ endDate + ", dateCreate=" + dateCreate + ", dateUpdate="
				+ dateUpdate + ",attributeDefinition=" + attributeDefinition + "]";
	}
	
}
