/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package fr.hoteia.qalingo.core.common.dao;

import java.util.List;

import fr.hoteia.qalingo.core.common.domain.ProductSkuStock;

public interface StockDao {

	ProductSkuStock getStockById(Long stockId);

	List<ProductSkuStock> findByExample(ProductSkuStock stockExample);

	void saveOrUpdateStock(ProductSkuStock stock);

	void deleteStock(ProductSkuStock stock);

}
