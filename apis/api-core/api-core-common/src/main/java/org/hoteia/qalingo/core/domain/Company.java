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
import java.util.Iterator;
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

@Entity
@Table(name = "TBO_COMPANY")
public class Company extends AbstractEntity {

    /**
     * Generated UID
     */
    private static final long serialVersionUID = -6310662684890302556L;

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

    @Column(name = "THEME")
    private String theme;

    @Column(name = "IS_ACTIVE", nullable = false, columnDefinition = "tinyint(1) default 1")
    private boolean active;
    
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
    
    @Column(name = "CREATED_BY_USER_ID")
    private Long createdByUserId;
    
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "COMPANY_ID")
    private Set<User> users = new HashSet<User>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DEFAULT_LOCALIZATION_ID", insertable = true, updatable = true)
    private Localization defaultLocalization;

    @ManyToMany(fetch = FetchType.LAZY, targetEntity = org.hoteia.qalingo.core.domain.Localization.class)
    @JoinTable(name = "TBO_COMPANY_LOCALIZATION_REL", joinColumns = @JoinColumn(name = "COMPANY_ID"), inverseJoinColumns = @JoinColumn(name = "LOCALIZATION_ID"))
    private Set<Localization> localizations = new HashSet<Localization>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, targetEntity = org.hoteia.qalingo.core.domain.Retailer.class)
    @JoinColumn(name = "COMPANY_ID")
    private Set<Retailer> retailers = new HashSet<Retailer>();
    
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, targetEntity = org.hoteia.qalingo.core.domain.ProductBrand.class)
    @JoinColumn(name = "COMPANY_ID")
    private Set<ProductBrand> brands = new HashSet<ProductBrand>();
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATE_CREATE")
    private Date dateCreate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATE_UPDATE")
    private Date dateUpdate;

    public Company() {
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
        this.code = code.replaceAll(" ", "");
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

    public Set<User> getUsers() {
        return users;
    }

    public void addUser(User user) {
        if(this.users != null){
            this.users.add(user);
        } else {
            Set<User> users = new HashSet<User>();
            users.add(user);
            this.users = users;
        }
    }
    
    public void setUsers(Set<User> users) {
        this.users = users;
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
        return brands;
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
        if(this.brands != null){
            this.brands.add(productBrand);
        } else {
            Set<ProductBrand> brands = new HashSet<ProductBrand>();
            brands.add(productBrand);
            this.brands = brands;
        }
    }
    
    public void setProductBrands(Set<ProductBrand> brands) {
        this.brands = brands;
    }

    public Set<Retailer> getRetailers() {
        return retailers;
    }
    
    public Retailer getRetailer(String code) {
        Set<Retailer> localizations = getRetailers();
        if (retailers != null 
                && Hibernate.isInitialized(retailers)) {
            for (Iterator<Retailer> iterator = retailers.iterator(); iterator.hasNext();) {
                Retailer retailer = (Retailer) iterator.next();
                if (retailer.getCode().equalsIgnoreCase(code)) {
                    return retailer;
                }
            }
        }
        return null;
    }

    public void addRetailer(Retailer retailer) {
        if(this.retailers != null){
            this.retailers.add(retailer);
        } else {
            Set<Retailer> retailers = new HashSet<Retailer>();
            retailers.add(retailer);
            this.retailers = retailers;
        }
    }
    
    public void setRetailers(Set<Retailer> retailers) {
        this.retailers = retailers;
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
        return "Company [id=" + id + ", version=" + version + ", name=" + name + ", description=" + description + ", code=" + code + ", theme=" + theme + ", dateCreate=" + dateCreate
                + ", dateUpdate=" + dateUpdate + "]";
    }

}