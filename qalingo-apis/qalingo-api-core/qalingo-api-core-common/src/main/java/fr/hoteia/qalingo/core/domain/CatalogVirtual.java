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
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
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
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.persistence.Version;

@Entity
@Table(name="TECO_CATALOG_VIRTUAL", uniqueConstraints = {@UniqueConstraint(columnNames= {"code"})})
public class CatalogVirtual implements Serializable {

	/**
	 * Generated UID
	 */
	private static final long serialVersionUID = -1012950853049724224L;

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
	
	@OneToOne(mappedBy="virtualCatalog")
	private MarketArea marketArea;

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name="MASTER_CATALOG_ID", insertable=false, updatable=false)
	private CatalogMaster catalogMaster;
	
	@ManyToMany(
	        targetEntity=fr.hoteia.qalingo.core.domain.ProductCategoryVirtual.class,
       		fetch = FetchType.EAGER,
	        cascade={CascadeType.PERSIST, CascadeType.MERGE}
	    )
    @JoinTable(
	        name="TECO_CATALOG_VIRTUAL_CATEGORY_VIRTUAL_REL",
	        joinColumns=@JoinColumn(name="VIRTUAL_CATALOG_ID"),
	        inverseJoinColumns=@JoinColumn(name="VIRTUAL_CATEGORY_ID")
	    )	
	private Set<ProductCategoryVirtual> productCategories = new HashSet<ProductCategoryVirtual>(); 
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATE_CREATE")
	private Date dateCreate;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATE_UPDATE")
	private Date dateUpdate;

	public CatalogVirtual(){
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
	
	public MarketArea getMarketArea() {
		return marketArea;
	}
	
	public void setMarketArea(MarketArea marketArea) {
		this.marketArea = marketArea;
	}
	
	public CatalogMaster getCatalogMaster() {
		return catalogMaster;
	}
	
	public void setCatalogMaster(CatalogMaster catalogMaster) {
		this.catalogMaster = catalogMaster;
	}
	
	public Set<ProductCategoryVirtual> getProductCategories() {
		return productCategories;
	}
	
	public List<ProductCategoryVirtual> getProductCategories(final Long marketAreaId) {
		List<ProductCategoryVirtual> sortedObjects = new LinkedList<ProductCategoryVirtual>(productCategories);
		Collections.sort(sortedObjects, new Comparator<ProductCategoryVirtual>() {
			@Override
			public int compare(ProductCategoryVirtual o1, ProductCategoryVirtual o2) {
				if(o1 != null
						&& o2 != null){
					Integer order1 = o1.getOrder(marketAreaId);
					Integer order2 = o2.getOrder(marketAreaId);
					if(order1 != null
							&& order2 != null){
						return order1.compareTo(order2);				
					} else {
						return o1.getId().compareTo(o2.getId());	
					}
				}
				return 0;
			}
		});
		return sortedObjects;
	}
	public void setProductCategories(Set<ProductCategoryVirtual> productCategories) {
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
