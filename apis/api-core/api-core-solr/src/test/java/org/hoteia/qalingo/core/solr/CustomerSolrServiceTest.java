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

import org.apache.solr.client.solrj.SolrServerException;
import org.hoteia.qalingo.core.domain.Customer;
import org.hoteia.qalingo.core.domain.MarketArea;
import org.hoteia.qalingo.core.solr.response.CustomerResponseBean;
import org.hoteia.qalingo.core.solr.service.CustomerSolrService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * CustomerSolrService Test
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "/conf/spring/qalingo-core-solr-test.xml" })
public class CustomerSolrServiceTest {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    
    @Autowired
    protected CustomerSolrService customerSolrService;

    protected Customer customer;

    protected CustomerResponseBean responseBean;

    private MarketArea marketArea;
    
    @Before
    public void setUp() throws Exception {
        marketArea = new MarketArea();
        marketArea.setId(new Long("1"));
        
        customer = new Customer();
        customer.setId(Long.parseLong("21"));
        customer.setFirstname("vivek");
        customer.setEmail("vivek@gmail.com");
        customer.setGender("mail");
        customer.setTitle("customer details");
    }
    
    /**
     * Test Case to check: if required field is blank of null (i.e. id here)
     */
    @Test(expected = IllegalArgumentException.class)
    public void testIndexDataWithBlankID() throws SolrServerException, IOException {
        logger.debug("--------------->testIndexDataFirst()");
        customer.setId(null);
        customerSolrService.addOrUpdateCustomer(customer, marketArea);
    }

    /**
     * Test Case to check for all given fields have indexed
     */
    @Test
    public void testIndexData() throws SolrServerException, IOException {
        logger.debug("--------------->testIndexDataSecond()");
        customerSolrService.addOrUpdateCustomer(customer, marketArea);
    }

    /**
     * Test case to check: search by given field Id
     */
    @Test
    public void testSearchId() throws SolrServerException, IOException {
        logger.debug("--------------->Search: Id");
        responseBean = customerSolrService.searchCustomer("id", "", "");
        printData();
    }

    /**
     * Test case to check: search by given field email
     */
    @Test
    public void testSearchEmail() throws SolrServerException, IOException {
        logger.debug("--------------->search: Email");
        responseBean = customerSolrService.searchCustomer("email", "", "");
        printData();
    }

    /**
     * Test case to check: search by given field email with text
     */
    @Test
    public void testSearchEmailWithTest() throws SolrServerException, IOException {
        logger.debug("--------------->search: Email with text");
        responseBean = customerSolrService.searchCustomer("email", "vi", "");
        printData();
    }

    /**
     * Test case to check: search by given field email with facet
     */
    @Test
    public void testSearchEmailWithFacet() throws SolrServerException, IOException {
        logger.debug("--------------->search: Email with facet");
        responseBean = customerSolrService.searchCustomer("email", "", "email");
        printData();
    }

    /**
     * Test case to check: search by given field Illegal Argument which is not
     * mansion in schema
     */
    @Test(expected = org.apache.solr.common.SolrException.class)
    public void testSearch() throws SolrServerException, IOException {
        logger.debug("--------------->Search unknown field");
        responseBean = customerSolrService.searchCustomer("xyz", "123", "abc");
        printData();
    }

    /**
     * Test case to check: search by: empty String
     */
    @Test(expected = IllegalArgumentException.class)
    public void testEmptySearch() throws SolrServerException, IOException {
        logger.debug("--------------->Empty Search ");
        responseBean = customerSolrService.searchCustomer("", "", "");
        printData();
    }

    /**
     * Test case to check: search by default
     */
    @Test
    public void testDefaultSearch() throws SolrServerException, IOException {
        logger.debug("--------------->Default Search ");
        responseBean = customerSolrService.searchCustomer();
        printData();
    }

    protected void printData() {
        if (responseBean != null) {
            logger.debug("---FACETS---");
            for (int i = 0; i < responseBean.getCustomerSolrFacetFieldList().size(); i++) {
                logger.debug("" + responseBean.getCustomerSolrFacetFieldList().get(i));
            }
            logger.debug("---CUSTOMER LIST---");
            for (int i = 0; i < responseBean.getCustomerSolrList().size(); i++) {
                logger.debug(responseBean.getCustomerSolrList().get(i).getLastname());
            }
        }
    }

}