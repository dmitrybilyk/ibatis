package com.ibatis;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement
public class SearchOrder implements Serializable {
  private static final long serialVersionUID = 9185270389361044666L;

  @SuppressWarnings("unchecked")
  private String field;
  private Direction direction;

  @Deprecated
  public SearchOrder() {}

  @SuppressWarnings("unchecked")
  public SearchOrder(Enum field, Direction direction) {
    this.field = field.toString();
    this.direction = direction;
  }

  @SuppressWarnings("unchecked")
  @XmlElement
  public String getField() {
    return field;
  }

  @XmlElement
  public Direction getDirection() {
    return direction;
  }

  private void setField(String field) {
    this.field = field;
  }

  private void setDirection(Direction direction) {
    this.direction = direction;
  }

  @Override
  public String toString() {
    return "Order: " + field + (direction != null ? " " + direction : "");
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof SearchOrder)) {
      return false;
    }

    SearchOrder that = (SearchOrder) o;

    return (field == null ? that.field == null : field.equals(that.field))
            && direction == that.direction;
  }

  @Override
  public int hashCode() {
    int result = field != null ? field.hashCode() : 0;
    result = 31 * result + (direction != null ? direction.hashCode() : 0);
    return result;
  }

  public enum Direction {
    ASC("asc"), DESC("desc");
    private String value;

    Direction(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }

  }
}
