package com.ibatis.scorecardmodel.bo.interaction;

import java.math.BigDecimal;
import java.util.Date;

public class StatisticPOJO<T> {
  private T beanId;
  private Date timeSlot;
  private BigDecimal value;

  public StatisticPOJO() {}

  public StatisticPOJO(T beanId, Date timeSlot, BigDecimal value) {
    this.beanId = beanId;
    this.timeSlot = timeSlot;
    this.value = value;
  }

  public T getBeanId() {
    return beanId;
  }

  public void setBeanId(T beanId) {
    this.beanId = beanId;
  }

  public Date getTimeSlot() {
    return timeSlot;
  }

  public void setTimeSlot(Date timeSlot) {
    this.timeSlot = timeSlot;
  }

  public BigDecimal getValue() {
    return value;
  }

  public void setValue(BigDecimal value) {
    this.value = value;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }

    if (!(o instanceof StatisticPOJO)) {
      return false;
    }
    StatisticPOJO that = (StatisticPOJO) o;
    return (beanId == null ? that.beanId == null : beanId.equals(that.beanId))
            && (timeSlot == null ? that.timeSlot == null : timeSlot.equals(that.timeSlot))
            // equals will return false when comparing values with different scales (e.g. 25 and 25.00)
            && (value == null ? that.value == null : value.compareTo(that.value) == 0);

  }

  @Override
  public int hashCode() {
    int result = beanId != null ? beanId.hashCode() : 0;
    result = 31 * result + (timeSlot != null ? timeSlot.hashCode() : 0);
    result = 31 * result + (value != null ? value.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "StatisticPOJO{beanId=" + beanId + ", timeSlot=" + timeSlot + ", value=" + value + "}";
  }
}
