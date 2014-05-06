/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.service;

import java.util.List;

import org.hoteia.qalingo.core.domain.Tax;

public interface TaxService {

    Tax getTaxById(Long taxId, Object... params);

    Tax getTaxById(String taxId, Object... params);

    Tax getTaxByCode(String taxCode, Object... params);

    List<Tax> findTaxes(Object... params);

    List<Tax> findTaxesByMarketAreaId(Long marketAreaId, Object... params);

    Tax saveOrUpdateTax(Tax tax);

    void deleteTax(Tax tax);

}
