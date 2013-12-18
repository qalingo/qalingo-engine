/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.persistence.Version;

@Entity
@Table(name = "TECO_PRODUCT_MARKETING_FAMILLY", uniqueConstraints = { @UniqueConstraint(columnNames = { "code" }) })
public class ProductMarketingFamilly extends AbstractEntity {

    /**
     * Generated UID
     */
    private static final long serialVersionUID = -3980707330914384779L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Version
    @Column(name = "VERSION", nullable = false, columnDefinition = "int(11) default 1")
    private int version;

    @Column(name = "NAME")
    private String name;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "CODE")
    private String code;

    @ManyToMany(fetch = FetchType.LAZY, targetEntity = org.hoteia.qalingo.core.domain.CurrencyReferential.class, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(name = "TECO_PRODUCT_MARKETING_FAMILLY_TAX_REL", joinColumns = @JoinColumn(name = "PRODUCT_MARKETING_TYPE_FAMILLY_ID"), inverseJoinColumns = @JoinColumn(name = "TAX_ID"))
    private Set<Tax> taxes = new HashSet<Tax>();

    @ManyToMany(fetch = FetchType.LAZY, targetEntity = org.hoteia.qalingo.core.domain.ProductSkuOption.class, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(name = "TECO_PRODUCT_MARKETING_FAMILLY_PRODUCT_SKU_OPTION_REL", joinColumns = @JoinColumn(name = "PRODUCT_MARKETING_FAMILLY_ID"), inverseJoinColumns = @JoinColumn(name = "PRODUCT_SKU_OPTION_ID"))
    private Set<ProductSkuOption> productSkuOptions = new HashSet<ProductSkuOption>();

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATE_CREATE")
    private Date dateCreate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATE_UPDATE")
    private Date dateUpdate;

    public ProductMarketingFamilly() {
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

    public Set<Tax> getTaxes() {
        return taxes;
    }

    public void setTaxes(Set<Tax> taxes) {
        this.taxes = taxes;
    }

    public Set<ProductSkuOption> getProductSkuOptions() {
        return productSkuOptions;
    }

    public void setProductSkuOptions(Set<ProductSkuOption> productSkuOptions) {
        this.productSkuOptions = productSkuOptions;
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