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
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrRequest;
import org.apache.solr.client.solrj.SolrRequest.METHOD;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.request.QueryRequest;
import org.apache.solr.client.solrj.response.FacetField;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.hibernate.Hibernate;
import org.hoteia.qalingo.core.domain.CatalogCategoryVirtual;
import org.hoteia.qalingo.core.domain.MarketArea;
import org.hoteia.qalingo.core.domain.ProductMarketing;
import org.hoteia.qalingo.core.domain.ProductSku;
import org.hoteia.qalingo.core.domain.ProductSkuOptionRel;
import org.hoteia.qalingo.core.domain.ProductSkuPrice;
import org.hoteia.qalingo.core.domain.Retailer;
import org.hoteia.qalingo.core.service.ProductService;
import org.hoteia.qalingo.core.solr.bean.ProductMarketingSolr;
import org.hoteia.qalingo.core.solr.response.ProductMarketingResponseBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("productMarketingSolrService")
@Transactional
public class ProductMarketingSolrService extends AbstractSolrService {

    private final Logger logger = LoggerFactory.getLogger(getClass());
	
    @Autowired
    public SolrServer productMarketingSolrServer;
    
    @Autowired
    protected ProductService productService;
    
    public void addOrUpdateProductMarketing(final ProductMarketing productMarketing, final List<ProductSku> productSkus, final List<CatalogCategoryVirtual> catalogCategories, 
                                            final MarketArea marketArea, final Retailer retailer) throws SolrServerException, IOException {
        if (productMarketing.getId() == null) {
            throw new IllegalArgumentException("Id  cannot be blank or null.");
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Indexing productMarketing " + productMarketing.getId() + " : " + productMarketing.getCode() + " : " + productMarketing.getName());
        }
        
        ProductMarketingSolr productMarketingSolr = new ProductMarketingSolr();
        productMarketingSolr.setId(productMarketing.getId());
        productMarketingSolr.setCode(productMarketing.getCode());
        productMarketingSolr.setName(productMarketing.getName());
        productMarketingSolr.setDescription(productMarketing.getDescription());

        productMarketingSolr.setEnabledToB2C(productMarketing.isEnabledToB2C());

        if(productMarketing.getProductBrand() != null
                && Hibernate.isInitialized(productMarketing.getProductBrand())){
            productMarketingSolr.setProductBrandCode(productMarketing.getProductBrand().getCode());
        }

        CatalogCategoryVirtual defaultVirtualCatalogCategory = productService.getDefaultVirtualCatalogCategory(productMarketing, catalogCategories, true);

        if(defaultVirtualCatalogCategory != null){
            productMarketingSolr.setDefaultCategoryCode(defaultVirtualCatalogCategory.getCode());
        }
        
        if(catalogCategories != null){
            for (CatalogCategoryVirtual catalogCategoryVirtual : catalogCategories) {
                String catalogCode = catalogCategoryVirtual.getCatalog().getCode(); 
                productMarketingSolr.addCatalogCode(catalogCode);
                String catalogCategoryCode = catalogCategoryVirtual.getCatalog().getCode() + "_" + catalogCategoryVirtual.getCode(); 
                productMarketingSolr.addCatalogCategories(catalogCategoryCode);
            }
        }
        
        if(productSkus != null){
            Map<String, String> skuOptionDefinitionsCodes = new HashMap<String, String>();
            for (ProductSku productSku : productSkus) {
                if(productSku.getOptionRels() != null
                        && Hibernate.isInitialized(productSku.getOptionRels())){
                    for (ProductSkuOptionRel productSkuOptionRel : productSku.getOptionRels()) {
                        if(productSkuOptionRel.getProductSkuOptionDefinition() != null
                                && Hibernate.isInitialized(productSkuOptionRel.getProductSkuOptionDefinition())){
                            skuOptionDefinitionsCodes.put(productSkuOptionRel.getProductSkuOptionDefinition().getCode(), productSkuOptionRel.getProductSkuOptionDefinition().getCode());
                        }
                    }
                }
            }
            for (Iterator<String> iterator = skuOptionDefinitionsCodes.keySet().iterator(); iterator.hasNext();) {
                String skuOptionDefinitionsCode = (String) iterator.next();
                productMarketingSolr.addOptionDefinition(skuOptionDefinitionsCode);
            }
        }
        
        if(marketArea != null 
                && retailer != null){
            ProductSkuPrice productSkuPrice = productMarketing.getDefaultProductSku().getBestPrice(marketArea.getId());
            if(productSkuPrice != null){
                BigDecimal salePrice = productSkuPrice.getSalePrice();
                productMarketingSolr.setPrice(salePrice.floatValue());
            }
        }
        
        productMarketingSolrServer.addBean(productMarketingSolr);
        productMarketingSolrServer.commit();
    }

    public void removeProductMarketing(final ProductMarketingSolr productSolr) throws SolrServerException, IOException {
        if (productSolr.getId() == null) {
            throw new IllegalArgumentException("Id  cannot be blank or null.");
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Remove Index ProductMarketing " + productSolr.getId() + " : " + productSolr.getCode() + " : " + productSolr.getName());
        }
        productMarketingSolrServer.deleteById(productSolr.getId().toString());
        productMarketingSolrServer.commit();
    }

