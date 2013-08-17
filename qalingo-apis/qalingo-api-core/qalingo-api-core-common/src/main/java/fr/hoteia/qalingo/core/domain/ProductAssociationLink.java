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

import javax.persistence.CascadeType;
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

import fr.hoteia.qalingo.core.domain.enumtype.ProductAssociationLinkType;

@Entity
@Table(name="TECO_PRODUCT_ASSOCIATION_LINK")
public class ProductAssociationLink implements Serializable {

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
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name="PRODUCT_MARKETING_ID", insertable=false, updatable=false)
	private ProductMarketing productMarketing;
	
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

	public ProductMarketing getProductMarketing() {
		return productMarketing;
	}
	
	public void setProductMarketing(ProductMarketing productMarketing) {
		this.productMarketing = productMarketing;
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
	
}
