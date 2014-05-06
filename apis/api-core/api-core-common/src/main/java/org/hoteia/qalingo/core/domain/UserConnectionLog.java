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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="TBO_USER_CONNECTION_LOG")
public class UserConnectionLog extends AbstractEntity {

	/**
	 * Generated UID
	 */
	private static final long serialVersionUID = -8768486955895382297L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="ID", nullable=false)
	private Long id;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="LOGIN_DATE")
	private Date loginDate;
	
	@Column(name="APP")
	private String app;
	
	@Column(name="HOST")
	private String host;
	
	@Column(name="PUBLIC_ADDRESS")
	private String publicAddress;
	
    @Column(name="PRIVATE_ADDRESS")
    private String privateAddress;
	
	@Column(name="USER_ID")
	private Long userId;
	
	public UserConnectionLog() {
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getLoginDate() {
		return loginDate;
	}

	public void setLoginDate(Date loginDate) {
		this.loginDate = loginDate;
	}
	
	public String getApp() {
		return app;
	}
	
	public void setApp(String app) {
		this.app = app;
	}
	
	public String getHost() {
		return host;
	}
	
	public void setHost(String host) {
		this.host = host;
	}
	
	public String getPublicAddress() {
        return publicAddress;
    }
	
	public void setPublicAddress(String publicAddress) {
        this.publicAddress = publicAddress;
    }
	
	public String getPrivateAddress() {
        return privateAddress;
    }
	
	public void setPrivateAddress(String privateAddress) {
        this.privateAddress = privateAddress;
    }

	public Long getUserId() {
		return userId;
	}
	
	public void setUserId(Long userId) {
		this.userId = userId;
	}

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((loginDate == null) ? 0 : loginDate.hashCode());
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
        UserConnectionLog other = (UserConnectionLog) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (loginDate == null) {
            if (other.loginDate != null)
                return false;
        } else if (!loginDate.equals(other.loginDate))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "UserConnectionLog [id=" + id + ", loginDate=" + loginDate + ", app=" + app + ", host=" + host + ", publicAddress=" + publicAddress + ", privateAddress=" + privateAddress + ", userId="
                + userId + "]";
    }

}