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

import org.hoteia.qalingo.core.domain.impl.DomainEntity;

@Entity
@Table(name="TBO_USER_TOKEN_AUTH")
public class UserTokenAuth extends AbstractEntity<UserTokenAuth> implements DomainEntity {

	/**
	 * Generated UID
	 */
	private static final long serialVersionUID = -8768411955895382297L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="ID", nullable=false)
	private Long id;
	
	@Column(name="TOKEN")
	private String token;
	
	@Column(name="USER_ID")
	private Long userId;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATE_CREATE")
    private Date dateCreate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATE_UPDATE")
    private Date dateUpdate;
    
	public UserTokenAuth() {
        this.dateCreate = new Date();
        this.dateUpdate = new Date();
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getToken() {
        return token;
    }
	
	public void setToken(String token) {
        this.token = token;
    }

	public Long getUserId() {
		return userId;
	}
	
	public void setUserId(Long userId) {
		this.userId = userId;
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
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((dateCreate == null) ? 0 : dateCreate.hashCode());
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
        UserTokenAuth other = (UserTokenAuth) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (dateCreate == null) {
            if (other.dateCreate != null)
                return false;
        } else if (!dateCreate.equals(other.dateCreate))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "UserTokenAuth [id=" + id + ", token=" + token + ", userId=" + userId + ", dateCreate=" + dateCreate + ", dateUpdate=" + dateUpdate + "]";
    }

}