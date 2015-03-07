package com.ibatis.scorecardmodel.bo.interaction;


import com.ibatis.SearchCondition;

/**
 * Stanislav Valenta, 10/2/13
 */
public interface ComparatorResolver {
  SearchCondition.Comparator getComparator(String comparatorName);
}
