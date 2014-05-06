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

import org.apache.solr.client.solrj.SolrServerException;
import org.hoteia.qalingo.core.domain.Customer;
import org.hoteia.qalingo.core.domain.MarketArea;
import org.hoteia.qalingo.core.solr.response.CustomerResponseBean;

public interface CustomerSolrService {

	/**
	 * Adds the or update customer.
	 *
	 * @param customer the customer
	 * @throws SolrServerException the solr server exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	void addOrUpdateCustomer(Customer customer, MarketArea marketArea) throws SolrServerException, IOException;
	
	/**
	 * Search customer.
	 *
	 * @param searchBy the search by
	 * @param searchText the search text
	 * @param facetField the facet field
	 * @return the customer response bean
	 * @throws SolrServerException the solr server exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	CustomerResponseBean searchCustomer(String searchBy,String searchText, String facetField) throws SolrServerException, IOException ;
	
	/**
	 * Search customer.
	 *
	 * @return the customer response bean
	 * @throws SolrServerException the solr server exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	CustomerResponseBean searchCustomer() throws SolrServerException, IOException;
	
}