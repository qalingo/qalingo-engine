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

import fr.hoteia.qalingo.core.domain.CatalogCategoryMaster;
import fr.hoteia.qalingo.core.solr.bean.CategorySolr;
import fr.hoteia.qalingo.core.solr.bean.ProductSolr;
import fr.hoteia.qalingo.core.solr.response.CategoryResponseBean;
import fr.hoteia.qalingo.core.solr.service.CategorySolrService;

@Service("categorySolrService")
@Transactional
public class CategorySolrServiceImpl extends AbstractSolrService implements CategorySolrService {

	private final Logger LOG = LoggerFactory.getLogger(getClass());

	/**
     * 
     */
	public void addOrUpdateCategory(CatalogCategoryMaster productCategory) throws SolrServerException, IOException {

		ProductSolr productSolr = new ProductSolr();
		productSolr.setId(productCategory.getId());
		productSolr.setName(productCategory.getBusinessName());
		productSolr.setDescription(productCategory.getDescription());
		productSolr.setCode(productCategory.getCode());
		solrServer.addBean(productSolr);
        
		solrServer.commit();
	}

	/**
     * 
     */
	public CategoryResponseBean searchCategory() throws SolrServerException, IOException {

        SolrQuery solrQuery = new SolrQuery();
        solrQuery.setQuery("*");
        solrQuery.setFacet(true);
        solrQuery.setFacetMinCount(1);
        solrQuery.setFacetLimit(8);
        solrQuery.addFacetField("name");
        solrQuery.addFacetField("code");

        SolrRequest request = new QueryRequest(solrQuery, METHOD.POST);
        request.setPath(getRequestPath());
        QueryResponse response = new QueryResponse(solrServer.request(request), solrServer);
    	List<CategorySolr> productSolrList = response.getBeans(CategorySolr.class);
    	List<FacetField> productSolrFacetFieldList = response.getFacetFields();
    	
    	CategoryResponseBean categoryResponseBean = new CategoryResponseBean();
    	categoryResponseBean.setCategorySolrList(productSolrList);
    	categoryResponseBean.setCategorySolrFacetFieldList(productSolrFacetFieldList);
    	
    	return categoryResponseBean;
	}
	
	private String getRequestPath(){
		return "/category";
	}
	
}
