package com.ibatis;

import com.google.common.base.Joiner;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * This BO represents search, paging and sorting rules for most of the business methods.
 *
 * Example of usage:
 *
 * for sorting for example a user by surname, name and login. Get a records in an interval
 * in between 30 and 50 and get only those users who are active.
 * <pre>{@code
 *  SearchBO searchBO = new SearchBO();
 *  searchBO.addOrder(UserBO.Fields.SURNAME, SearchOrder.Direction.ASC);
 *  searchBO.addOrder(UserBO.Fields.USER_NAME, SearchOrder.Direction.DESC);
 *  searchBO.addOrder(UserBO.Fields.LOGIN, SearchOrder.Direction.DESC);
 *  searchBO.setOffset(30);
 *  searchBO.setLimit(20);
 *  SearchCondition condition = new SearchCondition(UserBO.Fields.ACTIVE, true);
 *  searchBO.addCondition(condition);
 * }</pre>
 *  You can also use brackets and logical operator between conditions.
 *  <pre>{@code
 *  condition.setOpenBracket(true);
 *  condition.setComparator(SearchCondition.Comparator.EQUALS);
 *  condition.setOperator(SearchCondition.Operator.AND);
 *  condition.setCloseBracket(true);
 *  }</pre>
 * @author Martin Zdarsky
 *
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
public class SearchBO implements Serializable {
  private static final long serialVersionUID = 6575025533881867351L;

  /**
   * maximum of records to return
   */
  private Integer limit;

  /**
   * give me records starting with <code>offset</code>.
   */
  private Integer offset;

  /**
   * stores all conditions to process. A key is a BO field.
   */
  private List<SearchCondition> conditions;

  /**
   * stores a sorted order.
   */
  private List<SearchOrder> order;

  /**
   * True to sort in business logic according to user locale, false to sort on DB. Default false.
   */
  private boolean localizedSort;

  public SearchBO() {
    order = new ArrayList<SearchOrder>();
    conditions = new ArrayList<SearchCondition>();
    localizedSort = false;
  }

  /**
   * Copy Constructor
   *
   * @param searchBO a <code>SearchBO</code> object to copy
   */
  public SearchBO(SearchBO searchBO) {
    this();
    if (searchBO != null) {
      this.limit = searchBO.limit;
      this.offset = searchBO.offset;
      this.conditions.addAll(searchBO.conditions);
      this.order.addAll(searchBO.order);
      this.localizedSort = searchBO.isLocalizedSort();
    }
  }

  /**
   * This methods checks whether the set of conditions is valid. It checks
   * opening and closing brackets and operators.
   *
   * @return true if it is valid.
   */
  public boolean isValid() {
    int brackets = 0;
    for (int index = 0; index < conditions.size(); index++) {
      SearchCondition condition = conditions.get(index);
      if (condition.isOpenBracket()) {
        brackets++;
      }
      if (condition.isCloseBracket()) {
        brackets--;
      }
      if (brackets < 0) {
        return false;
      }
      if (index == conditions.size() - 1) {
        if (condition.getOperator() != null) {
          return false;
        }
      } else {
        if (condition.getOperator() == null) {
          return false;
        }
      }
    }
    return brackets == 0;
  }

  @XmlElement
  public Integer getLimit() {
    return limit;
  }

  public void setLimit(Integer limit) {
    this.limit = limit;
  }

  public void setOffset(Integer offset) {
    this.offset = offset;
  }

  public void clearOrder() {
    order.clear();
  }

  @SuppressWarnings("unchecked")
  public void addOrder(Enum field, SearchOrder.Direction direction) {
    order.add(new SearchOrder(field, direction));
  }

  public void addOrderList(List<SearchOrder> orderList){
    order.addAll(orderList);
  }

  @XmlElement
  public List<SearchOrder> getOrder() {
    return order;
  }

  public void clearConditions() {
    conditions.clear();
  }

