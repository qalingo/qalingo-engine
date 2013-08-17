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
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.Version;

import org.apache.commons.lang.StringUtils;

@Entity
@Table(name="TECO_RULE_REFERENTIAL")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(
    name="RULE_TYPE",
    discriminatorType=DiscriminatorType.STRING
)
public abstract class AbstractRuleReferential implements Serializable {

	/**
	 * Generated UID
	 */
	private static final long serialVersionUID = -8747405711328545732L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="ID", nullable=false)
	private Long id;
	
	@Version
	@Column(name="VERSION", nullable=false, columnDefinition="int(11) default 1")
	private int version;

	@Column(name="CODE")
	private String code;

	@Column(name="NAME")
	private String name;

	@Column(name="DESCRIPTION")
	private String description;

	@Column(name="SALIENCE")
	private String salience;
	
	@ManyToMany(
			fetch = FetchType.LAZY,
	        targetEntity=fr.hoteia.qalingo.core.domain.RuleRepositoryAttribute.class,
	        cascade={CascadeType.PERSIST, CascadeType.MERGE}
	    )
    @JoinTable(
	        name="TECO_RULE_REPOSITORY_ATTRIBUTE_REL",
	        joinColumns=@JoinColumn(name="RULE_REPOSITORY_ID"),
	        inverseJoinColumns=@JoinColumn(name="RULE_REPOSITORY_ATTRIBUTE_ID")
	    )
	private Set<RuleRepositoryAttribute> ruleRepositoryAttributes = new HashSet<RuleRepositoryAttribute>(); 
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATE_CREATE")
	private Date dateCreate;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATE_UPDATE")
	private Date dateUpdate;
	
	public AbstractRuleReferential() {
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}
	
	public void setCode(String code) {
		this.code = code;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getSalience() {
		if(StringUtils.isNotEmpty(salience)){
			return salience;
		} else {
			return "" +  getId();
		}
	}
	
	public void setSalience(String salience) {
		this.salience = salience;
	}

	public Set<RuleRepositoryAttribute> getRuleRepositoryAttributes() {
		return ruleRepositoryAttributes;
	}
	
	public void setRuleRepositoryAttributes(Set<RuleRepositoryAttribute> ruleRepositoryAttributes) {
		this.ruleRepositoryAttributes = ruleRepositoryAttributes;
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
	
	@Transient
	public String getCondition(){
		return "";
	}
	
	@Transient
	public String getConsequence(){
		return "";
	}
	
}
