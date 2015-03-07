package com.ibatis.scorecardmodel.bo.evaluation;

import com.ibatis.SearchCondition;
import com.ibatis.scorecardmodel.BaseBean;
import com.ibatis.scorecardmodel.bo.ReplaceableLinkedHashSet;
import com.ibatis.scorecardmodel.bo.ServerSideDate;
import com.ibatis.scorecardmodel.bo.ServerSideDateSupport;
import com.ibatis.scorecardmodel.bo.TrackableLinkedHashSet;
import com.ibatis.search.SearchOrder;

import java.util.*;

/** @author Stanislav Valenta, 25.6.2009 */
public class CriteriaBO extends WeekdayBean implements  Comparable<CriteriaBO> {
  private static final long serialVersionUID = 5539324328329072617L;

  public enum Fields {
    IS_MAIN("isMain"), PERIOD_FROM("periodFrom"), PERIOD_TO("periodTo"), TOTAL_EVENTS("totalEvents"),
    CALL_DIR_TYPE("callDirType"), CALL_WRAPUP("callWrapup"), MIN_LENGTH("minimalLength"), MAX_LENGTH("maximalLength"),
    DAY_TIME_FROM("daytimeFrom"), DAY_TIME_TO("daytimeTo"), CALL_SELECT("allowCallSelection"), CALL_REPLACE("allowCallReplacement"),
    CALL_FILLING("allowCallFilling"), FINISH_FIRST("finishSubcriteriaFirst"), DEAD_LINE("deadline"), REMAINDER_DAYS("reminderDays"),
    REMAINDER_DATE("reminderDate"), REMAINDER_ON("remindedOn"), ORDERING("ordering"), RANDOM_ORDER("randomOrder"), WEEKDAYS(""),
    SIPNUMBER("sipnumber"), PERIOD("period"), CRITERIA_DESCRIPTION("description"), ORIGINALCALLEDNR("originalcallednr"),
    CRITERIA_ID("criteriaid"), CALLINGNR("callingnr"), CALLING_AGENT("callingAgent"), CALLED_AGENT("calledAgent");

    private final String fieldName;

    Fields(final String fieldName) {
      this.fieldName = fieldName;
    }

    public String getFieldName() {
      return fieldName;
    }

    public static Fields getField(String fieldName) {
      for (Fields field: Fields.values()) {
        if (field.getFieldName().equals(fieldName)) {
            return field;
        }
    }
      return null;
    }
  }

  public enum CallDirection {
    INCOMING, OUTGOING, BOTH, ALL, INTERNAL, NOT_INTERNAL, UNKNOWN;

    public static CallDirection getField(String fieldName) {
      for (CallDirection direction : CallDirection.values())
        if (direction.toString().equals(fieldName))
          return direction;
      return null;
    }
  }

  //todo synchronize this with database eval_status domain and TimeInterval in UI
  public enum TimeInterval{
    YESTERDAY(-1,0),
    LAST_3_DAYS(-3,2),
    LAST_30_DAYS(-30,29),

    THIS_WEEK(0,1),
    LAST_WEEK(-1,1),
    LAST_2_WEEKS(-2,2),

    NEXT_MONTH(+1,1),
    THIS_MONTH(0,1),
    LAST_MONTH(-1,1),
    LAST_6_MONTHS(-6,6),

    THIS_QUARTER(-1,1),
    LAST_QUARTER(-2,1),
    FIRST_QUARTER(0,1),
    SECOND_QUARTER(1,1),
    THIRD_QUARTER(2,1),
    FOURTH_QUARTER(3,1),

    THIS_YEAR(0,1),
    LAST_YEAR(-1,1),


    CUSTOM_LAST_WEEK(-1,1),
    CUSTOM_THIS_WEEK(0,1),
    CUSTOM_NEXT_WEEK(+1,1),

    CUSTOM_LAST_MONTH(-1,1),
    CUSTOM_THIS_MONTH(0,1),
    CUSTOM_NEXT_MONTH(+1,1),

    CUSTOM_PERIOD(0,1),
    FIXED_DATE(0,1);


    /* these constants can be
       re-enabled in the future
     */
//    NEXT_YEAR(+1),
//    NEXT_WEEK(+1),
//    TODAY(0);

    private final int offset;
    private final int limit;

    private TimeInterval(int offset, int limit) {
        this.offset = offset;
        this.limit = limit;
    }

    public final int getOffset() {
        return offset;
    }

    public int getLimit() {
      return limit;
    }
  }

