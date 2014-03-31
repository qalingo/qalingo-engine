package org.hoteia.qalingo.core.solr;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.solr.client.solrj.SolrServerException;
import org.hoteia.qalingo.core.domain.CatalogCategoryVirtual;
import org.hoteia.qalingo.core.domain.MarketArea;
import org.hoteia.qalingo.core.domain.ProductMarketing;
import org.hoteia.qalingo.core.domain.ProductSku;
import org.hoteia.qalingo.core.domain.ProductSkuPrice;
import org.hoteia.qalingo.core.domain.Retailer;
import org.hoteia.qalingo.core.solr.response.ProductMarketingResponseBean;
import org.hoteia.qalingo.core.solr.service.ProductMarketingSolrService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * ProductMarketingSolrServiceTest Test
 */
@RunWith(SpringJUnit4ClassRunner.class) 	
@ContextConfiguration({ "/conf/spring/qalingo-core-solr-test.xml" })
public class ProductMarketingSolrServiceTest {
    
    private final Logger logger = LoggerFactory.getLogger(getClass());
    
	@Autowired
	protected ProductMarketingSolrService productMarketingSolrService; 

	protected ProductMarketing productMarketing;

	protected ProductMarketingResponseBean responseBean;

    private MarketArea marketArea;
    private Retailer retailer;
    
    @Before
    public void setUp() throws Exception {
        marketArea = new MarketArea();
        marketArea.setId(new Long("1"));
        
        retailer = new Retailer();
        retailer.setId(new Long("1"));
        
        productMarketing = new ProductMarketing();
        productMarketing.setId(new Long("1"));
        productMarketing.setName("Product Marketing");
        productMarketing.setDescription("Product Marketing ...");
        productMarketing.setCode("productMarketing");
        
        ProductSku productSku = new ProductSku();
        productSku.setId(new Long("1"));
        productSku.setDefault(true);
        ProductSkuPrice productSkuPrice = new ProductSkuPrice();
        productSkuPrice.setId(new Long("1"));
        productSkuPrice.setMarketAreaId(new Long("1"));
        productSkuPrice.setRetailerId(new Long("1"));
        productSkuPrice.setSalePrice(new BigDecimal("2"));
        productSku.getPrices().add(productSkuPrice);
        productMarketing.getProductSkus().add(productSku);
        
    }
    
    /**
     * Test Case to check: if required field is blank of null (i.e. id here)
     */
    @Test(expected = IllegalArgumentException.class)
    public void testIndexDataWithBlankID() throws SolrServerException, IOException {
        productMarketing.setId(null);
        List<CatalogCategoryVirtual> catalogCategories = new ArrayList<CatalogCategoryVirtual>();
        productMarketingSolrService.addOrUpdateProductMarketing(productMarketing, catalogCategories, marketArea, retailer);
        logger.debug("--------------->testFirstIndexData()");
    }
    
	/**
	 * Test Case to check for all given fields have  indexed
	 */
    @Test
    public void testIndexData() throws SolrServerException, IOException {
        logger.debug("--------------->testIndexData");
        List<CatalogCategoryVirtual> catalogCategories = new ArrayList<CatalogCategoryVirtual>();
        productMarketingSolrService.addOrUpdateProductMarketing(productMarketing, catalogCategories, marketArea, retailer);
    }
    
	/**
	 * Test case to check: search by given field Id
	 */
    @Test
    public void testSearchId() throws SolrServerException, IOException {
        logger.debug("--------------->Search: Id");
        responseBean = productMarketingSolrService.searchProductMarketing("id", "", "");
        printData();
    }
    
	/**
	 *  Test case to check: search by given field code with given text
	 */
    @Test
    public void testSearchIdWithText() throws SolrServerException, IOException {
        logger.debug("--------------->search: code with some text");
        responseBean = productMarketingSolrService.searchProductMarketing("code", "N", "");
        printData();
    }
    
	/**
	 * Test case to check: search by given field id with given facet
	 */
    @Test
    public void testSearchIdWithFacet() throws SolrServerException, IOException {
        logger.debug("--------------->search: code with facet");
        responseBean = productMarketingSolrService.searchProductMarketing("code", "", "code");
        printData();
    }
    
	/**
	 * Test case to check: search by given field  Illegal Argument which is not mansion in schema
	 */
    @Test(expected = org.apache.solr.common.SolrException.class)
    public void testSearch() throws SolrServerException, IOException {
        logger.debug("--------------->Search unknown field");
        responseBean = productMarketingSolrService.searchProductMarketing("abc", "91", "xyz");
        printData();
    }
    
	/**
	 * Test case to check: search by: empty String
	 */ 
    @Test(expected = IllegalArgumentException.class)
    public void testEmptySearch() throws SolrServerException, IOException {
        logger.debug("--------------->Empty Search ");
        responseBean = productMarketingSolrService.searchProductMarketing("", "", "");
        printData();
    }
    
	/**
	 * Test case to check: search by default argument i.e.code
	 * 
	 */
    @Test
    public void testDefaultSearch() throws SolrServerException, IOException {
        logger.debug("--------------->Default Search ");
        responseBean = productMarketingSolrService.searchProductMarketing();
        printData();
    }

    public void printData() {
        if (responseBean != null) {
            logger.debug("---Facets---");
            for (int i = 0; i < responseBean.getProductMarketingSolrFacetFieldList().size(); i++) {
                logger.debug("" + responseBean.getProductMarketingSolrFacetFieldList().get(i));
            }
            logger.debug("---PRODUCT LIST---");
            for (int i = 0; i < responseBean.getProductMarketingSolrList().size(); i++) {
                logger.debug("" + responseBean.getProductMarketingSolrList().get(i).getId());
                logger.debug(responseBean.getProductMarketingSolrList().get(i).getName());
                logger.debug(responseBean.getProductMarketingSolrList().get(i).getDescription());
                logger.debug(responseBean.getProductMarketingSolrList().get(i).getCode());
            }
        }
    }

}