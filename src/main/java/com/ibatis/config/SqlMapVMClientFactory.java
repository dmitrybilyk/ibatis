package com.ibatis.config;

import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;

import java.io.IOException;
import java.io.Reader;

/**
 * Created by dmitry on 27.12.14.
 */
public class SqlMapVMClientFactory {
    private static SqlMapClient sqlMapClient;

    public static SqlMapClient getClientInstance(){
        if (sqlMapClient == null) {
            return getClient();
        } else {
            return sqlMapClient;
        }
    }

    public static  SqlMapClient getClient() {
        //Create the SQLMapClient
        Reader reader = null;
        try {
            reader = Resources.getResourceAsReader("vm-sql-map-client.xml");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return SqlMapClientBuilder.buildSqlMapClient(reader);
    }
}
