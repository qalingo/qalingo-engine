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
import org.hoteia.qalingo.core.domain.CatalogCategoryMaster;
import org.hoteia.qalingo.core.domain.MarketArea;
import org.hoteia.qalingo.core.solr.bean.CatalogCategorySolr;
import org.hoteia.qalingo.core.solr.response.CatalogCategoryResponseBean;
import org.hoteia.qalingo.core.solr.service.CatalogCategorySolrService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("catalogCategorySolrService")
@Transactional
public class CatalogCategorySolrServiceImpl extends AbstractSolrService implements CatalogCategorySolrService {
	
    private final Logger logger = LoggerFactory.getLogger(getClass());
	
    @Autowired
    public SolrServer catalogCategorySolrServer;
    
	/* (non-Javadoc)
	 * @see fr.hoteia.qalingo.core.solr.service.CategorySolrService#addOrUpdateCategory(fr.hoteia.qalingo.core.domain.CatalogCategoryMaster)
	 */
	public void addOrUpdateCatalogCategory(final CatalogCategoryMaster catalogCategoryMaster, final MarketArea marketArea) throws SolrServerException, IOException {
        if (catalogCategoryMaster.getId() == null) {
            throw new IllegalArgumentException("Id cannot be blank or null.");
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Indexing customer " + catalogCategoryMaster.getId());
            logger.debug("Indexing customer " + catalogCategoryMaster.getCode());
            logger.debug("Indexing customer " + catalogCategoryMaster.getName());
            logger.debug("Indexing customer " + catalogCategoryMaster.getDateCreate());
            logger.debug("Indexing customer " + catalogCategoryMaster.getDateUpdate());
        }

        CatalogCategorySolr categorySolr = new CatalogCategorySolr();
        categorySolr.setId(catalogCategoryMaster.getId());
        categorySolr.setCode(catalogCategoryMaster.getCode());
        categorySolr.setDateCreate(catalogCategoryMaster.getDateCreate());
        categorySolr.setDateUpdate(catalogCategoryMaster.getDateUpdate());
        categorySolr.setName(catalogCategoryMaster.getName());

        // Adding Been in to solr for indexing
        catalogCategorySolrServer.addBean(categorySolr);
        catalogCategorySolrServer.commit();
        
        logger.debug("Fields has been added sucessfully ");
    }

	/*
	 * Method for  product search  with given parameter 
	 */
	/* (non-Javadoc)
	 * @see fr.hoteia.qalingo.core.solr.service.CatalogCategorySolrService#searchCatalogCategory(java.lang.String, java.lang.String, java.lang.String)
	 */
	public CatalogCategoryResponseBean searchCatalogCategory(String searchBy,String searchText, String facetField) throws SolrServerException, IOException {
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

        if (StringUtils.isNotEmpty(facetField)) {
            solrQuery.setFacet(true);
            solrQuery.setFacetMinCount(1);
            solrQuery.setFacetLimit(8);
            solrQuery.addFacetField(facetField);
        }

        SolrRequest request = new QueryRequest(solrQuery, METHOD.POST);
        QueryResponse response = new QueryResponse(catalogCategorySolrServer.request(request), catalogCategorySolrServer);
        logger.debug("QueryResponse Obj: " + response);

        List<CatalogCategorySolr> categorySolrList = response.getBeans(CatalogCategorySolr.class);
        logger.debug(" categorySolrList: " + categorySolrList);
        
        CatalogCategoryResponseBean catalogCategoryResponseBean = new CatalogCategoryResponseBean();
        catalogCategoryResponseBean.setCatalogCategorySolrList(categorySolrList);
        
        logger.debug("categorySolrList add sucessflly in productResponseBeen ");
        
        if (StringUtils.isNotEmpty(facetField)) {
            List<FacetField> categorySolrFacetFieldList = response.getFacetFields();
            logger.debug("ProductFacetFileList: " + categorySolrFacetFieldList);
            catalogCategoryResponseBean.setCatalogCategorySolrFacetFieldList(categorySolrFacetFieldList);
            logger.debug(" categoryFacetFileList Add sucessflly in categoryResponseBeen  ");
        }
        
        return catalogCategoryResponseBean;
    }

	/*
	 * Method for  CatalogCategory search by given parameter
	 * */ 
	/* (non-Javadoc)
	 * @see fr.hoteia.qalingo.core.solr.service.CatalogCategorySolrService#searchCatalogCategory()
	 */
    public CatalogCategoryResponseBean searchCatalogCategory() throws SolrServerException, IOException {
        SolrQuery solrQuery = new SolrQuery();
        solrQuery.setParam("rows", ROWS_DEFAULT_VALUE);
        
        solrQuery.setQuery("*");
        solrQuery.setFacet(true);
        solrQuery.setFacetMinCount(1);
        solrQuery.setFacetLimit(8);
        solrQuery.addFacetField("code");
        SolrRequest request = new QueryRequest(solrQuery, METHOD.POST);
        // request.setPath(getRequestPath());
        QueryResponse response = new QueryResponse(catalogCategorySolrServer.request(request), catalogCategorySolrServer);
        List<CatalogCategorySolr> productSolrList = response.getBeans(CatalogCategorySolr.class);
        List<FacetField> productSolrFacetFieldList = response.getFacetFields();

        CatalogCategoryResponseBean catalogCategoryResponseBean = new CatalogCategoryResponseBean();
        catalogCategoryResponseBean.setCatalogCategorySolrList(productSolrList);
        catalogCategoryResponseBean.setCatalogCategorySolrFacetFieldList(productSolrFacetFieldList);
        return catalogCategoryResponseBean;
    }

}