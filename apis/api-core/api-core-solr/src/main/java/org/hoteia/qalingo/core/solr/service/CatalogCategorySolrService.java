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
import org.hoteia.qalingo.core.domain.CatalogCategoryMaster;
import org.hoteia.qalingo.core.domain.MarketArea;
import org.hoteia.qalingo.core.solr.response.CatalogCategoryResponseBean;

public interface CatalogCategorySolrService {

	/**
	 * Adds the or update category.
	 *
	 * @param catalogCategoryMaster the catalog category
	 * @throws SolrServerException the solr server exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	void addOrUpdateCatalogCategory(CatalogCategoryMaster catalogCategoryMaster, MarketArea marketArea) throws SolrServerException, IOException;
	
	/**
	 * Search category.
	 *
	 * @param searchBy the search by field
	 * @param searchText the search text
	 * @param facetField the facet field
	 * @return the category response bean
	 * @throws SolrServerException the solr server exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	CatalogCategoryResponseBean searchCatalogCategory(String searchBy,String searchText, String facetField) throws SolrServerException, IOException;
	
	/**
	 * Search category.
	 *
	 * @return the category response bean
	 * @throws SolrServerException the solr server exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	CatalogCategoryResponseBean searchCatalogCategory() throws SolrServerException, IOException;
	
}