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
public class GetPersonsById {
    static PersonDao manager = new PersonDaoIbatis();
    public static void main(String[] args) {
        SqlMapClient sqlMapClient = SqlMapClientFactory.getClientInstance();
        List<PersonIdHolder> personIdHolderList = new ArrayList<PersonIdHolder>();
        PersonIdHolder personIdHolder = new PersonIdHolder();
        personIdHolder.setPersonId(17);
        PersonIdHolder personIdHolder2 = new PersonIdHolder();
        personIdHolder2.setPersonId(18);
        personIdHolderList.add(personIdHolder);
        personIdHolderList.add(personIdHolder2);
        List<Person> persons = manager.<List<BaseBean>>getPersonsByIds(sqlMapClient, personIdHolderList);

        View personsView = new ViewPersonImpl();
        personsView.viewEntity(persons);

    }
}