  private Integer criteriaid;
  private Integer evaluationid;
  private EvaluationBO evaluationBO;
  private Boolean isMain;
  private ServerSideDate periodFrom;
  private ServerSideDate periodTo;
  private Integer totalEvents;
  private CallDirection callDirType;
  private Integer callWrapup;
//  private CallWrapupBO callWrapupBO;
  private TimeInterval period;
  private String description;
  private SearchCondition.Comparator descriptionComparator;

  /**
   * Minimal length in seconds
   */
  private Integer minimalLength;

  /**
   * Maximal length in seconds
   */
  private Integer maximalLength;
  private ServerSideDate daytimeFrom;
  private ServerSideDate daytimeTo;
  private Set<Weekdays> weekdays;
  private Boolean allowCallSelection;
  private Boolean allowCallReplacement;
  private Boolean allowCallFilling;
  private Boolean finishSubcriteriaFirst;
  private ServerSideDate deadline;
  private Integer reminderDays;
  private ServerSideDate reminderDate;
  private ServerSideDate remindedOn;
  private Integer ordering;
  private boolean randomOrder;

  private TrackableLinkedHashSet<ExternalDataBO> externalDataSet;
  private TrackableLinkedHashSet<MediaLimitBO> mediaLimitSet;
  private TrackableLinkedHashSet<SubevaluationBO> subevaluationSet;

  public CriteriaBO() {
    externalDataSet = new TrackableLinkedHashSet<ExternalDataBO>();
    mediaLimitSet = new TrackableLinkedHashSet<MediaLimitBO>();
    subevaluationSet = new TrackableLinkedHashSet<SubevaluationBO>();
    weekdays = new ReplaceableLinkedHashSet<Weekdays>();
    randomOrder = true;
    ordering = 0;
    allowCallFilling = true;
    allowCallSelection = false;
    allowCallReplacement = false;
    finishSubcriteriaFirst = true;
    callDirType = CallDirection.ALL;
    isMain = false;
  }

  /**
   * Copy constructor. Expect {@link #subevaluationSet} and {@link #mediaLimitSet} all fields are copied
   * @param sourceCriteria
   */
  public CriteriaBO(CriteriaBO sourceCriteria) {
    this();
    setAllowCallFilling(sourceCriteria.getAllowCallFilling());
    setAllowCallReplacement(sourceCriteria.getAllowCallReplacement());
    setAllowCallSelection(sourceCriteria.getAllowCallSelection());
    setCallDirectionEnum(sourceCriteria.getCallDirectionEnum());
//    setCallWrapupBO(sourceCriteria.getCallWrapupBO());
    setDaytimeFrom(sourceCriteria.getDaytimeFrom());
    setDaytimeTo(sourceCriteria.getDaytimeTo());
    setDeadline(sourceCriteria.getDeadline());
    setDescription(sourceCriteria.getDescription());
    setDescriptionComparatorEnum(sourceCriteria.getDescriptionComparatorEnum());
    setEvaluationBO(sourceCriteria.getEvaluationBO());
    setExternalData(sourceCriteria.getExternalData());
    setFinishSubcriteriaFirst(sourceCriteria.getFinishSubcriteriaFirst());
    setMaximalLength(sourceCriteria.getMaximalLength());
    setMinimalLength(sourceCriteria.getMinimalLength());
    setOrdering(sourceCriteria.getOrdering());
    setPeriodFrom(sourceCriteria.getPeriodFrom());
    setPeriodTo(sourceCriteria.getPeriodTo());
    setRandomOrder(sourceCriteria.getRandomOrder());
    setRemindedOn(sourceCriteria.getRemindedOn());
    setReminderDate(sourceCriteria.getReminderDate());
    setReminderDays(sourceCriteria.getReminderDays());
    setWeekdaysEnum(sourceCriteria.getWeekdaysEnum());
  }

  @Override
  public boolean hasAnythingChanged() {
    boolean changed = super.hasAnythingChanged();
    for (ExternalDataBO externalDataBO: externalDataSet) {
      changed |= externalDataBO.hasAnythingChanged();
    }
    for (MediaLimitBO mediaLimitBO: mediaLimitSet) {
      changed |= mediaLimitBO.hasAnythingChanged();
    }
    for (SubevaluationBO subevaluationBO: subevaluationSet) {
      changed |= subevaluationBO.hasAnythingChanged();
    }
    return changed;
  }

  public EvaluationBO getEvaluationBO() {
    return evaluationBO;
  }

