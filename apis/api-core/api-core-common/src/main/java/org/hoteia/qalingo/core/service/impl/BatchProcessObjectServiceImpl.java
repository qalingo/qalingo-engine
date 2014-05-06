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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.hoteia.qalingo.core.dao.BatchProcessObjectDao;
import org.hoteia.qalingo.core.domain.BatchProcessObject;
import org.hoteia.qalingo.core.domain.enumtype.BatchProcessObjectType;
import org.hoteia.qalingo.core.service.BatchProcessObjectService;

@Service("batchProcessObjectService")
@Transactional
public class BatchProcessObjectServiceImpl implements BatchProcessObjectService {

    @Autowired
    private BatchProcessObjectDao batchProcessObjectDao;

    public BatchProcessObject getBatchProcessObjectById(final Long batchProcessObjectId, Object... params) {
        return batchProcessObjectDao.getBatchProcessObjectById(batchProcessObjectId, params);
    }

    public BatchProcessObject getBatchProcessObjectById(final String rawBatchProcessObjectId, Object... params) {
        long batchProcessObjectId = -1;
        try {
            batchProcessObjectId = Long.parseLong(rawBatchProcessObjectId);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(e);
        }
        return batchProcessObjectDao.getBatchProcessObjectById(batchProcessObjectId, params);
    }

    public List<BatchProcessObject> findBatchProcessObjects(Object... params) {
        return batchProcessObjectDao.findBatchProcessObjects(params);
    }

    public List<BatchProcessObject> findBatchProcessObjectsByTypeObject(final BatchProcessObjectType typeObject, Object... params) {
        return batchProcessObjectDao.findBatchProcessObjectsByTypeObject(typeObject, params);
    }

    public void saveOrUpdateBatchProcessObject(final BatchProcessObject batchProcessObject) {
        batchProcessObjectDao.saveOrUpdateBatchProcessObject(batchProcessObject);
    }

    public void deleteBatchProcessObject(final BatchProcessObject batchProcessObject) {
        batchProcessObjectDao.deleteBatchProcessObject(batchProcessObject);
    }

}
