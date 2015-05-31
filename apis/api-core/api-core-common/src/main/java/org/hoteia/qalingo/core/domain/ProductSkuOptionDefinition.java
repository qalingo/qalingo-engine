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
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import org.apache.commons.lang.StringUtils;

@Entity
@Table(name = "TECO_PRODUCT_SKU_OPTION_DEFINITION")
public class ProductSkuOptionDefinition extends AbstractExtendEntity<ProductSkuOptionDefinition, ProductSkuOptionDefinitionAttribute> {

    /**
     * Generated UID
     */
    private static final long serialVersionUID = 8407137582137874358L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Version
    @Column(name = "VERSION", nullable = false, columnDefinition = "int(11) default 1")
    private int version;

    @Column(name = "CODE", unique = true, nullable = false)
    private String code;

    @Column(name = "NAME")
    private String name;

    @Column(name = "DESCRIPTION")
    @Lob
    private String description;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, targetEntity = org.hoteia.qalingo.core.domain.ProductSkuOptionDefinitionAttribute.class)
    @JoinColumn(name = "PRODUCT_SKU_OPTION_DEFINITION_ID")
    private Set<ProductSkuOptionDefinitionAttribute> attributes = new HashSet<ProductSkuOptionDefinitionAttribute>();

    @ManyToOne(fetch = FetchType.EAGER, targetEntity = org.hoteia.qalingo.core.domain.ProductSkuOptionDefinitionType.class)
    @JoinColumn(name = "PRODUCT_SKU_OPTION_DEFINITION_TYPE_ID", insertable = true, updatable = true)
    private ProductSkuOptionDefinitionType optionDefinitionType;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATE_CREATE")
    private Date dateCreate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATE_UPDATE")
    private Date dateUpdate;

    public ProductSkuOptionDefinition() {
        this.dateCreate = new Date();
        this.dateUpdate = new Date();
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

    public Set<ProductSkuOptionDefinitionAttribute> getAttributes() {
        return attributes;
    }
    
    public void setAttributes(Set<ProductSkuOptionDefinitionAttribute> attributes) {
        this.attributes = attributes;
    }

    public ProductSkuOptionDefinitionType getOptionDefinitionType() {
		return optionDefinitionType;
	}
    
    public void setOptionDefinitionType(ProductSkuOptionDefinitionType optionDefinitionType) {
		this.optionDefinitionType = optionDefinitionType;
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
    
    // Attributes
    
    public Object getValue(String attributeCode, Long marketAreaId, String localizationCode) {
        AbstractAttribute attribute = getAttribute(attributeCode, marketAreaId, localizationCode);
        if(attribute != null) {
            return attribute.getValue();
        }
        return null;
    }
    
    public String getI18nName(String localizationCode) {
        String i18nName = (String) getValue(ProductSkuAttribute.PRODUCT_SKU_OPTION_DEFINITION_ATTRIBUTE_I18N_NAME, null, localizationCode);
        if(StringUtils.isEmpty(i18nName)){
            i18nName = getName();
        }
        return i18nName;
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
    public boolean equals(Object sourceObj) {
        Object obj = deproxy(sourceObj);
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ProductSkuOptionDefinition other = (ProductSkuOptionDefinition) obj;
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
        return "ProductSkuOptionDefinition [id=" + id + ", version=" + version + ", name=" + name + ", description=" + description + ", code=" + code + ", dateCreate=" + dateCreate + ", dateUpdate="
                + dateUpdate + "]";
    }

}