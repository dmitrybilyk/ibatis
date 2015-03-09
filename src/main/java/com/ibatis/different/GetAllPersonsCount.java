package com.ibatis.different;

import com.ibatis.config.SqlMapClientFactory;
import com.ibatis.dao.PersonDao;
import com.ibatis.dao.PersonDaoIbatis;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.view.View;
import com.ibatis.view.ViewPersonImpl;
import com.learn.model.BaseBean;
import com.learn.model.Person;

import java.util.Date;
import java.util.List;

/**
 * Created by dmitry on 27.12.14.
 */
public class GetAllPersonsCount {
    static PersonDao manager = new PersonDaoIbatis(SqlMapClientFactory.getClientInstance());
    public static void main(String[] args) {
        Integer personsCount = manager.<Integer>getAllPersonsCount();
        System.out.println(personsCount);
        manager.addPerson(new Person.PersonBuilder(0, "Dima", "Bilyk", new Date()).build());
        Integer personsCount2 = manager.<Integer>getAllPersonsCount();
        System.out.println(personsCount2);

    }
}
