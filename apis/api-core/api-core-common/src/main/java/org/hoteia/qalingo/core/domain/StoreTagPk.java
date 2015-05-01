package org.hoteia.qalingo.core.domain;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Embeddable
public class StoreTagPk extends AbstractEntity<StoreTagPk> {

    /**
     * Generated UID
     */
    private static final long serialVersionUID = 8745892510682286668L;

    @ManyToOne(fetch = FetchType.LAZY,  targetEntity = org.hoteia.qalingo.core.domain.Store.class)
    @JoinColumn(name = "STORE_ID")
    private Store store;
    
    @ManyToOne(fetch = FetchType.LAZY,  targetEntity = org.hoteia.qalingo.core.domain.Tag.class)
    @JoinColumn(name = "TAG_ID")
    private Tag tag;
    
    public StoreTagPk() {
    }
    
    public StoreTagPk(final Store store, final Tag tag) {
        this.store = store;
        this.tag = tag;
    }
    
    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
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
        result = prime * result + ((store == null) ? 0 : store.hashCode());
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
        StoreTagPk other = (StoreTagPk) obj;
        if (tag == null) {
            if (other.tag != null)
                return false;
        } else if (!tag.equals(other.tag))
            return false;
        if (store == null) {
            if (other.store != null)
                return false;
        } else if (!store.equals(other.store))
            return false;
        return true;
    }

}