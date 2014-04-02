/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.dao.impl;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.hoteia.qalingo.core.dao.WarehouseDao;
import org.hoteia.qalingo.core.domain.ProductSkuStock;
import org.hoteia.qalingo.core.domain.Warehouse;
import org.hoteia.qalingo.core.fetchplan.FetchPlan;
import org.hoteia.qalingo.core.fetchplan.common.FetchPlanGraphCommon;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository("warehouseDao")
public class WarehouseDaoImpl extends AbstractGenericDaoImpl implements WarehouseDao {

	private final Logger logger = LoggerFactory.getLogger(getClass());

    // WAREHOUSE

    public Warehouse getWarehouseById(final Long warehouseId, Object... params) {
        Criteria criteria = createDefaultCriteria(Warehouse.class);

        FetchPlan fetchPlan = handleSpecificFetchMode(criteria, params);

        criteria.add(Restrictions.eq("id", warehouseId));
        Warehouse warehouse = (Warehouse) criteria.uniqueResult();
        if(warehouse != null){
            warehouse.setFetchPlan(fetchPlan);
        }
        return warehouse;
    }

    public Warehouse getWarehouseByCode(final String warehouseCode, Object... params) {
        Criteria criteria = createDefaultCriteria(Warehouse.class);

        FetchPlan fetchPlan = handleSpecificFetchMode(criteria, params);

        criteria.add(Restrictions.eq("code", warehouseCode));
        Warehouse warehouse = (Warehouse) criteria.uniqueResult();
        if(warehouse != null){
            warehouse.setFetchPlan(fetchPlan);
        }
        return warehouse;
    }

    public List<Warehouse> findWarehouses(Object... params) {
        Criteria criteria = createDefaultCriteria(Warehouse.class);

        handleSpecificFetchMode(criteria, params);

        criteria.addOrder(Order.asc("code"));

        @SuppressWarnings("unchecked")
        List<Warehouse> warehouses = criteria.list();

        return warehouses;
    }
    
    public List<Warehouse> findWarehousesByMarketAreaId(Long marketAreaId, Object... params) {
        Criteria criteria = createDefaultCriteria(Warehouse.class);

        handleSpecificFetchMode(criteria, params);

        criteria.createAlias("marketAreas", "marketArea", JoinType.LEFT_OUTER_JOIN);
        criteria.add(Restrictions.eq("marketArea.id", marketAreaId));

        criteria.addOrder(Order.asc("code"));

        @SuppressWarnings("unchecked")
        List<Warehouse> warehouses = criteria.list();
        return warehouses;
    }
    
    public List<Warehouse> findWarehousesByDeliveryMethodId(Long deliveryMethodId, Object... params) {
        Criteria criteria = createDefaultCriteria(Warehouse.class);

        handleSpecificFetchMode(criteria, params);

        criteria.createAlias("deliveryMethods", "deliveryMethod", JoinType.LEFT_OUTER_JOIN);
        criteria.add(Restrictions.eq("deliveryMethod.id", deliveryMethodId));

        criteria.addOrder(Order.asc("code"));

        @SuppressWarnings("unchecked")
        List<Warehouse> warehouses = criteria.list();
        return warehouses;
    }

    public Warehouse saveOrUpdateWarehouse(final Warehouse warehouse) {
        if (warehouse.getDateCreate() == null) {
            warehouse.setDateCreate(new Date());
        }
        if (StringUtils.isEmpty(warehouse.getCode())) {
            warehouse.setCode(UUID.randomUUID().toString());
        }
        warehouse.setDateUpdate(new Date());

        if (warehouse.getId() != null) {
            if (em.contains(warehouse)) {
                em.refresh(warehouse);
            }
            Warehouse mergedWarehouse = em.merge(warehouse);
            em.flush();
            return mergedWarehouse;
        } else {
            em.persist(warehouse);
            return warehouse;
        }
    }

    public void deleteWarehouse(final Warehouse warehouse) {
        em.remove(warehouse);
    }

    @Override
    protected FetchPlan handleSpecificFetchMode(Criteria criteria, Object... params) {
        if (params != null && params.length > 0) {
            return super.handleSpecificFetchMode(criteria, params);
        } else {
            return super.handleSpecificFetchMode(criteria, FetchPlanGraphCommon.defaultWarehouseFetchPlan());
        }
    }
	    
	// STOCK
	
	public ProductSkuStock getStockById(final Long productSkuStockId, Object... params) {
        Criteria criteria = createDefaultCriteria(ProductSkuStock.class);
        criteria.add(Restrictions.eq("id", productSkuStockId));
        ProductSkuStock productSkuStock = (ProductSkuStock) criteria.uniqueResult();
        return productSkuStock;
	}

	public ProductSkuStock saveOrUpdateStock(ProductSkuStock productSkuStock) {
        if (productSkuStock.getId() != null) {
            if(em.contains(productSkuStock)){
                em.refresh(productSkuStock);
            }
            ProductSkuStock mergedProductSkuStock = em.merge(productSkuStock);
            em.flush();
            return mergedProductSkuStock;
        } else {
            em.persist(productSkuStock);
            return productSkuStock;
        }
	}

	public void deleteStock(ProductSkuStock productSkuStock) {
		em.remove(productSkuStock);
	}

}