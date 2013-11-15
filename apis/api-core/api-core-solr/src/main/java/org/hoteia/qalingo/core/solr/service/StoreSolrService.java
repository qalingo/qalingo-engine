/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.solr.service;

import java.io.IOException;

import org.apache.solr.client.solrj.SolrServerException;

import org.hoteia.qalingo.core.domain.Store;
import org.hoteia.qalingo.core.solr.response.StoreResponseBean;

/**
 * The Interface StoreSolrService.
 */
public interface StoreSolrService {

	/**
	 * Adds the or update store.
	 *
	 * @param product the product
	 * @throws SolrServerException the solr server exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	void addOrUpdateStore(Store product) throws SolrServerException, IOException;
	
	/**
	 * Search store.
	 *
	 * @param searchBy the search by
	 * @param searchText the search text
	 * @param facetField the facet field
	 * @return the store response bean
	 * @throws SolrServerException the solr server exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	StoreResponseBean searchStore(String searchBy,String searchText, String facetField) throws SolrServerException, IOException;
	
	/**
	 * Search store.
	 *
	 * @return the store response bean
	 * @throws SolrServerException the solr server exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	StoreResponseBean searchStore() throws SolrServerException, IOException;
}
