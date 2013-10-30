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

import fr.hoteia.qalingo.core.domain.CatalogCategoryMaster;
import fr.hoteia.qalingo.core.solr.response.CategoryResponseBean;

/**
 * The Interface CategorySolrService.
 */
public interface CategorySolrService {

	/**
	 * Adds the or update category.
	 *
	 * @param productCategory the product category
	 * @throws SolrServerException the solr server exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	void addOrUpdateCategory(CatalogCategoryMaster productCategory) throws SolrServerException, IOException;
	
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
	CategoryResponseBean searchCategory(String searchBy,String searchText, String facetField) throws SolrServerException, IOException;
	
	/**
	 * Search category.
	 *
	 * @return the category response bean
	 * @throws SolrServerException the solr server exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	CategoryResponseBean searchCategory() throws SolrServerException, IOException;
}
