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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import org.apache.commons.lang.StringUtils;
import org.hoteia.qalingo.core.annotation.CacheEntityInformation;
import org.hoteia.qalingo.core.domain.impl.DomainEntity;

@Entity
@Table(name="TECO_TAG")
@CacheEntityInformation(cacheName="web_cache_common")
public class Tag extends AbstractExtendEntity<Tag, TagAttribute> implements DomainEntity {

	/**
	 * Generated UID
	 */
	private static final long serialVersionUID = 52973267776013413L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Version
    @Column(name = "VERSION", nullable = false) // , columnDefinition = "int(11) default 1"
    private int version;

    @Column(name = "CODE", unique = true, nullable = false)
    private String code;

    @Column(name = "NAME")
    private String name;

    @Column(name = "DESCRIPTION")
    @Lob
    private String description;

    @Column(name = "IS_DEFAULT", nullable = false) // , columnDefinition = "tinyint(1) default 0"
    private boolean isDefault = false;

    @Column(name = "IS_GLOBAL", nullable = false) // , columnDefinition = "tinyint(1) default 0"
    private boolean isGlobal = false;

    @Column(name = "ORDERING", nullable = false) // , columnDefinition = "int(11) default 0"
    private Integer ordering;

    @Column(name = "MARKET_AREA_ID")
    private Long marketAreaId;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, targetEntity = org.hoteia.qalingo.core.domain.TagAttribute.class)
    @JoinColumn(name = "TAG_ID")
    private Set<TagAttribute> attributes = new HashSet<TagAttribute>();

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATE_CREATE")
    private Date dateCreate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATE_UPDATE")
    private Date dateUpdate;

	public Tag(){
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
	
    public Set<TagAttribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(Set<TagAttribute> attributes) {
        this.attributes = attributes;
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
        if (attribute != null) {
            return attribute.getValue();
        }
        return null;
    }

    public String getI18nName(String localizationCode) {
        String i18Name = (String) getValue(TagAttribute.TAG_ATTRIBUTE_I18N_NAME, null, localizationCode);
        if(StringUtils.isNotEmpty(i18Name)){
            return i18Name;
        }
        return name;
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
        Tag other = (Tag) obj;
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
        return "Tag [id=" + id + ", version=" + version + ", name=" + name + ", code=" + code + ", description=" + description + ", isDefault=" + isDefault + ", isGlobal=" + isGlobal
                + ", ordering=" + ordering + ", marketAreaId=" + marketAreaId + ", dateCreate=" + dateCreate + ", dateUpdate=" + dateUpdate + "]";
    }

}