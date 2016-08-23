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

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
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
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import org.hibernate.Hibernate;
import org.hoteia.qalingo.core.domain.impl.DomainEntity;

@Entity
@Table(name = "TBO_COMPANY")
public class Company extends AbstractExtendEntity<Company, CompanyAttribute> implements DomainEntity {

    /**
     * Generated UID
     */
    private static final long serialVersionUID = -6310662684890302556L;

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

    @Column(name = "THEME")
    private String theme;

    @Column(name = "IS_ACTIVE", nullable = false) // , columnDefinition = "tinyint(1) default 1"
    private boolean active = false;
    
    @Column(name = "ADDRESS1")
    private String address1;

    @Column(name = "ADDRESS2")
    private String address2;

    @Column(name = "ADDITIONAL_INFORMATION")
    private String addressAdditionalInformation;

    @Column(name = "POSTAL_CODE")
    private String postalCode;

    @Column(name = "CITY")
    private String city;

    @Column(name = "STATE_CODE")
    private String stateCode;

    @Column(name = "AREA_CODE")
    private String areaCode;

    @Column(name = "COUNTRY_CODE")
    private String countryCode;
    
    @Column(name = "LEGAL_GUID")
    private String legalGuid;
    
    @Column(name = "EMAIL")
    private String email;
    
    @Column(name = "PHONE")
    private String phone;
    
    @Column(name = "FAX")
    private String fax;
    
    @Column(name = "WEBSITE")
    private String website;
    
