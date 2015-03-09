package com.ibatis;

import com.ibatis.dao.DbOperations;
import com.ibatis.sqlmap.client.SqlMapClient;
import org.dbunit.DatabaseUnitException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeMethod;

import java.sql.Connection;
import java.sql.SQLException;

import static org.dbunit.operation.DatabaseOperation.CLEAN_INSERT;
import static org.dbunit.operation.DatabaseOperation.DELETE_ALL;

/**
 * Stanislav Valenta, 10/24/13
 */
public abstract class ITDBBase extends AbstractTestNGSpringContextTests {
  @Autowired
  protected SqlMapClient sqlMapClient;
  @Autowired
  protected Connection adminConnection;
  @Autowired
  protected DbOperations dbOperations;

  @BeforeMethod
  public void setUpDatabase() throws SQLException, DatabaseUnitException {
    String pathToDataSet = getPathToDataSet();
    dbOperations.execute(pathToDataSet, DELETE_ALL, CLEAN_INSERT);
    resetSpeechSequences();
  }

  @AfterClass
  public void cleanUpDatabase() throws SQLException, DatabaseUnitException {
    String pathToDataSet = getPathToDataSet();
    dbOperations.execute(pathToDataSet, DELETE_ALL);
  }

  protected abstract String getPathToDataSet();

  protected  void resetSpeechSequences(){
  }
}
