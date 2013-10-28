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

import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import fr.hoteia.qalingo.core.dao.EngineSettingDao;
import fr.hoteia.qalingo.core.domain.EngineSetting;
import fr.hoteia.qalingo.core.domain.EngineSettingValue;

@Transactional
@Repository("engineSettingDao")
public class EngineSettingDaoImpl extends AbstractGenericDaoImpl implements EngineSettingDao {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	// Engine Setting
	public EngineSetting getEngineSettingById(Long id) {
		return em.find(EngineSetting.class, id);
	}
	
	public EngineSetting getEngineSettingByCode(String code) {
		Session session = (Session) em.getDelegate();
		String sql = "FROM EngineSetting WHERE code = :code";
		Query query = session.createQuery(sql);
		query.setString("code", code);
		EngineSetting engineSetting = (EngineSetting) query.uniqueResult();
		return engineSetting;
	}
	
	public List<EngineSetting> findEngineSettings() {
		Session session = (Session) em.getDelegate();
		String sql = "FROM EngineSetting ORDER BY code";
		Query query = session.createQuery(sql);
		List<EngineSetting> engineSettings = (List<EngineSetting>) query.list();
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

}
