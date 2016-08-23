package org.hoteia.qalingo.core.domain;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hoteia.qalingo.core.domain.impl.DomainEntity;

@Embeddable
public class RetailerTagPk extends AbstractEntity<RetailerTagPk> implements DomainEntity {

    /**
     * Generated UID
     */
    private static final long serialVersionUID = 8745892210612286668L;

    @ManyToOne(fetch = FetchType.LAZY,  targetEntity = org.hoteia.qalingo.core.domain.Retailer.class)
    @JoinColumn(name = "RETAILER_ID")
    private Retailer retailer;
    
    @ManyToOne(fetch = FetchType.LAZY,  targetEntity = org.hoteia.qalingo.core.domain.Tag.class)
    @JoinColumn(name = "TAG_ID")
    private Tag tag;
    
    public RetailerTagPk() {
    }
    
    public RetailerTagPk(final Retailer retailer, final Tag tag) {
        this.retailer = retailer;
        this.tag = tag;
    }
    
    public Retailer getRetailer() {
        return retailer;
    }

    public void setRetailer(Retailer retailer) {
        this.retailer = retailer;
    }
    
    public Tag getTag() {
        return tag;
    }
    
    public void setTag(Tag tag) {
        this.tag = tag;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((retailer == null) ? 0 : retailer.hashCode());
        result = prime * result + ((tag == null) ? 0 : tag.hashCode());
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
        RetailerTagPk other = (RetailerTagPk) obj;
        if (tag == null) {
            if (other.tag != null)
                return false;
        } else if (!tag.equals(other.tag))
            return false;
        if (retailer == null) {
            if (other.retailer != null)
                return false;
        } else if (!retailer.equals(other.retailer))
            return false;
        return true;
    }

}