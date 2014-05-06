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
import java.util.Date;

import org.apache.solr.client.solrj.SolrServerException;
import org.hoteia.qalingo.core.domain.CatalogCategoryMaster;
import org.hoteia.qalingo.core.domain.MarketArea;
import org.hoteia.qalingo.core.solr.response.CatalogCategoryResponseBean;
import org.hoteia.qalingo.core.solr.service.CatalogCategorySolrService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * CatalogCategorySolrService Test
 */
@RunWith(SpringJUnit4ClassRunner.class) 	
@ContextConfiguration({ "/conf/spring/qalingo-core-solr-test.xml" })
public class CatalogCategorySolrServiceTest {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    
	@Autowired
	protected CatalogCategorySolrService  catalogCategorySolrService; 

	protected CatalogCategoryMaster catalogCategoryMaster;
    
	protected CatalogCategoryResponseBean responseBean;

    private MarketArea marketArea;
    
    @Before
    public void setUp() throws Exception {
        marketArea = new MarketArea();
        marketArea.setId(new Long("1"));
        
        catalogCategoryMaster = new CatalogCategoryMaster();
        catalogCategoryMaster.setId(Long.parseLong("80"));
        catalogCategoryMaster.setCode("xyz123");
        catalogCategoryMaster.setName("Development");
        catalogCategoryMaster.setDateCreate(new Date());
        catalogCategoryMaster.setDateUpdate(new Date());
    }
    
	/**
	 * Test Case to check if required field is blank of null (i.e. id here)
	 */ 
    @Test(expected = IllegalArgumentException.class)
    public void testIndexDataWithBlankId() throws SolrServerException, IOException {
        logger.debug("--------------->testIndexDataFirst()");
        catalogCategoryMaster.setId(null);
        catalogCategorySolrService.addOrUpdateCatalogCategory(catalogCategoryMaster, marketArea);
    }
	
	/**
	 * Test Case to check for all given fields have  indexed
	 */
    @Test
    public void testIndexData() throws SolrServerException, IOException {
        logger.debug("--------------->testIndexDataFirst()");
        catalogCategorySolrService.addOrUpdateCatalogCategory(catalogCategoryMaster, marketArea);
    }

	/**
	 * Test case to check: search by given field Id
	 */
    @Test
    public void testSearchId() throws SolrServerException, IOException {
        logger.debug("--------------->SearchId");
        responseBean = catalogCategorySolrService.searchCatalogCategory("id", "", "");
        printData();
    }

	/**
	 *  Test case to check: search by given field name
	 */
    @Test
    public void testSearchName() throws SolrServerException, IOException {
        logger.debug("--------------->SearchName");
        responseBean = catalogCategorySolrService.searchCatalogCategory("name", "", "");
        printData();
    }
	
	/**
	 * Test case to check: search by given field name with some text
	 */
    @Test
    public void testSearchNameWithText() throws SolrServerException, IOException {
        logger.debug("--------------->SearchNamewith some text");
        responseBean = catalogCategorySolrService.searchCatalogCategory("name", "Dev", "");
        printData();
    }
	
	/**
	 * Test case to check: search by given field name with facet
	 */
    @Test
    public void testSearchNameWithFacet() throws SolrServerException, IOException {
        logger.debug("--------------->SearchName with facet ");
        responseBean = catalogCategorySolrService.searchCatalogCategory("name", "", "name");
        printData();
    }

	/**
	 * Test case to check: search by given field  Illegal Argument which is not mansion in schema 
	 */
    @Test(expected = org.apache.solr.common.SolrException.class)
    public void testSearch() throws SolrServerException, IOException {
        logger.debug("--------------->Search unknown field");
        responseBean = catalogCategorySolrService.searchCatalogCategory("xyz", "123", "abc");
        printData();
    }

	/**
	 * Test case to check: search by: empty String
	 */
    @Test(expected = IllegalArgumentException.class)
    public void testEmptySearch() throws SolrServerException, IOException {
        logger.debug("--------------->Empty Search ");
        responseBean = catalogCategorySolrService.searchCatalogCategory("", "", "");
        printData();
    }
	
	/**
	 * Test case to check: search by default argument i.e. code
	 */
    @Test
    public void testDefaultSearch() throws SolrServerException, IOException {
        logger.debug("--------------->Default Search ");
        responseBean = catalogCategorySolrService.searchCatalogCategory();
        printData();
    }

    protected void printData() {
        if (responseBean != null) {
            logger.debug("---FACETS---");
            for (int i = 0; i < responseBean.getCatalogCategorySolrFacetFieldList().size(); i++) {
                logger.debug("" + responseBean.getCatalogCategorySolrFacetFieldList().get(i));
            }
            logger.debug("-----CatalogCategory List-----");
            for (int i = 0; i < responseBean.getCatalogCategorySolrList().size(); i++) {
                logger.debug("" + responseBean.getCatalogCategorySolrList().get(i).getId());

            }
        } else {
            logger.debug("Results Not Found");
        }
    }

}