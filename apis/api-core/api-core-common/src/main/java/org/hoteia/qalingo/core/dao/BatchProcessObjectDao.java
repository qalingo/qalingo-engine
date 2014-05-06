/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.dao;

import java.util.List;

import org.hoteia.qalingo.core.domain.BatchProcessObject;
import org.hoteia.qalingo.core.domain.enumtype.BatchProcessObjectType;

public interface BatchProcessObjectDao {

	BatchProcessObject getBatchProcessObjectById(Long batchProcessObjectId, Object... params);

	List<BatchProcessObject> findBatchProcessObjects(Object... params);
	
	List<BatchProcessObject> findBatchProcessObjectsByTypeObject(BatchProcessObjectType typeObject, Object... params);

	BatchProcessObject saveOrUpdateBatchProcessObject(BatchProcessObject batchProcessObject);

	void deleteBatchProcessObject(BatchProcessObject batchProcessObject);

}
