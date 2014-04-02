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

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hoteia.qalingo.core.dao.EngineSettingDao;
import org.hoteia.qalingo.core.domain.EngineSetting;
import org.hoteia.qalingo.core.domain.EngineSettingValue;
import org.hoteia.qalingo.core.fetchplan.FetchPlan;
import org.hoteia.qalingo.core.fetchplan.common.FetchPlanGraphCommon;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository("engineSettingDao")
public class EngineSettingDaoImpl extends AbstractGenericDaoImpl implements EngineSettingDao {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	// Engine Setting
	
	public EngineSetting getEngineSettingById(final Long engineSettingId, Object... params) {
        Criteria criteria = createDefaultCriteria(EngineSetting.class);
        criteria.add(Restrictions.eq("id", engineSettingId));
        
        FetchPlan fetchPlan = handleSpecificFetchMode(criteria, params);
        
        EngineSetting engineSetting = (EngineSetting) criteria.uniqueResult();
        if(engineSetting != null){
            engineSetting.setFetchPlan(fetchPlan);
        }
        return engineSetting;
	}
	
	public EngineSetting getEngineSettingByCode(final String code, Object... params) {
        Criteria criteria = createDefaultCriteria(EngineSetting.class);
        criteria.add(Restrictions.eq("code", code));
        
        FetchPlan fetchPlan = handleSpecificFetchMode(criteria, params);
        
        EngineSetting engineSetting = (EngineSetting) criteria.uniqueResult();
        if(engineSetting != null){
            engineSetting.setFetchPlan(fetchPlan);
        }
		return engineSetting;
	}
	
	public List<EngineSetting> findEngineSettings(Object... params) {
        Criteria criteria = createDefaultCriteria(EngineSetting.class);
        
        handleSpecificFetchMode(criteria, params);
        
        criteria.addOrder(Order.asc("code"));

        @SuppressWarnings("unchecked")
        List<EngineSetting> engineSettings = criteria.list();
		return engineSettings;
	}
	
	public EngineSetting saveEngineSetting(EngineSetting engineSetting) {
		if(engineSetting.getDateCreate() == null){
			engineSetting.setDateCreate(new Date());
		}
		engineSetting.setDateUpdate(new Date());
        if (engineSetting.getId() != null) {
            if(em.contains(engineSetting)){
                em.refresh(engineSetting);
            }
            EngineSetting mergedEngineSetting = em.merge(engineSetting);
            em.flush();
            return mergedEngineSetting;
        } else {
            em.persist(engineSetting);
            return engineSetting;
        }
	}

	public void deleteEngineSetting(EngineSetting engineSetting) {
		em.remove(engineSetting);
	}

	// Engine Setting Value
	
	public EngineSettingValue getEngineSettingValueById(Long id, Object... params) {
		return em.find(EngineSettingValue.class, id);
	}
	
	public EngineSettingValue saveOrUpdateEngineSettingValue(EngineSettingValue engineSettingValue) {
		if(engineSettingValue.getDateCreate() == null){
			engineSettingValue.setDateCreate(new Date());
		}
		engineSettingValue.setDateUpdate(new Date());
        if (engineSettingValue.getId() != null) {
            if(em.contains(engineSettingValue)){
                em.refresh(engineSettingValue);
            }
            EngineSettingValue mergedEngineSettingValue = em.merge(engineSettingValue);
            em.flush();
            return mergedEngineSettingValue;
        } else {
            em.persist(engineSettingValue);
            return engineSettingValue;
        }
	}
	
    public void deleteEngineSettingValue(EngineSettingValue engineSettingValue) {
        em.remove(engineSettingValue);
    }

    @Override
    protected FetchPlan handleSpecificFetchMode(Criteria criteria, Object... params) {
        if (params != null && params.length > 0) {
            return super.handleSpecificFetchMode(criteria, params);
        } else {
            return super.handleSpecificFetchMode(criteria, FetchPlanGraphCommon.defaultEngineSettingFetchPlan());
        }
    }
}