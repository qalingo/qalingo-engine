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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import fr.hoteia.qalingo.core.dao.TaxDao;
import fr.hoteia.qalingo.core.domain.Tax;

@Transactional
@Repository("taxDao")
public class TaxDaoImpl extends AbstractGenericDaoImpl implements TaxDao {

	private final Logger LOG = LoggerFactory.getLogger(getClass());

	public Tax getTaxById(Long taxId) {
		return em.find(Tax.class, taxId);
	}

	public void saveOrUpdateTax(Tax tax) {
		if(tax.getId() == null){
			em.persist(tax);
		} else {
			em.merge(tax);
		}
	}

	public void deleteTax(Tax tax) {
		em.remove(tax);
	}

}
