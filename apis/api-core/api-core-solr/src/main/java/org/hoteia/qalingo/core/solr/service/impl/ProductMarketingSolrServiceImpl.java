/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
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
import org.hoteia.qalingo.core.domain.ProductMarketing;
import org.hoteia.qalingo.core.solr.bean.ProductMarketingSolr;
import org.hoteia.qalingo.core.solr.response.ProductMarketingResponseBean;
import org.hoteia.qalingo.core.solr.service.ProductMarketingSolrService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("productMarketingSolrService")
@Transactional
public class ProductMarketingSolrServiceImpl extends AbstractSolrService implements ProductMarketingSolrService {

    private final Logger logger = LoggerFactory.getLogger(getClass());
	
    @Autowired
    public SolrServer productMarketingSolrServer;
    
	/* (non-Javadoc)
	 * @see fr.hoteia.qalingo.core.solr.service.ProductMarketingSolrService#addOrUpdateProductMarketing(fr.hoteia.qalingo.core.domain.ProductMarketing)
	 */
    public void addOrUpdateProductMarketing(ProductMarketing productMarketing) throws SolrServerException, IOException {
        if (productMarketing.getId() == null) {
            throw new IllegalArgumentException("Id  cannot be blank or null.");
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Indexing productMarketing " + productMarketing.getId());
            logger.debug("Indexing productMarketing " + productMarketing.getBusinessName());
            logger.debug("Indexing productMarketing " + productMarketing.getDescription());
            logger.debug("Indexing productMarketing " + productMarketing.getCode());
        }
        ProductMarketingSolr productSolr = new ProductMarketingSolr();
        productSolr.setId(productMarketing.getId());
        productSolr.setBusinessname(productMarketing.getBusinessName());
        productSolr.setDescription(productMarketing.getDescription());
        productSolr.setCode(productMarketing.getCode());
        productMarketingSolrServer.addBean(productSolr);
        productMarketingSolrServer.commit();
    }

	/* (non-Javadoc)
	 * @see fr.hoteia.qalingo.core.solr.service.ProductMarketingSolrService#searchProductMarketing(java.lang.String, java.lang.String, java.lang.String)
	 */
    public ProductMarketingResponseBean searchProductMarketing(String searchBy, String searchText, String facetField) throws SolrServerException, IOException {
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
        QueryResponse response = new QueryResponse(productMarketingSolrServer.request(request), productMarketingSolrServer);
        logger.debug("QueryResponse Obj: " + response);
        List<ProductMarketingSolr> ProductMarketingSolrList = response.getBeans(ProductMarketingSolr.class);
        logger.debug(" ProductMarketingSolrList: " + ProductMarketingSolrList);
        ProductMarketingResponseBean productMarketingResponseBean = new ProductMarketingResponseBean();
        productMarketingResponseBean.setProductMarketingSolrList(ProductMarketingSolrList);
        logger.debug("ProductMarketingSolrList add sucessflly in productResponseBeen ");
        if (StringUtils.isNotEmpty(facetField)) {
            List<FacetField> productSolrFacetFieldList = response.getFacetFields();
            logger.debug("ProductFacetFileList: " + productSolrFacetFieldList);
            productMarketingResponseBean.setProductMarketingSolrFacetFieldList(productSolrFacetFieldList);
            logger.debug(" ProductFacetFileList Add sucessflly in productResponseBeen  ");
        }
        return productMarketingResponseBean;
    }

	/* (non-Javadoc)
	 * @see fr.hoteia.qalingo.core.solr.service.ProductMarketingSolrService#searchProductMarketing()
	 */
    public ProductMarketingResponseBean searchProductMarketing() throws SolrServerException, IOException {
        SolrQuery solrQuery = new SolrQuery();
        solrQuery.setQuery("*");
        solrQuery.setFacet(true);
        solrQuery.setFacetMinCount(1);
        solrQuery.setFacetLimit(8);
        solrQuery.addFacetField("businessname");
        solrQuery.addFacetField("code");
        SolrRequest request = new QueryRequest(solrQuery, METHOD.POST);
        // request.setPath(getRequestPath());
        QueryResponse response = new QueryResponse(productMarketingSolrServer.request(request), productMarketingSolrServer);
        logger.debug("QueryResponse Obj: " + response);
        List<ProductMarketingSolr> productMarketingSolrList = response.getBeans(ProductMarketingSolr.class);
        logger.debug(" ProductMarketingSolrList: " + productMarketingSolrList);
        List<FacetField> productSolrFacetFieldList = response.getFacetFields();
        logger.debug("ProductFacetFileList: " + productSolrFacetFieldList);
        ProductMarketingResponseBean productMarketingResponseBean = new ProductMarketingResponseBean();
        productMarketingResponseBean.setProductMarketingSolrList(productMarketingSolrList);
        productMarketingResponseBean.setProductMarketingSolrFacetFieldList(productSolrFacetFieldList);
        logger.debug("ProductMarketingSolrList  And ProductFacetFileList Add sucessflly in productResponseBeen  ");
        return productMarketingResponseBean;
    }

}
