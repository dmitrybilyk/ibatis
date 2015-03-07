package com.ibatis.dao;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.learn.model.Address;
import com.learn.model.Person;

/**
 * Created by dmitry on 24.12.14.
 */
public interface AddressDao {
    void addAddress(Address address, SqlMapClient sqlmapClient);
}
