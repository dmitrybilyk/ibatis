package com.ibatis.dao;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.learn.model.Address;
import com.learn.model.Person;

/**
 * Created by dmitry on 24.12.14.
 */
public class AddressDaoImpl implements AddressDao {
    @Override
    public void addAddress(Address address, SqlMapClient sqlmapClient) {
        try
        {
            Integer id = (Integer)sqlmapClient.queryForObject("getMaxAddressId");
            id = id == null ? 1 : id + 1;
            address.setId(id);
            sqlmapClient.insert("insertAddress", address);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
