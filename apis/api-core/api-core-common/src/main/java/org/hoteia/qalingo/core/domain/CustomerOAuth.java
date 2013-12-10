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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import org.hoteia.qalingo.core.domain.enumtype.OAuthType;

@Entity
@Table(name="TECO_CUSTOMER_OAUTH")
public class CustomerOAuth extends AbstractAddress {

	/**
	 * Generated UID
	 */
	private static final long serialVersionUID = 2501221341288490523L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="ID", nullable=false)
	private Long id;
	
	@Version
	@Column(name="VERSION", nullable=false, columnDefinition="int(11) default 1")
	private int version;
	
	@Column(name="OAUTH_TOKEN")
    private String oauthToken;

	@Column(name="USER_ID")
    private String userId;
	
	@Column(name="EXPIRES")
    private String expires;
	
	@Column(name="TYPE")
	@Enumerated(EnumType.STRING) 
	private OAuthType type;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATE_CREATE")
	private Date dateCreate;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATE_UPDATE")
	private Date dateUpdate;
	
	public CustomerOAuth() {
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

	public String getOauthToken() {
    	return oauthToken;
    }

	public void setOauthToken(String oauthToken) {
    	this.oauthToken = oauthToken;
    }

	public String getUserId() {
	    return userId;
    }
	
	public void setUserId(String userId) {
	    this.userId = userId;
    }
	
	public String getExpires() {
	    return expires;
    }
	
	public void setExpires(String expires) {
	    this.expires = expires;
    }
	
	public OAuthType getType() {
    	return type;
    }

	public void setType(OAuthType type) {
    	this.type = type;
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