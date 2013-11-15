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
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.request.QueryRequest;
import org.apache.solr.client.solrj.response.FacetField;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.hoteia.qalingo.core.domain.ProductMarketing;
import org.hoteia.qalingo.core.solr.bean.ProductSkuSolr;
import org.hoteia.qalingo.core.solr.bean.ProductSolr;
import org.hoteia.qalingo.core.solr.response.ProductResponseBean;
import org.hoteia.qalingo.core.solr.service.ProductSolrService;

/**
 * The Class ProductSolrServiceImpl.
 */
@Service("productSolrService")
@Transactional
public class ProductSolrServiceImpl extends AbstractSolrService implements ProductSolrService {

	/** The log. */
	private final Logger LOG = LoggerFactory.getLogger(getClass());
	
	/* (non-Javadoc)
	 * @see org.hoteia.qalingo.core.solr.service.ProductSolrService#addOrUpdateProduct(org.hoteia.qalingo.core.domain.ProductMarketing)
	 */
	public void addOrUpdateProduct(ProductMarketing productMarketing) throws SolrServerException, IOException {

		// Id should not be blank or null
		if(productMarketing.getId()==null) {
			throw new IllegalArgumentException("Id  cannot be blank or null.");
		}
		if (LOG.isDebugEnabled()) {
			LOG.debug("Indexing productMarketing " + productMarketing.getId());
			LOG.debug("Indexing productMarketing " + productMarketing.getBusinessName());
			LOG.debug("Indexing productMarketing " + productMarketing.getDescription());
			LOG.debug("Indexing productMarketing " + productMarketing.getCode());
		}
		ProductSolr productSolr = new ProductSolr();
		productSolr.setId(productMarketing.getId());
		productSolr.setBusinessname(productMarketing.getBusinessName());
		productSolr.setDescription(productMarketing.getDescription());
		productSolr.setCode(productMarketing.getCode());
		productSolrServer.addBean(productSolr);
		productSolrServer.commit();
	}


	/* (non-Javadoc)
	 * @see org.hoteia.qalingo.core.solr.service.ProductSolrService#searchProduct(java.lang.String, java.lang.String, java.lang.String)
	 */
	public ProductResponseBean searchProduct(String searchBy,String searchText, String facetField) throws SolrServerException, IOException {
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
		QueryResponse response = new QueryResponse(productSolrServer.request(request), productSolrServer);
		LOG.debug("QueryResponse Obj: "+response);
		List<ProductSkuSolr> productSkuSolrList = response.getBeans(ProductSkuSolr.class);
		LOG.debug(" productSkuSolrList: "+productSkuSolrList);
		ProductResponseBean productResponseBean = new ProductResponseBean();
		productResponseBean.setProductSolrList(productSkuSolrList);
		LOG.debug("productSkuSolrList add sucessflly in productResponseBeen ");
		if(StringUtils.isNotEmpty(facetField))
		{
			List<FacetField> productSolrFacetFieldList = response.getFacetFields();
			LOG.debug("ProductFacetFileList: "+productSolrFacetFieldList);
			productResponseBean.setProductSolrFacetFieldList(productSolrFacetFieldList);
			LOG.debug(" ProductFacetFileList Add sucessflly in productResponseBeen  ");
		}
		return productResponseBean;

	}

	/* (non-Javadoc)
	 * @see org.hoteia.qalingo.core.solr.service.ProductSolrService#searchProduct()
	 */
	public ProductResponseBean searchProduct() throws SolrServerException, IOException {
		SolrQuery solrQuery = new SolrQuery();
		solrQuery.setQuery("*");
		solrQuery.setFacet(true);
		solrQuery.setFacetMinCount(1);
		solrQuery.setFacetLimit(8);
		solrQuery.addFacetField("name");
		solrQuery.addFacetField("code");
		SolrRequest request = new QueryRequest(solrQuery, METHOD.POST);
		//  request.setPath(getRequestPath());
		QueryResponse response = new QueryResponse(productSolrServer.request(request), productSolrServer);
		LOG.debug("QueryResponse Obj: "+response);
		List<ProductSkuSolr> productSkuSolrList = response.getBeans(ProductSkuSolr.class);
		LOG.debug(" productSkuSolrList: "+productSkuSolrList);
		List<FacetField> productSolrFacetFieldList = response.getFacetFields();
		LOG.debug("ProductFacetFileList: "+productSolrFacetFieldList);
		ProductResponseBean productResponseBean = new ProductResponseBean();
		productResponseBean.setProductSolrList(productSkuSolrList);
		productResponseBean.setProductSolrFacetFieldList(productSolrFacetFieldList);
		LOG.debug("productSkuSolrList  And ProductFacetFileList Add sucessflly in productResponseBeen  ");
		return productResponseBean;
	}

}


