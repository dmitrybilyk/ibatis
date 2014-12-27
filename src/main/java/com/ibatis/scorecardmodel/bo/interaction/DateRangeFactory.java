package com.ibatis.scorecardmodel.bo.interaction;

import com.ibatis.scorecardmodel.bo.evaluation.CriteriaBO;
import org.joda.time.DateTime;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * Author: Stanislav Valenta
 * Date: 14.9.11
 */
public class DateRangeFactory {
  private static final int QUARTER_IN_MONTHS = 3;


  public  Date endDate(DateRange dateRange) {
    if (dateRange.isFixedDate()) {
      return dateRange.getToDate();
    }
    if (dateRange.isCustomPeriod()) {
      return endCustomPeriod(dateRange.getPeriodCount(), dateRange.getPeriod());
    }
    return endDateRange(dateRange.getKey());
  }

  public Date startDate(DateRange dateRange) {
    if (dateRange.isFixedDate()) {
      return dateRange.getFromDate();
    }
    if (dateRange.isCustomPeriod()) {
      return startCustomPeriod(dateRange.getPeriodCount(), dateRange.getPeriod());
    }
    return startDateRange(dateRange.getKey());
  }

  public Date startDateRange(CriteriaBO.TimeInterval key) {
    int offset = Math.abs(key.getOffset());
    DateTime now = DateTime.now();
    switch (key) {
      case YESTERDAY:
      case LAST_3_DAYS:
      case LAST_30_DAYS:
        return now.withTimeAtStartOfDay().minusDays(offset).toDate();
      case LAST_WEEK:
      case LAST_2_WEEKS:
        return now.withTimeAtStartOfDay().withDayOfWeek(1).minusWeeks(offset).toDate();
      case LAST_MONTH:
      case LAST_6_MONTHS:
        return now.withTimeAtStartOfDay().withDayOfMonth(1).minusMonths(offset).toDate();
      case LAST_QUARTER:
        int month = getStartOfQuarter(now);
        return now.withTimeAtStartOfDay().withDayOfMonth(1).withMonthOfYear(month).minusMonths(offset).toDate();
      case LAST_YEAR:
        return now.withTimeAtStartOfDay().withDayOfYear(1).minusYears(offset).toDate();
      default:
        throw new IllegalArgumentException("Unsupported type: " + key);
    }
  }

  private int getStartOfQuarter(DateTime now) {
    int monthOfYear = now.getMonthOfYear();
    return monthOfYear - (monthOfYear % QUARTER_IN_MONTHS);
  }

  private Date startCustomPeriod(Integer periodCount, Period period) {
    DateTime now = DateTime.now();
    switch (period) {
      case DAY:
        return now.minusDays(periodCount).toDate();
      case WEEK:
        return now.withTimeAtStartOfDay().minusWeeks(periodCount).toDate();
      case MONTH:
      case QUARTER:
        return now.withTimeAtStartOfDay().minusMonths(periodCount).toDate();
      case YEAR:
        return now.withTimeAtStartOfDay().minusYears(periodCount).toDate();
      default:
        throw new IllegalArgumentException("Unsupported type: " + period);
    }
  }

  public Date endDateRange(CriteriaBO.TimeInterval key) {
    DateTime startDateTime = new DateTime(startDateRange(key));
    int offset = Math.abs(key.getOffset());
    switch (key) {
      case YESTERDAY:
      case LAST_3_DAYS:
      case LAST_30_DAYS:
        return startDateTime.plusDays(offset).minusSeconds(1).toDate();
      case LAST_WEEK:
      case LAST_2_WEEKS:
        return startDateTime.plusWeeks(offset).minusSeconds(1).toDate();
      case LAST_MONTH:
      case LAST_QUARTER:
      case LAST_6_MONTHS:
        return startDateTime.plusMonths(offset).minusSeconds(1).toDate();
      case LAST_YEAR:
        return startDateTime.plusYears(offset).minusSeconds(1).toDate();
      default:
        throw new IllegalArgumentException("Unsupported type: " + key);
    }
  }

  private Date endCustomPeriod(Integer periodCount, Period period) {
    return new Date();
  }

}
