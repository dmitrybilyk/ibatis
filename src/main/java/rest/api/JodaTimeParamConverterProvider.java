package rest.api;

import org.joda.time.DateTime;
import org.joda.time.Period;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;
import org.joda.time.format.ISOPeriodFormat;
import org.joda.time.format.PeriodFormatter;

import javax.ws.rs.QueryParam;
import javax.ws.rs.ext.ParamConverter;
import javax.ws.rs.ext.ParamConverterProvider;
import javax.ws.rs.ext.Provider;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

/**
 * Provider to be able to handle Joda DateTime as params, e.g. {@link QueryParam}.
 */
@Provider
public class JodaTimeParamConverterProvider implements ParamConverterProvider {
  @SuppressWarnings("unchecked")
  @Override
  public <T> ParamConverter<T> getConverter(Class<T> type, Type genericType, Annotation[] annotations) {
    if (DateTime.class.equals(type)) {
      return (ParamConverter<T>) new DateTimeParamConverter(
              ISODateTimeFormat.dateOptionalTimeParser().withZoneUTC());
    } else if (Period.class.equals(type)) {
      return (ParamConverter<T>) new PeriodParamConverter();
    } else {
      return null;
    }
  }

  public static class DateTimeParamConverter implements ParamConverter<DateTime> {
    private final DateTimeFormatter dateTimeFormatter;

    public DateTimeParamConverter(DateTimeFormatter dateTimeFormatter) {
      this.dateTimeFormatter = dateTimeFormatter;
    }

    @Override
    public DateTime fromString(String value) {
      return dateTimeFormatter.parseDateTime(value);
    }

    @Override
    public String toString(DateTime value) {
      return value.toString();
    }
  }

  public static class PeriodParamConverter implements ParamConverter<Period> {
    private final PeriodFormatter periodFormatter;

    public PeriodParamConverter() {
      periodFormatter = ISOPeriodFormat.standard();
    }

    @Override
    public Period fromString(String value) {
      return periodFormatter.parsePeriod(value);
    }

    @Override
    public String toString(Period value) {
      return periodFormatter.print(value);
    }
  }
}