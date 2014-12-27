package com.ibatis.scorecardmodel.bo.charts;

import java.util.ArrayList;
import java.util.List;

/** @author Stanislav Valenta, 24.3.2010
 *  Helper keeping double values and counting it's average
 */

public class AvgCounter extends ChartRecord {
  private List<Double> values = new ArrayList<Double>();
  private static final long serialVersionUID = -1852592726416223010L;

  public AvgCounter(double emptyListValue) {
    super(emptyListValue);
  }

  public AvgCounter() {
  }

  public void addValue(Double value) {
    if (value < 0) {
      return;
    }
    values.add(value);
  }

  Double getAvg() {
    // empty list means there is no score for given date
    if (values.isEmpty()) {
      return emptyListValue;
    }

    // otherwise count average
    double tmp = 0;
    for (Double value : values) {
      tmp += value;
    }
    return tmp / values.size();
  }

  @Override
  public Number getValue() {
    return getAvg();
  }

  public List<Double> getValues() {
    return values;
  }

  public void setValues(List<Double> values) {
    this.values = values;
  }
}
