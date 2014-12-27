//package com.ibatis.scorecardmodel.bo.interaction;
//
//import com.ibatis.search.SearchCondition;
//
//public class IgnoreCaseComparatorResolver implements ComparatorResolver {
//
//  @Override
//  public SearchCondition.Comparator getComparator(String comparatorName) {
//    SearchCondition.Comparator comparator = SearchCondition.Comparator.valueOf(comparatorName);
//    switch (comparator) {
//      case EQUALS:
//        return SearchCondition.Comparator.LIKE_IGNORE_CASE;
//      case START_WITH:
//        return SearchCondition.Comparator.START_WITH_IGNORE_CASE;
//      case CONTAINS:
//        return SearchCondition.Comparator.CONTAINS_IGNORE_CASE;
//      default:
//        return comparator;
//    }
//  }
//}