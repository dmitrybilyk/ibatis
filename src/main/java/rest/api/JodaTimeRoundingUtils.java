package rest.api;

import com.google.common.collect.ImmutableList;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joda.time.DateTime;
import org.joda.time.DateTimeFieldType;
import org.joda.time.Interval;
import org.joda.time.Period;

import java.util.List;

import static org.joda.time.DateTimeFieldType.*;

public class JodaTimeRoundingUtils {
  /**
   * Fields by which dates can be truncated. Should be order by precedence (i.e., descending).
   *
   * @see #truncateInterval(org.joda.time.Interval, org.joda.time.Period)
   */
  private List<DateTimeFieldType> truncateFieldTypeList = ImmutableList.of(
          year(), monthOfYear(), weekOfWeekyear(), dayOfMonth(),
          hourOfDay(), minuteOfHour(), secondOfMinute(), millisOfSecond());

  /**
   * Truncates interval to specified period.
   * The result should be a new interval that fully contains specified interval,
   * but also starts and ends at a whole greatest field in periodicity.
   * E.g., if greatest field in periodicity is year,
   * the resulting interval starts at a whole year and ends at a whole year.
   * <p/>
   * This doesn't guarantee that the interval will contain integer number of periods.
   * For example, the number of periods in the interval won't be an integer,
   * if the period contains 5 months or 7 hours, or multiple fields like 1 month 1 week.
   *
   * @param interval    the interval to truncate start date down and end date up
   * @param periodicity the periodicity with the greatest field to truncate interval upon
   * @return an interval larger or equal to original interval, with start & end truncated to the highest periodicity field;
   * @see org.joda.time.Interval#contains(org.joda.time.ReadableInterval)
   */
  @NotNull
  public Interval truncateInterval(@NotNull Interval interval, @NotNull Period periodicity) {
    DateTimeFieldType fieldProperty = getTruncateField(periodicity);
    if (fieldProperty == null) {
      return interval;
    }
    int fieldValue = periodicity.get(fieldProperty.getDurationType());
    return new Interval(
            roundDownToField(interval.getStart(), fieldProperty, fieldValue),
            roundUpToField(interval.getEnd(), fieldProperty, fieldValue));
  }

  @Nullable
  private DateTimeFieldType getTruncateField(@NotNull Period periodicity) {
    for (DateTimeFieldType truncateField : truncateFieldTypeList) {
      if (periodicity.get(truncateField.getDurationType()) != 0) {
        return truncateField;
      }
    }
    return null;
  }

  /**
   * Round down the timestamp to the specified field. E.g., if the field is hoursOfDay and value interval is 8,
   * the timestamp will be rounded down to hours (minutes, seconds, milliseconds removed)
   * and then hours will be rounded down to 8-hour intervals, leaving the only possible values of 0, 8, 16.
   * A more specific example is 2013-01-12T12:48:51.341 rounded down to 2013-01-12T08:00:00.000.
   * <p/>
   * Fields that start from 1 instead of 0 (such as months) should round to 1-based intervals.
   * E.g., 4 months start at month 1 (January) and end at month 4 (April).
   *
   * @param dateTime      the timestamp to round down
   * @param field         the lowest field allowed to keep non-zero value
   * @param valueInterval the size of field value intervals to which the field should be rounded down
   * @return a new dateTime rounded down to specified field value
   */
  public DateTime roundDownToField(DateTime dateTime, DateTimeFieldType field, int valueInterval) {
    DateTime rounded = dateTime.property(field).roundFloorCopy();// round down smaller fields
    if (valueInterval > 1) {
      DateTime.Property property = rounded.property(field);
      int remainder = (property.get() - property.getMinimumValue()) % valueInterval;
      return property.addToCopy(-remainder); // remove remainder
    }
    return rounded;
  }

  /**
   * Round up the timestamp to the specified field. E.g., if the field is hoursOfDay and value interval is 8,
   * the timestamp will be rounded up to hours (m, s, ms removed; hours increased by 1 if removed part is non-zero)
   * and then hours will be rounded up to 8-hour intervals, leaving the only possible values of 8, 16, 0 (+1 day).
   * According to this example with 8 hours,
   * 2013-01-12T12:48:51.341 is rounded up to 2013-01-12T16:00:00.000,
   * and 2013-01-12T21:48:51.341 is rounded up to 2013-01-13T00:00:00.000.
   * <p/>
   * Fields that start from 1 instead of 0 (such as months) should round to 1-based intervals.
   * E.g., 4 months start at month 1 (January) and end at month 4 (April).
   *
   * @param dateTime      the timestamp to round up
   * @param field         the lowest field allowed to keep non-zero value
   * @param valueInterval the size of field value intervals to which the field should be rounded up
   * @return a new dateTime rounded up to specified field value
   */
  public DateTime roundUpToField(DateTime dateTime, DateTimeFieldType field, int valueInterval) {
    DateTime rounded = dateTime.property(field).roundCeilingCopy();// round up smaller fields
    if (valueInterval > 1) {
      DateTime.Property property = rounded.property(field);
      int remainder = (property.get() - property.getMinimumValue()) % valueInterval;
      if (remainder != 0) {
        return property.addToCopy(valueInterval - remainder); // round up to next fieldValue
      }
    }
    return rounded;
  }
}
