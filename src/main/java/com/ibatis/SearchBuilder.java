package com.ibatis;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Helper object to build searches in a more meaningful way.
 * 
 * @author boris
 */
public class SearchBuilder {
  private SearchBO mySearch = new SearchBO();

  public SearchBuilder() {
  }

  public SearchBuilder(SearchBuilder.ConditionGroup group) {
    and(group);
  }

  public SearchBO toSearch() {
    return mySearch;
  }

  public SearchBuilder and(ConditionGroup group) {
    SearchCondition last = mySearch.getLastCondition();
    if (last != null) {
      last.setOperator(SearchCondition.Operator.AND);
    }
    for (SearchCondition next : group.getConditions()) {
      mySearch.addCondition(next);
    }
    return this;
  }

  public SearchBuilder or(ConditionGroup group) {
    SearchCondition last = mySearch.getLastCondition();
    if (last != null) {
      last.setOperator(SearchCondition.Operator.OR);
    }
    for (SearchCondition next : group.getConditions()) {
      mySearch.addCondition(next);
    }
    return this;
  }

  public SearchBuilder orderBy(Enum<?> field) {
    mySearch.addOrder(field, SearchOrder.Direction.ASC);
    return this;
  }

  public SearchBuilder orderBy(Enum<?> field, SearchOrder.Direction direction) {
    mySearch.addOrder(field, direction);
    return this;
  }

  public interface ConditionGroup {
    public SearchCondition[] getConditions();
  }

  public static ConditionGroup create(@NotNull final SearchCondition singleCondition) {
    return new ConditionGroup() {
      @Override
      public SearchCondition[] getConditions() {
        return new SearchCondition[] { singleCondition };
      }
    };
  }

  /**
   * Creates a new SearchCondition with specified field and value, and default comparator.
   */
  public static ConditionGroup create(@Nullable Enum<?> field, @Nullable Object value) {
    return create(new SearchCondition(field, value));
  }

  /**
   * Creates a new SearchCondition with specified field and comparator and a null value.
   */
  public static ConditionGroup create(@Nullable Enum<?> field, @Nullable SearchCondition.Comparator comparator) {
    SearchCondition condition = new SearchCondition(field, null);
    condition.setComparator(comparator);
    return create(condition);
  }

  /**
   * Creates a new SearchCondition with specified field, comparator and value.
   */
  public static ConditionGroup create(@Nullable Enum<?> field, @Nullable SearchCondition.Comparator comparator, @Nullable Object value) {
    SearchCondition condition = new SearchCondition(field, value);
    condition.setComparator(comparator);
    return create(condition);
  }

  /**
   * Returns a condition group that represents the condition that the field takes one of these values.
   * 
   * @param field
   * @param values
   * @return
   * @throws IllegalArgumentException
   *           if the list of values is empty
   */
  public static ConditionGroup create(final Enum<?> field, final Object[] values) {
    switch (values.length) {
    case 0:
      throw new IllegalArgumentException();
    case 1:
      return create(new SearchCondition(field, values[0]));
    default:
      return new ConditionGroup() {
        @Override
        public SearchCondition[] getConditions() {
          SearchCondition[] result = new SearchCondition[values.length];
          for (int i = 0; i < values.length; i++) {
            SearchCondition next = new SearchCondition(field, values[i]);
            next.setOpenBracket(i == 0);
            if (i == values.length - 1) {
              next.setCloseBracket(true);
              next.setOperator(null);
            } else {
              next.setCloseBracket(false);
              next.setOperator(SearchCondition.Operator.OR);
            }
            result[i] = next;
          }
          return result;
        }
      };
    }
  }
}
