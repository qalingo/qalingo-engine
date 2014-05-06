/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.service.impl;

import java.util.List;

import org.hoteia.qalingo.core.dao.WarehouseDao;
import org.hoteia.qalingo.core.domain.ProductSkuStock;
import org.hoteia.qalingo.core.domain.Warehouse;
import org.hoteia.qalingo.core.service.WarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("warehouseService")
@Transactional
public class WarehouseServiceImpl implements WarehouseService {

	@Autowired
	private WarehouseDao warehouseDao;
	
    public Warehouse getWarehouseById(final Long warehouseId, Object... params) {
        return warehouseDao.getWarehouseById(warehouseId, params);
    }

    public Warehouse getWarehouseById(final String rawWarehouseId, Object... params) {
        long warehouseId = -1;
        try {
            warehouseId = Long.parseLong(rawWarehouseId);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(e);
        }
        return getWarehouseById(warehouseId, params);
    }

    public Warehouse getWarehouseByCode(final String code, Object... params) {
        return warehouseDao.getWarehouseByCode(code, params);
    }

    public List<Warehouse> findWarehouses(Object... params) {
        return warehouseDao.findWarehouses(params);
    }

    public List<Warehouse> findWarehousesByMarketAreaId(Long marketAreaId, Object... params) {
        return warehouseDao.findWarehousesByMarketAreaId(marketAreaId, params);
    }
    
    public List<Warehouse> findWarehousesByDeliveryMethodId(Long deliveryMethodId, Object... params) {
        return warehouseDao.findWarehousesByDeliveryMethodId(deliveryMethodId, params);
    }
    
    public Warehouse saveOrUpdateWarehouse(final Warehouse warehouse) {
        return warehouseDao.saveOrUpdateWarehouse(warehouse);
    }

    public void deleteWarehouse(final Warehouse warehouse) {
        warehouseDao.deleteWarehouse(warehouse);
    }
	
	// STOCK

    public ProductSkuStock getStockById(final Long stockId, Object... params) {
        return warehouseDao.getStockById(stockId, params);
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
		warehouseDao.saveOrUpdateStock(stock);
	}

	public void deleteStock(ProductSkuStock stock) {
		warehouseDao.deleteStock(stock);
	}

}