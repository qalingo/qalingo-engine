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

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.hoteia.qalingo.core.dao.StockDao;
import org.hoteia.qalingo.core.domain.ProductSkuStock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository("stockDao")
public class StockDaoImpl extends AbstractGenericDaoImpl implements StockDao {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	public ProductSkuStock getStockById(final Long productSkuStockId) {
        Criteria criteria = getSession().createCriteria(ProductSkuStock.class);
        criteria.add(Restrictions.eq("id", productSkuStockId));
        ProductSkuStock productSkuStock = (ProductSkuStock) criteria.uniqueResult();
        return productSkuStock;
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