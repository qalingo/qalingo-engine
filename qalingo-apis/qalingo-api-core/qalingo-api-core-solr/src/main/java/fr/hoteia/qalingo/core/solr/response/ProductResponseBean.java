/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version ${license.version})
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package fr.hoteia.qalingo.core.solr.response;

import java.util.ArrayList;
import java.util.List;

import org.apache.solr.client.solrj.response.FacetField;

import fr.hoteia.qalingo.core.solr.bean.ProductSolr;

public class ProductResponseBean {

	private List<ProductSolr> productSolrList = new ArrayList<ProductSolr>();
	
	private List<FacetField> productSolrFacetFieldList = new ArrayList<FacetField>();

	public List<ProductSolr> getProductSolrList() {
		return productSolrList;
	}

	public void setProductSolrList(List<ProductSolr> productSolrList) {
		this.productSolrList = productSolrList;
	}

	public List<FacetField> getProductSolrFacetFieldList() {
		return productSolrFacetFieldList;
	}

	public void setProductSolrFacetFieldList(
			List<FacetField> productSolrFacetFieldList) {
		this.productSolrFacetFieldList = productSolrFacetFieldList;
	}
	
}
