package com.ibatis.dao;

import org.dbunit.DatabaseUnitException;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Stanislav Valenta, 10/25/13
 */
public abstract class DbOperations {
  private static final Logger LOG = LoggerFactory.getLogger(DbOperations.class);

  protected Connection connection;
  protected DatabaseConnection databaseConnection;
  private final FlatXmlDataSetBuilder setBuilder;

  protected DbOperations(Connection connection) throws DatabaseUnitException {
    this.connection = connection;
    setBuilder = new FlatXmlDataSetBuilder();
    setBuilder.setColumnSensing(true);
  }

  public void initDatabaseConnection() throws DatabaseUnitException {
    databaseConnection = createDatabaseConnection(connection);
  }

  public void closeConnection() throws SQLException {
    connection.close();
  }

  public void execute(String pathToDataSet, DatabaseOperation... dbOperations) throws DatabaseUnitException, SQLException {
    FlatXmlDataSet dataSet = setBuilder.build(getClass().getResourceAsStream(pathToDataSet));
    setCheckConstraints(false);
    for (DatabaseOperation dbOperation : dbOperations) {
      dbOperation.execute(databaseConnection, dataSet);
    }
    setCheckConstraints(true);
  }

  protected void execute(String query)  {
    try {
      Statement statement = connection.createStatement();
      statement.execute(query);
      statement.close();
    } catch (SQLException e) {
      LOG.error("Error to execute query {}", query, e);
    }
  }

  protected abstract void setCheckConstraints(boolean enableChecking);

  protected abstract DatabaseConnection createDatabaseConnection(Connection connection) throws DatabaseUnitException;

}
