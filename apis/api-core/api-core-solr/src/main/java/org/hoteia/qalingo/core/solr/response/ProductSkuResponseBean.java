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
import org.hoteia.qalingo.core.solr.bean.ProductSkuSolr;

public class ProductSkuResponseBean {

    private List<ProductSkuSolr> productSkuSolrList = new ArrayList<ProductSkuSolr>();

    private List<FacetField> productSkuSolrFacetFieldList = new ArrayList<FacetField>();

    public List<ProductSkuSolr> getProductSkuSolrList() {
        return productSkuSolrList;
    }

    public void setProductSkuSolrList(List<ProductSkuSolr> productSkuSolrList) {
        this.productSkuSolrList = productSkuSolrList;
    }

    public List<FacetField> getProductSkuSolrFacetFieldList() {
        return productSkuSolrFacetFieldList;
    }

    public void setProductSkuSolrFacetFieldList(List<FacetField> productSkuSolrFacetFieldList) {
        this.productSkuSolrFacetFieldList = productSkuSolrFacetFieldList;
    }

}