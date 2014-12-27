package com.ibatis.scorecardmodel.bo.interaction;

import com.ibatis.scorecardmodel.bo.evaluation.WeekdayBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

/**
* Created by IntelliJ IDEA.
* Author: Stanislav Valenta
* Date: 31.8.11
*/
public class HoursRange implements Serializable {
  private static final int MINUTE_IN_SECONDS = 60;
  private static final int HOUR_IN_SECONDS = MINUTE_IN_SECONDS * 60;


  public enum Key {
    EVERY_DAY, EVERY_DAY_BUSINESS_HOURS, EVERY_DAY_BETWEEN, BUSINESS_DAYS, BUSINESS_DAYS_BUSINESS_HOURS,
    BUSINESS_DAYS_BETWEEN, SELECTED_DAYS, SELECTED_DAYS_BUSINESS_HOURS, SELECTED_DAYS_BETWEEN
  }

  public HoursRange() {
    // default empty constructor. needed by CustomHoursRangePanel
  }

  public HoursRange(Key key, int startHour, int startMinute, int endHour, int endMinute, WeekdayBean.Weekdays... weekdays) {
    this.startMinute = startMinute;
    this.endMinute = endMinute;
    this.weekdays = Arrays.asList(weekdays);
    this.startHour = startHour;
    this.endHour = endHour;
    this.key = key;
  }

  private Collection<WeekdayBean.Weekdays> weekdays;
  private int startHour;
  private int startMinute;
  private int endHour;
  private int endMinute;
  private Key key;

  public void setEndHour(int endHour) {
    this.endHour = endHour;
  }

  public void setEndMinute(int endMinute) {
    this.endMinute = endMinute;
  }

  public void setKey(Key key) {
    this.key = key;
  }

  public void setStartHour(int startHour) {
    this.startHour = startHour;
  }

  public void setStartMinute(int startMinute) {
    this.startMinute = startMinute;
  }

  public Collection<WeekdayBean.Weekdays> getWeekdays() {
    return weekdays == null ? Collections.<WeekdayBean.Weekdays>emptyList() : weekdays;
  }

  public Collection<Integer> getWeekdayNumbers() {
    Collection<Integer> weekdayNumbers = new ArrayList<Integer>();
    for (WeekdayBean.Weekdays weekday : getWeekdays()) {
      weekdayNumbers.add(weekday.getJavaOrdinal());
    }
    return weekdayNumbers;
  }

  public int getEndHour() {
    return endHour;
  }

  public int getStartHour() {
    return startHour;
  }

  public int getEndMinute() {
    return endMinute;
  }

  public int getStartMinute() {
    return startMinute;
  }

  public int countStartSeconds() {
    return startHour * HOUR_IN_SECONDS + startMinute * MINUTE_IN_SECONDS;
  }

  public int countEndSeconds() {
    return endHour * HOUR_IN_SECONDS + endMinute * MINUTE_IN_SECONDS;
  }

  public int getMaxSecondsInDay(){
    return 24 * HOUR_IN_SECONDS;
  }

  public Key getKey() {
    return key;
  }

  public boolean isCustomDays() {
    Key key = getKey();
    return Key.SELECTED_DAYS.equals(key) || Key.SELECTED_DAYS_BETWEEN.equals(key) || Key.SELECTED_DAYS_BUSINESS_HOURS.equals(key);
  }

  public boolean isCustomHours() {
    Key key = getKey();
    return Key.SELECTED_DAYS_BETWEEN.equals(key) || Key.BUSINESS_DAYS_BETWEEN.equals(key) || Key.EVERY_DAY_BETWEEN.equals(key);
  }

  public boolean isBusinessHours() {
    Key key = getKey();
    return Key.BUSINESS_DAYS_BUSINESS_HOURS.equals(key) || Key.EVERY_DAY_BUSINESS_HOURS.equals(key) || Key.SELECTED_DAYS_BUSINESS_HOURS.equals(key);
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    HoursRange that = (HoursRange) o;

    if (endHour != that.endHour) return false;
    if (endMinute != that.endMinute) return false;
    if (startHour != that.startHour) return false;
    if (startMinute != that.startMinute) return false;
    if (key != that.key) return false;
    if (weekdays != null ? !weekdays.equals(that.weekdays) : that.weekdays != null) return false;

    return true;
  }

  @Override
  public int hashCode() {
    int result = weekdays != null ? weekdays.hashCode() : 0;
    result = 31 * result + startHour;
    result = 31 * result + startMinute;
    result = 31 * result + endHour;
    result = 31 * result + endMinute;
    result = 31 * result + key.hashCode();
    return result;
  }
}
