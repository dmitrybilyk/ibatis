package com.ibatis.scorecardmodel.bo.dashboard;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Bean keeping dashboard configuration info
 * @author Stanislav Valenta, 13.1.2010
 */
public class DashboardConfig implements Serializable {
  private static final long serialVersionUID = 3472675121484708591L;
  
  // indicates dashboard is enabled
  private boolean enabled = true;
  // widget column's count
  private int columnCount = 3;
  // widgets config that belongs to dashboard
  private List<WidgetConfig> widgetConfigList = new ArrayList<WidgetConfig>();


  /**
   * Creates new instance based on passed params
   * @param columnCount      count of widget't columns
   * @param widgetConfigList list of widgets config
   */
  public DashboardConfig(int columnCount, List<WidgetConfig> widgetConfigList) {
    this.columnCount = columnCount;
    this.widgetConfigList = widgetConfigList;
  }


  /**
   * Java bean convention
   */
  public DashboardConfig() {
  }


  /**
   * Columns count getter
   * @return columns count
   */
  public int getColumnCount() {
    return columnCount;
  }


  /**
   * Columns count setter
   * @param columnCount column's count
   */
  public void setColumnCount(int columnCount) {
    this.columnCount = columnCount;
  }


  /**
   * Adds widget config to dashboard config
   * @param config added widget config
   */
  public void addWidgetConfig(WidgetConfig... config) {
    widgetConfigList.addAll(Arrays.asList(config));
  }


  /**
   * Removes widget config from dashboard config
   * @param config removed config
   */
  public void removeWidgetConfig(WidgetConfig... config) {
    widgetConfigList.removeAll(Arrays.asList(config));
  }


  /**
   * Returns list of widget configs belongs to this dashboard config
   * @return list of widget configs belongs to this dashboard config
   */
  public List<WidgetConfig> getWidgetConfigList() {
    return widgetConfigList;
  }


  /**
   * Widget's config list setter
   * @param widgetConfigList widget config list is being set
   */
  public void setWidgetConfigList(List<WidgetConfig> widgetConfigList) {
    this.widgetConfigList = widgetConfigList;
  }


  /**
   * Returns flag this dashboard is enabled
   * @return flag this dashboard is enabled
   */
  public boolean isEnabled() {
    return enabled;
  }


  /**
   * Sets flag this dashboard is enabled
   * @param enabled true = enabled, false = disabled
   */
  public void setEnabled(boolean enabled) {
    this.enabled = enabled;
  }

  
  /**
   * @see Object
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    DashboardConfig that = (DashboardConfig) o;

    if (columnCount != that.columnCount) return false;
    if (!widgetConfigList.equals(that.widgetConfigList)) return false;

    return true;
  }


  /**
   * @see Object
   */
  @Override
  public int hashCode() {
    int result = columnCount;
    result = 31 * result + widgetConfigList.hashCode();
    return result;
  }
}