  public void setEvaluationBO(EvaluationBO evaluationBO) {
    this.evaluationBO = evaluationBO;
    if (evaluationBO != null) {
      markChanged(evaluationid, evaluationBO.getEvaluationid());
      evaluationid = evaluationBO.getEvaluationid();
    } else {
      markChanged(evaluationid, null);
      evaluationid = null;
    }
  }


  public SearchCondition.Comparator getDescriptionComparatorEnum() {
    return descriptionComparator;
  }


  public void setDescriptionComparatorEnum(SearchCondition.Comparator descriptionComparator) {
    this.descriptionComparator = descriptionComparator;
  }

  public String getDescriptionComparator() {
    return descriptionComparator == null ? null : descriptionComparator.toString();
  }


  public void setDescriptionComparator(String descriptionComparator) {
    if (descriptionComparator != null) {
      this.descriptionComparator = SearchCondition.Comparator.valueOf(descriptionComparator);
    }
    else {
      this.descriptionComparator = null;
    }
  }


  public void addSubevaluation(SubevaluationBO subevaluationBO) {
    subevaluationBO.setCriteriaBO(this);
    subevaluationSet.add(subevaluationBO);
  }

  public void removeSubevaluation(SubevaluationBO subevaluationBO) {
    subevaluationBO.setCriteriaBO(null);
    subevaluationSet.remove(subevaluationBO);
  }

  public Set<SubevaluationBO> getSubevaluations() {
    return subevaluationSet;
  }


  public void setDescription(String value) {
    this.description = value;
  }


  public String getDescription() {
    return description;
  }

  // used by iBatis

  @SuppressWarnings("unused")
  private void setSubevaluationSet(Collection<SubevaluationBO> subevaluationSet) {
    if (subevaluationSet != null) {
      this.subevaluationSet = new TrackableLinkedHashSet<SubevaluationBO>(subevaluationSet);
    }
  }

  public void setCallDirectionEnum(CallDirection direction) {
    markChanged(this.callDirType, direction);
    this.callDirType = direction;
  }

  public CallDirection getCallDirectionEnum() {
    return callDirType;
  }

//  public CallWrapupBO getCallWrapupBO() {
//    return callWrapupBO;
//  }

//  public void setCallWrapupBO(CallWrapupBO callWrapupBO) {
//    this.callWrapupBO = callWrapupBO;
//    if (callWrapupBO != null) {
//      markChanged(callWrapup, callWrapupBO.getCallwrapupid());
//      callWrapup = callWrapupBO.getCallwrapupid();
//    } else {
//      markChanged(callWrapup, null);
//      callWrapup = null;
//    }
//  }

  public boolean isWeekdaysEmpty(){
    return weekdays.isEmpty();
  }

  public Set<Weekdays> getWeekdaysEnum() {
    return this.weekdays;
  }

  public void setWeekdaysEnum(Set<Weekdays> weekdays) {
    markChanged(Boolean.TRUE);
    this.weekdays = weekdays;
  }

  public void addMediaLimit(MediaLimitBO limit) {
    boolean changed = mediaLimitSet.add(limit);
    markChanged(changed);
  }

  public void removeMediaLimit(MediaLimitBO limit) {
    boolean changed = mediaLimitSet.remove(limit);
    markChanged(changed);
  }

  public void addExternalData(ExternalDataBO data) {
    boolean changed = externalDataSet.add(data);
    markChanged(changed);
  }

  public void removeExternalData(ExternalDataBO data) {
    boolean changed = externalDataSet.remove(data);
    markChanged(changed);
  }

  public Set<ExternalDataBO> getExternalData() {
    return externalDataSet;
  }

  public void setExternalData(Set<ExternalDataBO> externalDataSet) {
    this.externalDataSet.clear();
    this.externalDataSet.addAll(externalDataSet);
  }

  public Set<MediaLimitBO> getMediaLimit() {
    return mediaLimitSet;
  }

  public void setMediaLimit(Set<MediaLimitBO> mediaLimitSet) {
    this.mediaLimitSet.clear();
    this.mediaLimitSet.addAll(mediaLimitSet);
  }

  @Override
  public Integer getId() {
    return getCriteriaid();
  }

  @Override
  public void setId(Integer value) {
    setCriteriaid(value);
  }

  // GETTERS, SETTERS ##################################################################################################

  public Integer getCriteriaid() {
    return criteriaid;
  }

