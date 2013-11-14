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

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import fr.hoteia.qalingo.core.dao.LocalizationDao;
import fr.hoteia.qalingo.core.domain.Localization;

@Transactional
@Repository("localizationDao")
public class LocalizationDaoImpl extends AbstractGenericDaoImpl implements LocalizationDao {

	private final Logger LOG = LoggerFactory.getLogger(getClass());

	public Localization getLocalizationById(Long localizationId) {
		return em.find(Localization.class, localizationId);
	}

	public Localization getLocalizationByCode(String code) {
		Session session = (Session) em.getDelegate();
		String sql = "FROM Localization WHERE upper(code) = upper(:code)";
		Query query = session.createQuery(sql);
		query.setString("code", code);
		Localization localization = (Localization) query.uniqueResult();
		return localization;
	}
	
//	public List<Localization> findByExample(Localization localizationExample) {
//		return super.findByExample(localizationExample);
//	}

	public List<Localization> findLocalizations() {
		Session session = (Session) em.getDelegate();
		String sql = "FROM Localization ORDER BY localeCode";
		Query query = session.createQuery(sql);
		List<Localization> localizations = (List<Localization>) query.list();
		return localizations;
	}
	
	public void saveOrUpdateLocalization(Localization localization) {
		if(localization.getId() == null){
			em.persist(localization);
		} else {
			em.merge(localization);
		}
	}

	public void deleteLocalization(Localization localization) {
		em.remove(localization);
	}

}
