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

import org.hoteia.qalingo.core.dao.TaxDao;
import org.hoteia.qalingo.core.domain.Tax;
import org.hoteia.qalingo.core.service.TaxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("taxService")
@Transactional
public class TaxServiceImpl implements TaxService {

    @Autowired
    private TaxDao taxDao;

    public Tax getTaxById(Long taxId, Object... params) {
        return taxDao.getTaxById(taxId, params);
    }

    public Tax getTaxById(String rawTaxId, Object... params) {
        long taxId = -1;
        try {
            taxId = Long.parseLong(rawTaxId);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(e);
        }
        return getTaxById(taxId, params);
    }
    
    public Tax getTaxByCode(String taxCode, Object... params) {
        return taxDao.getTaxByCode(taxCode, params);
    }
    
    public List<Tax> findTaxes(Object... params) {
        return taxDao.findTaxes(params);
    }

    public List<Tax> findTaxesByMarketAreaId(Long marketAreaId, Object... params) {
        return taxDao.findTaxesByMarketAreaId(marketAreaId, params);
    }
    
    public Tax saveOrUpdateTax(Tax tax) {
        return taxDao.saveOrUpdateTax(tax);
    }

    public void deleteTax(Tax tax) {
        taxDao.deleteTax(tax);
    }

}