  // used by ibatis
  public void setCriteriaid(Integer criteriaid) {
    if (isLocked()) {
        throw new UnsupportedOperationException("You cannot set a primary key");
    }
    this.criteriaid = criteriaid;
    for (SubevaluationBO subevaluationBO: getSubevaluations()) {
        subevaluationBO.setCriteriaBO(this);
    }
  }

  public Integer getEvaluationid() {
    return evaluationid;
  }

  // used by ibatis
  @SuppressWarnings({"unused"})
  private void setEvaluationid(Integer evaluationid) {
    if (isLocked()) {
        throw new UnsupportedOperationException("You cannot set a foreign key");
    }
    this.evaluationid = evaluationid;
  }

  public Boolean getIsMain() {
    return isMain;
  }

  public void setIsMain(Boolean isMain) {
    markChanged(this.isMain, isMain);
    this.isMain = isMain;
  }

  public Date getPeriodFrom() {
    return periodFrom;
  }

  public void setPeriodFrom(Date periodFrom) {
    ServerSideDate serverSideDate = ServerSideDateSupport.createServerSideDate(periodFrom);
    markChanged(this.periodFrom, serverSideDate);
    this.periodFrom = serverSideDate;
  }

  public Date getPeriodTo() {
    return periodTo;
  }

  public void setPeriodTo(Date periodTo) {
    ServerSideDate serverSideDate = ServerSideDateSupport.createServerSideDate(periodTo);
    markChanged(this.periodTo, serverSideDate);
    this.periodTo = serverSideDate;
  }

  public Integer getTotalEvents() {
    return totalEvents;
  }

  public void setTotalEvents(Integer totalEvents) {
    markChanged(this.totalEvents, totalEvents);
    this.totalEvents = totalEvents;
  }

  // used by ibatis
  protected String getCallDirType() {
    return callDirType.toString();
  }

  public CallDirection getCallDirection(){
    return callDirType;
  }

  // used by ibatis
  @SuppressWarnings({"unused"})
  private void setCallDirType(String callDirType) {
    if (isLocked()) {
        throw new UnsupportedOperationException("You cannot set this field");
    }
    this.callDirType = CallDirection.valueOf(callDirType);
  }

  public Integer getCallWrapup() {
    return callWrapup;
  }

  // used by ibatis
  @SuppressWarnings({"unused"})
  private void setCallWrapup(Integer callWrapup) {
    if (isLocked()) {
        throw new UnsupportedOperationException("You cannot set this field");
    }
    this.callWrapup = callWrapup;
  }

//  private CallWrapupBO getCallWrapupIbatis(){
//    if (callWrapupBO == null) {
//      callWrapupBO = new CallWrapupBO();
//    }
//    return callWrapupBO;
//  }
//
//  private void setCallWrapupIbatis(CallWrapupBO wrapup){
//    setCallWrapupBO(wrapup);
//  }

  public Integer getMinimalLength() {
    return minimalLength;
  }

  public void setMinimalLength(Integer minimalLength) {
    markChanged(this.minimalLength, minimalLength);
    this.minimalLength = minimalLength;
  }

  public Integer getMaximalLength() {
    return maximalLength;
  }

  public void setMaximalLength(Integer maximalLength) {
    markChanged(this.maximalLength, maximalLength);
    this.maximalLength = maximalLength;
  }

  public Date getDaytimeFrom() {
    return daytimeFrom;
  }

  public void setDaytimeFrom(Date daytimeFrom) {
    ServerSideDate serverSideDate = ServerSideDateSupport.createServerSideDate(daytimeFrom);
    markChanged(this.daytimeFrom, serverSideDate);
    this.daytimeFrom = serverSideDate;
  }

  public Date getDaytimeTo() {
    return daytimeTo;
  }

  public void setDaytimeTo(Date daytimeTo) {
    ServerSideDate serverSideDate = ServerSideDateSupport.createServerSideDate(daytimeTo);
    markChanged(this.daytimeTo, serverSideDate);
    this.daytimeTo = serverSideDate;
  }

  public String getWeekdays() {
    return toString(weekdays);
  }

  @SuppressWarnings("unused")
  private  void setWeekdays(String strWeekdays) {
    weekdays = toWeekdays(strWeekdays);
  }

  public Boolean getAllowCallSelection() {
    return allowCallSelection;
  }

  public void setAllowCallSelection(Boolean allowCallSelection) {
    markChanged(this.allowCallSelection, allowCallSelection);
    this.allowCallSelection = allowCallSelection;
  }

