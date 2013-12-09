package org.hoteia.qalingo.core.solr;

import java.io.IOException;
import java.util.Date;

import org.apache.solr.client.solrj.SolrServerException;
import org.hoteia.qalingo.core.domain.CatalogCategoryMaster;
import org.hoteia.qalingo.core.solr.response.CatalogCategoryResponseBean;
import org.hoteia.qalingo.core.solr.service.CatalogCategorySolrService;
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

	/**
	 * Test Case to check if required field is blank of null (i.e. id here)
	 */ 
    @Test(expected = IllegalArgumentException.class)
    public void testIndexDataWithBlankId() throws SolrServerException, IOException {
        logger.debug("--------------->testIndexDataFirst()");
        catalogCategoryMaster = new CatalogCategoryMaster();
        catalogCategoryMaster.setBusinessName("Development");
        catalogCategoryMaster.setCode("abc123");
        // productCategory.setId(Long.parseLong("71"));
        catalogCategoryMaster.setDateCreate(new Date());
        catalogCategoryMaster.setDateUpdate(new Date());
        catalogCategorySolrService.addOrUpdateCatalogCategory(catalogCategoryMaster);
    }
	
	/**
	 * Test Case to check for all given fields have  indexed
	 */
    @Test
    public void testIndexData() throws SolrServerException, IOException {
        logger.debug("--------------->testIndexDataFirst()");
        catalogCategoryMaster = new CatalogCategoryMaster();
        catalogCategoryMaster.setBusinessName("Development");
        catalogCategoryMaster.setCode("xyz123");
        catalogCategoryMaster.setId(Long.parseLong("80"));
        catalogCategoryMaster.setDateCreate(new Date());
        catalogCategoryMaster.setDateUpdate(new Date());
        catalogCategorySolrService.addOrUpdateCatalogCategory(catalogCategoryMaster);
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
	 *  Test case to check: search by given field businessname
	 */
    @Test
    public void testSearchBusinessname() throws SolrServerException, IOException {
        logger.debug("--------------->SearchBusinessname");
        responseBean = catalogCategorySolrService.searchCatalogCategory("businessname", "", "");
        printData();
    }
	
	/**
	 * Test case to check: search by given field businessname with some text
	 */
    @Test
    public void testSearchBusinessnameWithText() throws SolrServerException, IOException {
        logger.debug("--------------->SearchBusinessnamewith some text");
        responseBean = catalogCategorySolrService.searchCatalogCategory("businessname", "Dev", "");
        printData();
    }
	
	/**
	 * Test case to check: search by given field businessname with facet
	 */
    @Test
    public void testSearchBusinessnameWithFacet() throws SolrServerException, IOException {
        logger.debug("--------------->SearchBusinessname with facet ");
        responseBean = catalogCategorySolrService.searchCatalogCategory("businessname", "", "businessname");
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