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
import java.util.Iterator;
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
import org.hoteia.qalingo.core.domain.User;
import org.hoteia.qalingo.core.solr.bean.SolrParam;
import org.hoteia.qalingo.core.solr.bean.SortField;
import org.hoteia.qalingo.core.solr.bean.UserSolr;
import org.hoteia.qalingo.core.solr.response.UserResponseBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("userSolrService")
@Transactional
public class UserSolrService extends AbstractSolrService {

    private final Logger logger = LoggerFactory.getLogger(getClass());
	
    @Autowired
    protected SolrServer userSolrServer;
    
    public void addOrUpdateUser(final User user) throws SolrServerException, IOException, IllegalArgumentException {
        if (user.getId() == null) {
            throw new IllegalArgumentException("Id  cannot be blank or null.");
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Indexing User " + user.getId() + " : " + user.getCode() + " : " + user.getFirstname() + " : " + user.getLastname());
        }
        UserSolr userSolr = new UserSolr();
        userSolr.setId(user.getId());
        userSolr.setLastname(user.getLastname());
        userSolr.setFirstname(user.getFirstname());
        userSolr.setEmail(user.getEmail());
        userSolr.setTitle(user.getTitle());
        userSolrServer.addBean(userSolr);
        userSolrServer.commit();
    }
	
    public void removeUser(final UserSolr userSolr) throws SolrServerException, IOException {
        if (userSolr.getId() == null) {
            throw new IllegalArgumentException("Id  cannot be blank or null.");
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Remove Index User " + userSolr.getId() + " : " + userSolr.getLastname() + " : " + userSolr.getFirstname());
        }
        userSolrServer.deleteById(userSolr.getId().toString());
        userSolrServer.commit();
    }
    
    public UserResponseBean searchUser(final String searchQuery, final List<String> facetFields, final List<String> filterQueries, final SolrParam solrParam) throws IllegalArgumentException, SolrServerException, IOException {
        SolrQuery solrQuery = new SolrQuery();

        if(solrParam != null){
            if(solrParam.get("rows") != null){
                solrQuery.setParam("rows", (String)solrParam.get("rows"));
            } else {
                solrQuery.setParam("rows", getMaxResult());
            }
            
            if(solrParam.get("sortField") != null){
                SortField sortField = (SortField) solrParam.get("sortField");
                for (Iterator<String> iterator = sortField.keySet().iterator(); iterator.hasNext();) {
                    String field = (String) iterator.next();
                    solrQuery.addSortField(field, sortField.get(field));
                }
            }
        }
        
        if (StringUtils.isEmpty(searchQuery)) {
            throw new IllegalArgumentException("SearchQuery field can not be Empty or Blank!");
        }
        solrQuery.setQuery(searchQuery);

        if(facetFields != null && !facetFields.isEmpty()){
            solrQuery.setFacet(true);
            solrQuery.setFacetMinCount(1);
            for(String facetField : facetFields){
                solrQuery.addFacetField(facetField);
            }
        }

        logger.debug("QueryRequest solrQuery: " + solrQuery);

        SolrRequest request = new QueryRequest(solrQuery, METHOD.POST);

        QueryResponse response = new QueryResponse(userSolrServer.request(request), userSolrServer);

        logger.debug("QueryResponse Obj: " + response.toString());
        
        List<UserSolr> solrList = response.getBeans(UserSolr.class);
        UserResponseBean userResponseBean = new UserResponseBean();
        userResponseBean.setUserSolrList(solrList);
        
        if(facetFields != null && !facetFields.isEmpty()){
            List<FacetField> solrFacetFieldList = response.getFacetFields();
            userResponseBean.setUserSolrFacetFieldList(solrFacetFieldList);
        }
        return userResponseBean;
    }
    
    @Deprecated
    public UserResponseBean searchUser(String searchBy, String searchText, List<String> facetFields) throws IllegalArgumentException, SolrServerException, IOException {
        SolrQuery solrQuery = new SolrQuery();
        solrQuery.setParam("rows", getMaxResult());
        
        if (StringUtils.isEmpty(searchBy)) {
            throw new IllegalArgumentException("SearchBy field can not be Empty or Blank!");
        }

        if (StringUtils.isEmpty(searchText)) {
            solrQuery.setQuery(searchBy + ":*");
        } else {
            solrQuery.setQuery(searchBy + ":" + searchText + "*");
        }

        if(facetFields != null && !facetFields.isEmpty()){
            solrQuery.setFacet(true);
            solrQuery.setFacetMinCount(1);
            for(String facetField : facetFields){
                solrQuery.addFacetField(facetField);
            }
        }

        logger.debug("QueryRequest solrQuery: " + solrQuery);

        SolrRequest request = new QueryRequest(solrQuery, METHOD.POST);

        QueryResponse response = new QueryResponse(userSolrServer.request(request), userSolrServer);

        logger.debug("QueryResponse Obj: " + response.toString());
        
        List<UserSolr> solrList = response.getBeans(UserSolr.class);
        UserResponseBean userResponseBean = new UserResponseBean();
        userResponseBean.setUserSolrList(solrList);
        
        if(facetFields != null && !facetFields.isEmpty()){
            List<FacetField> solrFacetFieldList = response.getFacetFields();
            userResponseBean.setUserSolrFacetFieldList(solrFacetFieldList);
        }
        return userResponseBean;
    }
	
    public UserResponseBean searchUser() throws IllegalArgumentException, SolrServerException, IOException {
        SolrQuery solrQuery = new SolrQuery();
        solrQuery.setParam("rows", getMaxResult());
        
        solrQuery.setQuery("*");
        solrQuery.setFacet(true);
        solrQuery.setFacetMinCount(1);
        solrQuery.addFacetField("lastname");

        logger.debug("QueryRequest solrQuery: " + solrQuery);

        SolrRequest request = new QueryRequest(solrQuery, METHOD.POST);
        
        QueryResponse response = new QueryResponse(userSolrServer.request(request), userSolrServer);
        
        logger.debug("QueryResponse Obj: " + response.toString());

        List<UserSolr> solrList = response.getBeans(UserSolr.class);
        List<FacetField> solrFacetFieldList = response.getFacetFields();
        
        UserResponseBean userResponseBean = new UserResponseBean();
        userResponseBean.setUserSolrList(solrList);
        userResponseBean.setUserSolrFacetFieldList(solrFacetFieldList);
        return userResponseBean;
    }

}