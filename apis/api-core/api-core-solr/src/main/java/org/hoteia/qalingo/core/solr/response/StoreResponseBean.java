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
import org.hoteia.qalingo.core.solr.bean.StoreSolr;

public class StoreResponseBean {

    public static final String STORE_DEFAULT_SEARCH_FIELD = "text";
    public static final String STORE_DEFAULT_FACET_FIELD = "city";
    public static final String STORE_SECOND_FACET_FIELD = "countrycode";

    private List<StoreSolr> storeSolrList = new ArrayList<StoreSolr>();

    private List<FacetField> storeSolrFacetFieldList = new ArrayList<FacetField>();

    public List<StoreSolr> getStoreSolrList() {
        return storeSolrList;
    }

    public void setStoreSolrList(List<StoreSolr> storeSolrList) {
        this.storeSolrList = storeSolrList;
    }

    public List<FacetField> getStoreSolrFacetFieldList() {
        return storeSolrFacetFieldList;
    }

    public void setStoreSolrFacetFieldList(List<FacetField> storeSolrFacetFieldList) {
        this.storeSolrFacetFieldList = storeSolrFacetFieldList;
    }

}
