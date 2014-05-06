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
import org.hoteia.qalingo.core.solr.bean.CatalogCategorySolr;

public class CatalogCategoryResponseBean {

    private List<CatalogCategorySolr> catalogCategorySolrList = new ArrayList<CatalogCategorySolr>();

    private List<FacetField> catalogCategorySolrFacetFieldList = new ArrayList<FacetField>();

    public List<CatalogCategorySolr> getCatalogCategorySolrList() {
        return catalogCategorySolrList;
    }

    public void setCatalogCategorySolrList(List<CatalogCategorySolr> catalogCategorySolrList) {
        this.catalogCategorySolrList = catalogCategorySolrList;
    }

    public List<FacetField> getCatalogCategorySolrFacetFieldList() {
        return catalogCategorySolrFacetFieldList;
    }

    public void setCatalogCategorySolrFacetFieldList(List<FacetField> catalogCategorySolrFacetFieldList) {
        this.catalogCategorySolrFacetFieldList = catalogCategorySolrFacetFieldList;
    }

}