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
import org.hibernate.Hibernate;
import org.hoteia.qalingo.core.domain.Store;
import org.hoteia.qalingo.core.solr.bean.SolrFields;
import org.hoteia.qalingo.core.solr.bean.SolrParam;
import org.hoteia.qalingo.core.solr.bean.StoreSolr;
import org.hoteia.qalingo.core.solr.response.StoreResponseBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("storeSolrService")
@Transactional
public class StoreSolrService extends AbstractSolrService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    protected SolrServer storeSolrServer;
    
    public void addOrUpdateStore(final Store store) throws SolrServerException, IOException {
        if (store.getId() == null) {
            throw new IllegalArgumentException("Id  cannot be blank or null.");
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Indexing store " + store.getId() + " : " + store.getName() + " : " + store.getCity());
        }
        StoreSolr storeSolr = new StoreSolr();
        storeSolr.setId(store.getId());
        storeSolr.setCode(store.getCode());
        storeSolr.setName(store.getName());
        if(Hibernate.isInitialized(store.getRetailer()) && store.getRetailer() != null
                && Hibernate.isInitialized(store.getRetailer().getCompany()) && store.getRetailer().getCompany() != null){
            storeSolr.setCompanyName(store.getRetailer().getCompany().getName());
        }
        storeSolr.setActive(store.isActive());
        storeSolr.setB2b(store.isB2b());
        storeSolr.setB2c(store.isB2c());
        storeSolr.setActive(store.isActive());
        storeSolr.setAddress(store.getAddress1());
        storeSolr.setPostalCode(store.getPostalCode());
        storeSolr.setCity(store.getCity());
        storeSolr.setCountryCode(store.getCountryCode());
        storeSolr.setType(store.getType());
        storeSolrServer.addBean(storeSolr);
        storeSolrServer.commit();
    }
    
    public void removeStore(final StoreSolr storeSolr) throws SolrServerException, IOException {
        if (storeSolr.getId() == null) {
            throw new IllegalArgumentException("Id  cannot be blank or null.");
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Remove Index Store " + storeSolr.getId() + " : " + storeSolr.getName() + " : " + storeSolr.getCity());
        }
        storeSolrServer.deleteById(storeSolr.getId().toString());
        storeSolrServer.commit();
    }

    public StoreResponseBean searchStore(final String searchQuery, final List<String> facetFields, final List<String> cities, final List<String> countries, final SolrParam solrParam) throws SolrServerException, IOException {
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

        if (facetFields != null && facetFields.size() > 0) {
            solrQuery.setFacet(true);
            solrQuery.setFacetMinCount(1);
            for (String facetField : facetFields) {
                solrQuery.addFacetField(facetField);
            }
        }

        if (cities != null && cities.size() > 0) {
            StringBuilder fq = new StringBuilder("city:(");
            for (int i = 0; i < cities.size(); i++) {
                String city = cities.get(i);
                fq.append('"' + city + '"');
                if (i < cities.size() - 1) {
                    fq.append(" OR ");
                }
            }
            fq.append(")");
            solrQuery.addFilterQuery(fq.toString());
        }
        if (countries != null && countries.size() > 0) {
            StringBuilder fq = new StringBuilder("countrycode:(");
            for (int i = 0; i < countries.size(); i++) {
                String country = countries.get(i);
                fq.append('"' + country + '"');
                if (i < countries.size() - 1) {
                    fq.append(" OR ");
                }
            }
            fq.append(")");
            solrQuery.addFilterQuery(fq.toString());
        }

        logger.debug("QueryRequest solrQuery: " + solrQuery);

        SolrRequest request = new QueryRequest(solrQuery, METHOD.POST);

        QueryResponse response = new QueryResponse(storeSolrServer.request(request), storeSolrServer);

        logger.debug("QueryResponse Obj: " + response.toString());

        List<StoreSolr> solrList = response.getBeans(StoreSolr.class);
        StoreResponseBean storeResponseBean = new StoreResponseBean();
        storeResponseBean.setStoreSolrList(solrList);

        if (facetFields != null && facetFields.size() > 0) {
            List<FacetField> solrFacetFieldList = response.getFacetFields();
            storeResponseBean.setStoreSolrFacetFieldList(solrFacetFieldList);
        }
        return storeResponseBean;
    }
    
    @Deprecated
    public StoreResponseBean searchStore(String searchBy, String searchText, List<String> facetFields) throws SolrServerException, IOException {
    	return searchStore(searchBy, searchText, facetFields, null, null);
    }

    @Deprecated
    public StoreResponseBean searchStore(String searchBy, String searchText, List<String> facetFields,
                                         List<String> cities, List<String> countries) throws SolrServerException, IOException {
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
        
        if (facetFields != null && facetFields.size() > 0) {
            solrQuery.setFacet(true);
            solrQuery.setFacetMinCount(1);
            for( String facetField : facetFields){
            	solrQuery.addFacetField(facetField);
            }
        }

        if(cities != null && cities.size() > 0){
        	StringBuilder fq = new StringBuilder("city:(");
        	for (int i = 0; i < cities.size(); i++) {
				String city = cities.get(i);
				fq.append('"'+city+'"');
				if(i < cities.size() - 1){
					fq.append(" OR ");
				}
			}
        	fq.append(")");
        	solrQuery.addFilterQuery(fq.toString());
        }
        if(countries != null && countries.size() > 0){
        	StringBuilder fq = new StringBuilder("countrycode:(");
        	for (int i = 0; i < countries.size(); i++) {
				String country = countries.get(i);
				fq.append('"'+country+'"');
				if(i < countries.size() - 1){
					fq.append(" OR ");
				}
			}
        	fq.append(")");
        	solrQuery.addFilterQuery(fq.toString());
        }
        
        logger.debug("QueryRequest solrQuery: " + solrQuery);

        SolrRequest request = new QueryRequest(solrQuery, METHOD.POST);

        QueryResponse response = new QueryResponse(storeSolrServer.request(request), storeSolrServer);

        logger.debug("QueryResponse Obj: " + response.toString());
        
        List<StoreSolr> solrList = response.getBeans(StoreSolr.class);
        StoreResponseBean storeResponseBean = new StoreResponseBean();
        storeResponseBean.setStoreSolrList(solrList);

        if (facetFields != null && facetFields.size() > 0) {
            List<FacetField> solrFacetFieldList = response.getFacetFields();
            storeResponseBean.setStoreSolrFacetFieldList(solrFacetFieldList);
        }
        return storeResponseBean;
    }
	
    @Deprecated
    public StoreResponseBean searchStore() throws SolrServerException, IOException {
        SolrQuery solrQuery = new SolrQuery();
        solrQuery.setParam("rows", getMaxResult());
        
        solrQuery.setQuery("*");
        solrQuery.setFacet(true);
        solrQuery.setFacetMinCount(1);
        solrQuery.addFacetField("name");
        
        logger.debug("QueryRequest solrQuery: " + solrQuery);

        SolrRequest request = new QueryRequest(solrQuery, METHOD.POST);
        
        QueryResponse response = new QueryResponse(storeSolrServer.request(request), storeSolrServer);
        
        logger.debug("QueryResponse Obj: " + response.toString());

        List<StoreSolr> solrList = response.getBeans(StoreSolr.class);
        List<FacetField> solrFacetFieldList = response.getFacetFields();
        StoreResponseBean storeResponseBean = new StoreResponseBean();
        storeResponseBean.setStoreSolrList(solrList);
        storeResponseBean.setStoreSolrFacetFieldList(solrFacetFieldList);
        return storeResponseBean;
    }
    
}