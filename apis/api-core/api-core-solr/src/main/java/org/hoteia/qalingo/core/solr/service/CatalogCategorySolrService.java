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
import org.hoteia.qalingo.core.solr.bean.StoreSolr;
import org.hoteia.qalingo.core.solr.response.CatalogCategoryResponseBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("catalogCategorySolrService")
@Transactional
public class CatalogCategorySolrService extends AbstractSolrService {
	
    private final Logger logger = LoggerFactory.getLogger(getClass());
	
    @Autowired
    public SolrServer catalogCategorySolrServer;
    
	public void addOrUpdateCatalogCategory(final CatalogCategoryMaster catalogCategoryMaster, final MarketArea marketArea) throws SolrServerException, IOException {
        if (catalogCategoryMaster.getId() == null) {
            throw new IllegalArgumentException("Id cannot be blank or null.");
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Indexing category " + catalogCategoryMaster.getId() + " : " + catalogCategoryMaster.getCode() + " : " + catalogCategoryMaster.getName());
        }

        CatalogCategorySolr categorySolr = new CatalogCategorySolr();
        categorySolr.setId(catalogCategoryMaster.getId());
        categorySolr.setCode(catalogCategoryMaster.getCode());
        categorySolr.setDateCreate(catalogCategoryMaster.getDateCreate());
        categorySolr.setDateUpdate(catalogCategoryMaster.getDateUpdate());
        categorySolr.setName(catalogCategoryMaster.getName());

        catalogCategorySolrServer.addBean(categorySolr);
        catalogCategorySolrServer.commit();
        
        logger.debug("Fields has been added sucessfully ");
    }

    public void removeCatalogCategory(final CatalogCategorySolr categorySolr) throws SolrServerException, IOException {
        if (categorySolr.getId() == null) {
            throw new IllegalArgumentException("Id  cannot be blank or null.");
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Remove Index category " + categorySolr.getId() + " : " + categorySolr.getName());
        }
        catalogCategorySolrServer.deleteById(categorySolr.getId().toString());
        catalogCategorySolrServer.commit();
    }
    
	public CatalogCategoryResponseBean searchCatalogCategory(String searchBy,String searchText, String facetField) throws SolrServerException, IOException {
        SolrQuery solrQuery = new SolrQuery();
        solrQuery.setParam("rows", ROWS_DEFAULT_VALUE);
        
        if (StringUtils.isEmpty(searchBy)) {
            throw new IllegalArgumentException("SearchBy field can not be Empty or Blank!");
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

        logger.debug("QueryRequest solrQuery: " + solrQuery);

        SolrRequest request = new QueryRequest(solrQuery, METHOD.POST);

        QueryResponse response = new QueryResponse(catalogCategorySolrServer.request(request), catalogCategorySolrServer);
        
        logger.debug("QueryResponse Obj: " + response.toString());

        List<CatalogCategorySolr> solrList = response.getBeans(CatalogCategorySolr.class);
        CatalogCategoryResponseBean catalogCategoryResponseBean = new CatalogCategoryResponseBean();
        catalogCategoryResponseBean.setCatalogCategorySolrList(solrList);
        
        if (StringUtils.isNotEmpty(facetField)) {
            List<FacetField> solrFacetFieldList = response.getFacetFields();
            catalogCategoryResponseBean.setCatalogCategorySolrFacetFieldList(solrFacetFieldList);
        }
        
        return catalogCategoryResponseBean;
    }

    public CatalogCategoryResponseBean searchCatalogCategory() throws SolrServerException, IOException {
        SolrQuery solrQuery = new SolrQuery();
        solrQuery.setParam("rows", ROWS_DEFAULT_VALUE);
        
        solrQuery.setQuery("*");
        solrQuery.setFacet(true);
        solrQuery.setFacetMinCount(1);
        solrQuery.setFacetLimit(8);
        solrQuery.addFacetField("code");

        logger.debug("QueryRequest solrQuery: " + solrQuery);

        SolrRequest request = new QueryRequest(solrQuery, METHOD.POST);

        QueryResponse response = new QueryResponse(catalogCategorySolrServer.request(request), catalogCategorySolrServer);
        
        logger.debug("QueryResponse Obj: " + response.toString());

        List<CatalogCategorySolr> solrList = response.getBeans(CatalogCategorySolr.class);
        List<FacetField> solrFacetFieldList = response.getFacetFields();

        CatalogCategoryResponseBean catalogCategoryResponseBean = new CatalogCategoryResponseBean();
        catalogCategoryResponseBean.setCatalogCategorySolrList(solrList);
        catalogCategoryResponseBean.setCatalogCategorySolrFacetFieldList(solrFacetFieldList);
        return catalogCategoryResponseBean;
    }

}