package com.ibatis.different;

import com.ibatis.config.SqlMapClientFactory;
import com.ibatis.dao.PersonDao;
import com.ibatis.dao.PersonDaoIbatis;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.view.View;
import com.ibatis.view.ViewPersonImpl;
import com.learn.model.BaseBean;
import com.learn.model.Person;
import com.learn.model.PersonIdHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dmitry on 27.12.14.
 */
public class GetAllPersons {
    static PersonDao manager = new PersonDaoIbatis();
    public static void main(String[] args) {
        SqlMapClient sqlMapClient = SqlMapClientFactory.getClientInstance();
        List<Person> persons = manager.<List<BaseBean>>getAllPersons(sqlMapClient);

        View personsView = new ViewPersonImpl();
        personsView.viewEntity(persons);

    }
}
