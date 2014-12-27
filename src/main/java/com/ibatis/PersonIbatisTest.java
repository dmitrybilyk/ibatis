package com.ibatis;

/**
 * Created by dik81 on 11/27/14.
 */

import com.ibatis.common.resources.Resources;
import com.ibatis.dao.PersonDao;
import com.ibatis.dao.PersonDaoIbatis;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;
import com.learn.model.Person;

import java.io.Reader;
import java.util.Date;


public class PersonIbatisTest {
  public static void main(String[] args) throws Exception
  {
    //Initialize dao
    PersonDao manager = new PersonDaoIbatis();

    //Create the SQLMapClient
    Reader reader = Resources.getResourceAsReader("sqlmapclientPerson.xml");
    SqlMapClient sqlmapClient = SqlMapClientBuilder.buildSqlMapClient (reader);

    for (int i = 0; i < 10; i++) {
      manager.addPerson(new Person.PersonBuilder(i, "Dima"+i, "Bilyk"+i, new Date()).build(),sqlmapClient);
    }
  }
}
