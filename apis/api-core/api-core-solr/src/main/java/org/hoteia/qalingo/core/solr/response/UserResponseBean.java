/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.solr.response;

import java.util.ArrayList;
import java.util.List;

import org.apache.solr.client.solrj.response.FacetField;
import org.hoteia.qalingo.core.solr.bean.UserSolr;

public class UserResponseBean {

    public static final String USER_SEARCH_FIELD_TEXT          = "text";
    
    public static final String USER_SEARCH_FIELD_CODE                   = "code";
    public static final String USER_SEARCH_FIELD_FIRSTNAME              = "firstname";
    public static final String USER_SEARCH_FIELD_LASTNAME               = "lastname";

    public static final String USER_DEFAULT_SEARCH_FIELD   = USER_SEARCH_FIELD_TEXT;
    
    private List<UserSolr> userSolrList = new ArrayList<UserSolr>();

    private List<FacetField> userSolrFacetFieldList = new ArrayList<FacetField>();

    public List<UserSolr> getUserSolrList() {
        return userSolrList;
    }

    public void setUserSolrList(List<UserSolr> userSolrList) {
        this.userSolrList = userSolrList;
    }

    public List<FacetField> getUserSolrFacetFieldList() {
        return userSolrFacetFieldList;
    }

    public void setUserSolrFacetFieldList(List<FacetField> userSolrFacetFieldList) {
        this.userSolrFacetFieldList = userSolrFacetFieldList;
    }

}
