package org.hoteia.qalingo.core.solr;

import java.io.IOException;

import org.apache.solr.client.solrj.SolrServerException;
import org.hoteia.qalingo.core.domain.ProductSku;
import org.hoteia.qalingo.core.solr.response.ProductSkuResponseBean;
import org.hoteia.qalingo.core.solr.service.ProductSkuSolrService;
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
	protected ProductSkuSolrService productSolrService; 

	protected ProductSku productSku;

	protected ProductSkuResponseBean responseBean;

    /**
     * Test Case to check: if required field is blank of null (i.e. id here)
     */
    @Test(expected = IllegalArgumentException.class)
    public void testIndexDataWithBlankID() throws SolrServerException, IOException {
        productSku = new ProductSku();
        productSku.setBusinessName("Product Sku");
        productSku.setDescription("Product Sku ...");
        productSku.setCode("productSku");
        productSolrService.addOrUpdateProductSku(productSku);
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
        productSku.setBusinessName("Product Sku");
        productSku.setDescription("Product Sku ...");
        productSku.setCode("productSku");
        productSolrService.addOrUpdateProductSku(productSku);
    }
    
	/**
	 * Test case to check: search by given field Id
	 */
    @Test
    public void testSearchId() throws SolrServerException, IOException {
        logger.debug("--------------->Search: Id");
        responseBean = productSolrService.searchProductSku("id", "", "");
        printData();
    }
    
	/**
	 *  Test case to check: search by given field code with given text
	 */
    @Test
    public void testSearchIdWithText() throws SolrServerException, IOException {
        logger.debug("--------------->search: code with some text");
        responseBean = productSolrService.searchProductSku("code", "N", "");
        printData();
    }
    
	/**
	 * Test case to check: search by given field id with given facet
	 */
    @Test
    public void testSearchIdWithFacet() throws SolrServerException, IOException {
        logger.debug("--------------->search: code with facet");
        responseBean = productSolrService.searchProductSku("code", "", "code");
        printData();
    }
    
	/**
	 * Test case to check: search by given field  Illegal Argument which is not mansion in schema
	 */
    @Test(expected = org.apache.solr.common.SolrException.class)
    public void testSearch() throws SolrServerException, IOException {
        logger.debug("--------------->Search unknown field");
        responseBean = productSolrService.searchProductSku("abc", "91", "xyz");
        printData();
    }
    
	/**
	 * Test case to check: search by: empty String
	 */ 
    @Test(expected = IllegalArgumentException.class)
    public void testEmptySearch() throws SolrServerException, IOException {
        logger.debug("--------------->Empty Search ");
        responseBean = productSolrService.searchProductSku("", "", "");
        printData();
    }
    
	/**
	 * Test case to check: search by default argument i.e.code
	 * 
	 */
    @Test
    public void testDefaultSearch() throws SolrServerException, IOException {
        logger.debug("--------------->Default Search ");
        responseBean = productSolrService.searchProductSku();
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
                logger.debug(responseBean.getProductSkuSolrList().get(i).getBusinessname());
                logger.debug(responseBean.getProductSkuSolrList().get(i).getDescription());
                logger.debug(responseBean.getProductSkuSolrList().get(i).getCode());
            }
        }
    }

}