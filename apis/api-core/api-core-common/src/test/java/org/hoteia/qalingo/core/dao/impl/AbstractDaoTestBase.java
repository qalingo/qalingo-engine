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

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.Column;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.ITableMetaData;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.operation.DatabaseOperation;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration({ "/conf/spring/test/qalingo-core-dao-test-context.xml" })
public class AbstractDaoTestBase {

    @Autowired
    private DataSource dataSource;

    @Before
    public void init() throws Exception {
        IDatabaseConnection connection = getConnection();
        try {
             DatabaseOperation.CLEAN_INSERT.execute(connection, getDataSet());
//            createHsqldbTables(getDataSet(), connection.getConnection());

        } finally {
            connection.getConnection().commit();
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

    private void createHsqldbTables(IDataSet dataSet, Connection connection) throws DataSetException, SQLException {
        String[] tableNames = dataSet.getTableNames();

        String sql = "";
        for (String tableName : tableNames) {
            ITable table = dataSet.getTable(tableName);
            ITableMetaData metadata = table.getTableMetaData();
            Column[] columns = metadata.getColumns();

            sql += "create table " + tableName + "( ";
            boolean first = true;
            for (Column column : columns) {
                if (!first) {
                    sql += ", ";
                }
                String columnName = column.getColumnName();
                String type = resolveType((String) table.getValue(0, columnName));
                sql += columnName + " " + type;
                if (first) {
                    sql += " primary key";
                    first = false;
                }
            }
            sql += "); ";
        }
        PreparedStatement pp = connection.prepareStatement(sql);
        pp.executeUpdate();
    }

    private String resolveType(String str) {
        try {
            if (new Double(str).toString().equals(str)) {
                return "double";
            }
        } catch (Exception e) {
        }

        try {
            if (new Integer(str).toString().equals(str)) {
                return "int";
            }
        } catch (Exception e) {
        }

        return "varchar";
    }

}