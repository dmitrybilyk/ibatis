package com.ibatis.scorecardmodel.bo.evaluation;

import com.ibatis.scorecardmodel.BaseBean;
import com.ibatis.scorecardmodel.bo.ReplaceableLinkedHashSet;
import com.ibatis.search.SearchOrder;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class WeekdayBean extends BaseBean {
  private static final long serialVersionUID = -456763324826266974L;

  public enum Weekdays {
    // the order of this enum isn't random. Don't change it, please.
    SATURDAY(6), FRIDAY(5), THURSDAY(4), WEDNESDAY(3), TUESDAY(2), MONDAY(1), SUNDAY(0);

    private int javaOrdinal;

    Weekdays(int javaOrdinal) {
      this.javaOrdinal = javaOrdinal;
    }

    public int getJavaOrdinal() {
      return javaOrdinal;
    }
  }

  public String toString(Set<Weekdays> enumSet) {
    StringBuilder buffer = new StringBuilder();
    for (Weekdays weekday: Weekdays.values()) {
      if (enumSet.contains(weekday))
        buffer.append('1');
      else
        buffer.append('0');
    }
    return buffer.toString(); 
  }
  
  public Set<Weekdays> toWeekdays(String encodedString) {
    if (encodedString == null || encodedString.length() != Weekdays.values().length)
      throw new IllegalArgumentException(WeekdayBean.class.getName()+"toWeekdays(): wrong argument"+encodedString);
    Set<Weekdays> result = new ReplaceableLinkedHashSet<Weekdays>();
    int index=0;
    for (Weekdays weekday: Weekdays.values()) {
      char bit = encodedString.charAt(index);
      switch (bit) {
        case '0':
          break;
        case '1':
          result.add(weekday);
          break;
        default:
          throw new IllegalArgumentException(WeekdayBean.class.getName()+"toWeekdays(): wrong argument"+encodedString);
      }
      index++;
    }
    return result;
  }

  @Override
  public BaseBean newInstance() {
    return new WeekdayBean();
  }

  @Override
  public List<SearchOrder> defaultSortOrder() {
    return new ArrayList<SearchOrder>();
  }

}
