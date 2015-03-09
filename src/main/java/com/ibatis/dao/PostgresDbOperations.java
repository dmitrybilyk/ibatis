package com.ibatis.dao;

import org.dbunit.DatabaseUnitException;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseConnection;

import java.sql.Connection;

/**
 * Stanislav Valenta, 10/25/13
 */
public class PostgresDbOperations extends DbOperations {
  private final String schema;

  protected PostgresDbOperations(Connection connection, String schema) throws DatabaseUnitException {
    super(connection);
    this.schema = schema;
  }

  @Override
  protected void setCheckConstraints(boolean enableChecking) {
//    String replicationRole = enableChecking ? "DEFAULT;" : "replica;";
//    execute("SET session_replication_role = " + replicationRole);
  }

  protected DatabaseConnection createDatabaseConnection(Connection connection) throws DatabaseUnitException {
    DatabaseConnection databaseConnection = new DatabaseConnection(connection, schema);
    DatabaseConfig config = databaseConnection.getConfig();
//    config.setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, new BitPostgresqlDataTypeFactory());
    config.setProperty(DatabaseConfig.FEATURE_CASE_SENSITIVE_TABLE_NAMES, false);
    config.setProperty(DatabaseConfig.FEATURE_QUALIFIED_TABLE_NAMES, true);
    return databaseConnection;
  }
}
