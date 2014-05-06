/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.dao.impl;

import java.util.Date;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.hoteia.qalingo.core.dao.CmsContentDao;
import org.hoteia.qalingo.core.domain.CmsContent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository("cmsContentDao")
public class CmsContentDaoImpl extends AbstractGenericDaoImpl implements CmsContentDao {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	public CmsContent getCmsContentById(final Long cmsContentId, Object... params) {
        Criteria criteria = createDefaultCriteria(CmsContent.class);
        criteria.add(Restrictions.eq("id", cmsContentId));
        CmsContent cmsContent = (CmsContent) criteria.uniqueResult();
        return cmsContent;
	}

	public CmsContent saveOrUpdateCmsContent(final CmsContent cmsContent) {
		if(cmsContent.getDateCreate() == null){
			cmsContent.setDateCreate(new Date());
		}
		cmsContent.setDateUpdate(new Date());
        if (cmsContent.getId() != null) {
            if(em.contains(cmsContent)){
                em.refresh(cmsContent);
            }
            CmsContent mergedCmsContent = em.merge(cmsContent);
            em.flush();
            return mergedCmsContent;
        } else {
            em.persist(cmsContent);
            return cmsContent;
        }
	}

	public void deleteCmsContent(final CmsContent cmsContent) {
		em.remove(cmsContent);
	}

}