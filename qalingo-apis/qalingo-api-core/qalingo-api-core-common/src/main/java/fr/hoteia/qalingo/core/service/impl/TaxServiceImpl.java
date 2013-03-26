/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package fr.hoteia.qalingo.core.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.hoteia.qalingo.core.dao.TaxDao;
import fr.hoteia.qalingo.core.domain.Tax;
import fr.hoteia.qalingo.core.service.TaxService;

@Service("taxService")
@Transactional
public class TaxServiceImpl implements TaxService {

	@Autowired
	private TaxDao taxDao;

	public Tax getTaxById(String rawTaxId) {
		long taxId = -1;
		try {
			taxId = Long.parseLong(rawTaxId);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException(e);
		}
		return taxDao.getTaxById(taxId);
	}

	public List<Tax> findTax(Tax criteria) {
		return taxDao.findByExample(criteria);
	}

	public void saveOrUpdateTax(Tax tax) {
		taxDao.saveOrUpdateTax(tax);
	}

	public void deleteTax(Tax tax) {
		taxDao.deleteTax(tax);
	}

}
