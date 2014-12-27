package com.ibatis.scorecardmodel.bo;

import com.ibatis.sqlmap.client.extensions.ParameterSetter;
import com.ibatis.sqlmap.client.extensions.ResultGetter;
import com.ibatis.sqlmap.client.extensions.TypeHandlerCallback;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;

/**
 * Created with IntelliJ IDEA.
 * User: Zdary
 * Date: 24/06/13
 * Time: 09:52
 * To change this template use File | Settings | File Templates.
 */
public class ServerSideDateHandler implements TypeHandlerCallback {

    @Override
    public void setParameter(ParameterSetter setter, Object parameter) throws SQLException {
        java.util.Date date = (java.util.Date) parameter;
        if ( date == null )
            setter.setNull(Types.TIMESTAMP);
        else
        {
            Timestamp timestamp = new Timestamp(date.getTime());
            setter.setTimestamp(timestamp);
        }
    }

    @Override
    public Object getResult(ResultGetter getter) throws SQLException {
        Timestamp timestamp = getter.getTimestamp();
        if (timestamp == null) {
            return null;
        }
        ServerSideDate serverSideDate = new ServerSideDate(timestamp.getTime());
        return serverSideDate;
    }

    @Override
    public Object valueOf(String datetime) {
        throw new UnsupportedOperationException("ServerSideDateHandler.valueOf() is not supported. " + datetime);
    }
}
