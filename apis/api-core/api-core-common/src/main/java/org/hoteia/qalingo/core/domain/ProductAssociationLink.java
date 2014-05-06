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
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import org.hoteia.qalingo.core.domain.enumtype.ProductAssociationLinkType;

@Entity
@Table(name="TECO_PRODUCT_ASSOCIATION_LINK")
public class ProductAssociationLink extends AbstractEntity {

	/**
	 * Generated UID
	 */
	private static final long serialVersionUID = -4568210271972815776L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="ID", nullable=false)
	private Long id;
	
	@Version
	@Column(name="VERSION", nullable=false, columnDefinition="int(11) default 1")
	private int version;

	@Column(name="RANK_POSITION", nullable=false, columnDefinition="int(11) default 1")
	private int rankPosition;

	@Column(name="TYPE")
	@Enumerated(EnumType.STRING) 
	private ProductAssociationLinkType type;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="PRODUCT_SKU_ID", insertable = true, updatable = true)
	private ProductSku productSku;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATE_START")
	private Date dateStart;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATE_END")
	private Date dateEnd;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATE_CREATE")
	private Date dateCreate;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATE_UPDATE")
	private Date dateUpdate;

	public ProductAssociationLink(){
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

	public int getRankPosition() {
		return rankPosition;
	}
	
	public void setRankPosition(int rankPosition) {
		this.rankPosition = rankPosition;
	}
	
	public ProductAssociationLinkType getType() {
		return type;
	}
	
	public void setType(ProductAssociationLinkType type) {
		this.type = type;
	}

	public ProductSku getProductSku() {
        return productSku;
    }
	
	public void setProductSku(ProductSku productSku) {
        this.productSku = productSku;
    }

	public Date getDateStart() {
		return dateStart;
	}

	public void setDateStart(Date dateStart) {
		this.dateStart = dateStart;
	}

	public Date getDateEnd() {
		return dateEnd;
	}

	public void setDateEnd(Date dateEnd) {
		this.dateEnd = dateEnd;
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
        ProductAssociationLink other = (ProductAssociationLink) obj;
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
        return "ProductAssociationLink [id=" + id + ", version=" + version + ", rankPosition=" + rankPosition + ", type=" + type + ", dateStart=" + dateStart + ", dateEnd=" + dateEnd
                + ", dateCreate=" + dateCreate + ", dateUpdate=" + dateUpdate + "]";
    }
	
}