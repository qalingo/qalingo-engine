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
    
}