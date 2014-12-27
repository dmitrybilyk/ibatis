package com.ibatis;

import org.jetbrains.annotations.Nullable;

import javax.xml.bind.annotation.*;
import java.io.Serializable;

@XmlRootElement
@XmlType(name = "", propOrder = {
        "field",
        "comparator",
        "value",
        "operator"
})
public class SearchCondition implements Serializable {
  private static final long serialVersionUID = -1048389305807298308L;

  private Comparator comparator = Comparator.EQUALS;
  private boolean openBracket = false;
  private boolean closeBracket = false;
  private Operator operator;

  /*
   * This is a hack, I know it. GWT cannot serialize Object and doesn't allow
   * reflection nor overloaded setters.
   */
  private ValueHolder<?> valueHolder = new ValueHolders.NullHolder();


  /**
   * This constructor is user by GWT serialization
   */
  @Deprecated
  public SearchCondition() {
  }

  public SearchCondition(@Nullable Enum field, @Nullable Object value) {
    setField(field);
    setValue(value);
  }

  private String field;

  @XmlElement
  public String getField() {
    return field;
  }

  private void setField(String field) {
    this.field = field;
  }

  public void setField(Enum field) {
    this.field = field != null ? field.toString() : null;
  }

  @XmlElement
  public Comparator getComparator() {
    return comparator;
  }

  @XmlElement
  public Operator getOperator() {
    return operator;
  }

  @XmlElement(required = true)
  public Object getValue() {
    Object result = valueHolder.getValue();

    if (getComparator() == Comparator.CONTAINS_IGNORE_CASE || getComparator() == Comparator.CONTAINS) {
      return "%" + result + "%";
    } else if (getComparator() == Comparator.START_WITH_IGNORE_CASE || getComparator() == Comparator.START_WITH) {
      return result + "%";
    } else if (getComparator() == Comparator.END_WITH_IGNORE_CASE || getComparator() == Comparator.END_WITH) {
      return "%" + result;
    } else {
      return result;
    }
  }

  public void setValue(Object value) {
    this.valueHolder = ValueHolders.forValue(value);
  }

  @XmlAttribute
  public boolean isCloseBracket() {
    return closeBracket;
  }

  @XmlAttribute
  public boolean isOpenBracket() {
    return openBracket;
  }

  public void setCloseBracket(boolean closeBracket) {
    this.closeBracket = closeBracket;
  }

  public void setComparator(Comparator comparator) {
    this.comparator = comparator;
  }

  public void setOpenBracket(boolean openBracket) {
    this.openBracket = openBracket;
  }

  public void setOperator(@Nullable Operator operator) {
    this.operator = operator;
  }

  @Override
  public String toString() {
    Object value = getValue();
    String valueString = value == null ? "null" :
            (value.getClass() + " \"" + String.valueOf(value) + "\"");
    return "Condition: " + (openBracket ? "(" : "")
            + field + " (is) " + getComparator() + " " + valueString
            + (closeBracket ? ")" : "")
            + (operator != null ? " " + operator + "..." : "");
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof SearchCondition)) {
      return false;
    }

    SearchCondition condition = (SearchCondition) o;
    Object thisValue = getValue();
    Object otherValue = condition.getValue();

    return (field == null ? condition.field == null : field.equals(condition.field))
            && (thisValue == null ? otherValue == null : thisValue.equals(otherValue))
            && comparator == condition.comparator
            && operator == condition.operator
            && openBracket == condition.openBracket
            && closeBracket == condition.closeBracket;
  }

  @Override
  public int hashCode() {
    Object value = getValue();
    int result = comparator != null ? comparator.hashCode() : 0;
    result = 31 * result + (openBracket ? 1 : 0);
    result = 31 * result + (closeBracket ? 1 : 0);
    result = 31 * result + (operator != null ? operator.hashCode() : 0);
    result = 31 * result + (value != null ? value.hashCode() : 0);
    result = 31 * result + (field != null ? field.hashCode() : 0);
    return result;
  }

  @XmlEnum
  public enum Comparator {
    BIGGER(">", false),
    BIGGER_OR_EQUAL(">=", false),
    EQUALS("=", false),
    ISNULL("is null", false),
    NOTEQUAL("<>", false),
    NOTNULL("is not null", false),
    SMALLER("<", false),
    SMALLER_OR_EQUAL("<=", false),
    ILIKE("like", true),
    CONTAINS_IGNORE_CASE("like", true),
    START_WITH_IGNORE_CASE("like", true),
    END_WITH_IGNORE_CASE("like", true),
    CONTAINS("like", false),
    START_WITH("like", false),
    END_WITH("like", false),
    LIKE("like", false),
    LIKE_IGNORE_CASE("like", true);

    private String value;
    private boolean caseInSensitive = false;

    Comparator(String value, boolean caseInSensitive) {
      this.value = value;
      this.caseInSensitive = caseInSensitive;
    }

    public String getValue() {
      return value;
    }

    @XmlTransient
    public boolean isCaseInSensitive() {
      return caseInSensitive;
    }
  }

  @XmlRootElement
  public enum Operator {
    AND("and"), OR("or");

    private String value;

    Operator(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }
  }
}
