package com.ibatis;

/**
 * Created by dik81 on 11/27/14.
 */

import java.io.Reader;
import java.util.List;

import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;


public class IbatisTest {
  public static void main(String[] args) throws Exception
  {
    //Initialize dao
    UserDao manager = new UserDaoIbatis();

    //Create the SQLMapClient
    Reader reader = Resources.getResourceAsReader("sql-maps-config.xml");
    SqlMapClient sqlmapClient = SqlMapClientBuilder.buildSqlMapClient (reader);

//    //Create a new user to persist
//    UserTEO user = new UserTEO();
//    user.setId(3);
//    user.setName("Demo User3");
//    user.setPassword("password3");
//    user.setEmail("demo-user@howtodoinjava.com3");
//    user.setStatus(2);

//    manager.addUser(user2,sqlmapClient);

    //Fetch the user detail


    UserTEO createdUser = manager.getUserById(1, sqlmapClient);
    System.out.println(createdUser.getEmail());

    List<UserTEO> allUsers = manager.getUsers(sqlmapClient);

    for (UserTEO selectedUser : allUsers) {
      System.out.println(selectedUser.getName());
    }

    //Lets delete the user
//    manager.deleteUserById(1, sqlmapClient);
  }
}
