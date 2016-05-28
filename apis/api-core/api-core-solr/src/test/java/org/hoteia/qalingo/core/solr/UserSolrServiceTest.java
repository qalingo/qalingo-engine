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
import java.util.List;

import org.apache.solr.client.solrj.SolrServerException;
import org.hoteia.qalingo.core.domain.User;
import org.hoteia.qalingo.core.solr.response.UserResponseBean;
import org.hoteia.qalingo.core.solr.service.UserSolrService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * UserSolrService Test
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "/conf/spring/qalingo-core-solr-test.xml" })
public class UserSolrServiceTest {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    
    @Autowired
    protected UserSolrService userSolrService;

    protected User user;

    protected UserResponseBean responseBean;

    private List<String> facetFields = new ArrayList<String>();
    
    @Before
    public void setUp() throws Exception {
        
        user = new User();
        user.setId(Long.parseLong("21"));
        user.setFirstname("vivek");
        user.setEmail("vivek@gmail.com");
        user.setTitle("user details");
        
        facetFields.add(UserResponseBean.USER_SEARCH_FIELD_FIRSTNAME);
        facetFields.add(UserResponseBean.USER_SEARCH_FIELD_LASTNAME);
    }
    
    /**
     * Test Case to check: if required field is blank of null (i.e. id here)
     */
    @Test(expected = IllegalArgumentException.class)
    public void testIndexDataWithBlankID() throws SolrServerException, IOException {
        logger.debug("--------------->testIndexDataFirst()");
        user.setId(null);
        userSolrService.addOrUpdateUser(user);
    }

    /**
     * Test Case to check for all given fields have indexed
     */
    @Test
    public void testIndexData() throws SolrServerException, IOException {
        logger.debug("--------------->testIndexDataSecond()");
        userSolrService.addOrUpdateUser(user);
    }

    /**
     * Test case to check: search by given field Id
     */
    @Test
    public void testSearchId() throws SolrServerException, IOException {
        logger.debug("--------------->Search: Id");
        responseBean = userSolrService.searchUser("id", "", facetFields);
        printData();
    }

    /**
     * Test case to check: search by given field email
     */
    @Test
    public void testSearchEmail() throws SolrServerException, IOException {
        logger.debug("--------------->search: Email");
        responseBean = userSolrService.searchUser("email", "", facetFields);
        printData();
    }

    /**
     * Test case to check: search by given field email with text
     */
    @Test
    public void testSearchEmailWithTest() throws SolrServerException, IOException {
        logger.debug("--------------->search: Email with text");
        responseBean = userSolrService.searchUser("email", "vi", facetFields);
        printData();
    }

    /**
     * Test case to check: search by given field email with facet
     */
    @Test
    public void testSearchEmailWithFacet() throws SolrServerException, IOException {
        logger.debug("--------------->search: Email with facet");
        responseBean = userSolrService.searchUser("email", "", facetFields);
        printData();
    }

    /**
     * Test case to check: search by given field Illegal Argument which is not
     * mansion in schema
     */
    @Test(expected = org.apache.solr.common.SolrException.class)
    public void testSearch() throws SolrServerException, IOException {
        logger.debug("--------------->Search unknown field");
        responseBean = userSolrService.searchUser("xyz", "123", facetFields);
        printData();
    }

    /**
     * Test case to check: search by: empty String
     */
    @Test(expected = IllegalArgumentException.class)
    public void testEmptySearch() throws SolrServerException, IOException {
        logger.debug("--------------->Empty Search ");
        responseBean = userSolrService.searchUser("", "", facetFields);
        printData();
    }

    /**
     * Test case to check: search by default
     */
    @Test
    public void testDefaultSearch() throws SolrServerException, IOException {
        logger.debug("--------------->Default Search ");
        responseBean = userSolrService.searchUser();
        printData();
    }

    protected void printData() {
        if (responseBean != null) {
            logger.debug("---FACETS---");
            for (int i = 0; i < responseBean.getUserSolrFacetFieldList().size(); i++) {
                logger.debug("" + responseBean.getUserSolrFacetFieldList().get(i));
            }
            logger.debug("---USER LIST---");
            for (int i = 0; i < responseBean.getUserSolrList().size(); i++) {
                logger.debug(responseBean.getUserSolrList().get(i).getLastname());
            }
        }
    }

}