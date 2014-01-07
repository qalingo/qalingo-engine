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

    public BatchProcessObject getBatchProcessObjectById(final Long batchProcessObjectId) {
        return batchProcessObjectDao.getBatchProcessObjectById(batchProcessObjectId);
    }

    public BatchProcessObject getBatchProcessObjectById(final String rawBatchProcessObjectId) {
        long batchProcessObjectId = -1;
        try {
            batchProcessObjectId = Long.parseLong(rawBatchProcessObjectId);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(e);
        }
        return batchProcessObjectDao.getBatchProcessObjectById(batchProcessObjectId);
    }

    public List<BatchProcessObject> findBatchProcessObjects() {
        return batchProcessObjectDao.findBatchProcessObjects();
    }

    public List<BatchProcessObject> findBatchProcessObjectsByTypeObject(final BatchProcessObjectType typeObject) {
        return batchProcessObjectDao.findBatchProcessObjectsByTypeObject(typeObject);
    }

    public void saveOrUpdateBatchProcessObject(final BatchProcessObject batchProcessObject) {
        batchProcessObjectDao.saveOrUpdateBatchProcessObject(batchProcessObject);
    }

    public void deleteBatchProcessObject(final BatchProcessObject batchProcessObject) {
        batchProcessObjectDao.deleteBatchProcessObject(batchProcessObject);
    }

}
