/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version ${license.version})
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package fr.hoteia.qalingo.core.solr.service;

import java.io.IOException;

import org.apache.solr.client.solrj.SolrServerException;

import fr.hoteia.qalingo.core.common.domain.ProductCategoryMaster;
import fr.hoteia.qalingo.core.solr.response.CategoryResponseBean;

public interface CategorySolrService {

	void addOrUpdateCategory(ProductCategoryMaster productCategory) throws SolrServerException, IOException;
	
	CategoryResponseBean searchCategory() throws SolrServerException, IOException;
}
