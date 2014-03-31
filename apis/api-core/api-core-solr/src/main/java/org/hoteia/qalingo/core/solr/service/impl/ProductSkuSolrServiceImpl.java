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
import java.math.BigDecimal;
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
import org.hoteia.qalingo.core.domain.ProductSku;
import org.hoteia.qalingo.core.domain.ProductSkuPrice;
import org.hoteia.qalingo.core.domain.Retailer;
import org.hoteia.qalingo.core.solr.bean.ProductSkuSolr;
import org.hoteia.qalingo.core.solr.response.ProductSkuResponseBean;
import org.hoteia.qalingo.core.solr.service.ProductSkuSolrService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("productSkuSolrService")
@Transactional
public class ProductSkuSolrServiceImpl extends AbstractSolrService implements ProductSkuSolrService {

    private final Logger logger = LoggerFactory.getLogger(getClass());
	
    @Autowired
    public SolrServer productSkuSolrServer;
    
	/* (non-Javadoc)
	 * @see fr.hoteia.qalingo.core.solr.service.ProductSkuSolrService#addOrUpdateProductSku(fr.hoteia.qalingo.core.domain.ProductSku)
	 */
    public void addOrUpdateProductSku(final ProductSku productSku, final MarketArea marketArea, final Retailer retailer) throws SolrServerException, IOException {
        if (productSku.getId() == null) {
            throw new IllegalArgumentException("Id  cannot be blank or null.");
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Indexing productSku " + productSku.getId());
            logger.debug("Indexing productSku " + productSku.getName());
            logger.debug("Indexing productSku " + productSku.getDescription());
            logger.debug("Indexing productSku " + productSku.getCode());
        }
        ProductSkuSolr productSkuSolr = new ProductSkuSolr();
        productSkuSolr.setId(productSku.getId());
        productSkuSolr.setName(productSku.getName());
        productSkuSolr.setDescription(productSku.getDescription());
        productSkuSolr.setCode(productSku.getCode());
        ProductSkuPrice productSkuPrice = productSku.getPrice(marketArea.getId(), retailer.getId());
        if(productSkuPrice != null){
            BigDecimal salePrice = productSkuPrice.getSalePrice();
            productSkuSolr.setPrice(salePrice.toString());
        }
        productSkuSolrServer.addBean(productSkuSolr);
        productSkuSolrServer.commit();
    }

	/* (non-Javadoc)
	 * @see fr.hoteia.qalingo.core.solr.service.ProductSkuSolrService#searchProductSku(java.lang.String, java.lang.String, java.lang.String)
	 */
    public ProductSkuResponseBean searchProductSku(String searchBy, String searchText, String facetField) throws SolrServerException, IOException {
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
        QueryResponse response = new QueryResponse(productSkuSolrServer.request(request), productSkuSolrServer);
        logger.debug("QueryResponse Obj: " + response);
        List<ProductSkuSolr> productSkuSolrList = response.getBeans(ProductSkuSolr.class);
        logger.debug(" productSkuSolrList: " + productSkuSolrList);
        ProductSkuResponseBean productResponseBean = new ProductSkuResponseBean();
        productResponseBean.setProductSkuSolrList(productSkuSolrList);
        logger.debug("productSkuSolrList add sucessflly in productResponseBeen ");
        if (StringUtils.isNotEmpty(facetField)) {
            List<FacetField> productSkuSolrFacetFieldList = response.getFacetFields();
            logger.debug("ProductFacetFileList: " + productSkuSolrFacetFieldList);
            productResponseBean.setProductSkuSolrFacetFieldList(productSkuSolrFacetFieldList);
            logger.debug(" ProductFacetFileList Add sucessflly in productResponseBeen  ");
        }
        return productResponseBean;
    }

	/* (non-Javadoc)
	 * @see fr.hoteia.qalingo.core.solr.service.ProductSkuSolrService#searchProductSku()
	 */
    public ProductSkuResponseBean searchProductSku() throws SolrServerException, IOException {
        SolrQuery solrQuery = new SolrQuery();
        solrQuery.setParam("rows", ROWS_DEFAULT_VALUE);
        
        solrQuery.setQuery("*");
        solrQuery.setFacet(true);
        solrQuery.setFacetMinCount(1);
        solrQuery.setFacetLimit(8);
        solrQuery.addFacetField("name");
        solrQuery.addFacetField("code");
        SolrRequest request = new QueryRequest(solrQuery, METHOD.POST);
        // request.setPath(getRequestPath());
        QueryResponse response = new QueryResponse(productSkuSolrServer.request(request), productSkuSolrServer);
        logger.debug("QueryResponse Obj: " + response);
        List<ProductSkuSolr> productSkuSolrList = response.getBeans(ProductSkuSolr.class);
        logger.debug(" productSkuSolrList: " + productSkuSolrList);
        List<FacetField> productSkuSolrFacetFieldList = response.getFacetFields();
        logger.debug("ProductFacetFileList: " + productSkuSolrFacetFieldList);
        ProductSkuResponseBean productSkuResponseBean = new ProductSkuResponseBean();
        productSkuResponseBean.setProductSkuSolrList(productSkuSolrList);
        productSkuResponseBean.setProductSkuSolrFacetFieldList(productSkuSolrFacetFieldList);
        logger.debug("productSkuSolrList  And ProductFacetFileList Add sucessflly in productSkuResponseBeen  ");
        return productSkuResponseBean;
    }

}

