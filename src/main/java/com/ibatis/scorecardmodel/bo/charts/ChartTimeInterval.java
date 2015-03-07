package com.ibatis.scorecardmodel.bo.charts;

/** Chart X axis segment */
  public enum ChartTimeInterval {
    DAY_VALUES (50), WEEK_VALUES(54), MONTH_VALUES(50), QUARTER_VALUES(40), YEAR_VALUES(10);
  private int limit;
  ChartTimeInterval(int limit){
    this.limit=limit;
  }
  public int getLimit(){
    return limit;
  }
  }