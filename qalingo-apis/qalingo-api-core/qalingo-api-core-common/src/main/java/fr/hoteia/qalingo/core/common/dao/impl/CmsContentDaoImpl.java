/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package fr.hoteia.qalingo.core.common.dao.impl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import fr.hoteia.qalingo.core.common.dao.CmsContentDao;
import fr.hoteia.qalingo.core.common.domain.CmsContent;

@Transactional
@Repository("cmsContentDao")
public class CmsContentDaoImpl extends AbstractGenericDaoImpl implements CmsContentDao {

	private final Logger LOG = LoggerFactory.getLogger(getClass());

	public CmsContent getCmsContentById(Long cmsContentId) {
		return em.find(CmsContent.class, cmsContentId);
	}

	public List<CmsContent> findByExample(CmsContent cmsContentExample) {
		return super.findByExample(cmsContentExample);
	}

	public void saveOrUpdateCmsContent(CmsContent cmsContent) {
		if(cmsContent.getDateCreate() == null){
			cmsContent.setDateCreate(new Date());
		}
		cmsContent.setDateUpdate(new Date());
		if(cmsContent.getId() == null){
			em.persist(cmsContent);
		} else {
			em.merge(cmsContent);
		}
	}

	public void deleteCmsContent(CmsContent cmsContent) {
		em.remove(cmsContent);
	}

}
