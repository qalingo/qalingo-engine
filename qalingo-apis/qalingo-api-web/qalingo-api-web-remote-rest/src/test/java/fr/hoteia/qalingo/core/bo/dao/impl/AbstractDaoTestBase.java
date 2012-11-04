/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package fr.hoteia.qalingo.core.bo.dao.impl;

import java.io.InputStream;
import java.sql.Connection;

import javax.sql.DataSource;

import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.operation.DatabaseOperation;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration({ "/conf/spring/test/core-dao-test-context.xml" })
public class AbstractDaoTestBase {

	@Autowired
	private DataSource dataSource;

	@Before
	public void init() throws Exception {
		IDatabaseConnection connection = getConnection();
		try {
			DatabaseOperation.CLEAN_INSERT.execute(connection, getDataSet());
		} finally {
			connection.getConnection().commit();
			connection.close();
		}
	}

	private IDatabaseConnection getConnection() throws Exception {
		Connection con = dataSource.getConnection();
		IDatabaseConnection connection = new DatabaseConnection(con);
		return connection;
	}

	private IDataSet getDataSet() throws Exception {
		String datasetPath = "/" + this.getClass().getCanonicalName().replace('.', '/') + ".xml";
		InputStream dataSetStream = this.getClass().getResourceAsStream(datasetPath);
		if (dataSetStream == null) {
			throw new IllegalArgumentException("Dataset is required in '" + datasetPath + "'");
		}
		return new FlatXmlDataSet(dataSetStream);
	}

}
