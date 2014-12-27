package com.ibatis.scorecardmodel.bo.interaction;

/**
 * <p>Description: POJO objekt popisujici jednotlive casti ulozeneho filtru</p>
 * <p>Date: 23.3.2005 17:09:55</p>
 *
 * @author Tomas Beranek
 */

public class FilterPartPOJO {
  private int filterId = 0;
  private String formField = null;
  private String columnName = null;
  private String sqlValue = null;
  private String userValue = null;
  private boolean numeric = false;
  private String predicate = null;
  private String operator = null;
  private String extDataKey = null;

  public String getColumnName() {
    return columnName;
  }

  public void setColumnName(String columnName) {
    this.columnName = columnName;
  }

  public String getExtDataKey() {
    return extDataKey;
  }

  public void setExtDataKey(String extDataKey) {
    this.extDataKey = extDataKey;
  }

  public int getFilterId() {
    return filterId;
  }

  public void setFilterId(int filterId) {
    this.filterId = filterId;
  }

  public String getFormField() {
    return formField;
  }

  public void setFormField(String formField) {
    this.formField = formField;
  }

  public boolean isNumeric() {
    return numeric;
  }

  public void setNumeric(boolean numeric) {
    this.numeric = numeric;
  }

  public String getOperator() {
    return operator;
  }

  public void setOperator(String operator) {
    this.operator = operator;
  }

  public String getPredicate() {
    return predicate;
  }

  public void setPredicate(String predicate) {
    this.predicate = predicate;
  }

  public String getSqlValue() {
    return sqlValue;
  }

  public void setSqlValue(String sqlValue) {
    this.sqlValue = sqlValue;
  }

  public String getUserValue() {
    return userValue;
  }

  public void setUserValue(String userValue) {
    this.userValue = userValue;
  }

}
