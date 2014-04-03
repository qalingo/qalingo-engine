package org.hoteia.qalingo.core.fetchplan;

import java.io.Serializable;

import org.hibernate.sql.JoinType;

public class SpecificAlias implements Serializable {

    /**
     * Generated UID
     */
    private static final long serialVersionUID = -4790589302644546692L;
    
    private String assocationPath;
    private String alias;
    private JoinType joinType;
    
    public SpecificAlias() {
    }
    
    public SpecificAlias(String assocationPath) {
        this.assocationPath = assocationPath;
    }
    
    public String getAssocationPath() {
        return assocationPath;
    }
    
    public void setAssocationPath(String assocationPath) {
        this.assocationPath = assocationPath;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public JoinType getJoinType() {
        if(joinType == null){
            return JoinType.LEFT_OUTER_JOIN;
        }
        return joinType;
    }

    public void setJoinType(JoinType joinType) {
        this.joinType = joinType;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((alias == null) ? 0 : alias.hashCode());
        result = prime * result + ((assocationPath == null) ? 0 : assocationPath.hashCode());
        result = prime * result + ((joinType == null) ? 0 : joinType.hashCode());
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
        SpecificAlias other = (SpecificAlias) obj;
        if (alias == null) {
            if (other.alias != null)
                return false;
        } else if (!alias.equals(other.alias))
            return false;
        if (assocationPath == null) {
            if (other.assocationPath != null)
                return false;
        } else if (!assocationPath.equals(other.assocationPath))
            return false;
        if (joinType != other.joinType)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "SpecificAlias [assocationPath=" + assocationPath + ", alias=" + alias + ", joinType=" + joinType + "]";
    }
    
}