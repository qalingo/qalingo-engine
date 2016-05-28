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
import org.hoteia.qalingo.core.solr.bean.CompanySolr;

public class CompanyResponseBean {

    public static final String COMPANY_SEARCH_FIELD_TEXT          = "text";
    
    public static final String COMPANY_CITY_FACET_FIELD           = "city";
    public static final String COMPANY_COUNTRY_FACET_FIELD        = "countryCode";
    
    public static final String COMPANY_CITY_FACET_FIELD_CODE       = "cities";
    public static final String COMPANY_COUNTRY_FACET_FIELD_CODE    = "countries";

    public static final String COMPANY_DEFAULT_SEARCH_FIELD   = COMPANY_SEARCH_FIELD_TEXT;

    private List<CompanySolr> companySolrList = new ArrayList<CompanySolr>();

    private List<FacetField> companySolrFacetFieldList = new ArrayList<FacetField>();

    public List<CompanySolr> getCompanySolrList() {
        return companySolrList;
    }

    public void setCompanySolrList(List<CompanySolr> companySolrList) {
        this.companySolrList = companySolrList;
    }

    public List<FacetField> getCompanySolrFacetFieldList() {
        return companySolrFacetFieldList;
    }

    public void setCompanySolrFacetFieldList(List<FacetField> companySolrFacetFieldList) {
        this.companySolrFacetFieldList = companySolrFacetFieldList;
    }

}