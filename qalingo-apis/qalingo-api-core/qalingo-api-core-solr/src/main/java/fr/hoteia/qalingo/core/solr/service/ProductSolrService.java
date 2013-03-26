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

import fr.hoteia.qalingo.core.domain.ProductMarketing;
import fr.hoteia.qalingo.core.solr.response.ProductResponseBean;

public interface ProductSolrService {

	void addOrUpdateProduct(ProductMarketing productMarketing) throws SolrServerException, IOException;
	
	ProductResponseBean searchProduct() throws SolrServerException, IOException;
}
