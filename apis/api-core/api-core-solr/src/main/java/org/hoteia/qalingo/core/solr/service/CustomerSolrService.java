/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.solr.service;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrRequest;
import org.apache.solr.client.solrj.SolrRequest.METHOD;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.request.QueryRequest;
import org.apache.solr.client.solrj.response.FacetField;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.hoteia.qalingo.core.domain.Customer;
import org.hoteia.qalingo.core.domain.MarketArea;
import org.hoteia.qalingo.core.solr.bean.CustomerSolr;
import org.hoteia.qalingo.core.solr.bean.SolrFields;
import org.hoteia.qalingo.core.solr.bean.SolrParam;
import org.hoteia.qalingo.core.solr.response.CustomerResponseBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("customerSolrService")
@Transactional
public class CustomerSolrService extends AbstractSolrService {

    private final Logger logger = LoggerFactory.getLogger(getClass());
	
    @Autowired
    protected SolrServer customerSolrServer;
    
    public void addOrUpdateCustomer(final Customer customer, final MarketArea marketArea) throws SolrServerException, IOException, IllegalArgumentException {
        if (customer.getId() == null) {
            throw new IllegalArgumentException("Id  cannot be blank or null.");
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Indexing Customer " + customer.getId() + " : " + customer.getCode() + " : " + customer.getFirstname() + " : " + customer.getLastname());
        }
        CustomerSolr customerSolr = new CustomerSolr();
        customerSolr.setId(customer.getId());
        customerSolr.setLastname(customer.getLastname());
        customerSolr.setFirstname(customer.getFirstname());
        customerSolr.setEmail(customer.getEmail());
        customerSolr.setGender(customer.getGender());
        customerSolr.setTitle(customer.getTitle());
        customerSolrServer.addBean(customerSolr);
        customerSolrServer.commit();
    }
	
    public void removeCustomer(final CustomerSolr customerSolr) throws SolrServerException, IOException {
        if (customerSolr.getId() == null) {
            throw new IllegalArgumentException("Id  cannot be blank or null.");
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Remove Index Customer " + customerSolr.getId() + " : " + customerSolr.getLastname() + " : " + customerSolr.getFirstname());
        }
        customerSolrServer.deleteById(customerSolr.getId().toString());
        customerSolrServer.commit();
    }
    
    public CustomerResponseBean searchCustomer(final String searchQuery, final List<String> facetFields, final List<String> filterQueries, final SolrParam solrParam) throws IllegalArgumentException, SolrServerException, IOException {
        SolrQuery solrQuery = new SolrQuery();

        if(solrParam != null){
            if(solrParam.get("rows") != null){
                solrQuery.setParam("rows", (String)solrParam.get("rows"));
            } else {
                solrQuery.setParam("rows", getMaxResult());
            }
            
            if(solrParam.get("solrFields") != null){
                SolrFields solrFields = (SolrFields) solrParam.get("solrFields");
                for (Iterator<String> iterator = solrFields.keySet().iterator(); iterator.hasNext();) {
                    String field = (String) iterator.next();
                    solrQuery.addSortField(field, solrFields.get(field));
                }
            }
        }
        
        if (StringUtils.isEmpty(searchQuery)) {
            throw new IllegalArgumentException("SearchQuery field can not be Empty or Blank!");
        }
        solrQuery.setQuery(searchQuery);

        if(facetFields != null && !facetFields.isEmpty()){
            solrQuery.setFacet(true);
            solrQuery.setFacetMinCount(1);
            for(String facetField : facetFields){
                solrQuery.addFacetField(facetField);
            }
        }

        logger.debug("QueryRequest solrQuery: " + solrQuery);

        SolrRequest request = new QueryRequest(solrQuery, METHOD.POST);

        QueryResponse response = new QueryResponse(customerSolrServer.request(request), customerSolrServer);

        logger.debug("QueryResponse Obj: " + response.toString());
        
        List<CustomerSolr> solrList = response.getBeans(CustomerSolr.class);
        CustomerResponseBean customerResponseBean = new CustomerResponseBean();
        customerResponseBean.setCustomerSolrList(solrList);
        
        if(facetFields != null && !facetFields.isEmpty()){
            List<FacetField> solrFacetFieldList = response.getFacetFields();
            customerResponseBean.setCustomerSolrFacetFieldList(solrFacetFieldList);
        }
        return customerResponseBean;
    }
    
    @Deprecated
    public CustomerResponseBean searchCustomer(String searchBy, String searchText, List<String> facetFields) throws IllegalArgumentException, SolrServerException, IOException {
        SolrQuery solrQuery = new SolrQuery();
        solrQuery.setParam("rows", getMaxResult());
        
        if (StringUtils.isEmpty(searchBy)) {
            throw new IllegalArgumentException("SearchBy field can not be Empty or Blank!");
        }

        if (StringUtils.isEmpty(searchText)) {
            solrQuery.setQuery(searchBy + ":*");
        } else {
            solrQuery.setQuery(searchBy + ":" + searchText + "*");
        }

        if(facetFields != null && !facetFields.isEmpty()){
            solrQuery.setFacet(true);
            solrQuery.setFacetMinCount(1);
            for(String facetField : facetFields){
                solrQuery.addFacetField(facetField);
            }
        }

        logger.debug("QueryRequest solrQuery: " + solrQuery);

        SolrRequest request = new QueryRequest(solrQuery, METHOD.POST);

        QueryResponse response = new QueryResponse(customerSolrServer.request(request), customerSolrServer);

        logger.debug("QueryResponse Obj: " + response.toString());
        
        List<CustomerSolr> solrList = response.getBeans(CustomerSolr.class);
        CustomerResponseBean customerResponseBean = new CustomerResponseBean();
        customerResponseBean.setCustomerSolrList(solrList);
        
        if(facetFields != null && !facetFields.isEmpty()){
            List<FacetField> solrFacetFieldList = response.getFacetFields();
            customerResponseBean.setCustomerSolrFacetFieldList(solrFacetFieldList);
        }
        return customerResponseBean;
    }
	
    public CustomerResponseBean searchCustomer() throws IllegalArgumentException, SolrServerException, IOException {
        SolrQuery solrQuery = new SolrQuery();
        solrQuery.setParam("rows", getMaxResult());
        
        solrQuery.setQuery("*");
        solrQuery.setFacet(true);
        solrQuery.setFacetMinCount(1);
        solrQuery.addFacetField("lastname");

        logger.debug("QueryRequest solrQuery: " + solrQuery);

        SolrRequest request = new QueryRequest(solrQuery, METHOD.POST);
        
        QueryResponse response = new QueryResponse(customerSolrServer.request(request), customerSolrServer);
        
        logger.debug("QueryResponse Obj: " + response.toString());

        List<CustomerSolr> solrList = response.getBeans(CustomerSolr.class);
        List<FacetField> solrFacetFieldList = response.getFacetFields();
        
        CustomerResponseBean customerResponseBean = new CustomerResponseBean();
        customerResponseBean.setCustomerSolrList(solrList);
        customerResponseBean.setCustomerSolrFacetFieldList(solrFacetFieldList);
        return customerResponseBean;
    }

}