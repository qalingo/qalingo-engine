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

import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hoteia.qalingo.core.domain.impl.DomainEntity;

@Entity
@Table(name = "TBO_COMPANY_USER_REL")
@AssociationOverrides({
    @AssociationOverride(name = "pk.company", joinColumns = @JoinColumn(name = "COMPANY_ID")),
    @AssociationOverride(name = "pk.user", joinColumns = @JoinColumn(name = "USER_ID"))})
public class CompanyUserRel extends AbstractEntity<CompanyUserRel> implements DomainEntity {

    /**
     * Generated UID
     */
    private static final long serialVersionUID = 9175230918678381872L;

    @EmbeddedId
    private CompanyUserPk pk;
    
    @Column(name = "RANKING")
    private Integer ranking;

    @Column(name = "IS_DEFAULT_COMPANY", nullable = false) // , columnDefinition = "tinyint(1) default 0"
    private boolean isDefaultCompany = false;

    @Column(name = "IS_PRINCIPAL_USER", nullable = false) // , columnDefinition = "tinyint(1) default 0"
    private boolean isPrincipalUser = false;
    
    public CompanyUserRel() {
        this.pk = new CompanyUserPk();
    }
    
    public CompanyUserRel(final Company company, final User user) {
        this.pk = new CompanyUserPk(company, user);
    }

    public CompanyUserPk getPk() {
        return pk;
    }

    public void setPk(CompanyUserPk pk) {
        this.pk = pk;
    }

    @Transient
    public Company getCompany() {
        return getPk().getCompany();
    }

    public void setCompany(final Company company) {
        pk.setCompany(company);
    }
    
    @Transient
    public User getUser() {
        return getPk().getUser();
    }

    public void setUser(final User user) {
        pk.setUser(user);
    }
    
    public Integer getRanking() {
        return ranking;
    }

    public void setRanking(Integer ranking) {
        this.ranking = ranking;
    }
    
    public boolean isDefaultCompany() {
        return isDefaultCompany;
    }

    public void setDefaultCompany(boolean isDefaultCompany) {
        this.isDefaultCompany = isDefaultCompany;
    }

    public boolean isPrincipalUser() {
        return isPrincipalUser;
    }

    public void setPrincipalUser(boolean isPrincipalUser) {
        this.isPrincipalUser = isPrincipalUser;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((pk == null) ? 0 : pk.hashCode());
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
        CompanyUserRel other = (CompanyUserRel) obj;
        if (pk == null) {
            if (other.pk != null)
                return false;
        } else if (!pk.equals(other.pk))
            return false;
        return true;
    }

}