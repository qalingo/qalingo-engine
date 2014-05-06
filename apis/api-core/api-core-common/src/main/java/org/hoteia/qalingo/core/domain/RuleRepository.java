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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.persistence.Version;

import org.hibernate.Hibernate;

@Entity
@Table(name="TECO_RULE_REPOSITORY", uniqueConstraints = {@UniqueConstraint(columnNames= {"CODE"})})
public class RuleRepository extends AbstractEntity {

	/**
	 * Generated UID
	 */
	private static final long serialVersionUID = -8807165174312292506L;

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

    @Column(name = "IS_ACTIVE", nullable = false, columnDefinition = "tinyint(1) default 1")
    private boolean active;

    @ManyToMany(fetch = FetchType.LAZY, targetEntity = org.hoteia.qalingo.core.domain.AbstractRuleReferential.class, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(name = "TECO_RULE_REPOSITORY_REFERENTIAL_REL", joinColumns = @JoinColumn(name = "RULE_REPOSITORY_ID"), inverseJoinColumns = @JoinColumn(name = "RULE_REFERENTIAL_ID"))
    private Set<AbstractRuleReferential> rules = new HashSet<AbstractRuleReferential>();

    @ManyToMany(fetch = FetchType.LAZY, targetEntity = org.hoteia.qalingo.core.domain.RuleRepositoryAttribute.class, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(name = "TECO_RULE_REPOSITORY_ATTRIBUTE_REL", joinColumns = @JoinColumn(name = "RULE_REPOSITORY_ID"), inverseJoinColumns = @JoinColumn(name = "RULE_REPOSITORY_ATTRIBUTE_ID"))
    private Set<RuleRepositoryAttribute> ruleRepositoryAttributes = new HashSet<RuleRepositoryAttribute>();

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "START_DATE")
    private Date startDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "END_DATE")
    private Date endDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATE_CREATE")
    private Date dateCreate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATE_UPDATE")
    private Date dateUpdate;

    public RuleRepository() {
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

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Set<AbstractRuleReferential> getRules() {
        return rules;
    }

    public void setRules(Set<AbstractRuleReferential> rules) {
        this.rules = rules;
    }

    public Set<RuleRepositoryAttribute> getRuleRepositoryAttributes() {
        return ruleRepositoryAttributes;
    }

    public void setRuleRepositoryAttributes(Set<RuleRepositoryAttribute> ruleRepositoryAttributes) {
        this.ruleRepositoryAttributes = ruleRepositoryAttributes;
    }
	
	@Transient
	public String getRuleString() {
		StringBuffer rule = new StringBuffer();
		Set<AbstractRuleReferential> rules = getRules();
		if(rules != null
		        && Hibernate.isInitialized(rules)){
		      for (Iterator<AbstractRuleReferential> iteratorRule = rules.iterator(); iteratorRule.hasNext();) {
		            AbstractRuleReferential ruleReferential = (AbstractRuleReferential) iteratorRule.next();
		            String name = ruleReferential.getName();
		            rule.append("rule \"" + name + "\"").append("\n");
		            rule.append("salience " + ruleReferential.getSalience()).append("\n");
		            rule.append("when").append("\n");
		            rule.append(ruleReferential.getCondition()).append("\n");
		            rule.append("then").append("\n");
		            rule.append(ruleReferential.getConsequence()).append("\n");
		            rule.append("end").append("\n");
		            rule.append("\n");
		        }
		}
		return rule.toString();
	}
	
	public Date getStartDate() {
		return startDate;
	}
	
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	
	public Date getEndDate() {
		return endDate;
	}
	
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
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
        RuleRepository other = (RuleRepository) obj;
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
        return "RuleRepository [id=" + id + ", version=" + version + ", name=" + name + ", description=" + description + ", code=" + code + ", active=" + active + ", startDate=" + startDate
                + ", endDate=" + endDate + ", dateCreate=" + dateCreate + ", dateUpdate=" + dateUpdate + "]";
    }
	
}