package com.ibatis.fillPersonTable;

import com.ibatis.config.SqlMapClientFactory;
import com.ibatis.config.SqlMapVMClientFactory;
import com.ibatis.dao.PersonDao;
import com.ibatis.dao.PersonDaoIbatis;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.view.View;
import com.ibatis.view.ViewPersonImpl;
import com.learn.model.BaseBean;
import com.learn.model.Person;

import java.util.List;

/**
 * Created by dmitry on 27.12.14.
 */
public class GetPersonsFromUsers {
    static PersonDao manager = new PersonDaoIbatis(SqlMapClientFactory.getClientInstance());
    public static void main(String[] args) {
        List<Person> persons = manager.<List<BaseBean>>getAllPersonsFromUsers();

        View personsView = new ViewPersonImpl();
        personsView.viewEntity(persons);

    }
}
