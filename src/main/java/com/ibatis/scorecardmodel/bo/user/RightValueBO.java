package com.ibatis.scorecardmodel.bo.user;




import com.ibatis.scorecardmodel.BaseBean;
import com.ibatis.search.SearchOrder;

import java.util.ArrayList;
import java.util.List;

/** @author Martin Zdarsky, 27.5.2009 */
public class RightValueBO extends BaseBean {
  private static final long serialVersionUID = -1101056351378145272L;
  public enum Fields {
    VALUE("value"), RIGHT_ID("rightid");
    private final String fieldName;

    Fields(final String fieldName) {
      this.fieldName = fieldName;
    }

    public String getFieldName() {
      return fieldName;
    }

    public static Fields getField(String fieldName) {
      for (Fields field: Fields.values())
        if (field.getFieldName().equals(fieldName))
          return field;
      return null;
    }
  }
  private Integer rightvalueid;
  private String value;
  private Integer rightid;

  public RightValueBO() {
    value = "";
  }

  @Override
  public Integer getId() {
    return getRightvalueid();
  }

  @Override
  public void setId(Integer value) {
    setRightvalueid(value);
  }

  public String getName() {
    return getValue();
  }

  //used by ibatis
  private Integer getRightvalueid() {
    return rightvalueid;
  }

  //used by ibatis
  public void setRightvalueid(Integer rightvalueid) {
    if (isLocked()) {
      throw new UnsupportedOperationException("You cannot set a primary key");
    }
    this.rightvalueid = rightvalueid;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    markChanged(this.value, value == null ? "" : value.trim());
    this.value = value == null ? "" : value.trim();
  }

  public Integer getRightid() {
    return rightid;
  }

  public void setRightid(Integer rightid) {
    markChanged(this.rightid, rightid);
    this.rightid = rightid;
  }

  @Override
  public int hashCode() {
    return getRightvalueid() == null ? 0 : getRightvalueid().hashCode();
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (!(obj instanceof RightValueBO)) {
      return false;
    }
    RightValueBO other = (RightValueBO) obj;
    return getRightvalueid() != null && other.getRightvalueid() != null && getRightvalueid().equals(other.getRightvalueid());
  }

  public String toString() {
    String tab = "\n    ";
    return "\nRightValueBO ("
            + tab + "rightvalueid = " + rightvalueid
            + tab + "rightid = " + rightid
            + tab + "value = " + value
            + tab + " )\n";
  }

  @Override
  public BaseBean newInstance() {
    return new RightValueBO();
  }

  @Override
  public List<SearchOrder> defaultSortOrder() {
    List<SearchOrder> ordering = new ArrayList<SearchOrder>();
    ordering.add(new SearchOrder(Fields.VALUE, SearchOrder.Direction.ASC));
    return ordering;
  }

}