    public ProductMarketingResponseBean searchProductMarketing(final String searchQuery, final List<String> facetFields) throws SolrServerException, IOException {
        return searchProductMarketing(searchQuery, facetFields, null, null, null);
    }
    
    public ProductMarketingResponseBean searchProductMarketing(final String searchQuery, final List<String> facetFields, final BigDecimal priceStart, final BigDecimal priceEnd,
                                                               final List<String> filterQueries) throws SolrServerException, IOException {
        SolrQuery solrQuery = new SolrQuery();
        solrQuery.setParam("rows", ROWS_DEFAULT_VALUE);

        solrQuery.setQuery(searchQuery);

        if (facetFields != null && !facetFields.isEmpty()) {
            solrQuery.setFacet(true);
            solrQuery.setFacetMinCount(1);
            for (String facetField : facetFields) {
                solrQuery.addFacetField(facetField);
            }
        }

        if (filterQueries != null && filterQueries.size() > 0) {
            for (Iterator<String> iterator = filterQueries.iterator(); iterator.hasNext();) {
                String filterQuery = (String) iterator.next();
                solrQuery.addFilterQuery(filterQuery);
            }
        }

        logger.debug("QueryRequest solrQuery: " + solrQuery);

        SolrRequest request = new QueryRequest(solrQuery, METHOD.POST);
        QueryResponse response = new QueryResponse(productMarketingSolrServer.request(request), productMarketingSolrServer);

        logger.debug("QueryResponse Obj: " + response.toString());

        List<ProductMarketingSolr> solrList = response.getBeans(ProductMarketingSolr.class);
        ProductMarketingResponseBean productMarketingResponseBean = new ProductMarketingResponseBean();
        productMarketingResponseBean.setProductMarketingSolrList(solrList);

        if (facetFields != null && !facetFields.isEmpty()) {
            List<FacetField> solrFacetFieldList = response.getFacetFields();
            productMarketingResponseBean.setProductMarketingSolrFacetFieldList(solrFacetFieldList);
        }
        return productMarketingResponseBean;
    }

    @Deprecated
    public ProductMarketingResponseBean searchProductMarketing(String searchBy, String searchText, List<String> facetFields) throws SolrServerException, IOException {
        return searchProductMarketing(searchBy, searchText, facetFields, null, null);
    }

    @Deprecated
    public ProductMarketingResponseBean searchProductMarketing(String searchBy, String searchText, List<String> facetFields, BigDecimal priceStart, BigDecimal priceEnd) throws SolrServerException, IOException {
    	return searchProductMarketing(searchBy, searchText, facetFields, priceStart, priceEnd, null);
    }

    @Deprecated
    public ProductMarketingResponseBean searchProductMarketing(final String searchBy, final String searchText, final List<String> facetFields, 
                                                               final BigDecimal priceStart, final BigDecimal priceEnd, final List<String> catalogCategories) throws SolrServerException, IOException {
        String searchQuery = null;
        if (StringUtils.isEmpty(searchBy)) {
            throw new IllegalArgumentException("SearchBy field can not be Empty or Blank");
        }

        if (StringUtils.isEmpty(searchText)) {
            searchQuery = searchBy + ":*";
        } else {
            searchQuery = searchBy + ":" + searchText + "*";
        }

        List<String> filterQueries = new ArrayList<String>();
        if(priceStart != null && priceEnd != null){
            String fq = String.format("price:[%1$,.0f TO %2$,.0f]", priceStart.doubleValue(), priceEnd.doubleValue());
            filterQueries.add(fq);
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
            filterQueries.add(fq.toString());
        }
        
        return searchProductMarketing(searchQuery, facetFields, priceStart, priceEnd, filterQueries);
    }

    public ProductMarketingResponseBean searchProductMarketing() throws SolrServerException, IOException {
        SolrQuery solrQuery = new SolrQuery();
        solrQuery.setParam("rows", ROWS_DEFAULT_VALUE);
        solrQuery.setQuery("*");
        solrQuery.setFacet(true);
        solrQuery.setFacetMinCount(1);
        solrQuery.addFacetField(ProductMarketingResponseBean.PRODUCT_MARKETING_SEARCH_FIELD_CATEGORIES_CODE);
        
        logger.debug("QueryRequest solrQuery: " + solrQuery);

        SolrRequest request = new QueryRequest(solrQuery, METHOD.POST);
        
        QueryResponse response = new QueryResponse(productMarketingSolrServer.request(request), productMarketingSolrServer);
        
        logger.debug("QueryResponse Obj: " + response.toString());
        
        List<ProductMarketingSolr> solrList = response.getBeans(ProductMarketingSolr.class);
        List<FacetField> solrFacetFieldList = response.getFacetFields();
        
        ProductMarketingResponseBean productMarketingResponseBean = new ProductMarketingResponseBean();
        productMarketingResponseBean.setProductMarketingSolrList(solrList);
        productMarketingResponseBean.setProductMarketingSolrFacetFieldList(solrFacetFieldList);
        
        return productMarketingResponseBean;
    }

}