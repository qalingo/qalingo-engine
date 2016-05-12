/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.solr.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.solr.client.solrj.beans.Field;

public class StoreSolr {

	@Field
	private Long id;
	
    @Field
    private Long scoring;
    
    @Field
    private String code;
    
    @Field
	private String name;
    
    @Field("companyname")
    private String companyName;

    @Field
    private boolean active;
    
    @Field
    private boolean b2c;
    
    @Field
    private boolean b2b;
    
    @Field("address")
    private String address;
    
    @Field("postalcode")
    private String postalCode;

    @Field
   	private String city;
    
    @Field("countrycode")
   	private String countryCode;
    
    @Field
   	private String type;
    
    @Field
    private List<String> tagCodes = new ArrayList<String>();
    
    @Field
    private List<String> productBrandCodes = new ArrayList<String>();
    
    @Field
    private String random;
    
    @Field("datecreate")
    private Date dateCreate;

    @Field("dateupdate")
    private Date dateUpdate;

	public Long getId() {
		return id;
	}

    public void setId(Long id) {
        this.id = id;
    }

    public Long getScoring() {
        return scoring;
    }

    public void setScoring(Long scoring) {
        this.scoring = scoring;
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
    
    public String getCompanyName() {
        return companyName;
    }
    
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public boolean isB2c() {
        return b2c;
    }

    public void setB2c(boolean b2c) {
        this.b2c = b2c;
    }

    public boolean isB2b() {
        return b2b;
    }

    public void setB2b(boolean b2b) {
        this.b2b = b2b;
    }
    
    public String getAddress() {
        return address;
    }
    
    public void setAddress(String address) {
        this.address = address;
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

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<String> getTagCodes() {
        return tagCodes;
    }
    
    public void setTagCodes(List<String> tagCodes) {
        this.tagCodes = tagCodes;
    }
    
    public void addTagCodes(String tag){
        if(this.tagCodes == null){
            this.tagCodes = new ArrayList<String>();
        }
        if(!this.tagCodes.contains(tag)){
            this.tagCodes.add(tag);
        }
    }
    
    public List<String> getProductBrandCodes() {
        return productBrandCodes;
    }
    
    public void addProductBrandCodes(String productBrandCode){
        if(this.productBrandCodes == null){
            this.productBrandCodes = new ArrayList<String>();
        }
        if(!this.productBrandCodes.contains(productBrandCode)){
            this.productBrandCodes.add(productBrandCode);
        }
    }
    
    public void setProductBrandCodes(List<String> productBrandCodes) {
        this.productBrandCodes = productBrandCodes;
    }
    
    public String getRandom() {
        return random;
    }
    
    public void setRandom(String random) {
        this.random = random;
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
        StoreSolr other = (StoreSolr) obj;
        if (code == null) {
            if (other.code != null)
                return false;
        } else if (!code.equals(other.code))
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
        return "StoreSolr [id=" + id + ", code=" + code + ", name=" + name + ", city=" + city + ", countryCode=" + countryCode + ", postalCode=" + postalCode + ", type=" + type + ", dateCreate="
                + dateCreate + ", dateUpdate=" + dateUpdate + "]";
    }
    
}