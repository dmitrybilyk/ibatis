package com.ibatis.scorecardmodel.bo.charts;

import java.io.Serializable;

/**
 * Interface for charts. Every bean displayed in chart has to implement it
 * @author Stanislav Valenta, 30.10.2009 */
public interface ChartInfo extends Serializable {
  /**
   * Returns info describing bean. Displayed as legend in chart
   * @return string describing bean
   */
  String getInfo();
}
