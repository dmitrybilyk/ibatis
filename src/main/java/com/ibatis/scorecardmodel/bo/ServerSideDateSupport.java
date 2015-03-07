package com.ibatis.scorecardmodel.bo;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Zdary
 * Date: 04/06/13
 * Time: 11:58
 * To change this template use File | Settings | File Templates.
 */
public class ServerSideDateSupport {

    public static ServerSideDate createServerSideDate(Date date) {
        return date == null ? null : new ServerSideDate(date);
    }
}
