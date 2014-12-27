package com.ibatis;

import com.ibatis.sqlmap.client.SqlMapClient;

import java.util.List;

/**
 * Created by dik81 on 11/27/14.
 */
public interface UserDao {
  UserTEO addUser(UserTEO user, SqlMapClient sqlmapClient);

  UserTEO getUserById(Integer id, SqlMapClient sqlmapClient);

  List<UserTEO> getUsers(SqlMapClient sqlmapClient);

  void deleteUserById(Integer id, SqlMapClient sqlmapClient);
}
