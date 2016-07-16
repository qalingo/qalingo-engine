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

@Entity
@Table(name = "TBO_COMPANY_STORE_REL")
@AssociationOverrides({
    @AssociationOverride(name = "pk.company", joinColumns = @JoinColumn(name = "COMPANY_ID")),
    @AssociationOverride(name = "pk.store", joinColumns = @JoinColumn(name = "STORE_ID"))})
public class CompanyStoreRel extends AbstractEntity<CompanyStoreRel> {

    /**
     * Generated UID
     */
    private static final long serialVersionUID = 8531590918678381872L;

    @EmbeddedId
    private CompanyStorePk pk;
    
    @Column(name = "RANKING")
    private Integer ranking;

    @Column(name = "IS_PRINCIPAL_USER", nullable = false, columnDefinition = "tinyint(1) default 0")
    private boolean isPrincipalStore;
    
    public CompanyStoreRel() {
        this.pk = new CompanyStorePk();
    }
    
    public CompanyStoreRel(final Company company, final Store store) {
        this.pk = new CompanyStorePk(company, store);
    }

    public CompanyStorePk getPk() {
        return pk;
    }

    public void setPk(CompanyStorePk pk) {
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
    public Store getStore() {
        return getPk().getStore();
    }

    public void setStore(final Store store) {
        pk.setStore(store);
    }
    
    public Integer getRanking() {
        return ranking;
    }

    public void setRanking(Integer ranking) {
        this.ranking = ranking;
    }
    
    public boolean isPrincipalStore() {
        return isPrincipalStore;
    }

    public void setPrincipalStore(boolean isPrincipalStore) {
        this.isPrincipalStore = isPrincipalStore;
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
        CompanyStoreRel other = (CompanyStoreRel) obj;
        if (pk == null) {
            if (other.pk != null)
                return false;
        } else if (!pk.equals(other.pk))
            return false;
        return true;
    }

}