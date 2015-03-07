package com.ibatis.scorecardmodel.bo.dashboard;




import com.ibatis.scorecardmodel.BaseBean;
import com.ibatis.search.SearchOrder;

import java.util.ArrayList;
import java.util.List;

/**
 * Can be used as "data related object" in <code>WidgetData</code>, if there is no other suitable object.
 * Keeps enum describing data state
 * @author Stanislav Valenta, 28.1.2010
 */
public class WidgetDataDescription<T extends Enum<?>> extends BaseBean {
  private static final long serialVersionUID = 799049379666935802L;


  /** Trashold data states */
  public enum Trashold {
    BAD, AVERAGE, GOOD
  }

  /** Trend data state */
  public enum TimeInterval {
    WEEK, MONTH, YEAR
  }

  // Description value
  private T value;


  /**
   * Returns description enum value
   * @return description enum value
   */
  public T getValue() {
    return value;
  }


  /**
   * Sets description enum value
   * @param value description enum value
   */
  public void setValue(T value) {
    this.value = value;
  }

  @Override
  public List<SearchOrder> defaultSortOrder() {
    return new ArrayList<SearchOrder>();
  }
}
