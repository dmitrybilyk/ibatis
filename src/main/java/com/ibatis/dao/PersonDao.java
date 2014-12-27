package com.ibatis.dao;

import com.ibatis.UserTEO;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.learn.model.Person;

import java.util.List;

/**
 * Created by dik81 on 11/27/14.
 */
public interface PersonDao {
  Person addPerson(Person user, SqlMapClient sqlmapClient);

  Person getUserById(Integer id, SqlMapClient sqlmapClient);

  List<UserTEO> getUsers(SqlMapClient sqlmapClient);

  void deleteUserById(Integer id, SqlMapClient sqlmapClient);
}
