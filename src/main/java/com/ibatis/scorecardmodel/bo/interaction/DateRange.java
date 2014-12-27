package com.ibatis.scorecardmodel.bo.interaction;

import com.ibatis.scorecardmodel.bo.evaluation.CriteriaBO;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * Author: Stanislav Valenta
 * Date: 8.9.11
 */
public class DateRange implements Serializable {
  private CriteriaBO.TimeInterval key;
  private Date fromDate;
  private Date toDate;
  private Integer periodCount;
  private Period period;
  private boolean isDefault = false;

  public DateRange(CriteriaBO.TimeInterval key) {
    this.key = key;
  }

  DateRange() {
  }

  public Date getFromDate() {
    return fromDate;
  }

  public void setFromDate(Date fromDate) {
    this.fromDate = fromDate;
  }

  public CriteriaBO.TimeInterval getKey() {
    return key;
  }

  public void setKey(CriteriaBO.TimeInterval key) {
    this.key = key;
  }

  public Date getToDate() {
    return toDate;
  }

  public void setToDate(Date toDate) {
    this.toDate = toDate;
  }

  public Period getPeriod() {
    return period;
  }

  public void setPeriod(Period period) {
    this.period = period;
  }

  public Integer getPeriodCount() {
    return periodCount;
  }

  public void setPeriodCount(Integer periodCount) {
    this.periodCount = periodCount;
  }

  public boolean isCustomPeriod() {
    return CriteriaBO.TimeInterval.CUSTOM_PERIOD.equals(getKey());
  }

  public boolean isFixedDate() {
    return CriteriaBO.TimeInterval.FIXED_DATE.equals(getKey());
  }

  public boolean isDefault() {
    return isDefault;
  }

  public void setIsDefault(boolean isDefault) {
    this.isDefault = isDefault;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    DateRange dateRange = (DateRange) o;

    if (fromDate != null ? !fromDate.equals(dateRange.fromDate) : dateRange.fromDate != null) return false;
    if (key != dateRange.key) return false;
    if (period != dateRange.period) return false;
    if (periodCount != null ? !periodCount.equals(dateRange.periodCount) : dateRange.periodCount != null) return false;
    if (toDate != null ? !toDate.equals(dateRange.toDate) : dateRange.toDate != null) return false;

    return true;
  }

  @Override
  public int hashCode() {
    int result = key.hashCode();
    result = 31 * result + (fromDate != null ? fromDate.hashCode() : 0);
    result = 31 * result + (toDate != null ? toDate.hashCode() : 0);
    result = 31 * result + (periodCount != null ? periodCount.hashCode() : 0);
    result = 31 * result + (period != null ? period.hashCode() : 0);
    return result;
  }
}
