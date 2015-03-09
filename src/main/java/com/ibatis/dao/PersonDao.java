package com.ibatis.dao;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.learn.model.Person;
import com.learn.model.PersonIdHolder;

import java.util.List;

/**
 * Created by dik81 on 11/27/14.
 */
public interface PersonDao {
  Person addPerson(Person user);

  Person getUserById(Integer id);

  List<Person> getPersons();

  List<Person> getPersonsByIds(List<PersonIdHolder> personIdHolderList);

  List<Person> getAllPersons();

  List<Person> getPersonsByConditions();

  Integer getAllPersonsCount();

  List<Person> getAllPersonsFromUsers();

  void deleteUserById(Integer id);
}
