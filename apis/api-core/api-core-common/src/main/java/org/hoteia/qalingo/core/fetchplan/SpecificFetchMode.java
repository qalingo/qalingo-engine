package org.hoteia.qalingo.core.fetchplan;

import java.io.Serializable;

import org.hibernate.FetchMode;

public class SpecificFetchMode implements Serializable {

    /**
     * Generated UID
     */
    private static final long serialVersionUID = -3845114496201750495L;
    
    private String assocationPath;
    private FetchMode fetchMode;
    private SpecificAlias requiredAlias;
    
    public SpecificFetchMode() {
    }
    
    public SpecificFetchMode(String assocationPath) {
        this.assocationPath = assocationPath;
    }
    
    public SpecificFetchMode(String assocationPath, SpecificAlias alias) {
        this.assocationPath = assocationPath;
        
        // BIND ALIAS
        alias.setAlias(assocationPath);
        this.requiredAlias = alias;
    }
    
    public String getAssocationPath() {
        return assocationPath;
    }
    
    public void setAssocationPath(String assocationPath) {
        this.assocationPath = assocationPath;
    }

    public FetchMode getFetchMode() {
        if(fetchMode == null){
            return FetchMode.JOIN;
        }
        return fetchMode;
    }

    public void setFetchMode(FetchMode fetchMode) {
        this.fetchMode = fetchMode;
    }

    public SpecificAlias getRequiredAlias() {
        return requiredAlias;
    }

    public void setRequiredAlias(SpecificAlias requiredAlias) {
        this.requiredAlias = requiredAlias;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((assocationPath == null) ? 0 : assocationPath.hashCode());
        result = prime * result + ((fetchMode == null) ? 0 : fetchMode.hashCode());
        result = prime * result + ((requiredAlias == null) ? 0 : requiredAlias.hashCode());
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
        SpecificFetchMode other = (SpecificFetchMode) obj;
        if (assocationPath == null) {
            if (other.assocationPath != null)
                return false;
        } else if (!assocationPath.equals(other.assocationPath))
            return false;
        if (fetchMode != other.fetchMode)
            return false;
        if (requiredAlias == null) {
            if (other.requiredAlias != null)
                return false;
        } else if (!requiredAlias.equals(other.requiredAlias))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "SpecificFetchMode [assocationPath=" + assocationPath + ", fetchMode=" + fetchMode + ", requiredAlias=" + requiredAlias + "]";
    }
    
}