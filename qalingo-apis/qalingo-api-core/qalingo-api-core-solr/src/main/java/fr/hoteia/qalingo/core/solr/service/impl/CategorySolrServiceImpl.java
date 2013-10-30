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

import fr.hoteia.qalingo.core.domain.CatalogCategoryMaster;

import fr.hoteia.qalingo.core.solr.bean.CategorySolr;

import fr.hoteia.qalingo.core.solr.bean.ProductSolr;

import fr.hoteia.qalingo.core.solr.response.CategoryResponseBean;

import fr.hoteia.qalingo.core.solr.service.CategorySolrService;



/**
 * The Class CategorySolrServiceImpl.
 */
@Service("categorySolrService")
@Transactional
public class CategorySolrServiceImpl extends AbstractSolrService implements CategorySolrService {
	
	/** The log. */
	private final Logger LOG = LoggerFactory.getLogger(getClass());
	
	/* (non-Javadoc)
	 * @see fr.hoteia.qalingo.core.solr.service.CategorySolrService#addOrUpdateCategory(fr.hoteia.qalingo.core.domain.CatalogCategoryMaster)
	 */
	public void addOrUpdateCategory(CatalogCategoryMaster productCategory) throws SolrServerException, IOException {
		
		// productCategory should not be blank or empty 
		if( productCategory.getId()==null)
		{
			throw new IllegalArgumentException("Id cannot be blank or null.");
		}
		if (LOG.isDebugEnabled()) {
			LOG.debug("Indexing customer " + productCategory.getId());
			LOG.debug("Indexing customer " + productCategory.getCode());
			LOG.debug("Indexing customer " + productCategory.getBusinessName());
			LOG.debug("Indexing customer " + productCategory.getDateCreate());
			LOG.debug("Indexing customer " + productCategory.getDateUpdate());
		}

		ProductSolr productSolr = new ProductSolr();
		productSolr.setId(productCategory.getId());
		productSolr.setCode(productCategory.getCode());
		productSolr.setDateCreate(productCategory.getDateCreate());
		productSolr.setDateUpdate(productCategory.getDateUpdate());
		productSolr.setBusinessname(productCategory.getBusinessName());
		// Adding Been  in to solr for indexing  
		categorySolrServer.addBean(productSolr);
		categorySolrServer.commit();
		LOG.debug("Fields has been added sucessfully ");
	}

	/*
	 * Method for  product search  with given parameter 
	 */
	/* (non-Javadoc)
	 * @see fr.hoteia.qalingo.core.solr.service.CategorySolrService#searchCategory(java.lang.String, java.lang.String, java.lang.String)
	 */
	public CategoryResponseBean searchCategory(String searchBy,String searchText, String facetField) throws SolrServerException, IOException {
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
		QueryResponse response = new QueryResponse(categorySolrServer.request(request), categorySolrServer);
		LOG.debug("QueryResponse Obj: "+response);
		List<CategorySolr> categorySolrList = response.getBeans(CategorySolr.class);
		LOG.debug(" categorySolrList: "+categorySolrList);
		CategoryResponseBean categoryResponseBean = new CategoryResponseBean();
		categoryResponseBean.setCategorySolrList(categorySolrList);
		LOG.debug("categorySolrList add sucessflly in productResponseBeen ");
		if(StringUtils.isNotEmpty(facetField))
		{
			List<FacetField> categorySolrFacetFieldList = response.getFacetFields();
			LOG.debug("ProductFacetFileList: "+categorySolrFacetFieldList);
			categoryResponseBean.setCategorySolrFacetFieldList(categorySolrFacetFieldList);
			LOG.debug(" categoryFacetFileList Add sucessflly in categoryResponseBeen  ");
		}
		return categoryResponseBean;
	}

	/*
	 * Method for  Category search by given parameter
	 * */ 
	/* (non-Javadoc)
	 * @see fr.hoteia.qalingo.core.solr.service.CategorySolrService#searchCategory()
	 */
	public CategoryResponseBean searchCategory() throws SolrServerException, IOException {
		SolrQuery solrQuery = new SolrQuery();
		solrQuery.setQuery("*");
		solrQuery.setFacet(true);
		solrQuery.setFacetMinCount(1);
		solrQuery.setFacetLimit(8);
		solrQuery.addFacetField("code");
		SolrRequest request = new QueryRequest(solrQuery, METHOD.POST);
		//	request.setPath(getRequestPath());
		QueryResponse response = new QueryResponse(categorySolrServer.request(request), categorySolrServer);
		List<CategorySolr> productSolrList = response.getBeans(CategorySolr.class);
		List<FacetField> productSolrFacetFieldList = response.getFacetFields();

		CategoryResponseBean categoryResponseBean = new CategoryResponseBean();
		categoryResponseBean.setCategorySolrList(productSolrList);
		categoryResponseBean.setCategorySolrFacetFieldList(productSolrFacetFieldList);
		return categoryResponseBean;
	}



}
