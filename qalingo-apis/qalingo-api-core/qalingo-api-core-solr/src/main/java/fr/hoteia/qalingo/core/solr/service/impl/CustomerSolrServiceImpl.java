/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package fr.hoteia.qalingo.core.solr.service.impl;

import java.io.IOException;
import java.util.List;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrRequest;
import org.apache.solr.client.solrj.SolrRequest.METHOD;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.request.QueryRequest;
import org.apache.solr.client.solrj.response.FacetField;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.hoteia.qalingo.core.domain.Customer;
import fr.hoteia.qalingo.core.solr.bean.CustomerSolr;
import fr.hoteia.qalingo.core.solr.response.CustomerResponseBean;
import fr.hoteia.qalingo.core.solr.service.CustomerSolrService;

@Service("customerSolrService")
@Transactional
public class CustomerSolrServiceImpl extends AbstractSolrService implements CustomerSolrService {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	/**
     * 
     */
	public void addOrUpdateCustomer(Customer customer) throws SolrServerException, IOException {

		CustomerSolr customerSolr = new CustomerSolr();
		customerSolr.setId(customer.getId());
		solrServer.addBean(customerSolr);
        
		solrServer.commit();
	}

	/**
     * 
     */
	public CustomerResponseBean searchCustomer() throws SolrServerException, IOException {

        SolrQuery solrQuery = new SolrQuery();
        solrQuery.setQuery("*");
        solrQuery.setFacet(true);
        solrQuery.setFacetMinCount(1);
        solrQuery.setFacetLimit(8);
        solrQuery.addFacetField("lastname");

        SolrRequest request = new QueryRequest(solrQuery, METHOD.POST);
        request.setPath(getRequestPath());
        QueryResponse response = new QueryResponse(solrServer.request(request), solrServer);
    	List<CustomerSolr> customerSolrList = response.getBeans(CustomerSolr.class);
    	List<FacetField> customerSolrFacetFieldList = response.getFacetFields();
    	
    	CustomerResponseBean customerResponseBean = new CustomerResponseBean();
    	customerResponseBean.setCustomerSolrList(customerSolrList);
    	customerResponseBean.setCustomerSolrFacetFieldList(customerSolrFacetFieldList);
    	
    	return customerResponseBean;
	}
	
	
	private String getRequestPath(){
		return "/customer";
	}
	
}
