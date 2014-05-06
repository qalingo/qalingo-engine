/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.dao;

import java.util.List;

import org.hoteia.qalingo.core.domain.ProductSkuStock;
import org.hoteia.qalingo.core.domain.Warehouse;

public interface WarehouseDao {

    // WAREHOUSE
    
    Warehouse getWarehouseById(Long warehouseId, Object... params);

    Warehouse getWarehouseByCode(String warehouseCode, Object... params);

    List<Warehouse> findWarehouses(Object... params);
    
    List<Warehouse> findWarehousesByMarketAreaId(Long marketAreaId, Object... params);

    List<Warehouse> findWarehousesByDeliveryMethodId(Long deliveryMethodId, Object... params);

    Warehouse saveOrUpdateWarehouse(Warehouse warehouse);

    void deleteWarehouse(Warehouse warehouse);
    
    // STOCK
    
	ProductSkuStock getStockById(Long stockId, Object... params);

	ProductSkuStock saveOrUpdateStock(ProductSkuStock stock);

	void deleteStock(ProductSkuStock stock);

}