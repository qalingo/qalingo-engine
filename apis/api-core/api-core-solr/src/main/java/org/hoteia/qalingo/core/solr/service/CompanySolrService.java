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
import org.hoteia.qalingo.core.domain.Company;
import org.hoteia.qalingo.core.service.GeolocService;
import org.hoteia.qalingo.core.solr.bean.CompanySolr;
import org.hoteia.qalingo.core.solr.bean.SolrParam;
import org.hoteia.qalingo.core.solr.bean.SortField;
import org.hoteia.qalingo.core.solr.response.CompanyResponseBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("companySolrService")
@Transactional
public class CompanySolrService extends AbstractSolrService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    protected SolrServer companySolrServer;
    
    @Autowired
    protected GeolocService geolocService;
    
    public void addOrUpdateCompany(final Company company) throws SolrServerException, IOException {
        CompanySolr companySolr = populateCompanySolr(company);
        companySolrServer.addBean(companySolr);
        companySolrServer.commit();
    }
    
    protected CompanySolr populateCompanySolr(Company company){
        if (company.getId() == null) {
            throw new IllegalArgumentException("Id  cannot be blank or null.");
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Indexing company " + company.getId() + " : " + company.getName() + " : " + company.getCity());
        }
        CompanySolr companySolr = new CompanySolr();
        companySolr.setId(company.getId());
        companySolr.setActive(company.isActive());
        companySolr.setCode(company.getCode());
        companySolr.setName(company.getName());
        companySolr.setAddress(company.getAddress1());
        companySolr.setPostalCode(company.getPostalCode());
        companySolr.setCity(company.getCity());
        companySolr.setCountryCode(company.getCountryCode());
        companySolr.setAddressUniqueKey(geolocService.encodeAddress(company.getAddress1(), company.getPostalCode(), company.getCity(), company.getCountryCode()));
        return companySolr;
    }
    
    public void removeCompany(final CompanySolr companySolr) throws SolrServerException, IOException {
        if (companySolr.getId() == null) {
            throw new IllegalArgumentException("Id  cannot be blank or null.");
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Remove Index Company " + companySolr.getId() + " : " + companySolr.getName() + " : " + companySolr.getCity());
        }
        companySolrServer.deleteById(companySolr.getId().toString());
        companySolrServer.commit();
    }

    public CompanyResponseBean searchCompany(final String searchQuery, final List<String> facetFields, final List<String> cities, 
                                         final List<String> countries, final SolrParam solrParam) throws SolrServerException, IOException {
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

        if (facetFields != null && facetFields.size() > 0) {
            solrQuery.setFacet(true);
            solrQuery.setFacetMinCount(1);
            for (String facetField : facetFields) {
                solrQuery.addFacetField(facetField);
            }
        }

        if (cities != null && cities.size() > 0) {
            StringBuilder fq = new StringBuilder("city:(");
            for (int i = 0; i < cities.size(); i++) {
                String city = cities.get(i);
                fq.append('"' + city + '"');
                if (i < cities.size() - 1) {
                    fq.append(" OR ");
                }
            }
            fq.append(")");
            solrQuery.addFilterQuery(fq.toString());
        }
        if (countries != null && countries.size() > 0) {
            StringBuilder fq = new StringBuilder("countryCode:(");
            for (int i = 0; i < countries.size(); i++) {
                String country = countries.get(i);
                fq.append('"' + country + '"');
                if (i < countries.size() - 1) {
                    fq.append(" OR ");
                }
            }
            fq.append(")");
            solrQuery.addFilterQuery(fq.toString());
        }

        logger.debug("QueryRequest solrQuery: " + solrQuery);

        SolrRequest request = new QueryRequest(solrQuery, METHOD.POST);

        QueryResponse response = new QueryResponse(companySolrServer.request(request), companySolrServer);

        logger.debug("QueryResponse Obj: " + response.toString());

        List<CompanySolr> solrList = response.getBeans(CompanySolr.class);
        CompanyResponseBean companyResponseBean = new CompanyResponseBean();
        companyResponseBean.setCompanySolrList(solrList);

        if (facetFields != null && facetFields.size() > 0) {
            List<FacetField> solrFacetFieldList = response.getFacetFields();
            companyResponseBean.setCompanySolrFacetFieldList(solrFacetFieldList);
        }
        return companyResponseBean;
    }
    
    @Deprecated
    public CompanyResponseBean searchCompany(String searchBy, String searchText, List<String> facetFields) throws SolrServerException, IOException {
    	return searchCompany(searchBy, searchText, facetFields, null, null);
    }

    @Deprecated
    public CompanyResponseBean searchCompany(String searchBy, String searchText, List<String> facetFields,
                                         List<String> cities, List<String> countries) throws SolrServerException, IOException {
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
        
        if (facetFields != null && facetFields.size() > 0) {
            solrQuery.setFacet(true);
            solrQuery.setFacetMinCount(1);
            for( String facetField : facetFields){
            	solrQuery.addFacetField(facetField);
            }
        }

        if(cities != null && cities.size() > 0){
        	StringBuilder fq = new StringBuilder("city:(");
        	for (int i = 0; i < cities.size(); i++) {
				String city = cities.get(i);
				fq.append('"'+city+'"');
				if(i < cities.size() - 1){
					fq.append(" OR ");
				}
			}
        	fq.append(")");
        	solrQuery.addFilterQuery(fq.toString());
        }
        if(countries != null && countries.size() > 0){
        	StringBuilder fq = new StringBuilder("countryCode:(");
        	for (int i = 0; i < countries.size(); i++) {
				String country = countries.get(i);
				fq.append('"'+country+'"');
				if(i < countries.size() - 1){
					fq.append(" OR ");
				}
			}
        	fq.append(")");
        	solrQuery.addFilterQuery(fq.toString());
        }
        
        logger.debug("QueryRequest solrQuery: " + solrQuery);

        SolrRequest request = new QueryRequest(solrQuery, METHOD.POST);

        QueryResponse response = new QueryResponse(companySolrServer.request(request), companySolrServer);

        logger.debug("QueryResponse Obj: " + response.toString());
        
        List<CompanySolr> solrList = response.getBeans(CompanySolr.class);
        CompanyResponseBean companyResponseBean = new CompanyResponseBean();
        companyResponseBean.setCompanySolrList(solrList);

        if (facetFields != null && facetFields.size() > 0) {
            List<FacetField> solrFacetFieldList = response.getFacetFields();
            companyResponseBean.setCompanySolrFacetFieldList(solrFacetFieldList);
        }
        return companyResponseBean;
    }
	
    @Deprecated
    public CompanyResponseBean searchCompany() throws SolrServerException, IOException {
        SolrQuery solrQuery = new SolrQuery();
        solrQuery.setParam("rows", getMaxResult());
        
        solrQuery.setQuery("*");
        solrQuery.setFacet(true);
        solrQuery.setFacetMinCount(1);
        solrQuery.addFacetField("name");
        
        logger.debug("QueryRequest solrQuery: " + solrQuery);

        SolrRequest request = new QueryRequest(solrQuery, METHOD.POST);
        
        QueryResponse response = new QueryResponse(companySolrServer.request(request), companySolrServer);
        
        logger.debug("QueryResponse Obj: " + response.toString());

        List<CompanySolr> solrList = response.getBeans(CompanySolr.class);
        List<FacetField> solrFacetFieldList = response.getFacetFields();
        CompanyResponseBean companyResponseBean = new CompanyResponseBean();
        companyResponseBean.setCompanySolrList(solrList);
        companyResponseBean.setCompanySolrFacetFieldList(solrFacetFieldList);
        return companyResponseBean;
    }
    
}