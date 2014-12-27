package com.ibatis.scorecardmodel.bo.interaction;

/**
 * <p>Description: POJO objekt pro externi data jak couplu tak i callu</p>
 * <p>Date: 23.3.2005 15:24:50</p>
 *
 * @author Tomas Beranek
 */

public class ExternalDataPOJO {
  private String key = null;
  private String value = null;
  //vyjadruje bud couple nebo call id podle toho k cemu se externi data vztahuji
  private int id =  0;

  public enum Fields {
    EXTERNAL_COL_CPLID, EXTERNAL_COL_1, EXTERNAL_COL_2, EXTERNAL_COL_3, EXTERNAL_COL_4, EXTERNAL_COL_5,
    EXTERNAL_COL_6, EXTERNAL_COL_7, EXTERNAL_COL_8, EXTERNAL_COL_9, EXTERNAL_COL_10, EXTERNAL_COL_11,
    EXTERNAL_COL_12, EXTERNAL_COL_13, EXTERNAL_COL_14, EXTERNAL_COL_15,;

    public static Fields getField(String fieldName) {
      for (Fields field : Fields.values()) {
        if (field.toString().equalsIgnoreCase(fieldName)) {
          return field;
        }
      }
      return null;
    }

    public static Fields getField(Integer id){
      switch (id){
        case 1: return EXTERNAL_COL_1;
        case 2: return EXTERNAL_COL_2;
        case 3: return EXTERNAL_COL_3;
        case 4: return EXTERNAL_COL_4;
        case 5: return EXTERNAL_COL_5;
        case 6: return EXTERNAL_COL_6;
        case 7: return EXTERNAL_COL_7;
        case 8: return EXTERNAL_COL_8;
        case 9: return EXTERNAL_COL_9;
        case 10: return EXTERNAL_COL_10;
        case 11: return EXTERNAL_COL_11;
        case 12: return EXTERNAL_COL_12;
        case 13: return EXTERNAL_COL_13;
        case 14: return EXTERNAL_COL_14;
        case 15: return EXTERNAL_COL_15;
      }
      return null;
    }
  }

  public ExternalDataPOJO() {
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  public boolean equalsKeyValue(ExternalDataPOJO that){
    if (this.value.equalsIgnoreCase(that.value)&&this.key.equalsIgnoreCase(that.key)) return true;
    return false;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof ExternalDataPOJO)) return false;

    ExternalDataPOJO dataPOJO = (ExternalDataPOJO) o;

    if (id != dataPOJO.id) return false;
    if (key != null ? !key.equals(dataPOJO.key) : dataPOJO.key != null) return false;
    if (value != null ? !value.equals(dataPOJO.value) : dataPOJO.value != null) return false;

    return true;
  }

  @Override
  public int hashCode() {
    int result = key != null ? key.hashCode() : 0;
    result = 31 * result + (value != null ? value.hashCode() : 0);
    result = 31 * result + id;
    return result;
  }
}
