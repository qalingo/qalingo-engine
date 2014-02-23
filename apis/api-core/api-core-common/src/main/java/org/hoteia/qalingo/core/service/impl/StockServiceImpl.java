/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.hoteia.qalingo.core.dao.StockDao;
import org.hoteia.qalingo.core.domain.ProductSkuStock;
import org.hoteia.qalingo.core.service.StockService;

@Service("stockService")
@Transactional
public class StockServiceImpl implements StockService {

	@Autowired
	private StockDao stockDao;

    public ProductSkuStock getStockById(final Long stockId, Object... params) {
        return stockDao.getStockById(stockId, params);
    }
    
	public ProductSkuStock getStockById(final String rawStockId, Object... params) {
		long stockId = -1;
		try {
			stockId = Long.parseLong(rawStockId);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException(e);
		}
		return getStockById(stockId, params);
	}

	public void saveOrUpdateStock(ProductSkuStock stock) {
		stockDao.saveOrUpdateStock(stock);
	}

	public void deleteStock(ProductSkuStock stock) {
		stockDao.deleteStock(stock);
	}

}