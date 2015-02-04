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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

@Entity
@Table(name="TECO_GEOLOC_ADDRESS")
public class GeolocAddress extends AbstractEntity {

    /**
     * Generated UID
     */
    private static final long serialVersionUID = 9020651155879779713L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="ID", nullable=false)
	private Long id;
	
    @Version
    @Column(name = "VERSION", nullable = false, columnDefinition = "int(11) default 1")
    private int version;

    @Column(name = "ADDRESS")
    private String address;

    @Column(name = "FORMATED_ADDRESS")
    private String formatedAddress;
    
    @Column(name = "POSTAL_CODE")
    private String postalCode;
    
    @Column(name = "CITY")
    private String city;

    @Column(name = "COUNTRY")
    private String country;

    @Column(name = "JSON")
    @Lob
    private byte[] json;
    
    @Column(name = "LONGITUDE")
    private String longitude;

    @Column(name = "LATITUDE")
    private String latitude;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATE_CREATE")
    private Date dateCreate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATE_UPDATE")
    private Date dateUpdate;
	
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

	public String getAddress() {
        return address;
    }
	
	public void setAddress(String address) {
        this.address = address;
    }
	
	public String getFormatedAddress() {
        return formatedAddress;
    }
	
	public void setFormatedAddress(String formatedAddress) {
        this.formatedAddress = formatedAddress;
    }
	
	public String getCity() {
        return city;
    }
	
	public void setCity(String city) {
        this.city = city;
    }
	
	public String getPostalCode() {
        return postalCode;
    }
	
	public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }
	
	public String getCountry() {
        return country;
    }
	
	public void setCountry(String country) {
        this.country = country;
    }
	
	public byte[] getJson() {
        return json;
    }
	
	public void setJson(byte[] json) {
        this.json = json;
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
        result = prime * result + ((address == null) ? 0 : address.hashCode());
        result = prime * result + ((city == null) ? 0 : city.hashCode());
        result = prime * result + ((country == null) ? 0 : country.hashCode());
        result = prime * result + ((dateCreate == null) ? 0 : dateCreate.hashCode());
        result = prime * result + ((formatedAddress == null) ? 0 : formatedAddress.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((latitude == null) ? 0 : latitude.hashCode());
        result = prime * result + ((longitude == null) ? 0 : longitude.hashCode());
        result = prime * result + ((postalCode == null) ? 0 : postalCode.hashCode());
        result = prime * result + version;
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
        GeolocAddress other = (GeolocAddress) obj;
        if (address == null) {
            if (other.address != null)
                return false;
        } else if (!address.equals(other.address))
            return false;
        if (city == null) {
            if (other.city != null)
                return false;
        } else if (!city.equals(other.city))
            return false;
        if (country == null) {
            if (other.country != null)
                return false;
        } else if (!country.equals(other.country))
            return false;
        if (dateCreate == null) {
            if (other.dateCreate != null)
                return false;
        } else if (!dateCreate.equals(other.dateCreate))
            return false;
        if (formatedAddress == null) {
            if (other.formatedAddress != null)
                return false;
        } else if (!formatedAddress.equals(other.formatedAddress))
            return false;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (latitude == null) {
            if (other.latitude != null)
                return false;
        } else if (!latitude.equals(other.latitude))
            return false;
        if (longitude == null) {
            if (other.longitude != null)
                return false;
        } else if (!longitude.equals(other.longitude))
            return false;
        if (postalCode == null) {
            if (other.postalCode != null)
                return false;
        } else if (!postalCode.equals(other.postalCode))
            return false;
        if (version != other.version)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "GeolocAddress [id=" + id + ", version=" + version + ", address=" + address + ", formatedAddress=" + formatedAddress + ", postalCode=" + postalCode + ", city=" + city + ", country="
                + country + ", longitude=" + longitude + ", latitude=" + latitude + ", dateCreate=" + dateCreate + ", dateUpdate=" + dateUpdate + "]";
    }

}