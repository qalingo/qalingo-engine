/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version ${license.version})
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

import fr.hoteia.qalingo.core.common.domain.ProductMarketing;
import fr.hoteia.qalingo.core.solr.bean.ProductSolr;
import fr.hoteia.qalingo.core.solr.response.ProductResponseBean;
import fr.hoteia.qalingo.core.solr.service.ProductSolrService;

@Service("productSolrService")
@Transactional
public class ProductSolrServiceImpl extends AbstractSolrService implements ProductSolrService {

	private final Logger LOG = LoggerFactory.getLogger(getClass());

	/**
     * 
     */
	public void addOrUpdateProduct(ProductMarketing productMarketing) throws SolrServerException, IOException {

		ProductSolr productSolr = new ProductSolr();
		productSolr.setId(productMarketing.getId());
		productSolr.setName(productMarketing.getBusinessName());
		productSolr.setDescription(productMarketing.getDescription());
		productSolr.setCode(productMarketing.getCode());
		solrServer.addBean(productSolr);
        
		solrServer.commit();
	}

	/**
     * 
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
        request.setPath(getRequestPath());
        QueryResponse response = new QueryResponse(solrServer.request(request), solrServer);
    	List<ProductSolr> productSolrList = response.getBeans(ProductSolr.class);
    	List<FacetField> productSolrFacetFieldList = response.getFacetFields();
    	
    	ProductResponseBean productResponseBean = new ProductResponseBean();
    	productResponseBean.setProductSolrList(productSolrList);
    	productResponseBean.setProductSolrFacetFieldList(productSolrFacetFieldList);
    	
    	return productResponseBean;
	}
	
	private String getRequestPath(){
		return "/product";
	}
	
}
