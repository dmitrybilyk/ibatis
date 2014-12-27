package com.ibatis;

import com.ibatis.sqlmap.client.SqlMapClient;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserDaoIbatis implements UserDao
{
    @Override
    public UserTEO addUser(UserTEO user, SqlMapClient sqlmapClient) {
        try
        {
            Integer id = (Integer)sqlmapClient.queryForObject("user.getMaxId");
            id = id == null ? 1 : id + 1;
            user.setId(id);
            user.setStatus(1);
            sqlmapClient.insert("user.addUser", user);
            user = getUserById(id, sqlmapClient);
            return user;
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }
 
    @Override
    public UserTEO getUserById(Integer id, SqlMapClient sqlmapClient) {
        try
        {
            UserTEO user = (UserTEO)sqlmapClient.queryForObject("user.getUserById", id);
            return user;
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

  @Override
  public List<UserTEO> getUsers(SqlMapClient sqlmapClient) {
    try {
      Map<String, Object> map = new HashMap<String, Object>();
      ComplexParam complexParam = new ComplexParam();
      complexParam.setComplexValue("alias");
      map.put("key", "alias");
      List<UserTEO> userTEOs = (List<UserTEO>) sqlmapClient.queryForList("user.getUsers", map);
      return userTEOs != null? userTEOs : new ArrayList<UserTEO>();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return null;
  }

  @Override
    public void deleteUserById(Integer id, SqlMapClient sqlmapClient) {
        try
        {
            sqlmapClient.delete("user.deleteUserById", id);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}