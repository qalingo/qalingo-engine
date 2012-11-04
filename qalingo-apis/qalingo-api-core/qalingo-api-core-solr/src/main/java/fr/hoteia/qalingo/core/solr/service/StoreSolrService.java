/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package fr.hoteia.qalingo.core.solr.service;

import java.io.IOException;

import org.apache.solr.client.solrj.SolrServerException;

import fr.hoteia.qalingo.core.common.domain.Store;
import fr.hoteia.qalingo.core.solr.response.StoreResponseBean;

public interface StoreSolrService {

	void addOrUpdateStore(Store product) throws SolrServerException, IOException;
	
	StoreResponseBean searchStore() throws SolrServerException, IOException;
}
