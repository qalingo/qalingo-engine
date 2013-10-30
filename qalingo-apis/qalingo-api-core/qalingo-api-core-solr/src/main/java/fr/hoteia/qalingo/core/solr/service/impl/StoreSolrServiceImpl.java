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
import org.apache.commons.lang.StringUtils;
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
import fr.hoteia.qalingo.core.domain.Store;

import fr.hoteia.qalingo.core.solr.bean.StoreSolr;

import fr.hoteia.qalingo.core.solr.response.StoreResponseBean;
import fr.hoteia.qalingo.core.solr.service.StoreSolrService;

/**
 * The Class StoreSolrServiceImpl.
 */
@Service("storeSolrService")
@Transactional
public class StoreSolrServiceImpl extends AbstractSolrService implements StoreSolrService {

    /** The log. */
    private final Logger LOG = LoggerFactory.getLogger(getClass());

    /*
     * (non-Javadoc)
     * 
     * @see
     * fr.hoteia.qalingo.core.solr.service.StoreSolrService#addOrUpdateStore
     * (fr.hoteia.qalingo.core.domain.Store)
     */
    public void addOrUpdateStore(Store store) throws SolrServerException, IOException {

        // Id should not be blank or null
        if (store.getId() == null) {
            throw new IllegalArgumentException("Id  cannot be blank or null.");
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("Indexing store " + store.getId());
            LOG.debug("Indexing store " + store.getBusinessName());
            LOG.debug("Indexing store " + store.getCity());
            LOG.debug("Indexing store " + store.getCountryCode());
            LOG.debug("Indexing store " + store.getPostalCode());
            LOG.debug("Indexing store " + store.getType());
        }
        StoreSolr storeSolr = new StoreSolr();
        storeSolr.setId(store.getId());
        storeSolr.setBusinessname(store.getBusinessName());
        storeSolr.setCity(store.getCity());
        storeSolr.setCountryCode(store.getCountryCode());
        storeSolr.setPostalCode(store.getPostalCode());
        storeSolr.setType(store.getType());
        storeSolrServer.addBean(storeSolr);
        storeSolrServer.commit();
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * fr.hoteia.qalingo.core.solr.service.StoreSolrService#searchStore(java
     * .lang.String, java.lang.String, java.lang.String)
     */
    public StoreResponseBean searchStore(String searchBy, String searchText, String facetField) throws SolrServerException, IOException {
        SolrQuery solrQuery = new SolrQuery();
        if (StringUtils.isEmpty(searchBy)) {
            throw new IllegalArgumentException("searcBy field can not be Empty or Blank ");
        }

        if (StringUtils.isEmpty(searchText)) {
            solrQuery.setQuery(searchBy + ":*");
        } else {
            solrQuery.setQuery(searchBy + ":" + searchText + "*");
        }

        if (StringUtils.isNotEmpty(facetField)) {
            solrQuery.setFacet(true);
            solrQuery.setFacetMinCount(1);
            solrQuery.setFacetLimit(8);
            solrQuery.addFacetField(facetField);
        }

        SolrRequest request = new QueryRequest(solrQuery, METHOD.POST);

        QueryResponse response = new QueryResponse(storeSolrServer.request(request), storeSolrServer);
        LOG.debug("QueryResponse Obj: " + response);
        List<StoreSolr> storeSolrList = response.getBeans(StoreSolr.class);
        LOG.debug(" storeSolrList: " + storeSolrList);
        StoreResponseBean storeResponseBean = new StoreResponseBean();
        storeResponseBean.setStoreSolrList(storeSolrList);

        LOG.debug("storeSolrList add sucessflly in StoreResponseBeen ");
        if (StringUtils.isNotEmpty(facetField)) {
            List<FacetField> storeSolrFacetFieldList = response.getFacetFields();
            LOG.debug("storeFacetFileList: " + storeSolrFacetFieldList);
            storeResponseBean.setStoreSolrFacetFieldList(storeSolrFacetFieldList);

            LOG.debug(" StoreFacetFileList Add sucessflly in StoreResponseBeen  ");
        }
        return storeResponseBean;

    }

    /**
     * Method for Default Store search.
     * 
     * @return the store response bean
     * @throws SolrServerException
     *             the solr server exception
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    public StoreResponseBean searchStore() throws SolrServerException, IOException {
        SolrQuery solrQuery = new SolrQuery();
        solrQuery.setQuery("*");
        solrQuery.setFacet(true);
        solrQuery.setFacetMinCount(1);
        solrQuery.setFacetLimit(8);
        solrQuery.addFacetField("businessname");
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
