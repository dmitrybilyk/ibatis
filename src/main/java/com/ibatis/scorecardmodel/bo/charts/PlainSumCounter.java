package com.ibatis.scorecardmodel.bo.charts;

import java.util.ArrayList;
import java.util.List;

/**Just sums the list of numbers.
 * If there is no number, -1 is returned
 *
 */
public class PlainSumCounter extends ChartRecord {
  private List<Double> values = new ArrayList<Double>();


  public void addValue(Double value) {
    if (value < 0) {
      return;
    }
    values.add(value);
  }

  Double getSum() {
    // empty list means there is no score for given date
    if (values.isEmpty()) {
      return emptyListValue;
    }

    // otherwise count average
    double tmp = 0;
    for (Double value : values) {
      tmp += value;
    }
    return tmp;
  }

  @Override
  public Number getValue() {
    return getSum();
  }

  public List<Double> getValues() {
    return values;
  }

  public void setValues(List<Double> values) {
    this.values = values;
  }

}
