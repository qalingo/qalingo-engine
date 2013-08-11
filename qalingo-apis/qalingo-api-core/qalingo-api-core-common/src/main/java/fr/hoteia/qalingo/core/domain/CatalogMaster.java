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
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.persistence.Version;

@Entity
@Table(name="TECO_CATALOG_MASTER", uniqueConstraints = {@UniqueConstraint(columnNames= {"code"})})
public class CatalogMaster implements Serializable {

	/**
	 * Generated UID
	 */
	private static final long serialVersionUID = 5663387436039325641L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="ID", nullable=false)
	private Long id;
	
	@Version
	@Column(name="VERSION", nullable=false, columnDefinition="int(11) default 1")
	private int version;
	
	@Column(name="BUSINESS_NAME")
	private String businessName;

	@Column(name="DESCRIPTION")
	private String description;
	
	@Column(name="IS_DEFAULT", nullable=false, columnDefinition="tinyint(1) default 0")
	private boolean isDefault;
	
	@Column(name="CODE", nullable=false)
	private String code;
	
	@OneToMany(
	        targetEntity=fr.hoteia.qalingo.core.domain.CatalogCategoryMaster.class,
       		fetch = FetchType.LAZY,
	        cascade={CascadeType.PERSIST, CascadeType.MERGE}
	    )
    @JoinTable(
	        name="TECO_CATALOG_MASTER_CATEGORY_MASTER_REL",
	        joinColumns=@JoinColumn(name="MASTER_CATALOG_ID"),
	        inverseJoinColumns=@JoinColumn(name="MASTER_CATEGORY_ID")
	    )
	private Set<CatalogCategoryMaster> productCategories = new HashSet<CatalogCategoryMaster>(); 
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATE_CREATE")
	private Date dateCreate;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATE_UPDATE")
	private Date dateUpdate;

	public CatalogMaster(){
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

	public String getBusinessName() {
		return businessName;
	}
	
	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}
	
	public boolean isDefault() {
		return isDefault;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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
	
	public Set<CatalogCategoryMaster> getProductCategories() {
		return productCategories;
	}
	
	public void setProductCategories(Set<CatalogCategoryMaster> productCategories) {
		this.productCategories = productCategories;
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
