/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package fr.hoteia.qalingo.core.dao.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import fr.hoteia.qalingo.core.dao.StockDao;
import fr.hoteia.qalingo.core.domain.ProductSkuStock;

@Transactional
@Repository("stockDao")
public class StockDaoImpl extends AbstractGenericDaoImpl implements StockDao {

	private final Logger LOG = LoggerFactory.getLogger(getClass());

	public ProductSkuStock getStockById(Long productSkuStockId) {
		return em.find(ProductSkuStock.class, productSkuStockId);
	}

	public List<ProductSkuStock> findByExample(ProductSkuStock productSkuStockExample) {
		return super.findByExample(productSkuStockExample);
	}

	public void saveOrUpdateStock(ProductSkuStock productSkuStock) {
		if(productSkuStock.getId() == null){
			em.persist(productSkuStock);
		} else {
			em.merge(productSkuStock);
		}
	}

	public void deleteStock(ProductSkuStock productSkuStock) {
		em.remove(productSkuStock);
	}

}
