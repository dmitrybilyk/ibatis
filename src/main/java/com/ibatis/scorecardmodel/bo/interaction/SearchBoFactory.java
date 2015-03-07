package com.ibatis.scorecardmodel.bo.interaction;

import com.ibatis.search.SearchBO;
import com.ibatis.search.SearchCondition;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * Author: Stanislav Valenta
 * Date: 1.8.11
 */
public class SearchBoFactory {

  public SearchBO createInteractionSearchBO(SearchDataDTO searchDataDTO) {
    SearchBO interactionSearch = new SearchBO();
    if (searchDataDTO == null) {
      return interactionSearch;
    }

    if (StringUtils.isNotEmpty(searchDataDTO.getClientPhone())) {
      SearchCondition callingPartyCondition = new SearchCondition(CouplePOJO.Fields.COUPLE_CALLING_NR, searchDataDTO.getClientPhone());
      callingPartyCondition.setOpenBracket(true);
      callingPartyCondition.setComparator(SearchCondition.Comparator.EQUALS);
      callingPartyCondition.setOperator(SearchCondition.Operator.OR);
      interactionSearch.addCondition(callingPartyCondition);
      SearchCondition calledPartyCondition = new SearchCondition(CouplePOJO.Fields.COUPLE_ORIGINAL_CALLED_NR, searchDataDTO.getClientPhone());
      calledPartyCondition.setComparator(SearchCondition.Comparator.EQUALS);
      calledPartyCondition.setOperator(SearchCondition.Operator.AND);
      calledPartyCondition.setCloseBracket(true);
      interactionSearch.addCondition(calledPartyCondition);
    }


    interactionSearch = addDescriptionToSearch(searchDataDTO,interactionSearch);

    DateRange dateRange = searchDataDTO.getDateRange();
    if (dateRange != null) {
      DateRangeFactory rangeFactory = new DateRangeFactory();
      Date startDate = rangeFactory.startDate(dateRange);
      if (startDate != null) {
        if(startDate.getTime()<new Date(0).getTime()){ //workaround wrong calendar, there is set 1912 isntead of 2012
          Calendar instance = Calendar.getInstance();
          instance.setTime(startDate);
          instance.add(Calendar.YEAR,100);
          startDate = instance.getTime();
        }
        SearchCondition startDateCondition = new SearchCondition(CouplePOJO.Fields.COUPLE_START_TS, startDate);
        startDateCondition.setComparator(SearchCondition.Comparator.BIGGER_OR_EQUAL);
        startDateCondition.setOperator(SearchCondition.Operator.AND);
        interactionSearch.addCondition(startDateCondition);
      }

      Date endDate = rangeFactory.endDate(dateRange);
      if (endDate != null) {
        if (endDate.getTime() < new Date(0).getTime()) { //workaround wrong calendar, there is set 1912 isntead of 2012
          Calendar instance = Calendar.getInstance();
          instance.setTime(endDate);
          instance.add(Calendar.YEAR, 100);
          endDate = instance.getTime();
        }
        Date endDateMidnight = new DateTime(endDate).plusDays(1).minusMillis(1).toDate();
        SearchCondition endDateCondition = new SearchCondition(CouplePOJO.Fields.COUPLE_START_TS, endDateMidnight);
        endDateCondition.setComparator(SearchCondition.Comparator.SMALLER_OR_EQUAL);
        endDateCondition.setOperator(SearchCondition.Operator.AND);
        interactionSearch.addCondition(endDateCondition);
      }
    }

    HoursRange hoursRange = searchDataDTO.getHoursRange();
    if (hoursRange != null) {
      if ((hoursRange.getStartHour() != 0 || hoursRange.getStartMinute() != 0 || hoursRange.getEndHour() != 0 || hoursRange.getEndMinute() != 0)) {
        int startSeconds = hoursRange.countStartSeconds();
        SearchCondition startSecondsCondition = new SearchCondition(CouplePOJO.Fields.COUPLE_TIME_OF_DAY, startSeconds);
        startSecondsCondition.setComparator(SearchCondition.Comparator.BIGGER_OR_EQUAL);
        startSecondsCondition.setOperator(SearchCondition.Operator.AND);
        interactionSearch.addCondition(startSecondsCondition);

        int endSeconds = hoursRange.countEndSeconds();
        if (endSeconds == 0){
          endSeconds = hoursRange.getMaxSecondsInDay();
        }
        SearchCondition endSecondsCondition = new SearchCondition(CouplePOJO.Fields.COUPLE_TIME_OF_DAY, endSeconds);
        endSecondsCondition.setComparator(SearchCondition.Comparator.SMALLER_OR_EQUAL);
        endSecondsCondition.setOperator(SearchCondition.Operator.AND);
        interactionSearch.addCondition(endSecondsCondition);


      }
    Collection<Integer> weekdayNumbers = hoursRange.getWeekdayNumbers();
      if (weekdayNumbers != null && !weekdayNumbers.isEmpty()) {
        SearchCondition weekdayCondition = null;
        boolean openBracket = true;
        for (Integer weekdayNumber : weekdayNumbers) {
          weekdayCondition = new SearchCondition(CouplePOJO.Fields.COUPLE_DAY_OF_WEEK, weekdayNumber);
          weekdayCondition.setOpenBracket(openBracket);
          openBracket = false;
          weekdayCondition.setComparator(SearchCondition.Comparator.EQUALS);
          weekdayCondition.setOperator(SearchCondition.Operator.OR);
          interactionSearch.addCondition(weekdayCondition);
        }
        weekdayCondition.setCloseBracket(true);
        weekdayCondition.setOperator(SearchCondition.Operator.AND);
      }
    }

    if (interactionSearch.getLastCondition() != null) {
      interactionSearch.getLastCondition().setOperator(null);
    }
    return interactionSearch;
  }

  private SearchBO addDescriptionToSearch(SearchDataDTO searchData, SearchBO searchBO) {
     if(StringUtils.isNotEmpty(searchData.getDescription())){
       SearchCondition condition = new SearchCondition(CouplePOJO.Fields.COUPLE_DESCRIPTION, searchData.getDescription());
       condition.setComparator(SearchCondition.Comparator.START_WITH_IGNORE_CASE);
       condition.setOperator(SearchCondition.Operator.AND);
       searchBO.addCondition(condition);
     }
    return searchBO;
   }
}