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

import fr.hoteia.qalingo.core.common.domain.Store;
import fr.hoteia.qalingo.core.solr.bean.StoreSolr;
import fr.hoteia.qalingo.core.solr.response.StoreResponseBean;
import fr.hoteia.qalingo.core.solr.service.StoreSolrService;

@Service("storeSolrService")
@Transactional
public class StoreSolrServiceImpl extends AbstractSolrService implements StoreSolrService {

	private final Logger LOG = LoggerFactory.getLogger(getClass());

	/**
     * 
     */
	public void addOrUpdateStore(Store store) throws SolrServerException, IOException {

		StoreSolr storeSolr = new StoreSolr();
		storeSolr.setId(store.getId());
		solrServer.addBean(storeSolr);
        
		solrServer.commit();
	}

	/**
     * 
     */
	public StoreResponseBean searchStore() throws SolrServerException, IOException {

        SolrQuery solrQuery = new SolrQuery();
        solrQuery.setQuery("*");
        solrQuery.setFacet(true);
        solrQuery.setFacetMinCount(1);
        solrQuery.setFacetLimit(8);
        solrQuery.addFacetField("businessname");

        SolrRequest request = new QueryRequest(solrQuery, METHOD.POST);
        request.setPath(getRequestPath());
        QueryResponse response = new QueryResponse(solrServer.request(request), solrServer);
    	List<StoreSolr> storeSolrList = response.getBeans(StoreSolr.class);
    	List<FacetField> storeSolrFacetFieldList = response.getFacetFields();
    	
    	StoreResponseBean storeResponseBean = new StoreResponseBean();
    	storeResponseBean.setStoreSolrList(storeSolrList);
    	storeResponseBean.setStoreSolrFacetFieldList(storeSolrFacetFieldList);
    	
    	return storeResponseBean;
	}
	
	private String getRequestPath(){
		return "/store";
	}
	
}
