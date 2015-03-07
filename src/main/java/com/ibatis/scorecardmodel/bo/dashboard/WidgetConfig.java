package com.ibatis.scorecardmodel.bo.dashboard;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Bean keeping widget configuration data
 *
 * @author Stanislav Valenta, 13.1.2010
 */
public class WidgetConfig implements Serializable {
  private static final long serialVersionUID = -7240423141815920266L;

  /**
   * Config map keys literals
   */
  public static final String KEY_COUNT = "count";
  public static final String KEY_INVERSE = "inverse";
  public static final String KEY_TIME_INTERVAL = "interval";
  public static final String KEY_QUESTFORM_ID = "questformId";
  public static final String KEY_QUESTFORMPIE_ID = "questformPieId";
  public static final String KEY_GROUP_ID = "groupId";
  public static final String KEY_ALL_GROUPS_SELECTED = "isAllGroupsSelected";
  public static final String KEY_USER_ID = "userId";
  public static final String KEY_GOOD_THRESHOLD = "goodThreshold";
  public static final String KEY_BAD_THRESHOLD = "badThreshold";
  public static final String KEY_SCORING_SYSTEM = "scoringSystem";
  public static final String KEY_DESCRIPTION = "descrtiption";
  public static final String KEY_REFRESH_INTERVAL = "refreshInterval";
  public static final int MIN_REFRESH = 5;
  public static final int MAX_REFRESH = 30;
  public static final int ALL_GROUPS_ID = -1;
  // index where widget is put in dashboard
  private int columnIndex;
  private int rowIndex;
  // widget's id
  private String id;
  // map keeping configuration records
  private Map<String, String> configMap = new HashMap<String, String>();
  // set of listeners
  private transient Set<WidgetConfigListener> listenerSet = new HashSet<WidgetConfigListener>();


  /**
   * Creates new instance based on passed params
   *
   * @param columnIndex what column widget is place into
   * @param rowIndex    what row widget is placed into
   * @param id          widget's id
   * @param configMap   config's key->value map
   */
  public WidgetConfig(int columnIndex, int rowIndex, String id, Map<String, String> configMap) {
    this.columnIndex = columnIndex;
    this.rowIndex = rowIndex;
    this.id = id;
    this.configMap = configMap;
  }


  /**
   * Creates new instance based on passed params
   *
   * @param columnIndex what column is widget placed into
   * @param rowIndex    what row widget is placed into
   * @param id          widget's id
   */
  public WidgetConfig(int columnIndex, int rowIndex, String id) {
    this.columnIndex = columnIndex;
    this.rowIndex = rowIndex;
    this.id = id;
  }


  /**
   * Java beans convention
   */
  public WidgetConfig() {
  }


  /**
   * Column's index getter
   *
   * @return column's index
   */
  public int getColumnIndex() {
    return columnIndex;
  }


  /**
   * Column's index setter
   *
   * @param columnIndex what column is widget placed into
   */
  public void setColumnIndex(int columnIndex) {
    this.columnIndex = columnIndex;
    for (WidgetConfigListener listener : listenerSet) {
      listener.configChanged(this);
    }
  }


  /**
   * Ros's index getter
   *
   * @return row's index
   */
  public int getRowIndex() {
    return rowIndex;
  }


  /**
   * Row's index setter
   *
   * @param rowIndex what row is widget placed into
   */
  public void setRowIndex(int rowIndex) {
    this.rowIndex = rowIndex;
    for (WidgetConfigListener listener : listenerSet) {
      listener.configChanged(this);
    }
  }

  public void increaseRowIndexQuietly(){
    this.rowIndex++;
  }

  public void decreaseRowIndexQuietly(){
      this.rowIndex--;
    }


  /**
   * Adds config entry
   *
   * @param key   config's key
   * @param value config's value
   */
  public void addConfig(String key, String value) {
    configMap.put(key, value);
    for (WidgetConfigListener listener : listenerSet) {
      listener.configChanged(this);
    }
  }


  /**
   * Removes config entry
   *
   * @param key of entry is being removed
   */
  public void removeConfig(String key) {
    configMap.remove(key);
    for (WidgetConfigListener listener : listenerSet) {
      listener.configChanged(this);
    }
  }


  /**
   * Config's id getter
   *
   * @return config's id
   */
  public String getId() {
    return id;
  }


  /**
   * Config's id setter
   *
   * @param id config's id
   */
  public void setId(String id) {
    this.id = id;
  }


  /**
   * Config's map getter
   *
   * @return config map
   */
  public Map<String, String> getConfigMap() {
    return configMap;
  }

  /**
   * Returns a value from the config map
   *
   * @param key : the key to look for in the config map
   * @return the value associated to that key, or null if the key doesn't exist
   */
  public String getConfigValue(String key) {
    return configMap.get(key);
  }

  /**
   * Config's map setter
   *
   * @param configMap config map
   */
  public void setConfigMap(Map<String, String> configMap) {
    this.configMap = configMap;
  }


  /**
   * Removes listener passed as a parameter
   *
   * @param listener removed listener
   */
  public void removeConfigListener(WidgetConfigListener listener) {
    listenerSet.remove(listener);
  }


  /**
   * Adds listener passed as a parameter
   *
   * @param listener added listener
   */
  public void addConfigListener(WidgetConfigListener listener) {
    listenerSet.add(listener);
  }


  /**
   * @see Object
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    WidgetConfig that = (WidgetConfig) o;

    if (columnIndex != that.columnIndex) return false;
    if (rowIndex != that.rowIndex) return false;
    if (!configMap.equals(that.configMap)) return false;
    if (id != null ? !id.equals(that.id) : that.id != null) return false;

    return true;
  }


  /**
   * @see Object
   */
  @Override
  public int hashCode() {
    int result = columnIndex;
    result = 31 * result + rowIndex;
    result = 31 * result + (id != null ? id.hashCode() : 0);
    result = 31 * result + configMap.hashCode();
    return result;
  }
}