    @Column(name = "CREATED_BY_USER_ID")
    private Long createdByUserId;
    
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, targetEntity = org.hoteia.qalingo.core.domain.CompanyAttribute.class)
    @JoinColumn(name = "COMPANY_ID")
    private Set<CompanyAttribute> attributes = new HashSet<CompanyAttribute>();
    
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, targetEntity = org.hoteia.qalingo.core.domain.CompanyUserRel.class)
    @JoinColumn(name = "COMPANY_ID")
    private Set<CompanyUserRel> companyUserRels = new HashSet<CompanyUserRel>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DEFAULT_LOCALIZATION_ID", insertable = true, updatable = true)
    private Localization defaultLocalization;

    @ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.MERGE }, targetEntity = org.hoteia.qalingo.core.domain.Localization.class)
    @JoinTable(name = "TBO_COMPANY_LOCALIZATION_REL", joinColumns = @JoinColumn(name = "COMPANY_ID"), inverseJoinColumns = @JoinColumn(name = "LOCALIZATION_ID"))
    private Set<Localization> localizations = new HashSet<Localization>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, targetEntity = org.hoteia.qalingo.core.domain.ProductBrand.class)
    @JoinColumn(name = "COMPANY_ID")
    private Set<ProductBrand> productBrands = new HashSet<ProductBrand>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, targetEntity = org.hoteia.qalingo.core.domain.CompanyStoreRel.class)
    @JoinColumn(name = "COMPANY_ID")
    private Set<CompanyStoreRel> companyStoreRels = new HashSet<CompanyStoreRel>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, targetEntity = org.hoteia.qalingo.core.domain.CompanyPayment.class)
    @JoinColumn(name = "COMPANY_ID")
    private Set<CompanyPayment> payments = new HashSet<CompanyPayment>();
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATE_CREATE")
    private Date dateCreate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATE_UPDATE")
    private Date dateUpdate;

    public Company() {
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

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getAddressAdditionalInformation() {
        return addressAdditionalInformation;
    }

    public void setAddressAdditionalInformation(String addressAdditionalInformation) {
        this.addressAdditionalInformation = addressAdditionalInformation;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStateCode() {
        return stateCode;
    }

    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }
    
    public String getLegalGuid() {
        return legalGuid;
    }
    
    public void setLegalGuid(String legalGuid) {
        this.legalGuid = legalGuid;
    }
    
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }
    
    public String getWebsite() {
        return website;
    }
    
    public void setWebsite(String website) {
        this.website = website;
    }

    public Long getCreatedByUserId() {
        return createdByUserId;
    }
    
    public boolean isCreatedByUser(User user) {
        boolean userIsCompanyAdmin = false;
        if(createdByUserId != null
                && createdByUserId.equals(user.getId())){
            userIsCompanyAdmin = true;
        }
        return userIsCompanyAdmin;
    }
    
    public void setCreatedByUserId(Long createdByUserId) {
        this.createdByUserId = createdByUserId;
    }

    public Set<CompanyAttribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(Set<CompanyAttribute> attributes) {
        this.attributes = attributes;
    }
    
    public Set<CompanyUserRel> getCompanyUserRels() {
        return companyUserRels;
    }
    
    public void setCompanyUserRels(Set<CompanyUserRel> companyUserRels) {
        this.companyUserRels = companyUserRels;
    }
    
    public List<User> getUsers() {
        if(companyUserRels != null
                && Hibernate.isInitialized(companyUserRels)
                && companyUserRels.size() > 0){
            List<User> users = new ArrayList<User>();
            for (CompanyUserRel companyUserRel : companyUserRels) {
                users.add(companyUserRel.getUser());
            }
            return users;
        }
        return null;
    }

    public User getDefaultUser() {
        if(companyUserRels != null
                && Hibernate.isInitialized(companyUserRels)
                && companyUserRels.size() > 0){
            for (CompanyUserRel companyUserRel : companyUserRels) {
                if(companyUserRel.isPrincipalUser()){
                    return companyUserRel.getUser();
                }
            }
            return companyUserRels.iterator().next().getUser();
        }
        return null;
    }
    
    public void addUser(User user) {
        CompanyUserRel companyUserRel = new CompanyUserRel(this, user);
        if(companyUserRels != null
                && Hibernate.isInitialized(companyUserRels)
                && companyUserRels.size() > 0){
            if(!companyUserRels.contains(companyUserRel)){
                companyUserRels.add(companyUserRel);
            }
        } else {
            companyUserRels.add(companyUserRel);
        }
    }
    
    public Localization getDefaultLocalization() {
        return defaultLocalization;
    }

    public void setDefaultLocalization(Localization defaultLocalization) {
        this.defaultLocalization = defaultLocalization;
    }

    public Set<Localization> getLocalizations() {
        return localizations;
    }

    public Localization getLocalization(String code) {
        Set<Localization> localizations = getLocalizations();
        if (localizations != null 
                && Hibernate.isInitialized(localizations)) {
            for (Iterator<Localization> iterator = localizations.iterator(); iterator.hasNext();) {
                Localization localization = (Localization) iterator.next();
                if (localization.getCode().equalsIgnoreCase(code)) {
                    return localization;
                }
            }
        }
        return null;
    }

    public void addLocalization(Localization localization) {
        if(this.localizations != null){
            this.localizations.add(localization);
        } else {
            Set<Localization> localizations = new HashSet<Localization>();
            localizations.add(localization);
            this.localizations = localizations;
        }
    }
    
    public void setLocalizations(Set<Localization> localizations) {
        this.localizations = localizations;
    }

    public Set<ProductBrand> getProductBrands() {
        return productBrands;
    }

    public ProductBrand getProductBrand(String code) {
        Set<ProductBrand> brands = getProductBrands();
        if (brands != null 
                && Hibernate.isInitialized(brands)) {
            for (Iterator<ProductBrand> iterator = brands.iterator(); iterator.hasNext();) {
                ProductBrand brand = (ProductBrand) iterator.next();
                if (brand.getCode().equalsIgnoreCase(code)) {
                    return brand;
                }
            }
        }
        return null;
    }
    
    public void addProductBrand(ProductBrand productBrand) {
        if(this.productBrands != null){
            this.productBrands.add(productBrand);
        } else {
            Set<ProductBrand> brands = new HashSet<ProductBrand>();
            brands.add(productBrand);
            this.productBrands = brands;
        }
    }
    
    public void setProductBrands(Set<ProductBrand> brands) {
        this.productBrands = brands;
    }
    
    public Set<CompanyStoreRel> getCompanyStoreRels() {
        return companyStoreRels;
    }
    
    public void setCompanyStoreRels(Set<CompanyStoreRel> companyStoreRels) {
        this.companyStoreRels = companyStoreRels;
    }
    
    public List<Store> getStores() {
        if(companyStoreRels != null
                && Hibernate.isInitialized(companyStoreRels)
                && companyStoreRels.size() > 0){
            List<Store> stores = new ArrayList<Store>();
            for (CompanyStoreRel companyStoreRel : companyStoreRels) {
                stores.add(companyStoreRel.getStore());
            }
            return stores;
        }
        return null;
    }

    public Store getDefaultStore() {
        if(companyStoreRels != null
                && Hibernate.isInitialized(companyStoreRels)
                && companyStoreRels.size() > 0){
            for (CompanyStoreRel companyStoreRel : companyStoreRels) {
                if(companyStoreRel.isPrincipalStore()){
                    return companyStoreRel.getStore();
                }
            }
            return companyStoreRels.iterator().next().getStore();
        }
        return null;
    }
    
    public void addStore(Store store) {
        CompanyStoreRel companyStoreRel = new CompanyStoreRel(this, store);
        if(companyStoreRels != null
                && Hibernate.isInitialized(companyStoreRels)
                && companyStoreRels.size() > 0){
            if(!companyStoreRels.contains(companyStoreRel)){
                companyStoreRels.add(companyStoreRel);
            }
        } else {
            companyStoreRels.add(companyStoreRel);
        }
    }
    
    public Set<CompanyPayment> getPayments() {
        return payments;
    }
    
    public void setPayments(Set<CompanyPayment> payments) {
        this.payments = payments;
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
    public boolean equals(Object sourceObj) {
        Object obj = deproxy(sourceObj);
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Company other = (Company) obj;
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
        return "Company [id=" + id + ", version=" + version + ", code=" + code + ", name=" + name + ", description=" + description + ", theme=" + theme + ", active=" + active + ", address1="
                + address1 + ", address2=" + address2 + ", addressAdditionalInformation=" + addressAdditionalInformation + ", postalCode=" + postalCode + ", city=" + city + ", stateCode=" + stateCode
                + ", areaCode=" + areaCode + ", countryCode=" + countryCode + ", legalGuid=" + legalGuid + ", email=" + email + ", phone=" + phone + ", fax=" + fax + ", createdByUserId="
                + createdByUserId + ", dateCreate=" + dateCreate + ", dateUpdate=" + dateUpdate + "]";
    }

}