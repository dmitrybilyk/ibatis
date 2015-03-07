package com.ibatis.scorecardmodel.bo;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: Zdary
 * Date: 20/05/13
 * Time: 13:07
 * To change this template use File | Settings | File Templates.
 */
public class ServerSideDate extends Date {
  public ServerSideDate() {}

  public ServerSideDate(int year, int month, int date, int hrs, int min, int sec) {
    super(year, month, date, hrs, min, sec);
  }


  public ServerSideDate(Date evaluationDate) {
    this(evaluationDate.getTime());
  }

  public ServerSideDate(long time) {
    super(time);
  }
}