  public void addCondition(SearchCondition condition) {
    conditions.add(condition);
  }

  public void addNotInCondition(Enum field, Collection values){
    if(values == null){
      return;
    }
    List<SearchCondition> cons = new ArrayList<SearchCondition>(values.size());
    for (Object val : values) {
      SearchCondition con = new SearchCondition(field, val);
      con.setComparator(SearchCondition.Comparator.NOTEQUAL);
      con.setOperator(SearchCondition.Operator.AND);
      cons.add(con);
    }
    if (cons.size() > 0) {
      cons.get(cons.size() - 1).setOperator(null);
    }
    if (cons.size() > 1) {
      cons.get(0).setOpenBracket(true);
      cons.get(cons.size() - 1).setCloseBracket(true);
    }
    conditions.addAll(cons);
  }

  public void addInCondition(Enum field, Collection values){
    if(values == null){
      return;
    }
    List<SearchCondition> cons = new ArrayList<SearchCondition>(values.size());
    for (Object val : values) {
      SearchCondition con = new SearchCondition(field, val);
      con.setComparator(SearchCondition.Comparator.EQUALS);
      con.setOperator(SearchCondition.Operator.OR);
      cons.add(con);
    }
    if (cons.size() > 0) {
      cons.get(cons.size() - 1).setOperator(null);
    }
    if (cons.size() > 1) {
      cons.get(0).setOpenBracket(true);
      cons.get(cons.size() - 1).setCloseBracket(true);
    }
    conditions.addAll(cons);
  }

  public void removeCondition(SearchCondition condition) {
    conditions.remove(condition);
  }

  public void addConditionAtFirstPosition(SearchCondition condition) {
    conditions.add(0, condition);
  }

  @XmlElement
  public List<SearchCondition> getCondition() {
    return conditions;
  }

  @XmlElement
  public Integer getOffset() {
    return offset;
  }

  public boolean isLocalizedSort() {
    return localizedSort;
  }

  public void setLocalizedSort(boolean localizedSort) {
    this.localizedSort = localizedSort;
  }

  public SearchCondition getLastCondition(){
    return conditions.size() > 0 ? conditions.get(conditions.size() - 1) : null;
  }

  public SearchCondition getFirstCondition(){
    return conditions.size() > 0 ? conditions.get(0) : null;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("SearchBO [");
    if (conditions != null && !conditions.isEmpty()) {
      builder.append("conditions=");
      Joiner.on(" ").appendTo(builder, conditions);
      builder.append(", ");
    }
    if (limit != null) {
      builder.append("limit=");
      builder.append(limit);
      builder.append(", ");
    }
    if (offset != null) {
      builder.append("offset=");
      builder.append(offset);
      builder.append(", ");
    }
    if (order != null && !order.isEmpty()) {
      builder.append("order=");
      Joiner.on(" ").appendTo(builder, order);
    }
    builder.append("]");
    return builder.toString();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof SearchBO)) {
      return false;
    }
    SearchBO searchBO = (SearchBO) o;
    return (limit == null ? searchBO.limit == null : limit.equals(searchBO.limit))
            && (offset == null ? searchBO.offset == null : offset.equals(searchBO.offset))
            && (conditions == null ? searchBO.conditions == null : conditions.equals(searchBO.conditions))
            && (order == null ? searchBO.order == null : order.equals(searchBO.order))
            && localizedSort == searchBO.localizedSort;
  }

  @Override
  public int hashCode() {
    int result = limit != null ? limit.hashCode() : 0;
    result = 31 * result + (offset != null ? offset.hashCode() : 0);
    result = 31 * result + (conditions != null ? conditions.hashCode() : 0);
    result = 31 * result + (order != null ? order.hashCode() : 0);
    result = 31 * result + (localizedSort ? 1 : 0);
    return result;
  }

  /**
   * Used by iBatis.
   */
  public SearchBO getSearchBO() {
    return this;
  }

}
