package com.ibatis.fillPersonTable;

import com.ibatis.common.resources.Resources;
import com.ibatis.dao.AddressDao;
import com.ibatis.dao.AddressDaoImpl;
import com.ibatis.dao.PersonDao;
import com.ibatis.dao.PersonDaoIbatis;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;
import com.learn.model.Address;
import com.learn.model.Person;

import java.io.Reader;
import java.util.Date;

/**
 * Created by dmitry on 24.12.14.
 */
public class FillTables {
    public static void main(String[] args) throws Exception
    {
        //Initialize dao
        PersonDao manager = new PersonDaoIbatis();

        //Create the SQLMapClient
        Reader reader = Resources.getResourceAsReader("sqlmapclientPerson.xml");
        SqlMapClient sqlmapClient = SqlMapClientBuilder.buildSqlMapClient (reader);

        for (int i = 0; i < 10; i++) {
            manager.addPerson(new Person.PersonBuilder(i, "Dima"+i, "Bilyk"+i+2, new Date()).build(),sqlmapClient);
        }


        Address address = new Address();
        address.setId(1);
        address.setNumber(11);
        address.setStreet("Matrosova");

        Address address2 = new Address();
        address2.setId(2);
        address2.setNumber(12);
        address2.setStreet("Karpinskogo");

        Address address3 = new Address();
        address3.setId(3);
        address3.setNumber(14);
        address3.setStreet("Dizelnaya");

        AddressDao addressManager = new AddressDaoImpl();
        addressManager.addAddress(address, sqlmapClient);
        addressManager.addAddress(address2, sqlmapClient);
        addressManager.addAddress(address3, sqlmapClient);
//        Reader addressReader = Resources.getResourceAsReader("sqlmapclientPerson.xml");
//        SqlMapClient addressSqlmapClient = SqlMapClientBuilder.buildSqlMapClient (reader);

    }
}
