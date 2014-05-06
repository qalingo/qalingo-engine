/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.solr;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.solr.client.solrj.SolrServerException;
import org.hoteia.qalingo.core.domain.CatalogCategoryVirtual;
import org.hoteia.qalingo.core.domain.CatalogVirtual;
import org.hoteia.qalingo.core.domain.MarketArea;
import org.hoteia.qalingo.core.domain.ProductSku;
import org.hoteia.qalingo.core.domain.ProductSkuPrice;
import org.hoteia.qalingo.core.domain.Retailer;
import org.hoteia.qalingo.core.solr.response.ProductSkuResponseBean;
import org.hoteia.qalingo.core.solr.service.ProductSkuSolrService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * ProductSolrServiceTest Test
 */
@RunWith(SpringJUnit4ClassRunner.class) 	
@ContextConfiguration({ "/conf/spring/qalingo-core-solr-test.xml" })
public class ProductSkuSolrServiceTest {
    
    private final Logger logger = LoggerFactory.getLogger(getClass());
    
	@Autowired
	protected ProductSkuSolrService productSkuSolrService; 

	protected ProductSku productSku;
    private List<CatalogCategoryVirtual> catalogCategories;

	protected ProductSkuResponseBean responseBean;

    private CatalogVirtual virtualCatalog;
    private MarketArea marketArea;
    private Retailer retailer;
    
    @Before
    public void setUp() throws Exception {
        virtualCatalog = new CatalogVirtual();
        virtualCatalog.setId(new Long("1"));
        virtualCatalog.setCode("V_CAT_XXX");
        
        marketArea = new MarketArea();
        marketArea.setId(new Long("1"));
        marketArea.setCatalog(virtualCatalog);
        
        retailer = new Retailer();
        retailer.setId(new Long("1"));
        
        catalogCategories = new ArrayList<CatalogCategoryVirtual>();

        productSku = new ProductSku();
        productSku.setId(new Long("1"));
        productSku.setDefault(true);
        productSku.setName("Product Sku");
        productSku.setDescription("Product Sku ...");
        productSku.setCode("productSku");
        ProductSkuPrice productSkuPrice = new ProductSkuPrice();
        productSkuPrice.setId(new Long("1"));
        productSkuPrice.setMarketAreaId(new Long("1"));
        productSkuPrice.setRetailerId(new Long("1"));
        productSkuPrice.setSalePrice(new BigDecimal("2"));
        productSku.getPrices().add(productSkuPrice);
    }
    
    /**
     * Test Case to check: if required field is blank of null (i.e. id here)
     */
    @Test(expected = IllegalArgumentException.class)
    public void testIndexDataWithBlankID() throws SolrServerException, IOException {
        productSku.setId(null);
        productSkuSolrService.addOrUpdateProductSku(productSku, catalogCategories, marketArea, retailer);
        logger.debug("--------------->testFirstIndexData()");
    }
    
	/**
	 * Test Case to check for all given fields have  indexed
	 */
    @Test
    public void testIndexData() throws SolrServerException, IOException {
        logger.debug("--------------->testIndexData");
        productSku = new ProductSku();
        productSku.setId(Long.parseLong("91"));
        productSku.setName("Product Sku");
        productSku.setDescription("Product Sku ...");
        productSku.setCode("productSku");
        productSkuSolrService.addOrUpdateProductSku(productSku, catalogCategories, marketArea, retailer);
    }
    
	/**
	 * Test case to check: search by given field Id
	 */
    @Test
    public void testSearchId() throws SolrServerException, IOException {
        logger.debug("--------------->Search: Id");
        responseBean = productSkuSolrService.searchProductSku("id", "", "");
        printData();
    }
    
	/**
	 *  Test case to check: search by given field code with given text
	 */
    @Test
    public void testSearchIdWithText() throws SolrServerException, IOException {
        logger.debug("--------------->search: code with some text");
        responseBean = productSkuSolrService.searchProductSku("code", "N", "");
        printData();
    }
    
	/**
	 * Test case to check: search by given field id with given facet
	 */
    @Test
    public void testSearchIdWithFacet() throws SolrServerException, IOException {
        logger.debug("--------------->search: code with facet");
        responseBean = productSkuSolrService.searchProductSku("code", "", "code");
        printData();
    }
    
	/**
	 * Test case to check: search by given field  Illegal Argument which is not mansion in schema
	 */
    @Test(expected = org.apache.solr.common.SolrException.class)
    public void testSearch() throws SolrServerException, IOException {
        logger.debug("--------------->Search unknown field");
        responseBean = productSkuSolrService.searchProductSku("abc", "91", "xyz");
        printData();
    }
    
	/**
	 * Test case to check: search by: empty String
	 */ 
    @Test(expected = IllegalArgumentException.class)
    public void testEmptySearch() throws SolrServerException, IOException {
        logger.debug("--------------->Empty Search ");
        responseBean = productSkuSolrService.searchProductSku("", "", "");
        printData();
    }
    
	/**
	 * Test case to check: search by default argument i.e.code
	 * 
	 */
    @Test
    public void testDefaultSearch() throws SolrServerException, IOException {
        logger.debug("--------------->Default Search ");
        responseBean = productSkuSolrService.searchProductSku();
        printData();
    }

    public void printData() {
        if (responseBean != null) {
            logger.debug("---Facets---");
            for (int i = 0; i < responseBean.getProductSkuSolrFacetFieldList().size(); i++) {
                logger.debug("" + responseBean.getProductSkuSolrFacetFieldList().get(i));
            }
            logger.debug("---PRODUCT LIST---");
            for (int i = 0; i < responseBean.getProductSkuSolrList().size(); i++) {
                logger.debug("" + responseBean.getProductSkuSolrList().get(i).getId());
                logger.debug(responseBean.getProductSkuSolrList().get(i).getName());
                logger.debug(responseBean.getProductSkuSolrList().get(i).getDescription());
                logger.debug(responseBean.getProductSkuSolrList().get(i).getCode());
            }
        }
    }

}