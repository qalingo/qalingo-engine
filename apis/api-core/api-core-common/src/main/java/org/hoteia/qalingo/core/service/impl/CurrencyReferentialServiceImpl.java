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

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.hoteia.qalingo.core.dao.CurrencyReferentialDao;
import org.hoteia.qalingo.core.domain.CurrencyReferential;
import org.hoteia.qalingo.core.service.CurrencyReferentialService;

@Service("currencyReferentialService")
@Transactional
public class CurrencyReferentialServiceImpl implements CurrencyReferentialService {

	@Autowired
	private CurrencyReferentialDao currencyReferentialDao;

	public CurrencyReferential getCurrencyReferentialById(String rawCurrencyReferentialId) {
		long currencyReferentialId = -1;
		try {
			currencyReferentialId = Long.parseLong(rawCurrencyReferentialId);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException(e);
		}
		return currencyReferentialDao.getCurrencyReferentialById(currencyReferentialId);
	}
	
	public CurrencyReferential getCurrencyReferentialByCode(String currencyReferentialCode) {
		return currencyReferentialDao.getCurrencyReferentialByCode(currencyReferentialCode);
	}

	public List<CurrencyReferential> findCurrencyReferentials() {
		return currencyReferentialDao.findCurrencyReferentials();
	}
	
	public void saveOrUpdateCurrencyReferential(CurrencyReferential currencyReferential) {
		currencyReferentialDao.saveOrUpdateCurrencyReferential(currencyReferential);
	}

	public void deleteCurrencyReferential(CurrencyReferential currencyReferential) {
		currencyReferentialDao.deleteCurrencyReferential(currencyReferential);
	}

}
