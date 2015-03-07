package com.ibatis.dao;

import com.ibatis.ComplexParam;
import com.ibatis.search.RequestFailedException;
import com.ibatis.search.SearchBO;
import com.ibatis.search.SearchCondition;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.learn.model.Person;
import com.learn.model.PersonIdHolder;

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
  public List<Person> getPersons(SqlMapClient sqlmapClient) {
    try {
      Map<String, Object> map = new HashMap<String, Object>();
      ComplexParam complexParam = new ComplexParam();
      complexParam.setComplexValue("alias");
      map.put("key", "alias");
      List<Person> persons = (List<Person>) sqlmapClient.queryForList("person.getPersons", map);
      return persons != null? persons : new ArrayList<Person>();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return null;
  }

  @Override
  public List<Person> getPersonsByIds(SqlMapClient sqlmapClient, List<PersonIdHolder> personIdHolderList) {
    List<Person> persons = null;
    try {
      Map<String, Object> map = new HashMap<String, Object>();
      map.put("personIdHolderList", personIdHolderList);
      persons = (List<Person>) sqlmapClient.queryForList("getPersonsByIds", map);
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return persons != null? persons : new ArrayList<Person>();
  }

  @Override
    public List<Person> getAllPersons(SqlMapClient sqlmapClient) {
        try {
            List<Person> persons = null;
            try {
                persons = (List<Person>) sqlmapClient.queryForList("getAllPersons", createParams());
            } catch (RequestFailedException e) {
                e.printStackTrace();
            }
            return persons != null? persons : new ArrayList<Person>();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

  @Override
  public List<Person> getPersonsByConditions(SqlMapClient sqlmapClient) {
    try {
      List<Person> persons = null;
        Map<String, Object> map = new Hashtable<String, Object>();
        SearchBO searchBO = new SearchBO();
        searchBO.addCondition(new SearchCondition(Person.Fields.PER_FIRST_NAME, "Dima6"));
        map.put("searchBO", searchBO);
        persons = (List<Person>) sqlmapClient.queryForList("getPersonsByConditions", map);
      return persons != null? persons : new ArrayList<Person>();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return null;
  }


  @Override
  public Integer getAllPersonsCount(SqlMapClient sqlmapClient) {
    try {
      Integer personsCount = null;
        personsCount = (Integer) sqlmapClient.queryForObject("getAllPersonsCount");

      return personsCount;
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return null;
  }

    @Override
    public List<Person> getAllPersonsFromUsers(SqlMapClient sqlmapClient) {
        try {
            List<Person> persons = null;
            try {
                persons = (List<Person>) sqlmapClient.queryForList("getAllPersonsFromUsers", createParams());
            } catch (RequestFailedException e) {
                e.printStackTrace();
            }
            return persons != null? persons : new ArrayList<Person>();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    private Map<String,Object> createParams() throws RequestFailedException {
        Map<String,Object> parametersMap = new HashMap<String, Object>();
        Integer[] addressIds = {2, 3};
        parametersMap.put("allowedAddressIds", addressIds);
        return parametersMap;
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