package com.ibatis.scorecardmodel.bo.evaluation;


import com.ibatis.scorecardmodel.BaseBean;
import com.ibatis.search.SearchCondition;
import com.ibatis.search.SearchOrder;


import java.util.ArrayList;
import java.util.List;

/** @author Stanislav Valenta, 26.6.2009 */
public class ExternalDataBO extends BaseBean {
  private static final long serialVersionUID = -3364478622351959078L;

  public enum Fields {
    ORDERING("ordering"), KEY("key"), COMPARATOR("comparatorEnum"),
    FORMAT("formatEnum"), VALUE("value"), OPERATOR("operatorEnum");
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

   public enum Format {
    TEXT, NUMBER
  }


  private Integer externaldataid;
  private Integer criteriaid;
  private CriteriaBO criteriaBO;
  private Integer ordering;
  private String key;
  private SearchCondition.Comparator comparator;
  private Format format;
  private String value;
  private SearchCondition.Operator operator;
  private Integer fastDataColumnNumber;

  public ExternalDataBO() {
    key = "";
    value = "";
  }
  
  public CriteriaBO getCriteriaBO() {
    return criteriaBO;
  }
  
  public void setCriteriaBO(CriteriaBO criteriaBO) {
    markChanged(this.criteriaid, criteriaBO == null ? null : criteriaBO.getCriteriaid());
    this.criteriaBO = criteriaBO;
    this.criteriaid = criteriaBO == null ? null : criteriaBO.getCriteriaid();
  }

  @Override
  public Integer getId() {
    return getExternaldataid();
  }

  @Override
  public void setId(Integer value) {
    setExternaldataid(value);
  }

  public Integer getFastDataColumnNumber() {
    return fastDataColumnNumber;
  }

  public void setFastDataColumnNumber(Integer fastDataColumnNumber) {
    this.fastDataColumnNumber = fastDataColumnNumber;
  }

  public Integer getExternaldataid() {
    return externaldataid;
  }

  // used by ibatis
  public void setExternaldataid(Integer externaldataid) {
    if (isLocked()) {
      throw new UnsupportedOperationException("You cannot set a primary key");
    }
    this.externaldataid = externaldataid;
  }

  public Integer getCriteriaid() {
    return criteriaid;
  }

//used by ibatis
  @SuppressWarnings({"unused"})
  private void setCriteriaid(Integer criteriaid) {
    if (isLocked())
      throw new UnsupportedOperationException("You cannot set a foreign key");
    this.criteriaid = criteriaid;
  }

  public Integer getOrdering() {
    return ordering;
  }

  public void setOrdering(Integer ordering) {
    markChanged(this.ordering, ordering);
    this.ordering = ordering;
  }

  public String getKey() {
    return key;
  }

  public void setKey(String key) {
    markChanged(this.key, key == null ? "" : key.trim());
    this.key = key == null ? "" : key.trim();
  }

  public SearchCondition.Comparator getComparatorEnum() {
    return comparator;
  }

  public void setComparatorEnum(SearchCondition.Comparator comparator) {
    markChanged(this.comparator, comparator);
    this.comparator = comparator;
  }

  // used by ibatis
  @SuppressWarnings({"unused"})
  private String getComparator() {
    return comparator.toString();
  }

  // used by ibatis
  @SuppressWarnings({"unused"})
  private void setComparator(String comparator) {
    this.comparator = SearchCondition.Comparator.valueOf(comparator);
  }

  public void setFormatEnum(Format format) {
    markChanged(this.format, format);
    this.format = format;
  }

  public Format getFormatEnum() {
    return format;
  }

  // used by ibatis
  @SuppressWarnings({"unused"})
  private String getFormat() {
    return format.toString();
  }

  // used by ibatis
  @SuppressWarnings({"unused"})
  private void setFormat(String format) {
    this.format = Format.valueOf(format);
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    markChanged(this.value, value == null ? "" : value.trim());
    this.value = value == null ? "" : value.trim();
  }

  // used by ibatis
  @SuppressWarnings({"unused"})
  private String getOperator() {
    return operator==null?"":operator.toString();
  }

  // used by ibatis
  @SuppressWarnings({"unused"})
  private void setOperator(String operator) {
    this.operator = SearchCondition.Operator.valueOf(operator);
  }

  public void setOperatorEnum(SearchCondition.Operator operator) {
    markChanged(this.operator, operator);
    this.operator = operator;
  }

  public SearchCondition.Operator getOperatorEnum() {
    return operator;
  }


  @Override
  public int hashCode() {
    return ((getExternaldataid() == null) ? 0 : getExternaldataid().hashCode());
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (!(obj instanceof ExternalDataBO))
      return false;
    ExternalDataBO other = (ExternalDataBO) obj;
    if (getExternaldataid() == null || other.getExternaldataid() == null)
      return false;
    return getExternaldataid().equals(other.getExternaldataid());
  }

    @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append("ExternalDataBO");
    sb.append("{d=").append(externaldataid);
    sb.append(", key='").append(key).append('\'');
    sb.append(", value='").append(value).append('\'');
    sb.append('}');
    return sb.toString();
  }

  @Override
  public BaseBean newInstance() {
    return new ExternalDataBO();
  }

    @Override
  public List<SearchOrder> defaultSortOrder() {
    List<SearchOrder> ordering = new ArrayList<SearchOrder>();
    ordering.add(new SearchOrder(Fields.ORDERING, SearchOrder.Direction.ASC));
    return ordering;
  }

}
