package com.ibatis.dao;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.learn.model.Person;
import com.learn.model.PersonIdHolder;

import java.util.List;

/**
 * Created by dik81 on 11/27/14.
 */
public interface PersonDao {
  Person addPerson(Person user, SqlMapClient sqlmapClient);

  Person getUserById(Integer id, SqlMapClient sqlmapClient);

  List<Person> getPersons(SqlMapClient sqlmapClient);

  List<Person> getPersonsByIds(SqlMapClient sqlmapClient, List<PersonIdHolder> personIdHolderList);

  List<Person> getAllPersons(SqlMapClient sqlmapClient);

  List<Person> getPersonsByConditions(SqlMapClient sqlmapClient);

  Integer getAllPersonsCount(SqlMapClient sqlmapClient);

  List<Person> getAllPersonsFromUsers(SqlMapClient sqlmapClient);

  void deleteUserById(Integer id, SqlMapClient sqlmapClient);
}
