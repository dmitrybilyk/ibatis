package com.ibatis.dao;

import com.ibatis.ComplexParam;
import com.ibatis.UserTEO;
import com.ibatis.dao.PersonDao;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.learn.model.Person;

import java.sql.SQLException;
import java.util.*;

public class PersonDaoIbatis implements PersonDao
{
    @Override
    public Person addPerson(Person person, SqlMapClient sqlmapClient) {
        try
        {
            Integer id = (Integer)sqlmapClient.queryForObject("getMaxId");
            id = id == null ? 1 : id + 1;
            person.setId(id);
            sqlmapClient.insert("insertPerson", person);
            return person;
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }
 
    @Override
    public Person getUserById(Integer id, SqlMapClient sqlmapClient) {
        try
        {
            Person user = (Person)sqlmapClient.queryForObject("getPerson", id);
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