  public Boolean getAllowCallReplacement() {
    return allowCallReplacement;
  }

  public void setAllowCallReplacement(Boolean allowCallReplacement) {
    markChanged(this.allowCallReplacement, allowCallReplacement);
    this.allowCallReplacement = allowCallReplacement;
  }

  public Boolean getAllowCallFilling() {
    return allowCallFilling;
  }

  public void setAllowCallFilling(Boolean allowCallFilling) {
    markChanged(this.allowCallFilling, allowCallFilling);
    this.allowCallFilling = allowCallFilling;
  }

  public Boolean getFinishSubcriteriaFirst() {
    return finishSubcriteriaFirst;
  }

  public void setFinishSubcriteriaFirst(Boolean finishSubcriteriaFirst) {
    markChanged(this.finishSubcriteriaFirst, finishSubcriteriaFirst);
    this.finishSubcriteriaFirst = finishSubcriteriaFirst;
  }

  public Date getDeadline() {
    return deadline;
  }

  public void setDeadline(Date deadline) {
    ServerSideDate serverSideDate = ServerSideDateSupport.createServerSideDate(deadline);
    markChanged(this.deadline, serverSideDate);
    this.deadline = serverSideDate;
  }

  public Integer getReminderDays() {
    return reminderDays;
  }

  public void setReminderDays(Integer reminderDays) {
    markChanged(this.reminderDays, reminderDays);
    this.reminderDays = reminderDays;
  }

  public Date getReminderDate() {
    return reminderDate;
  }

  public void setReminderDate(Date reminderDate) {
    ServerSideDate serverSideDate = ServerSideDateSupport.createServerSideDate(reminderDate);
    markChanged(this.reminderDate, serverSideDate);
    this.reminderDate = serverSideDate;
  }

  public Date getRemindedOn() {
    return remindedOn;
  }

  public void setRemindedOn(Date remindedOn) {
    ServerSideDate serverSideDate = ServerSideDateSupport.createServerSideDate(remindedOn);
    markChanged(this.remindedOn, serverSideDate);
    this.remindedOn = serverSideDate;
  }

  public Integer getOrdering() {
    return ordering;
  }

  public void setOrdering(Integer ordering) {
    markChanged(this.ordering, ordering);
    this.ordering = ordering;
  }

  public boolean getRandomOrder() {
    return randomOrder;
  }

  public void setRandomOrder(boolean randomOrder) {
    markChanged(this.randomOrder, randomOrder);
    this.randomOrder = randomOrder;
  }

  public String getPeriod() {
    if(period!=null){
    return period.toString();
    }
    return null;
  }

  public void setPeriod(String period) {
    if(period==null){
      this.period=null;
      return;
    }
        if (isLocked()) {
            throw new UnsupportedOperationException("You cannot set this field");
        }
    this.period = TimeInterval.valueOf(period);
  }

  public TimeInterval getPeriodEnum(){
    return period;
  }

  public void setPeriodEnum(TimeInterval period){
    markChanged(this.period, period);
    this.period=period;

  }



  @Override
  public int hashCode() {
    return ((getCriteriaid() == null) ? 0 : getCriteriaid().hashCode());
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
        return true;
    }
    if (!(obj instanceof CriteriaBO)) {
        return false;
    }
    CriteriaBO other = (CriteriaBO) obj;
    return !(getCriteriaid() == null || other.getCriteriaid() == null) && getCriteriaid().equals(other.getCriteriaid());
  }


  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append("CriteriaBO");
    sb.append("{id=").append(criteriaid);
    sb.append('}');
    return sb.toString();
  }

  @Override
  public int compareTo(CriteriaBO criteriaBO) {
    return criteriaBO.ordering;
  }

  @Override
   public void lock() {
    super.lock();
    subevaluationSet.lock();
    mediaLimitSet.lock();
    externalDataSet.lock();
  }

  @Override
  public BaseBean newInstance() {
    return new CriteriaBO();
  }

    @Override
  public List<SearchOrder> defaultSortOrder() {
    List<SearchOrder> ordering = new ArrayList<SearchOrder>();
    ordering.add(new SearchOrder(Fields.IS_MAIN, SearchOrder.Direction.DESC));
    ordering.add(new SearchOrder(Fields.PERIOD_FROM, SearchOrder.Direction.DESC));
    ordering.add(new SearchOrder(Fields.PERIOD_TO, SearchOrder.Direction.DESC));
    return ordering;
  }

}
