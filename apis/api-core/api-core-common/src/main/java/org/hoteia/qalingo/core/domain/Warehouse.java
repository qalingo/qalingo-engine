package org.hoteia.qalingo.core.domain;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
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
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.persistence.Version;

import org.hibernate.Hibernate;

@Entity
@Table(name = "TECO_WAREHOUSE", uniqueConstraints = {@UniqueConstraint(columnNames= {"CODE"})})
public class Warehouse extends AbstractEntity {

    /**
     * Generated UID
     */
    private static final long serialVersionUID = 2977200965695011398L;

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
    
    @Column(name = "LONGITUDE")
    private String longitude;

    @Column(name = "LATITUDE")
    private String latitude;
    
    @OneToMany(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
    @JoinColumn(name = "WAREHOUSE_ID")
    private Set<WarehouseMarketAreaRel> warehouseMarketAreaRels = new HashSet<WarehouseMarketAreaRel>();
 
    @ManyToMany(fetch = FetchType.LAZY, targetEntity = org.hoteia.qalingo.core.domain.DeliveryMethod.class)
    @JoinTable(name = "TECO_WAREHOUSE_DELIVERY_METHOD_REL", joinColumns = @JoinColumn(name = "WAREHOUSE_ID"), inverseJoinColumns = @JoinColumn(name = "DELIVERY_METHOD_ID"))
    private Set<DeliveryMethod> deliveryMethods = new HashSet<DeliveryMethod>();
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATE_CREATE")
    private Date dateCreate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATE_UPDATE")
    private Date dateUpdate;
    
    public Warehouse() {
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

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public Set<WarehouseMarketAreaRel> getWarehouseMarketAreaRels() {
        return warehouseMarketAreaRels;
    }
    
    public List<MarketArea> getMarketAreas() {
        List<WarehouseMarketAreaRel> sortedCatalogVirtualCategoryVirtualRels = null;
        List<MarketArea> sortedMarketAreas = null;
        if (warehouseMarketAreaRels != null 
                && Hibernate.isInitialized(warehouseMarketAreaRels)) {
            sortedCatalogVirtualCategoryVirtualRels = new LinkedList<WarehouseMarketAreaRel>(warehouseMarketAreaRels);
            Collections.sort(sortedCatalogVirtualCategoryVirtualRels, new Comparator<WarehouseMarketAreaRel>() {
                @Override
                public int compare(WarehouseMarketAreaRel o1, WarehouseMarketAreaRel o2) {
                    if (o1 != null && o1.getRanking() != null && o2 != null && o2.getRanking() != null) {
                        return o1.getRanking().compareTo(o2.getRanking());
                    }
                    return 0;
                }
            });
            if(sortedCatalogVirtualCategoryVirtualRels != null){
                sortedMarketAreas = new LinkedList<MarketArea>();
                for (Iterator<WarehouseMarketAreaRel> iterator = sortedCatalogVirtualCategoryVirtualRels.iterator(); iterator.hasNext();) {
                    WarehouseMarketAreaRel warehouseMarketAreaRel = (WarehouseMarketAreaRel) iterator.next();
                    sortedMarketAreas.add(warehouseMarketAreaRel.getMarketArea());
                }
            }
        }
        return sortedMarketAreas;
    }
    
    public void setWarehouseMarketAreaRels(Set<WarehouseMarketAreaRel> warehouseMarketAreaRels) {
        this.warehouseMarketAreaRels = warehouseMarketAreaRels;
    }
    
    public Set<DeliveryMethod> getDeliveryMethods() {
        return deliveryMethods;
    }
    
    public void setDeliveryMethods(Set<DeliveryMethod> deliveryMethods) {
        this.deliveryMethods = deliveryMethods;
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
        Warehouse other = (Warehouse) obj;
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
        return "Warehouse [id=" + id + ", version=" + version + ", code=" + code + ", name=" + name + ", description=" + description + ", address1=" + address1 + ", address2=" + address2
                + ", addressAdditionalInformation=" + addressAdditionalInformation + ", postalCode=" + postalCode + ", city=" + city + ", stateCode=" + stateCode + ", areaCode=" + areaCode
                + ", countryCode=" + countryCode + ", longitude=" + longitude + ", latitude=" + latitude + ", dateCreate=" + dateCreate + ", dateUpdate=" + dateUpdate + "]";
    }

}