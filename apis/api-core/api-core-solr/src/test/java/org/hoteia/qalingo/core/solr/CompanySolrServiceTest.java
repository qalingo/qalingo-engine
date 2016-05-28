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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.solr.client.solrj.SolrServerException;
import org.hoteia.qalingo.core.domain.MarketArea;
import org.hoteia.qalingo.core.domain.Company;
import org.hoteia.qalingo.core.solr.response.CompanyResponseBean;
import org.hoteia.qalingo.core.solr.service.CompanySolrService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * CompanySolrServiceTest Test
 */
@RunWith(SpringJUnit4ClassRunner.class)     
@ContextConfiguration({ "/conf/spring/qalingo-core-solr-test.xml" })
public class CompanySolrServiceTest {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    
    @Autowired
    protected CompanySolrService  companySolrService;

    protected Company company;
    
    protected CompanyResponseBean responseBean;

    private MarketArea marketArea;

    private List<String> facetFields = new ArrayList<String>();
    
    @Before
    public void setUp() throws Exception {
        marketArea = new MarketArea();
        marketArea.setId(new Long("1"));
        
        company = new Company();
        company.setId(Long.parseLong("1"));
        company.setName("development");
        company.setAreaCode("Area-21");
        company.setCity("pune");
        company.setCountryCode("IND");
        company.setPostalCode("411014");
        
        facetFields.add(CompanyResponseBean.COMPANY_CITY_FACET_FIELD);
        facetFields.add(CompanyResponseBean.COMPANY_COUNTRY_FACET_FIELD);
    }
    
	/**
	 * Test Case to check: if required field is blank of null (i.e. id here)
	 */
    @Test(expected = IllegalArgumentException.class)
    public void testIndexDataWithBlankID() throws SolrServerException, IOException {
        company.setId(null);
        companySolrService.addOrUpdateCompany(company);
    }

	/**
	 * Test Case to check for all given fields have  indexed
	 */
    @Test
    public void testIndexData() throws SolrServerException, IOException {
        logger.debug("--------------->testIndexDataSecond()");
        companySolrService.addOrUpdateCompany(company);

    }

	/**
	 * Test case to check: search by given field Id
	 */
    @Test
    public void testSearchId() throws SolrServerException, IOException {
        logger.debug("--------------->Search: Id");
        responseBean = companySolrService.searchCompany("id", "", facetFields);
        printData();
    }

	/**
	 * Test case to check: search by given field city
	 */
    @Test
    public void testSearchCity() throws SolrServerException, IOException {
        logger.debug("--------------->search: City");
        responseBean = companySolrService.searchCompany("city", "", facetFields);
        printData();
    }

	/**
	 * Test case to check: search by given field country
	 */
    @Test
    public void testSearchCountry() throws SolrServerException, IOException {
        logger.debug("--------------->search: Country");
        responseBean = companySolrService.searchCompany("countryCode", "", facetFields);
        printData();
    }

	/**
	 * Test case to check: search by given field country with some startWith text parameter 
	 */
    @Test
    public void testSearchCountryWithText() throws SolrServerException, IOException {
        logger.debug("--------------->search: Country with text");
        responseBean = companySolrService.searchCompany("countryCode", "IN", facetFields);
        printData();
    }

	/**
	 * Test case to check: search by given field country with facet
	 */
    @Test
    public void testSearchCountryWithFacet() throws SolrServerException, IOException {
        logger.debug("--------------->search: Country with facet");        
        responseBean = companySolrService.searchCompany("countryCode", "", Arrays.asList(new String[]{"name"}));
        printData();
    }

	/**
	 * Test case to check: search by given field  Illegal Argument which is not mansion in schema
	 */ 
    @Test(expected = org.apache.solr.common.SolrException.class)
    public void testSearch() throws SolrServerException, IOException {
        logger.debug("--------------->Search unknown field");
        responseBean = companySolrService.searchCompany("xyz", "123", Arrays.asList(new String[]{"abc"}), null, facetFields);
        printData();
    }

	/**
	 *  Test case to check: search by: empty String
	 */
    @Test(expected = IllegalArgumentException.class)
    public void testEmptySearch() throws SolrServerException, IOException {
        logger.debug("--------------->Empty Search ");
        responseBean = companySolrService.searchCompany("", "", facetFields);
        printData();
    }
    
	/**
	 * Test case to check: search by default argument i.e.code
	 */
    @Test
    public void testDefaultSearch() throws SolrServerException, IOException {
        logger.debug("--------------->Default Search ");
        responseBean = companySolrService.searchCompany();
        printData();
    }

    public void printData() {
        if (responseBean != null) {
            logger.debug("---Facets---");
            for (int i = 0; i < responseBean.getCompanySolrFacetFieldList().size(); i++) {
                logger.debug("" + responseBean.getCompanySolrFacetFieldList().get(i));
            }
            logger.debug("---COMPANY LIST---");
            for (int i = 0; i < responseBean.getCompanySolrList().size(); i++) {
                logger.debug(responseBean.getCompanySolrList().get(i).getType());
                logger.debug(responseBean.getCompanySolrList().get(i).getName());
                logger.debug(responseBean.getCompanySolrList().get(i).getCity());
                logger.debug(responseBean.getCompanySolrList().get(i).getCountryCode());
                logger.debug(responseBean.getCompanySolrList().get(i).getPostalCode());
            }
        }
    }

}