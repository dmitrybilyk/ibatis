package com.ibatis.scorecardmodel.bo.charts;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

/**
 * Simple bean keeping record for chart. Value represents yAxis value. stringLabel, dateLabel and numberLabel are xAxis
 * labels. Unable to use generics because of f?????g GWT compiler.
 * @author Stanislav Valenta, 6.11.2009
 */
public class ChartRecord implements Serializable {
  private static final long serialVersionUID = -4066850256357122383L;
  private String xAxisLabel = "";
  private Date date;
  private Collection<Integer> evaluationIds;

  protected double emptyListValue = -1.0;

  public ChartRecord() {
  }

  public ChartRecord(double emptyListValue) {
    this.emptyListValue = emptyListValue;
  }

  // in case we need a special format of a date which it isn't possible to construct in GWT
  private String sDate = "";
  
  private Number value;


  public Number getValue() {
    return value;
  }

  /** Same functionality as setValue, because ChartRecord does not contain more values!
   *
   * @param value- value which is set to class value.
   */
  public void addValue(Double value) {
    this.value = value;
  }

  public void setValue(Number value) {
    this.value = value;
  }


  public String getXAxisLabel() {
    return xAxisLabel;
  }


  public void setXAxisLabel(String xAxisLabel) {
    this.xAxisLabel = xAxisLabel;
  }


  public Date getDate() {
    return date;
  }


  public void setDate(Date date) {
    this.date = date;
  }


  public void setStringDate(String stringDate) {
    this.sDate = stringDate;
  }


  public String getStringDate() {
    return sDate;
  }

  public Collection<Integer> getEvaluationIds() {
    return evaluationIds;
  }

  public void setEvaluationIds(Collection<Integer> evaluationIds) {
    this.evaluationIds = evaluationIds;
  }
}
