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
import org.apache.commons.lang.*;


/**
 * The Class CustomerSolrServiceImpl.
 */
@Service("customerSolrService")
@Transactional
public class CustomerSolrServiceImpl extends AbstractSolrService implements CustomerSolrService {

	/** The log. */
	private final Logger LOG = LoggerFactory.getLogger(getClass());
	
	/* (non-Javadoc)
	 * @see fr.hoteia.qalingo.core.solr.service.CustomerSolrService#addOrUpdateCustomer(fr.hoteia.qalingo.core.domain.Customer)
	 */
	public void addOrUpdateCustomer(Customer customer ) throws SolrServerException, IOException,IllegalArgumentException {
		// Id should not be blank or null
		if(customer.getId()==null) {
			throw new IllegalArgumentException("Id  cannot be blank or null.");
		}
		if (LOG.isDebugEnabled()) {
			LOG.debug("Indexing customer " + customer.getId());
			LOG.debug("Indexing customer " + customer.getFirstname());
			LOG.debug("Indexing customer " + customer.getEmail());
			LOG.debug("Indexing customer " + customer.getGender());
			LOG.debug("Indexing customer " + customer.getTitle());
		}
		CustomerSolr customerSolr = new CustomerSolr();
		customerSolr.setId(customer.getId());
		customerSolr.setName(customer.getFirstname());
		customerSolr.setEmail(customer.getEmail());
		customerSolr.setGender(customer.getGender());
		customerSolr.setTitle(customer.getTitle());
		customerSolrServer.addBean(customerSolr);
		customerSolrServer.commit();
	}
	/*
	 * Method for  Customer search by given parameters 
	 * */ 
	/* (non-Javadoc)
	 * @see fr.hoteia.qalingo.core.solr.service.CustomerSolrService#searchCustomer(java.lang.String, java.lang.String, java.lang.String)
	 */
	public CustomerResponseBean searchCustomer(String searchBy, String searchText, String facetField) throws IllegalArgumentException,SolrServerException, IOException {
		SolrQuery solrQuery = new SolrQuery();
		if(StringUtils.isEmpty(searchBy))
		{
			throw new IllegalArgumentException("searcBy field can not be Empty or Blank ");
		}

		if(StringUtils.isEmpty(searchText))
		{
			solrQuery.setQuery(searchBy+":*");
		}else
		{
			solrQuery.setQuery(searchBy+":"+searchText+"*");
		}

		if(StringUtils.isNotEmpty(facetField))
		{
			solrQuery.setFacet(true);
			solrQuery.setFacetMinCount(1);
			solrQuery.setFacetLimit(8);
			solrQuery.addFacetField(facetField);
		}

		SolrRequest request = new QueryRequest(solrQuery, METHOD.POST);
		
		QueryResponse response = new QueryResponse(customerSolrServer.request(request), customerSolrServer);
		LOG.debug("QueryResponse Obj: "+response);
		List<CustomerSolr> customerSolrList = response.getBeans(CustomerSolr.class);
		LOG.debug(" customerSolrList: "+customerSolrList);
		CustomerResponseBean customerResponseBean = new CustomerResponseBean();
		customerResponseBean.setCustomerSolrList(customerSolrList);
		LOG.debug("customerSolrList add sucessflly in customerResponseBeen ");
		if(StringUtils.isNotEmpty(facetField))
		{
			List<FacetField> customerSolrFacetFieldList = response.getFacetFields();
			LOG.debug("CustomerFacetFileList: "+customerSolrFacetFieldList);
			customerResponseBean.setCustomerSolrFacetFieldList(customerSolrFacetFieldList);
			LOG.debug(" CustomerFacetFileList Add sucessflly in customerResponseBeen  ");
		}
		return customerResponseBean;
	}
	/*
	 * Method for Default Customer search by
	 * */ 
	/* (non-Javadoc)
	 * @see fr.hoteia.qalingo.core.solr.service.CustomerSolrService#searchCustomer()
	 */
	public CustomerResponseBean searchCustomer() throws IllegalArgumentException,SolrServerException, IOException {
		SolrQuery solrQuery = new SolrQuery();
		solrQuery.setQuery("*");
		solrQuery.setFacet(true);
		solrQuery.setFacetMinCount(1);
		solrQuery.setFacetLimit(8);
	    solrQuery.addFacetField("lastname");
		SolrRequest request = new QueryRequest(solrQuery, METHOD.POST);
		// request.setPath(getRequestPath());
		QueryResponse response = new QueryResponse(customerSolrServer.request(request), customerSolrServer);
		List<CustomerSolr> customerSolrList = response.getBeans(CustomerSolr.class);
		List<FacetField> customerSolrFacetFieldList = response.getFacetFields();
		CustomerResponseBean customerResponseBean = new CustomerResponseBean();
		customerResponseBean.setCustomerSolrList(customerSolrList);
		customerResponseBean.setCustomerSolrFacetFieldList(customerSolrFacetFieldList);
		return customerResponseBean;
	}

}
