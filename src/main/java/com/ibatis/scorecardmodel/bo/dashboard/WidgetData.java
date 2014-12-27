package com.ibatis.scorecardmodel.bo.dashboard;



import com.ibatis.scorecardmodel.BaseBean;

import java.io.Serializable;


/**
 * Represents widget data. On it's base are widgets displayed
 * @author Stanislav Valenta, 8.1.2010
 */
public class WidgetData<T extends BaseBean> implements Serializable {
  private static final long serialVersionUID = -4692943872877210836L;
  
  // name
  private String name;
  // value
  protected Float value;
  // object related to data
  private T object;


  /**
   * Creates new instance based on given params
   * @param value  data value
   * @param object object related to data
   * @param name   data name
   */
  public WidgetData(Float value, T object, String name) {
    this.value = value;
    this.object = object;
    this.name = name;
  }


  /** Java beans convention */
  public WidgetData() {
  }


  /**
   * Returns object related to data
   * @return object related to data
   */
  public T getObject() {
    return object;
  }


  /**
   * Sets object related to data
   * @param object object related to data
   */
  public void setObject(T object) {
    this.object = object;
  }


  /**
   * Returns data value
   * @return data value
   */
  public float getValue() {
    return value;
  }


  /**
   * Sets data value
   * @param value data value
   */
  public void setValue(Float value) {
    this.value = value;
  }


  /**
   * Returns data name
   * @return data name
   */
  public String getName() {
    return name;
  }


  /**
   * Sets data name
   * @param name data name
   */
  public void setName(String name) {
    this.name = name;
  }
}
