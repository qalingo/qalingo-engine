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

import java.util.Arrays;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
@Table(name="TECO_CATALOG_VIRTUAL_CATEGORY_ATTRIBUTE")
public class CatalogCategoryVirtualAttribute extends AbstractAttribute {

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
	
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="ATTRIBUTE_DEFINITION_ID", insertable = true, updatable = true)
	private AttributeDefinition attributeDefinition;
	
    @Column(name = "SHORT_STRING_VALUE")
    private String shortStringValue;

    @Column(name = "LONG_STRING_VALUE")
    @Lob
    private String longStringValue;
	
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
	
//	@Column(name="IS_GLOBAL", nullable=false, columnDefinition="tinyint(1) default 0")
//	private boolean isGlobal;
//	
//	@Column(name="ORDERING", nullable=false, columnDefinition="int(11) default 0")
//	private int ordering;
	
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

	public CatalogCategoryVirtualAttribute() {
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
    public String getShortStringValue() {
        return shortStringValue;
    }
    
    public void setShortStringValue(String shortStringValue) {
        this.shortStringValue = shortStringValue;
    }
    
    public String getLongStringValue() {
        return longStringValue;
    }
    
    public void setLongStringValue(String longStringValue) {
        this.longStringValue = longStringValue;
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

//	public boolean isGlobal() {
//		return isGlobal;
//	}
//
//	public void setGlobal(boolean isGlobal) {
//		this.isGlobal = isGlobal;
//	}
//
//	public int getOrdering() {
//		return ordering;
//	}
//	
//	public void setOrdering(int ordering) {
//		this.ordering = ordering;
//	}
	
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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((dateCreate == null) ? 0 : dateCreate.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
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
        CatalogCategoryVirtualAttribute other = (CatalogCategoryVirtualAttribute) obj;
        if (dateCreate == null) {
            if (other.dateCreate != null)
                return false;
        } else if (!dateCreate.equals(other.dateCreate))
            return false;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "CatalogCategoryVirtualAttribute [id=" + id + ", version=" + version + ", shortStringValue=" + shortStringValue + ", longStringValue=" + longStringValue + ", integerValue=" + integerValue + ", doubleValue=" + doubleValue
                + ", floatValue=" + floatValue + ", blobValue=" + Arrays.toString(blobValue) + ", booleanValue=" + booleanValue + ", localizationCode=" + localizationCode + ", startDate=" + startDate + ", endDate=" + endDate + ", dateCreate=" + dateCreate + ", dateUpdate=" + dateUpdate + "]";
    }
	
}