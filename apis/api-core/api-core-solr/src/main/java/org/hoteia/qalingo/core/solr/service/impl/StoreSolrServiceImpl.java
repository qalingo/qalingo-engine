/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.solr.service.impl;

import java.io.IOException;
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
import org.hoteia.qalingo.core.domain.MarketArea;
import org.hoteia.qalingo.core.domain.Store;
import org.hoteia.qalingo.core.solr.bean.StoreSolr;
import org.hoteia.qalingo.core.solr.response.StoreResponseBean;
import org.hoteia.qalingo.core.solr.service.StoreSolrService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("storeSolrService")
@Transactional
public class StoreSolrServiceImpl extends AbstractSolrService implements StoreSolrService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    protected SolrServer storeSolrServer;
    
	/* (non-Javadoc)
	 * @see fr.hoteia.qalingo.core.solr.service.StoreSolrService#addOrUpdateStore(fr.hoteia.qalingo.core.domain.Store)
	 */
    public void addOrUpdateStore(final Store store, final MarketArea marketArea) throws SolrServerException, IOException {
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
        storeSolr.setCity(store.getCity());
        storeSolr.setCountryCode(store.getCountryCode());
        storeSolr.setPostalCode(store.getPostalCode());
        storeSolr.setType(store.getType());
        storeSolrServer.addBean(storeSolr);
        storeSolrServer.commit();
    }
    
    /* (non-Javadoc)
	 * @see fr.hoteia.qalingo.core.solr.service.StoreSolrService#searchStore(java.lang.String, java.lang.String, java.lang.String)
	 */
    public StoreResponseBean searchStore(String searchBy, String searchText, List<String> facetFields) throws SolrServerException, IOException {
    	return searchStore(searchBy, searchText, facetFields, null, null);
    }

	/* (non-Javadoc)
	 * @see fr.hoteia.qalingo.core.solr.service.StoreSolrService#searchStore(java.lang.String, java.lang.String, java.lang.String)
	 */
    public StoreResponseBean searchStore(String searchBy, String searchText, List<String> facetFields,
                                         List<String> cities, List<String> countries) throws SolrServerException, IOException {
        SolrQuery solrQuery = new SolrQuery();
        solrQuery.setParam("rows", ROWS_DEFAULT_VALUE);
        
        if (StringUtils.isEmpty(searchBy)) {
            throw new IllegalArgumentException("searcBy field can not be Empty or Blank ");
        }

        if (StringUtils.isEmpty(searchText)) {
            solrQuery.setQuery(searchBy + ":*");
        } else {
            solrQuery.setQuery(searchBy + ":" + searchText + "*");
        }

        
        if (facetFields != null && facetFields.size() > 0) {
            solrQuery.setFacet(true);
            solrQuery.setFacetMinCount(1);
            solrQuery.setFacetLimit(30);
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
        

        SolrRequest request = new QueryRequest(solrQuery, METHOD.POST);

        QueryResponse response = new QueryResponse(storeSolrServer.request(request), storeSolrServer);
        logger.debug("QueryResponse Obj: " + response);
        List<StoreSolr> storeSolrList = response.getBeans(StoreSolr.class);
        logger.debug(" storeSolrList: " + storeSolrList);
        StoreResponseBean storeResponseBean = new StoreResponseBean();
        storeResponseBean.setStoreSolrList(storeSolrList);

        logger.debug("storeSolrList add sucessflly in StoreResponseBeen ");
        if (facetFields != null && facetFields.size() > 0) {
            List<FacetField> storeSolrFacetFieldList = response.getFacetFields();
            logger.debug("storeFacetFileList: " + storeSolrFacetFieldList);
            storeResponseBean.setStoreSolrFacetFieldList(storeSolrFacetFieldList);

            logger.debug(" StoreFacetFileList Add sucessflly in StoreResponseBeen  ");
        }
        return storeResponseBean;
    }
	
	/**
	 * Method for Default Store search.
	 *
	 * @return the store response bean
	 * @throws SolrServerException the solr server exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
    public StoreResponseBean searchStore() throws SolrServerException, IOException {
        SolrQuery solrQuery = new SolrQuery();
        solrQuery.setParam("rows", ROWS_DEFAULT_VALUE);
        
        solrQuery.setQuery("*");
        solrQuery.setFacet(true);
        solrQuery.setFacetMinCount(1);
        solrQuery.setFacetLimit(8);
        solrQuery.addFacetField("name");
        SolrRequest request = new QueryRequest(solrQuery, METHOD.POST);
        // request.setPath(getRequestPath());
        QueryResponse response = new QueryResponse(storeSolrServer.request(request), storeSolrServer);
        List<StoreSolr> storeSolrList = response.getBeans(StoreSolr.class);
        List<FacetField> storeSolrFacetFieldList = response.getFacetFields();
        StoreResponseBean storeResponseBean = new StoreResponseBean();
        storeResponseBean.setStoreSolrList(storeSolrList);
        storeResponseBean.setStoreSolrFacetFieldList(storeSolrFacetFieldList);
        return storeResponseBean;
    }
    
}