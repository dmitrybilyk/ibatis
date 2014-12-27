package com.ibatis.scorecardmodel.bo.dashboard;


import com.ibatis.scorecardmodel.BaseBean;

/**
 * Extends widget data to keep older value. Suitable for trend widget
 * @author Stanislav Valenta, 8.1.2010
 */
public class TrendWidgetData<T extends BaseBean> extends WidgetData<T> {
  private static final long serialVersionUID = 8588766391058107089L;
  
  // data old value
  private float olderValue;


  /**
   * Creates new instance based on given params
   * @param name       data name
   * @param value      data value
   * @param object     data related object
   * @param olderValue data old value
   */
  public TrendWidgetData(String name, Float value, T object, float olderValue) {
    super(value, object, name);
    this.olderValue = olderValue;
  }


  /** Java bean convention */
  public TrendWidgetData() {
  }


  /**
   * Data old value getter
   * @return old value
   */
  public float getOlderValue() {
    return olderValue;
  }


  /**
   * Data old value setter
   * @param olderValue old value
   */
  public void setOlderValue(float olderValue) {
    this.olderValue = olderValue;
  }


  /**
   * Counts trend value. Value < 0 = trend decreases. Value == 0 = trend is same. Value > 0 = trend increases.
   * @return trend value
   */
  public float getTrend() {
    return value - olderValue;
  }
}
