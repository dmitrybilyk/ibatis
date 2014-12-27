package com.ibatis.scorecardmodel.bo.question;

import com.google.common.collect.Range;
import com.ibatis.scorecardmodel.BaseBean;
import com.ibatis.scorecardmodel.bo.ReplaceableLinkedHashSet;
import com.ibatis.scorecardmodel.bo.ServerSideDate;
import com.ibatis.scorecardmodel.bo.ServerSideDateSupport;
import com.ibatis.scorecardmodel.bo.TrackableLinkedHashSet;
import com.ibatis.scorecardmodel.bo.interaction.ScorecardUseStatus;
import com.ibatis.scorecardmodel.bo.user.CompanyBO;
import com.ibatis.scorecardmodel.bo.user.UserBO;
import com.ibatis.search.SearchCondition;
import com.ibatis.search.SearchOrder;
import org.jetbrains.annotations.NotNull;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "questforms")
public class QuestformBO extends QuestformBaseBean {
  /**
   * Comparator that compares integers their natural way (ascending),
   * but considers nulls greater (last) than any actual integer value.
   * Used for order comparators in {@link QuestiongroupBO}, {@link QuestionBO} and {@link AnswerBO}.
   */
  static final Comparator<Integer> NULL_LAST_INTEGER_COMPARATOR =
          new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
              if (o1 == null) {
                return o2 == null ? 0 : 1;
              } else if (o2 == null) {
                return -1;
              } else {
                return o1.compareTo(o2);
              }
            }
          };
  private static final long serialVersionUID = 6918370518587244219L;

  private static final Comparator<Double> HIGHER_SCORE_BETTER = new Comparator<Double>() {
    @Override
    public int compare(Double score1, Double score2) {
      return score1.compareTo(score2);
    }
  };
  private static final Comparator<Double> LOWER_SCORE_BETTER = new Comparator<Double>() {
    @Override
    public int compare(Double score1, Double score2) {
      return score2.compareTo(score1);
    }
  };

  public enum Fields {
    QUESTFORM_ID("qformid"), QUESTFORM_NAME("qformname"), VERSION("version"), ACCESSIBILITY("accessibility"),
    SCORING_SYSTEM("scoringSystemEnum"), DETAILED_REPORT("detailedReport"), SORTING("sorting"),
    EXACT_NUMBERS("exactNumbers"), SIP("sipnumber"), EXTENSION_LENGTH("extensionLength"),
    INTERNAL_CALLS_SCORING("internalCallsScoring"), MATCHING_PART("matchingPart"), WEEKDAYS("weekdays"),
    TIME_FROM("timeFrom"), TIME_TO("timeTo"), MIN_CALL_LENGTH("minCallLength"),
    MAX_CALL_LENGTH("maxCallLength"), QUESTFORM_DESC("description"), COMPANY("company"),
    QUESTFORM_COPYOF("copyof"), COMPLETED("completed"), TYPE("typeEnum");
    private final String fieldName;

    Fields(final String fieldName) {
      this.fieldName = fieldName;
    }

    public String getFieldName() {
      return fieldName;
    }

    public static Fields getField(String fieldName) {
      for (Fields field : Fields.values())
        if (field.fieldName.equals(fieldName))
          return field;
      return null;
    }
  }

  public enum Accessibility {
    ALLOW_ALL, DENY_ALL, ALLOW_SELECTED, DENY_SELECTED
  }

  public enum ScoringSystem {
    /**
     * Answer score is defined as percentage between -100% and 100% (both inclusive).
     * Questions and groups weights are percentage between 0% (exclusive) and 100% (inclusive).
     */
    PERCENTAGE(Range.closed(-100d, 100d), Range.openClosed(0d, 100d)) {
      @Override
      public Comparator<Double> getScoreComparator(@NotNull QuestformBO questform) {
        return HIGHER_SCORE_BETTER;
      }

      @Override
      protected double getBestAttainableScore(@NotNull QuestformBO questform) {
        return 100d;
      }

      @Override
      protected double getWorstAttainableScore(@NotNull QuestformBO questform) {
        return 0d;
      }
    },
    /**
     * Answer score is a grade between {@value #MARK_OPEN_LIMIT_MAX} (exclusive)
     * and {@value #MARK_NORMAL_RANGE_MIN} (inclusive).
     * Questions and groups weights are percentage between 0% (exclusive) and 100% (inclusive).
     */
    GRADES(Range.openClosed(MARK_OPEN_LIMIT_MAX, MARK_NORMAL_RANGE_MIN), Range.openClosed(0d, 100d)) {
      @Override
      public Comparator<Double> getScoreComparator(@NotNull final QuestformBO questform) {
        return questform.isLowerGradeBetter() ? LOWER_SCORE_BETTER : HIGHER_SCORE_BETTER;
      }

      @Override
      protected double getBestAttainableScore(@NotNull QuestformBO questform) {
        return questform.isLowerGradeBetter() ? MARK_NORMAL_RANGE_MAX : MARK_NORMAL_RANGE_MIN;
      }

      @Override
      protected double getWorstAttainableScore(@NotNull QuestformBO questform) {
        return questform.isLowerGradeBetter() ? MARK_NORMAL_RANGE_MIN : MARK_NORMAL_RANGE_MAX;
      }
    },
    /**
     * Answer score is any integer number of points (can be negative).
     * There is no question weight as such - answer weights are used.
     */
    POINTS(Range.<Double>all(), Range.greaterThan(0.0)) {
      @Override
      public Comparator<Double> getScoreComparator(@NotNull QuestformBO questform) {
        return HIGHER_SCORE_BETTER;
      }

      @Override
      protected double getBestAttainableScore(@NotNull QuestformBO questform) {
        double currentMax = 0;
        for (QuestiongroupBO questiongroupBO : questform.getQuestionGroups()) {
          for (QuestionBO questionBO : questiongroupBO.getQuestions()) {
            double questionMax = Double.NEGATIVE_INFINITY;
            for (AnswerBO answerBO : questionBO.getAnswers()) {
              if (answerBO.getComplianceEnum().isScored()) {
                if (Double.compare(questionMax, answerBO.getAnswerValue()) < 0) {
                  questionMax = answerBO.getAnswerValue();
                }
              }
            }
            currentMax += questionMax == Double.NEGATIVE_INFINITY ? 0 : questionMax;
          }
        }
        return currentMax;
      }

      @Override
      protected double getWorstAttainableScore(@NotNull QuestformBO questform) {
        double currentMin = 0;
        for (QuestiongroupBO questiongroupBO : questform.getQuestionGroups()) {
          for (QuestionBO questionBO : questiongroupBO.getQuestions()) {
            double questionMin = Double.POSITIVE_INFINITY;
            for (AnswerBO answerBO : questionBO.getAnswers()) {
              if (answerBO.getComplianceEnum().isScored()) {
                if (Double.compare(questionMin, answerBO.getAnswerValue()) > 0) {
                  questionMin = answerBO.getAnswerValue();
                }
              }
            }
            currentMin += questionMin == Double.POSITIVE_INFINITY ? 0 : questionMin;
          }
        }
        return currentMin;
      }
    };
    private final Range<Double> answerScoreRange;
    private final Range<Double> questionWeightRange;

    ScoringSystem(@NotNull Range<Double> answerScoreRange, @NotNull Range<Double> questionWeightRange) {
      this.answerScoreRange = answerScoreRange;
      this.questionWeightRange = questionWeightRange;
    }

    public abstract Comparator<Double> getScoreComparator(@NotNull QuestformBO questform);

    /**
     * Range of valid values for an answer score. (Minimum is not necessarily the worst score and vice-versa.)
     */
    @NotNull
    public Range<Double> getAnswerScoreRange() {
      return answerScoreRange;
    }

    /**
     * Range of valid values for a weight of question or question group.
     */
    @NotNull
    public Range<Double> getQuestionWeightRange() {
      return questionWeightRange;
    }

    /**
     * @param questform a questionnaire
     * @return the highest attainable score for this questionnaire and scoring system.
     */
    protected abstract double getBestAttainableScore(@NotNull QuestformBO questform);

    /**
     * @param questform a questionnaire
     * @return the highest attainable score for this questionnaire and scoring system.
     */
    protected abstract double getWorstAttainableScore(@NotNull QuestformBO questform);

  }

  public enum Sorting {
    CREATION_TIME, WEIGHT_ASC, WEIGHT_DESC
  }

  /**
   * The intended type of the questform
   */
  public enum Type {
    /**
     * Quality management - reviewing and evaluating agent interactions.
     * Doesn't support media files for questions, supports only regular question type and all answer compliance.
     * Questions are sorted by their name.
     */
    QUALITY(ScorecardUseStatus.EVALUATION,
            false, EnumSet.of(QuestionBO.QuestionType.REGULAR_QUESTION),
            QuestionBO.Fields.TEXT, false,
            false, EnumSet.of(AnswerBO.Compliance.NOT_APPLICABLE, AnswerBO.Compliance.NONE,
            AnswerBO.Compliance.SUCCESS_GROUP, AnswerBO.Compliance.FAIL_GROUP, AnswerBO.Compliance.SUCCESS_ALL, AnswerBO.Compliance.FAIL_ALL)),
    /**
     * Surveying - allowing customers to provide direct feedback.
     * Supports media files for questions, all question types and only None and N/A answer compliance.
     * Doesn't enforce sorting of questions, leaving them ordered by user.
     */
    SURVEY(ScorecardUseStatus.SURVEY,
            true, EnumSet.of(QuestionBO.QuestionType.REGULAR_QUESTION, QuestionBO.QuestionType.CUSTOMER_FEEDBACK, QuestionBO.QuestionType.PROMPT_ONLY),
            QuestionBO.Fields.ORDER, false,
            true, EnumSet.of(AnswerBO.Compliance.NOT_APPLICABLE, AnswerBO.Compliance.NONE)),
    /**
     * Training - agents answering the questions as a form of training (e-learning).
     * Doesn't support media files for questions, supports only regular question type, and only None answer compliance.
     * Questions are sorted by their name.
     */
    TRAINING(ScorecardUseStatus.TRAINING,
            false, EnumSet.of(QuestionBO.QuestionType.REGULAR_QUESTION),
            QuestionBO.Fields.TEXT, false,
            false, EnumSet.of(AnswerBO.Compliance.NOT_APPLICABLE, AnswerBO.Compliance.NONE));

    private final ScorecardUseStatus useStatus;
    private final boolean questionMediaFileSupported;
    private final Set<QuestionBO.QuestionType> questionTypes;
    private final QuestionBO.Fields questionOrder;
    private final boolean questionOrderReverse;
    private final boolean answerKeySupported;
    private final Set<AnswerBO.Compliance> answerCompliance;

    Type(@NotNull ScorecardUseStatus useStatus,
         boolean questionMediaFileSupported, @NotNull Set<QuestionBO.QuestionType> questionTypes,
         @NotNull QuestionBO.Fields questionOrder, boolean questionOrderReverse,
         boolean answerKeySupported, @NotNull Set<AnswerBO.Compliance> answerCompliance) {
      this.useStatus = useStatus;
      this.questionMediaFileSupported = questionMediaFileSupported;
      this.questionTypes = Collections.unmodifiableSet(questionTypes);
      this.questionOrder = questionOrder;
      this.questionOrderReverse = questionOrderReverse;
      this.answerKeySupported = answerKeySupported;
      this.answerCompliance = Collections.unmodifiableSet(answerCompliance);
    }

    /**
     * When there's an evaluation in ScoreCARD for a questionnaire of this type,
     * the evaluated segments (couples) are marked in CallREC with this {@link ScorecardUseStatus} flag.
     *
     * @return the flag that segments are marked with
     */
    @NotNull
    public ScorecardUseStatus getScorecardUseStatus() {
      return useStatus;
    }

    /**
     * Whether media files are supported for questions in a questionnaire of this type.
     *
     * @return true if media files are supported; false otherwise
     */
    public boolean isQuestionMediaFileSupported() {
      return questionMediaFileSupported;
    }

    /**
     * Question types supported for this questionnaire type.
     *
     * @return a non-empty unmodifiable set of supported question types
     * with predictable iteration order and the first element being the default question type
     */
    @NotNull
    public Set<QuestionBO.QuestionType> getQuestionTypes() {
      return questionTypes;
    }

    /**
     * QuestionBO field to order questions by.
     *
     * @return the field to order questions by; or null if order should not be enforced
     */
    @NotNull
    public QuestionBO.Fields getQuestionOrder() {
      return questionOrder;
    }

    /**
     * Whether questions should be ordered by {@link #getQuestionOrder()} in descending order.
     * This has no effect if {@link #getQuestionOrder()} is null.
     *
     * @return true to use reverse (descending) order; false to use default (ascending) order
     */
    public boolean isQuestionOrderReverse() {
      return questionOrderReverse;
    }

    /**
     * Whether keys are supported for answers in a questionnaire of this type.
     *
     * @return true if answer keys are supported; false otherwise
     */
    public boolean isAnswerKeySupported() {
      return answerKeySupported;
    }

    /**
     * Answer compliance supported for this questionnaire type.
     *
     * @return a non-empty unmodifiable set of supported answer compliance
     * with predictable iteration order and the first element being the default answer compliance
     */
    @NotNull
    public Set<AnswerBO.Compliance> getAnswerCompliance() {
      return answerCompliance;
    }
  }

  public static final double MARK_OPEN_LIMIT_MAX = 0;
  public static final double MARK_NORMAL_RANGE_MAX = 1;
  public static final double MARK_NORMAL_RANGE_MIN = 5;

  @Id
  @GeneratedValue
  private Integer qformid;
  private String qformname;
  private String version;
  private Accessibility accessibility;
  @Column(name = "scoring_system")
  private ScoringSystem scoringSystem;
  @Column(name = "detailed_report")
  private Boolean detailedReport;
  private Sorting sorting;
  @Column(name = "exact_numbers")
  private Boolean exactNumbers;
  private Boolean sipnumber;
  @Column(name = "extension_length")
  private Integer extensionLength;
  @Column(name = "internal_calls_scoring")
  private Boolean internalCallsScoring;
  @Column(name = "matching_part")
  private SearchCondition.Comparator matchingPart;
  @Transient
  private Set<Weekdays> weekdays;
  @Column(name = "time_from")
  private ServerSideDate timeFrom;
  @Column(name = "time_to")
  private ServerSideDate timeTo;
  @Column(name = "min_call_length")
  private Integer minCallLength;
  @Column(name = "max_call_length")
  private Integer maxCallLength;
  private String description;
  private Integer company;
  @Transient
  private CompanyBO companyBO;
  @Transient
  private Integer copyof;
  private Boolean completed;
  @Column(name = "report_weight")
  private Double reportWeight;

  @OneToMany(mappedBy = "questformBO")
  private Set<QuestiongroupBO> groups;
  @Transient
  private boolean lowerGradeBetter;
  @Column(name = "qftype")
  private Type type;

  public QuestformBO() {
    groups = new TrackableLinkedHashSet<QuestiongroupBO>();
    weekdays = new ReplaceableLinkedHashSet<Weekdays>(EnumSet.allOf(Weekdays.class));

    /**** DEFAUL VALUES ***/
    qformname = "";
    description = "";
    scoringSystem = ScoringSystem.PERCENTAGE;
    accessibility = Accessibility.ALLOW_ALL;
    version = "1.0";
    detailedReport = true;
    sorting = Sorting.CREATION_TIME;
    exactNumbers = true;
    sipnumber = false;
    extensionLength = 4;
    internalCallsScoring = false;
    matchingPart = SearchCondition.Comparator.CONTAINS_IGNORE_CASE;
    completed = false;
    reportWeight = 1.0;
    lowerGradeBetter = true;
    type = Type.QUALITY;
  }

  @Override
  public void lock() {
    super.lock();
    if (groups instanceof TrackableLinkedHashSet) {
      ((TrackableLinkedHashSet) groups).lock();
    }
  }

  @Override
  public boolean hasAnythingChanged() {
    boolean changed = super.hasAnythingChanged();
    changed |= hasPermissionsChanged();
    changed |= hasPropertiesChanged();
    changed |= hasCSRulesChanged();
    if (groups instanceof TrackableLinkedHashSet) {
      changed |= ((TrackableLinkedHashSet) groups).hasAnythingChanged();
    }
    return changed;
  }

  /**
   * Checks whether collections contains same user. If true mark questForm as changed
   * @param oldSet
   * @param newSet
   */
  public void markPermissionsChanged(Set<UserBO> oldSet, Set<UserBO> newSet) {
    boolean changed = markChanged(oldSet, newSet);
    markPermissionsChanged(changed);
  }

  public boolean hasPreventChangesChanged() {
    if (groups instanceof TrackableLinkedHashSet) {
      return isPreventChangesWhenQformUsedInEvaluation() || ((TrackableLinkedHashSet) groups).hasAnythingChanged();
    }
    return isPreventChangesWhenQformUsedInEvaluation();
  }

  public boolean hasCSRulesChanged() {
    return isCsRulesChanged();
  }

  public boolean hasPropertiesChanged() {
    return isPropertiesChanged();
  }

  public boolean hasPermissionsChanged() {
    return isPermissionsChanged();
  }

  public Set<QuestiongroupBO> getQuestionGroups() {
    return groups;
  }

  public boolean addQuestionGroup(QuestiongroupBO questiongroupBO) {
    questiongroupBO.setQuestformBO(this);
    return groups.add(questiongroupBO);
  }

  public void addQuestionGroups(Set<QuestiongroupBO> questiongroupsBO) {
    for (QuestiongroupBO questiongroupBO : questiongroupsBO)
      addQuestionGroup(questiongroupBO);
  }

  void setGroups(List<QuestiongroupBO> questiongroups) {
    groups = new TrackableLinkedHashSet<QuestiongroupBO>(questiongroups);
  }

  List<QuestiongroupBO> getGroups() {
    return new ArrayList<QuestiongroupBO>(groups);
  }


  public void removeQuestionGroup(QuestiongroupBO questiongroupBO) {
    groups.remove(questiongroupBO);
    questiongroupBO.setQuestformBO(null);
  }

  public void removeQuestionGroups(Set<QuestiongroupBO> questiongroupsBO) {
    for (QuestiongroupBO questiongroupBO : questiongroupsBO)
      removeQuestionGroup(questiongroupBO);
  }

  // userd by ibatis
  @SuppressWarnings("unused")
  private void setQuestionGroupList(List<QuestiongroupBO> questiongroups) {
    if (questiongroups == null)
      return;

    for (QuestiongroupBO questiongroupBO : questiongroups) {
      addQuestionGroup(questiongroupBO);
    }
  }

  // userd by ibatis
  @SuppressWarnings("unused")
  private Set<QuestiongroupBO> getQuestionGroupList() {
    return new HashSet<QuestiongroupBO>() {
      private static final long serialVersionUID = -3675859834170879383L;

      public boolean add(QuestiongroupBO e) {
        return addQuestionGroup(e);
      }
    };
  }

  @Override
  public Integer getId() {
    return getQformid();
  }

  @Override
  public void setId(Integer value) {
    setQformid(value);
  }

  public Integer getQformid() {
    return qformid;
  }

  //used by ibatis
  public void setQformid(Integer qformid) {
    if (isLocked()) {
      throw new UnsupportedOperationException("You cannot set a primary key");
    }
    this.qformid = qformid;
    for (QuestiongroupBO questiongroupBO : groups) {
      questiongroupBO.setQuestformBO(this);
    }
  }

  public String getQformname() {
    return qformname;
  }

  public void setQformname(String qformname) {
    boolean changed = markChanged(this.qformname, qformname == null ? "" : qformname.trim());
    markPreventChangesChanged(changed);
    this.qformname = qformname == null ? "" : qformname.trim();
  }

  public String getVersion() {
    return version;
  }

  public void setVersion(String version) {
    boolean changed = markChanged(this.version, version == null ? "" : version.trim());
    markPreventChangesChanged(changed);
    this.version = version == null ? "" : version.trim();
  }

  //used by ibatis
  @SuppressWarnings("unused")
  private String getAccessibility() {
    return accessibility == null ? "" : accessibility.toString();
  }

  //used by ibatis
  @SuppressWarnings("unused")
  private void setAccessibility(String accessibility) {
    setAccessibilityEnum(Accessibility.valueOf(accessibility));
  }

  public void setAccessibilityEnum(Accessibility accessibility) {
    boolean changed = markChanged(this.accessibility, accessibility);
    markPermissionsChanged(changed);
    this.accessibility = accessibility;
  }

  public Accessibility getAccessibilityEnum() {
    return accessibility;
  }

  //used by ibatis
  protected String getScoringSystem() {
    return scoringSystem == null ? "" : scoringSystem.toString();
  }

  public ScoringSystem getScoringSystemEnum() {
    return scoringSystem;
  }

  //used by ibatis
  @SuppressWarnings("unused")
  private void setScoringSystem(String scoringSystem) {
    setScoringSystemEnum(ScoringSystem.valueOf(scoringSystem));
  }

  public void setScoringSystemEnum(ScoringSystem scoringSystem) {
    boolean changed = markChanged(this.scoringSystem, scoringSystem);
    markPreventChangesChanged(changed);
    this.scoringSystem = scoringSystem;
  }

  public Boolean getDetailedReport() {
    return detailedReport;
  }

  public void setDetailedReport(Boolean detailedReport) {
    boolean changed = markChanged(this.detailedReport, detailedReport);
    markPropertiesChanged(changed);
    this.detailedReport = detailedReport;
  }

  //used by ibatis
  @SuppressWarnings("unused")
  private String getSorting() {
    return sorting == null ? "" : sorting.toString();
  }

  //used by ibatis
  @SuppressWarnings("unused")
  private void setSorting(String sorting) {
    setSortingEnum(Sorting.valueOf(sorting));
  }

  public void setSortingEnum(Sorting sorting) {
    boolean changed = markChanged(this.sorting, sorting);
    markPropertiesChanged(changed);
    this.sorting = sorting;
  }

  public Sorting getSortingEnum() {
    return sorting;
  }

  public Boolean getExactNumbers() {
    return exactNumbers;
  }

  public void setExactNumbers(Boolean exactNumbers) {
    boolean changed = markChanged(this.exactNumbers, exactNumbers);
    markCSRulesChanged(changed);
    this.exactNumbers = exactNumbers;
  }

  public Boolean getSipnumber() {
    return sipnumber;
  }

  public void setSipnumber(Boolean sipnumber) {
    boolean changed = markChanged(this.sipnumber, sipnumber);
    markCSRulesChanged(changed);
    this.sipnumber = sipnumber;
  }

  public Integer getExtensionLength() {
    return extensionLength;
  }

  public void setExtensionLength(Integer extensionLength) {
    boolean changed = markChanged(this.extensionLength, extensionLength);
    markCSRulesChanged(changed);
    this.extensionLength = extensionLength;
  }

  public Boolean getInternalCallsScoring() {
    return internalCallsScoring;
  }

  public void setInternalCallsScoring(Boolean internalCallsScoring) {
    boolean changed = markChanged(this.internalCallsScoring, internalCallsScoring);
    markCSRulesChanged(changed);
    this.internalCallsScoring = internalCallsScoring;
  }

  //used by ibatis
  @SuppressWarnings("unused")
  private String getMatchingPart() {
    return matchingPart.toString();
  }

  //used by ibatis
  @SuppressWarnings("unused")
  private void setMatchingPart(String matchingPart) {
    setMatchingPartEnum(SearchCondition.Comparator.valueOf(matchingPart));
  }

  public SearchCondition.Comparator getMatchingPartEnum() {
    return matchingPart;
  }

  public void setMatchingPartEnum(SearchCondition.Comparator matchingPart) {
    boolean changed = markChanged(this.matchingPart, matchingPart);
    markCSRulesChanged(changed);
    this.matchingPart = matchingPart;
  }

  public void setWeekdaysEnum(Set<Weekdays> weekdays) {
    boolean changed = markChanged(this.weekdays, weekdays);
    markCSRulesChanged(changed);
    this.weekdays = weekdays;
  }

  public Set<Weekdays> getWeekdaysEnum() {
    return weekdays;
  }

  public void addWeekday(Weekdays weekday) {
    boolean changed = weekdays.add(weekday);
    markChanged(changed);
  }

  public void removeWeekday(Weekdays weekday) {
    boolean changed = weekdays.remove(weekday);
    markChanged(changed);
  }

  public String getWeekdays() {
    return toString(weekdays);
  }

  //used by ibatis
  @SuppressWarnings("unused")
  private void setWeekdays(String strWeekdays) {
    weekdays = toWeekdays(strWeekdays);
  }

  public Date getTimeFrom() {
    return timeFrom;
  }

  public void setTimeFrom(Date timeFrom) {
    ServerSideDate serverSideDate = ServerSideDateSupport.createServerSideDate(timeFrom);
    boolean changed = markChanged(this.timeFrom, serverSideDate);
    markCSRulesChanged(changed);
    this.timeFrom = serverSideDate;
  }

  public Date getTimeTo() {
    return timeTo;
  }

  public void setTimeTo(Date timeTo) {
    ServerSideDate serverSideDate = ServerSideDateSupport.createServerSideDate(timeTo);
    boolean changed = markChanged(this.timeTo, serverSideDate);
    markCSRulesChanged(changed);
    this.timeTo = serverSideDate;
  }

  public Integer getMinCallLength() {
    return minCallLength;
  }

  public void setMinCallLength(Integer minCallLength) {
    boolean changed = markChanged(this.minCallLength, minCallLength);
    markCSRulesChanged(changed);
    this.minCallLength = minCallLength;
  }

  public Integer getMaxCallLength() {
    return maxCallLength;
  }

  public void setMaxCallLength(Integer maxCallLength) {
    boolean changed = markChanged(this.maxCallLength, maxCallLength);
    markCSRulesChanged(changed);
    this.maxCallLength = maxCallLength;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    markChanged(this.description, description == null ? "" : description.trim());
    this.description = description == null ? "" : description.trim();
  }

  public boolean isLowerGradeBetter() {
    return lowerGradeBetter;
  }

  //todo this must be loaded and saved from and to database
  public void setLowerGradeBetter(boolean lowerGradeBetter) {
    markChanged(this.lowerGradeBetter, lowerGradeBetter);
    this.lowerGradeBetter = lowerGradeBetter;
  }

  public String getType() {
    return type == null ? null : type.name();
  }

  public void setType(String type) {
    setTypeEnum(Type.valueOf(type));
  }

  /**
   * @return the intended type of this questform
   */
  public Type getTypeEnum() {
    return type;
  }

  public void setTypeEnum(Type type) {
    boolean changed = markChanged(this.type, type);
    markPreventChangesChanged(changed);
    this.type = type;
  }

  public Integer getCompany() {
    return company;
  }

  //used by ibatis
  @SuppressWarnings("unused")
  private void setCompany(Integer company) {
    if (isLocked())
      throw new UnsupportedOperationException("You cannot set a foreign key");
    this.company = company;
  }

  public void setCompanyBO(CompanyBO companyBO) {
    if (isLocked())
      throw new UnsupportedOperationException("You cannot change a company...");
    if (companyBO != null) {
      markChanged(company, companyBO.getCompanyid());
      company = companyBO.getCompanyid();
    }
    this.companyBO = companyBO;
  }

  public CompanyBO getCompanyBO() {
    return companyBO;
  }

  // used by ibatis
  @SuppressWarnings("unused")
  private void setCopyof(Integer copyof) {
    this.copyof = copyof;
  }

  // used by ibatis
  @SuppressWarnings("unused")
  private Integer getCopyof() {
    return copyof;
  }

  public void setCompleted(Boolean completed) {
    markChanged(this.completed, completed);
    this.completed = completed;
  }

  public Boolean getCompleted() {
    return completed;
  }

  public void setReportWeight(Double reportWeight) {
    boolean changed = markChanged(this.reportWeight, reportWeight);
    markPropertiesChanged(changed);
    this.reportWeight = reportWeight;
  }

  public Double getReportWeight() {
    return reportWeight;
  }

  @Override
  public int hashCode() {
    return (getQformid() == null) ? 0 : getQformid().hashCode();
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    return getQformid() != null
            && obj instanceof QuestformBO
            && getQformid().equals(((QuestformBO) obj).getQformid());
  }

  /**
   * Constructs a <code>String</code> with all attributes
   * in name = value format.
   *
   * @return a <code>String</code> representation
   * of this object.
   */
  public String toString() {
    final String tab = "\n    ";
    return new StringBuilder("\nQuestformBO (")
            .append(tab).append("qformid = ").append(this.qformid)
            .append(tab).append("qformname = ").append(this.qformname)
            .append(tab).append("version = ").append(this.version)
            .append(tab).append("accessibility = ").append(this.accessibility)
            .append(tab).append("scoringSystem = ").append(this.scoringSystem)
            .append(tab).append("detailedReport = ").append(this.detailedReport)
            .append(tab).append("sorting = ").append(this.sorting)
            .append(tab).append("exactNumbers = ").append(this.exactNumbers)
            .append(tab).append("sipnumber = ").append(this.sipnumber)
            .append(tab).append("extensionLength = ").append(this.extensionLength)
            .append(tab).append("internalCallsScoring = ").append(this.internalCallsScoring)
            .append(tab).append("matchingPart = ").append(this.matchingPart)
            .append(tab).append("weekdays = ").append(this.weekdays)
            .append(tab).append("timeFrom = ").append(this.timeFrom)
            .append(tab).append("timeTo = ").append(this.timeTo)
            .append(tab).append("minCallLength = ").append(this.minCallLength)
            .append(tab).append("maxCallLength = ").append(this.maxCallLength)
            .append(tab).append("description = ").append(this.description)
            .append(tab).append("company = ").append(this.company)
            .append(tab).append("copyof = ").append(this.copyof)
            .append(tab).append("type = ").append(this.type)
            .append(" )\n")
            .toString();
  }

  @Override
  public BaseBean newInstance() {
    return new QuestformBO();
  }

  @Override
  public List<SearchOrder> defaultSortOrder() {
    List<SearchOrder> ordering = new ArrayList<SearchOrder>();
    ordering.add(new SearchOrder(Fields.QUESTFORM_NAME, SearchOrder.Direction.ASC));
    ordering.add(new SearchOrder(Fields.VERSION, SearchOrder.Direction.DESC));
    return ordering;
  }

  public double getBestAttainableScore() {
    return scoringSystem.getBestAttainableScore(this);
  }

  public double getWorstAttainableScore() {
    return scoringSystem.getWorstAttainableScore(this);
  }

  public double getMaxAttainableScore() {
    double bestScore = getBestAttainableScore();
    double worstScore = getWorstAttainableScore();
    return Math.max(bestScore, worstScore);
  }

  public double getMinAttainableScore() {
    double bestScore = getBestAttainableScore();
    double worstScore = getWorstAttainableScore();
    return Math.min(bestScore, worstScore);
  }
}