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
import java.util.List;

import org.apache.solr.client.solrj.SolrServerException;
import org.hoteia.qalingo.core.domain.MarketArea;
import org.hoteia.qalingo.core.domain.Store;
import org.hoteia.qalingo.core.solr.response.StoreResponseBean;

public interface StoreSolrService {

	/**
	 * Adds the or update store.
	 *
	 * @param store the store
	 * @throws SolrServerException the solr server exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	void addOrUpdateStore(Store store, MarketArea marketArea) throws SolrServerException, IOException;
	
	/**
	 * Search store.
	 *
	 * @param searchBy the search by
	 * @param searchText the search text
	 * @param facetField the facet field
	 * @param facetFieldSecond the facet field
	 * @param cities the list city
	 * @param countries the list country
	 * @return the store response bean
	 * @throws SolrServerException the solr server exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	StoreResponseBean searchStore(String searchBy,String searchText, List<String> facetFields, List<String> cities, List<String> countries) throws SolrServerException, IOException;
	
	/**
	 * Search store.
	 *
	 * @param searchBy the search by
	 * @param searchText the search text
	 * @param facetField the facet field
	 * @param facetFieldSecond the facet field
	 * @return the store response bean
	 * @throws SolrServerException the solr server exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	
	StoreResponseBean searchStore(String searchBy, String searchText, List<String> facetFields) throws SolrServerException, IOException;
	
	/**
	 * Search store.
	 *
	 * @return the store response bean
	 * @throws SolrServerException the solr server exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	StoreResponseBean searchStore() throws SolrServerException, IOException;
	
}