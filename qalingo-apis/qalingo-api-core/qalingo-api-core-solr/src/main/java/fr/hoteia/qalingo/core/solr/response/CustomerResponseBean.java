/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
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

import fr.hoteia.qalingo.core.solr.bean.CustomerSolr;

public class CustomerResponseBean {

	private List<CustomerSolr> customerSolrList = new ArrayList<CustomerSolr>();
	
	private List<FacetField> customerSolrFacetFieldList = new ArrayList<FacetField>();

	public List<CustomerSolr> getCustomerSolrList() {
		return customerSolrList;
	}

	public void setCustomerSolrList(List<CustomerSolr> customerSolrList) {
		this.customerSolrList = customerSolrList;
	}

	public List<FacetField> getCustomerSolrFacetFieldList() {
		return customerSolrFacetFieldList;
	}

	public void setCustomerSolrFacetFieldList(
			List<FacetField> customerSolrFacetFieldList) {
		this.customerSolrFacetFieldList = customerSolrFacetFieldList;
	}
	
}
