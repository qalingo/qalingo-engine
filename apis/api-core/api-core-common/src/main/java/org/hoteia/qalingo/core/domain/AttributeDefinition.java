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
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

@Entity
@Table(name="TECO_ATTRIBUTE_DEFINITION")
public class AttributeDefinition extends AbstractEntity {

	/**
	 * Generated UID
	 */
	private static final long serialVersionUID = -9192631267286418164L;
	
    // TODO : ENUM this
    public static int ATTRIBUTE_TYPE_SHORT_STRING   = 1;
    public static int ATTRIBUTE_TYPE_LONG_STRING    = 2;
    public static int ATTRIBUTE_TYPE_DOUBLE         = 3;
    public static int ATTRIBUTE_TYPE_FLOAT          = 4;
    public static int ATTRIBUTE_TYPE_INTEGER        = 5;
    public static int ATTRIBUTE_TYPE_BLOB           = 6;
    public static int ATTRIBUTE_TYPE_BOOLEAN        = 7;

    public static int OBJECT_TYPE_CATALOG_CATEGORY  = 1;
    public static int OBJECT_TYPE_PRODUCT_MARKETING = 2;
    public static int OBJECT_TYPE_PRODUCT_SKU       = 3;
    public static int OBJECT_TYPE_CUSTOMER          = 4;
    public static int OBJECT_TYPE_STORE             = 5;
    public static int OBJECT_TYPE_PAYMENT_GATEWAY   = 6;
    public static int OBJECT_TYPE_RULE_REFERENTIAL  = 7;
    public static int OBJECT_TYPE_MARKET_AREA       = 8;
    public static int OBJECT_TYPE_TAX               = 9;
    public static int OBJECT_TYPE_RETAILER          = 10;
	
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Version
    @Column(name = "VERSION", nullable = false, columnDefinition = "int(11) default 1")
    private int version;

    @Column(name = "CODE", nullable = false)
    private String code;
    
    @Column(name = "NAME")
    private String name;

    @Column(name = "DESCRIPTION")
    @Lob
    private String description;

    @Column(name = "ATTRIBUTE_TYPE")
    private int attributeType;

    @Column(name = "OBJECT_TYPE")
    private int objectType;

    @Column(name = "ENABLED", nullable = false, columnDefinition = "tinyint(1) default 0")
    private boolean enabled;
    
    @Column(name = "MANDATORY", nullable = false, columnDefinition = "tinyint(1) default 0")
    private boolean mandatory;
    
    @Column(name = "LOCALIZABLE", nullable = false, columnDefinition = "tinyint(1) default 0")
    private boolean localizable;

    @Column(name = "GLOBAL", nullable = false, columnDefinition = "tinyint(1) default 1")
    private boolean global;

    @Column(name = "MULTI_VALUE", nullable = false, columnDefinition = "tinyint(1) default 0")
    private boolean multiValue;

    @Column(name = "WITH_PLANNER", nullable = false, columnDefinition = "tinyint(1) default 0")
    private boolean withPlanner;

    @Column(name="ORDERING", nullable=false, columnDefinition="int(11) default 0")
    private Integer ordering;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATE_CREATE")
    private Date dateCreate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATE_UPDATE")
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
	
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

	public int getAttributeType() {
		return attributeType;
	}

    public String getAttributeType(int type) {
        if (type == ATTRIBUTE_TYPE_SHORT_STRING) {
            return "STRING";
        } else if (type == ATTRIBUTE_TYPE_DOUBLE) {
            return "DOUBLE";
        } else if (type == ATTRIBUTE_TYPE_FLOAT) {
            return "FLOAT";
        } else if (type == ATTRIBUTE_TYPE_INTEGER) {
            return "INTEGER";
        } else if (type == ATTRIBUTE_TYPE_BLOB) {
            return "BLOB";
        } else if (type == ATTRIBUTE_TYPE_BOOLEAN) {
            return "BOOLEAN";
        }
        return null;
    }

	public void setAttributeType(int attributeType) {
		this.attributeType = attributeType;
	}

	public int getObjectType() {
		return objectType;
	}

    public String getObjectType(int type) {
        if (type == OBJECT_TYPE_CATALOG_CATEGORY) {
            return "CATALOG_CATEGORY";
        } else if (type == OBJECT_TYPE_PRODUCT_MARKETING) {
            return "PRODUCT_MARKETING";
        } else if (type == OBJECT_TYPE_PRODUCT_SKU) {
            return "PRODUCT_SKU";
        } else if (type == OBJECT_TYPE_CUSTOMER) {
            return "CUSTOMER";
        } else if (type == OBJECT_TYPE_STORE) {
            return "STORE";
        } else if (type == OBJECT_TYPE_PAYMENT_GATEWAY) {
            return "PAYMENT_GATEWAY";
        } else if (type == OBJECT_TYPE_RULE_REFERENTIAL) {
            return "RULE_REFERENTIAL";
        } else if (type == OBJECT_TYPE_MARKET_AREA) {
            return "MARKET_AREA";
        } else if (type == OBJECT_TYPE_TAX) {
            return "TAX";
        }
        return null;
    }
	   
	public void setObjectType(int objectType) {
		this.objectType = objectType;
	}

	public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isMandatory() {
        return mandatory;
    }

    public void setMandatory(boolean mandatory) {
        this.mandatory = mandatory;
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

    public boolean isWithPlanner() {
        return withPlanner;
    }

    public void setWithPlanner(boolean withPlanner) {
        this.withPlanner = withPlanner;
    }

    public Integer getOrdering() {
        return ordering;
    }

    public void setOrdering(Integer ordering) {
        this.ordering = ordering;
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
        AttributeDefinition other = (AttributeDefinition) obj;
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
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "AttributeDefinition [id=" + id + ", version=" + version + ", name=" + name + ", description=" + description + ", code=" + code + ", attributeType=" + attributeType + ", objectType="
                + objectType + ", localizable=" + localizable + ", global=" + global + ", multiValue=" + multiValue + ", withPlanner=" + withPlanner + ", dateCreate=" + dateCreate + ", dateUpdate="
                + dateUpdate + "]";
    }

}