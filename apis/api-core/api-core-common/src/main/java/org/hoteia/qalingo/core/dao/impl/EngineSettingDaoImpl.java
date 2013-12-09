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
import org.hibernate.FetchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hoteia.qalingo.core.dao.EngineSettingDao;
import org.hoteia.qalingo.core.domain.EngineSetting;
import org.hoteia.qalingo.core.domain.EngineSettingValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository("engineSettingDao")
public class EngineSettingDaoImpl extends AbstractGenericDaoImpl implements EngineSettingDao {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	// Engine Setting
	public EngineSetting getEngineSettingById(final Long engineSettingId) {
//		return em.find(EngineSetting.class, id);
        Criteria criteria = getSession().createCriteria(EngineSetting.class);
        criteria.add(Restrictions.eq("id", engineSettingId));
        
        addDefaultFetch(criteria);
        
        EngineSetting engineSetting = (EngineSetting) criteria.uniqueResult();
        return engineSetting;
	}
	
	public EngineSetting getEngineSettingByCode(final String code) {
//		Session session = (Session) em.getDelegate();
//		String sql = "FROM EngineSetting WHERE code = :code";
//		Query query = session.createQuery(sql);
//		query.setString("code", code);
//		EngineSetting engineSetting = (EngineSetting) query.uniqueResult();
        Criteria criteria = getSession().createCriteria(EngineSetting.class);
        criteria.add(Restrictions.eq("code", code));
        
        addDefaultFetch(criteria);
        
        EngineSetting engineSetting = (EngineSetting) criteria.uniqueResult();
		return engineSetting;
	}
	
	public List<EngineSetting> findEngineSettings() {
//		Session session = (Session) em.getDelegate();
//		String sql = "FROM EngineSetting ORDER BY code";
//		Query query = session.createQuery(sql);
//		List<EngineSetting> engineSettings = (List<EngineSetting>) query.list();
	    
        Criteria criteria = getSession().createCriteria(EngineSetting.class);
        
        addDefaultFetch(criteria);
        
        criteria.addOrder(Order.asc("code"));

        @SuppressWarnings("unchecked")
        List<EngineSetting> engineSettings = criteria.list();
        
		return engineSettings;
	}
	
	public void saveEngineSetting(EngineSetting engineSetting) {
		if(engineSetting.getDateCreate() == null){
			engineSetting.setDateCreate(new Date());
		}
		engineSetting.setDateUpdate(new Date());
		em.merge(engineSetting);
	}

	public void deleteEngineSetting(EngineSetting engineSetting) {
		em.remove(engineSetting);
	}

	// Engine Setting Value
	public EngineSettingValue getEngineSettingValueById(Long id) {
		return em.find(EngineSettingValue.class, id);
	}
	
	public void saveOrUpdateEngineSettingValue(EngineSettingValue engineSettingValue) {
		if(engineSettingValue.getDateCreate() == null){
			engineSettingValue.setDateCreate(new Date());
		}
		engineSettingValue.setDateUpdate(new Date());
		if(engineSettingValue.getId() == null){
			em.persist(engineSettingValue);
		} else {
			em.merge(engineSettingValue);
		}
	}
	
    private void addDefaultFetch(Criteria criteria) {
        criteria.setFetchMode("engineSettingValues", FetchMode.JOIN); 
    }

}