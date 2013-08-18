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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="TECO_RETAILER_LINK")
public class RetailerLink implements Serializable {

	/**
	 * Generated UID
	 */
    private static final long serialVersionUID = 1424510895043858148L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="ID", nullable=false)
	private Long id;
	
	@Column(name="LINK")
    private String link;

	@Column(name="TYPE")
    private String type;
	
	public RetailerLink() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getLink() {
	    return link;
    }
	
	public void setLink(String link) {
	    this.link = link;
    }
	
	public String getType() {
	    return type;
    }
	
	public void setType(String type) {
	    this.type = type;
    }
	
}