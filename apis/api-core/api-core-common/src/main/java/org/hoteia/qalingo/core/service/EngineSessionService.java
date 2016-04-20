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

import org.dozer.Mapper;
import org.hoteia.qalingo.core.dao.EngineSessionDao;
import org.hoteia.qalingo.core.domain.Cart;
import org.hoteia.qalingo.core.domain.EngineBoSession;
import org.hoteia.qalingo.core.domain.EngineEcoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Date;

@Service("engineSessionService")
@Transactional
public class EngineSessionService {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    
    @Autowired
    protected EngineSessionDao engineSessionDao;

    @Autowired 
    protected Mapper dozerBeanMapper;

    @Autowired
    protected TaxService taxService;

    // ECO SESSION
    
    /**
     * 
     */
    public EngineEcoSession updateAndSynchronizeEngineEcoSession(EngineEcoSession engineEcoSessionWithTransientValues) throws Exception {
        saveOrUpdateEngineEcoSession(engineEcoSessionWithTransientValues);
        // RELOAD ENGINE SESSION - NOT A GOOD WAY FOR PERF - BUT A GOOD WAY TO ALWAYS KEEP RIGHT ENGINE SESSION DATAS LIKE TRANSIENT
        EngineEcoSession engineEcoSession = getEngineEcoSessionById(engineEcoSessionWithTransientValues.getId());
        synchronizeEngineEcoSession(engineEcoSessionWithTransientValues, engineEcoSession);
        return engineEcoSessionWithTransientValues;
    }
    
    /**
     * 
     */
    private void synchronizeEngineEcoSession(final EngineEcoSession engineEcoSessionWithTransientValues, final EngineEcoSession engineEcoSession) throws Exception {
        try {
            dozerBeanMapper.map(engineEcoSession, engineEcoSessionWithTransientValues);
        } catch (BeansException e) {
            logger.error("", e);
        }
    }
    
    /**
     * 
     */
    public void synchronizeEngineEcoSession(final EngineEcoSession engineEcoSessionWithTransientValues, final String ecoEngineSessionGuid) throws Exception {
        EngineEcoSession engineEcoSession =  getEngineEcoSessionByEngineSessionGuid(ecoEngineSessionGuid);
        synchronizeEngineEcoSession(engineEcoSessionWithTransientValues, engineEcoSession);
    }
    
    
    public EngineEcoSession getEngineEcoSessionById(final Long engineSessionId, Object... params) {
        return engineSessionDao.getEngineEcoSessionById(engineSessionId, params);
    }

    public EngineEcoSession getEngineEcoSessionById(final String rawEngineSessionId, Object... params) {
        long engineSessionId = -1;
        try {
            engineSessionId = Long.parseLong(rawEngineSessionId);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(e);
        }
        return getEngineEcoSessionById(engineSessionId, params);
    }
    
    public EngineEcoSession getEngineEcoSessionByEngineSessionGuid(final String engineSessionGuid, Object... params) {
        return engineSessionDao.getEngineEcoSessionByEngineSessionGuid(engineSessionGuid, params);
    }

    public EngineEcoSession saveOrUpdateEngineEcoSession(final EngineEcoSession engineEcoSession) {
        return engineSessionDao.saveOrUpdateEngineEcoSession(engineEcoSession);
    }

    public void deleteEngineEcoSession(final EngineEcoSession engineEcoSession) {
        engineSessionDao.deleteEngineEcoSession(engineEcoSession);
    }

    public int deleteEngineEcoSession(final Timestamp before) {
        return engineSessionDao.deleteEngineEcoSession(before);
    }
    
    // BO SESSION

    public EngineBoSession getEngineBoSessionById(final Long engineSessionId, Object... params) {
        return engineSessionDao.getEngineBoSessionById(engineSessionId, params);
    }

    public EngineBoSession getEngineBoSessionById(final String rawEngineSessionId, Object... params) {
        long engineSessionId = -1;
        try {
            engineSessionId = Long.parseLong(rawEngineSessionId);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(e);
        }
        return getEngineBoSessionById(engineSessionId, params);
    }
    
    public EngineBoSession getEngineBoSessionByEngineSessionGuid(final String engineSessionGuid, Object... params) {
        return engineSessionDao.getEngineBoSessionByEngineSessionGuid(engineSessionGuid, params);
    }

    public EngineBoSession saveOrUpdateEngineBoSession(final EngineBoSession engineBoSession) {
        return engineSessionDao.saveOrUpdateEngineBoSession(engineBoSession);
    }

    public void deleteEngineBoSession(final EngineBoSession engineBoSession) {
        engineSessionDao.deleteEngineBoSession(engineBoSession);
    }

    public int deleteEngineBoSession(final Timestamp before) {
        return engineSessionDao.deleteEngineBoSession(before);
    }
    
    public EngineEcoSession addNewCart(EngineEcoSession engineEcoSession) {
        Cart cart = new Cart();
        cart.setVersion(1);
        cart.setMarketAreaId(engineEcoSession.getCurrentMarketArea().getId());
        cart.setLocalizationId(engineEcoSession.getCurrentMarketAreaLocalization().getId());
        cart.setRetailerId(engineEcoSession.getCurrentMarketAreaRetailer().getId());
        cart.setCurrency(engineEcoSession.getCurrentMarketAreaCurrency());
        Date date = new Date();
        cart.setDateCreate(date);
        cart.setDateUpdate(date);
//        List<Tax> taxesByMarketAreaId = taxService.findTaxesByMarketAreaId(engineEcoSession.getCurrentMarketArea().getId());
//        cart.getTaxes().addAll(taxesByMarketAreaId);
        if (engineEcoSession.getCurrentCustomer() != null) {
            cart.setCustomerId(engineEcoSession.getCurrentCustomer().getId());
            cart.setBillingAddressId(engineEcoSession.getCurrentCustomer().getDefaultBillingAddressId());
            cart.setShippingAddressId(engineEcoSession.getCurrentCustomer().getDefaultShippingAddressId());
        }

        engineEcoSession.addCart(cart);
        return engineEcoSession;
    }
}