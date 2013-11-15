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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.hoteia.qalingo.core.dao.CmsContentDao;
import org.hoteia.qalingo.core.domain.CmsContent;
import org.hoteia.qalingo.core.service.CmsContentService;

@Service("cmsContentService")
@Transactional
public class CmsContentServiceImpl implements CmsContentService {

	@Autowired
	private CmsContentDao cmsContentDao;

	public CmsContent getCmsContentById(String rawCmsContentId) {
		long cmsContentId = -1;
		try {
			cmsContentId = Long.parseLong(rawCmsContentId);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException(e);
		}
		return cmsContentDao.getCmsContentById(cmsContentId);
	}

	public void saveOrUpdateCmsContent(CmsContent cmsContent) {
		cmsContentDao.saveOrUpdateCmsContent(cmsContent);
	}

	public void deleteCmsContent(CmsContent cmsContent) {
		cmsContentDao.deleteCmsContent(cmsContent);
	}

}
