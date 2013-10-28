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
import java.sql.Blob;
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
@Table(name="TBO_EMAIL")
public class Email implements Serializable {

	/**
	 * Generated UID
	 */
	private static final long serialVersionUID = 4668053374460238571L;

	public static final String EMAIl_TYPE_NEW_ACCOUNT_CONFIRMATION					= "NEW_ACCOUNT_CONFIRMATION";
	public static final String EMAIl_TYPE_FORGOTTEN_PASSWORD						= "FORGOTTEN_PASSWORD";
	public static final String EMAIl_TYPE_RESET_PASSWORD_CONFIRMATION				= "RESET_PASSWORD_CONFIRMATION";
	public static final String EMAIl_TYPE_CONTACT									= "CONTACT";
	public static final String EMAIl_TYPE_RETAILER_CONTACT							= "RETAILER_CONTACT";
	public static final String EMAIl_TYPE_NEWSLETTER_SUBSCRIPTION					= "NEWSLETTER_SUBSCRIPTION";
	public static final String EMAIl_TYPE_NEWSLETTER_UNSUBSCRIPTION					= "NEWSLETTER_UNSUBSCRIPTION";
	public static final String EMAIl_TYPE_ORDER_CONFIRMATION						= "ORDER_CONFIRMATION";
	public static final String EMAIl_TYPE_ORDER_SHIPPED								= "ORDER_SHIPPED";
	public static final String EMAIl_TYPE_ABANDONED_SHOPPING_CART					= "ABANDONED_SHOPPING_CART";
	
	public static final String EMAIl_STATUS_PENDING = "PENDING";
	public static final String EMAIl_STATUS_SENDED = "SENDED";
	public static final String EMAIl_STATUS_ERROR = "ERROR";

	public static final String WORDING_SCOPE_EMAIL = "email";

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="ID", nullable=false)
	private Long id;
	
	@Version
	@Column(name="VERSION", nullable=false, columnDefinition="int(11) default 1")
	private int version;
	
	@Column(name="TYPE")
	private String type;
	
	@Column(name="EMAIL_CONTENT")
	@Lob
    private Blob emailContent;
	
	@Column(name="STATUS", nullable=false)
	private String status;
	
	@Column(name="EXCEPTION_CONTENT")
	@Lob
    private Blob exceptionContent;
	
	@Column(name="PROCESSED_COUNT", nullable=false, columnDefinition="int(11) default 0")
	private int processedCount;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATE_CREATE")
	private Date dateCreate;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATE_UPDATE")
	private Date dateUpdate;
	
	public Email(){
		this.status = EMAIl_STATUS_PENDING;
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public Blob getEmailContent() {
		return emailContent;
	}
	
	public void setEmailContent(Blob emailContent) {
		this.emailContent = emailContent;
	}

	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public Blob getExceptionContent() {
	    return exceptionContent;
    }
	
	public void setExceptionContent(Blob exceptionContent) {
	    this.exceptionContent = exceptionContent;
    }
	
	public int getProcessedCount() {
	    return processedCount;
    }
	
	public void setProcessedCount(int processedCount) {
	    this.processedCount = processedCount;
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