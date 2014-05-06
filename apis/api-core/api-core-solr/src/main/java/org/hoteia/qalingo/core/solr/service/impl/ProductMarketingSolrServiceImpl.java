/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
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
import org.hoteia.qalingo.core.domain.CatalogCategoryVirtual;
import org.hoteia.qalingo.core.domain.MarketArea;
import org.hoteia.qalingo.core.domain.ProductMarketing;
import org.hoteia.qalingo.core.domain.ProductSkuPrice;
import org.hoteia.qalingo.core.domain.Retailer;
import org.hoteia.qalingo.core.service.ProductService;
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
    
    @Autowired
    protected ProductService productService;
    
	/* (non-Javadoc)
	 * @see fr.hoteia.qalingo.core.solr.service.ProductMarketingSolrService#addOrUpdateProductMarketing(fr.hoteia.qalingo.core.domain.ProductMarketing)
	 */
    public void addOrUpdateProductMarketing(final ProductMarketing productMarketing, final List<CatalogCategoryVirtual> catalogCategories, final MarketArea marketArea, final Retailer retailer) throws SolrServerException, IOException {
        if (productMarketing.getId() == null) {
            throw new IllegalArgumentException("Id  cannot be blank or null.");
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Indexing productMarketing " + productMarketing.getId() + " : " + productMarketing.getCode() + " : " + productMarketing.getName());
        }
        
        ProductMarketingSolr productSolr = new ProductMarketingSolr();
        productSolr.setId(productMarketing.getId());
        productSolr.setCode(productMarketing.getCode());
        productSolr.setName(productMarketing.getName());
        productSolr.setDescription(productMarketing.getDescription());
        
        CatalogCategoryVirtual defaultVirtualCatalogCategory = productService.getDefaultVirtualCatalogCategory(productMarketing, catalogCategories, true);

        if(defaultVirtualCatalogCategory != null){
            productSolr.setDefaultCategoryCode(defaultVirtualCatalogCategory.getCode());
        }
        
        ProductSkuPrice productSkuPrice = productMarketing.getDefaultProductSku().getPrice(marketArea.getId(), retailer.getId());
        if(productSkuPrice != null){
            BigDecimal salePrice = productSkuPrice.getSalePrice();
            productSolr.setPrice(salePrice.floatValue());
        }
        
        if(catalogCategories != null){
            for (CatalogCategoryVirtual catalogCategoryVirtual : catalogCategories) {
                productSolr.addCatalogCategories(catalogCategoryVirtual.getCode());
            }
        }
        
        productMarketingSolrServer.addBean(productSolr);
        productMarketingSolrServer.commit();
    }

	/* (non-Javadoc)
	 * @see fr.hoteia.qalingo.core.solr.service.ProductMarketingSolrService#searchProductMarketing(java.lang.String, java.lang.String, java.lang.String)
	 */
    public ProductMarketingResponseBean searchProductMarketing(String searchBy, String searchText, String facetField) throws SolrServerException, IOException {
        return searchProductMarketing(searchBy, searchText, facetField, null, null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ProductMarketingResponseBean searchProductMarketing(String searchBy, String searchText, String facetField, BigDecimal priceStart, BigDecimal priceEnd) throws SolrServerException, IOException {
    	return searchProductMarketing(searchBy, searchText, facetField, priceStart, priceEnd, null);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public ProductMarketingResponseBean searchProductMarketing(final String searchBy, final String searchText, final String facetField, 
                                                               final BigDecimal priceStart, final BigDecimal priceEnd, final List<String> catalogCategories) throws SolrServerException, IOException {
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
        
        if(priceStart != null && priceEnd != null){
        	String fq = String.format("price:[%1$,.0f TO %2$,.0f]", priceStart.doubleValue(), priceEnd.doubleValue());
        	solrQuery.addFilterQuery(fq);
        }
        
        if(catalogCategories != null && catalogCategories.size() > 0){
        	StringBuilder fq = new StringBuilder("catalogCategories:(");
        	for (int i = 0; i < catalogCategories.size(); i++) {
				String cate = catalogCategories.get(i);
				fq.append(cate);
				if(i < catalogCategories.size() - 1){
					fq.append(" OR ");
				}
			}
        	fq.append(")");
        	solrQuery.addFilterQuery(fq.toString());
        }

        SolrRequest request = new QueryRequest(solrQuery, METHOD.POST);
        QueryResponse response = new QueryResponse(productMarketingSolrServer.request(request), productMarketingSolrServer);

        logger.debug("QueryResponse Obj: " + response);

        List<ProductMarketingSolr> ProductMarketingSolrList = response.getBeans(ProductMarketingSolr.class);

        logger.debug("ProductMarketingSolrList: " + ProductMarketingSolrList);

        ProductMarketingResponseBean productMarketingResponseBean = new ProductMarketingResponseBean();
        productMarketingResponseBean.setProductMarketingSolrList(ProductMarketingSolrList);

        logger.debug("ProductMarketingSolrList add sucessflly in productResponseBeen ");

        if (StringUtils.isNotEmpty(facetField)) {
            List<FacetField> productSolrFacetFieldList = response.getFacetFields();

            logger.debug("ProductFacetFileList: " + productSolrFacetFieldList);

            productMarketingResponseBean.setProductMarketingSolrFacetFieldList(productSolrFacetFieldList);

            logger.debug("ProductFacetFileList was successfully added in productResponseBeen  ");
        }
        return productMarketingResponseBean;
    }

	/* (non-Javadoc)
	 * @see fr.hoteia.qalingo.core.solr.service.ProductMarketingSolrService#searchProductMarketing()
	 */
    public ProductMarketingResponseBean searchProductMarketing() throws SolrServerException, IOException {
        SolrQuery solrQuery = new SolrQuery();
        solrQuery.setParam("rows", ROWS_DEFAULT_VALUE);
        solrQuery.setQuery("*");
        solrQuery.setFacet(true);
        solrQuery.setFacetMinCount(1);
        solrQuery.setFacetLimit(8);
        solrQuery.addFacetField(ProductMarketingResponseBean.PRODUCT_MARKETING_DEFAULT_FACET_FIELD);
        SolrRequest request = new QueryRequest(solrQuery, METHOD.POST);
        
        QueryResponse response = new QueryResponse(productMarketingSolrServer.request(request), productMarketingSolrServer);
        
        logger.debug("QueryResponse Obj: " + response);
        
        List<ProductMarketingSolr> productMarketingSolrList = response.getBeans(ProductMarketingSolr.class);
        
        logger.debug("ProductMarketingSolrList: " + productMarketingSolrList);
        
        List<FacetField> productSolrFacetFieldList = response.getFacetFields();
        
        logger.debug("ProductFacetFileList: " + productSolrFacetFieldList);
        
        ProductMarketingResponseBean productMarketingResponseBean = new ProductMarketingResponseBean();
        productMarketingResponseBean.setProductMarketingSolrList(productMarketingSolrList);
        productMarketingResponseBean.setProductMarketingSolrFacetFieldList(productSolrFacetFieldList);
        
        logger.debug("ProductMarketingSolrList And ProductFacetFileList was successfully added in productResponseBeen");
        
        return productMarketingResponseBean;
    }

}