package com.ibatis.scorecardmodel.bo.question;

import com.google.common.collect.Ordering;


import com.ibatis.scorecardmodel.BaseBean;
import com.ibatis.scorecardmodel.bo.ChartInfo;
import com.ibatis.scorecardmodel.bo.TrackableHolder;
import com.ibatis.scorecardmodel.bo.TrackableLinkedHashSet;
import com.ibatis.search.SearchOrder;

import javax.persistence.*;
import java.util.*;

import static com.google.common.base.Strings.isNullOrEmpty;

@Entity
@Table(name = "questions")
public class QuestionBO extends BaseBean implements ChartInfo {
  private static final long serialVersionUID = -2496044175797958205L;
  /**
   * Order by {@link #getQuestionOrder()}, nulls last.
   */
  public static final Comparator<QuestionBO> QUESTION_ORDER_COMPARATOR = new Comparator<QuestionBO>() {
    @Override
    public int compare(QuestionBO q1, QuestionBO q2) {
      return QuestformBO.NULL_LAST_INTEGER_COMPARATOR.compare(q1.getQuestionOrder(), q2.getQuestionOrder());
    }
  };
  /**
   * Guava Ordering equivalent of {@link #QUESTION_ORDER_COMPARATOR}.
   */
  public static final Ordering<QuestionBO> QUESTION_ORDERING = Ordering.from(QuestionBO.QUESTION_ORDER_COMPARATOR);

  public enum Fields {
    TEXT("questionText"), GROUP("groupid"), VALUE("questionValue"), ORDER("questionOrder"),
    DESC("description"), GROUP_BO("questiongroupBO"), MEDIA_FILE("smediafileid"), MEDIA_FILE_BO("mediaFileBO"),
    TYPE("questionTypeEnum");

    private final String fieldName;

    Fields(final String fieldName) {
      this.fieldName = fieldName;
    }

    public String getFieldName() {
      return fieldName;
    }

    public static Fields getField(String fieldName) {
      for (Fields field : Fields.values())
        if (field.fieldName.equals(fieldName)) {
          return field;
        }
      return null;
    }
  }

  public double getBestAttainablePointsScore() {
    double answerMax = Double.NEGATIVE_INFINITY;
    for (AnswerBO answerBO : this.getAnswers()) {
      if (answerBO.getComplianceEnum().isScored()) {
        if (Double.compare(answerMax, answerBO.getAnswerValue()) < 0) {
          answerMax = answerBO.getAnswerValue();
        }
      }
    }
    return answerMax;
  }


  public double getWorstAttainablePointsScore() {
    double answerMin = Double.POSITIVE_INFINITY;
    for (AnswerBO answerBO : this.getAnswers()) {
      if (answerBO.getComplianceEnum().isScored()) {
        if (Double.compare(answerMin, answerBO.getAnswerValue()) > 0) {
          answerMin = answerBO.getAnswerValue();
        }
      }
    }
    return answerMin;
  }

  public static enum QuestionType {
    /**
     * A regular question is a question format that limits respondents
     * with a list of answer choices from which they must choose to answer the question.
     */
    REGULAR_QUESTION(true, true),
    /**
     * Customer feedback questions do not constrain the respondent to a given set of answers
     * and allow the respondent to provide their own response in some way.
     * E.g., user can leave audio comment for IVR survey.
     */
    CUSTOMER_FEEDBACK(true, false),
    /**
     * Prompt-only questions are not questions as such.
     * They are providing some information to the user,
     * but neither expect, nor allow any answer from the respondent.
     */
    PROMPT_ONLY(false, false);

    private final boolean answerable;
    private final boolean scored;

    QuestionType(boolean answerable, boolean scored) {
      this.answerable = answerable;
      this.scored = scored;
    }

    /**
     * Signifies whether the question can have answers. If false, the question should not have any answers at all.
     * (A question that is not answerable should not be {@link #isScored() scored} either.)
     *
     * @return true if the question can have answers; false if the question should not have answers
     */
    public boolean isAnswerable() {
      return answerable;
    }

    /**
     * Signifies whether the question can affect the evaluation score.
     * If not scored, the question weight should be null (and it shouldn't have scored answers).
     *
     * @return true if this question can affect the evaluation score; false otherwise
     */
    public boolean isScored() {
      return scored;
    }
  }

  @Id
  @GeneratedValue
  private Integer questionid;
  @Column(name = "question_text")
  private String questionText;
  @Transient
  private Integer groupid;
  @ManyToOne
  @JoinColumn(name = "groupid")
  private QuestiongroupBO questiongroupBO;
  @Column(name = "question_value")
  private Double questionValue;
  @Column(name = "question_order")
  private Integer questionOrder;
  private String description;
  @Column(name = "question_type")
  private QuestionType questionType;

  private TrackableHolder<SMediaFileBO> mediaFileHolder;
  @OneToMany(mappedBy = "questionBO")
  private Set<AnswerBO> answers;

  public QuestionBO() {
    answers = new TrackableLinkedHashSet<AnswerBO>();
    mediaFileHolder = new TrackableHolder<SMediaFileBO>();
    questionText = "";
    description = "";
    questionType = QuestionType.REGULAR_QUESTION;
  }

  @Override
  public String getInfo() {
    String text = getQuestionText();
    return text.length() > 30 ? getQuestionText().substring(0, 27) + "..." : text;
  }

  @Override
  public void lock() {
    super.lock();
    if (answers instanceof TrackableLinkedHashSet) {
      ((TrackableLinkedHashSet) answers).lock();
    }
    mediaFileHolder.lock();
  }

  @Override
  public boolean hasAnythingChanged() {
    boolean changed = super.hasAnythingChanged();
    if (answers instanceof TrackableLinkedHashSet) {
      changed |= ((TrackableLinkedHashSet) answers).hasAnythingChanged();
    }
    changed |= mediaFileHolder.hasChanged();
    return changed;
  }

  public Set<AnswerBO> getAnswers() {
    return answers;
  }

  public boolean addAnswer(AnswerBO answerBO) {
    answerBO.setQuestionBO(this);
    return answers.add(answerBO);
  }

  public String getMediaFileName() {
    if (getMediaFileBO() != null) {
      return getMediaFileBO().getFilename();
    }
    return null;
  }

  public void addAnswers(Collection<AnswerBO> answersBO) {
    for (AnswerBO answerBO : answersBO)
      addAnswer(answerBO);
  }

  public void removeAnswer(AnswerBO answerBO) {
    answers.remove(answerBO);
    answerBO.setQuestionBO(null);
  }

  public void removeAnswers(Collection<AnswerBO> answersBO) {
    for (AnswerBO answerBO : answersBO)
      removeAnswer(answerBO);
  }

  // userd by ibatis
  @SuppressWarnings("unused")
  private void setAnswerList(List<AnswerBO> answers) {
    if (answers == null) {
      return;
    }

    for (AnswerBO answerBO : answers) {
      addAnswer(answerBO);
    }
  }

  // userd by ibatis
  @SuppressWarnings("unused")
  private Set<AnswerBO> getAnswerList() {
    return new HashSet<AnswerBO>() {
      private static final long serialVersionUID = -3473859834170879383L;

      public boolean add(AnswerBO e) {
        return addAnswer(e);
      }
    };
  }

  @Override
  public Integer getId() {
    return getQuestionid();
  }

  @Override
  public void setId(Integer value) {
    setQuestionid(value);
  }

  public Integer getQuestionid() {
    return questionid;
  }

  //used by ibatis
  public void setQuestionid(Integer questionid) {
    if (isLocked()) {
      throw new UnsupportedOperationException("You cannot set a primary key");
    }
    this.questionid = questionid;
    for (AnswerBO answerBO : answers) {
      answerBO.setQuestionBO(this);
    }
  }

  public String getQuestionText() {
    return questionText;
  }

  public void setQuestionText(String questionText) {
    markChanged(this.questionText, questionText == null ? "" : questionText.trim());
    this.questionText = questionText == null ? "" : questionText.trim();
  }

  public Integer getGroupid() {
    return groupid;
  }

  //used by ibatis
  @SuppressWarnings("unused")
  private void setGroupid(Integer groupid) {
    if (isLocked()) {
      throw new UnsupportedOperationException("You cannot set a foreign key");
    }
    this.groupid = groupid;
  }

  /**
   * this method will get filled in automatically by a manager
   */
  public void setQuestiongroupBO(QuestiongroupBO questiongroupBO) {
    if (questiongroupBO == null) {
      markChanged(this.groupid, null);
      this.groupid = null;
    } else {
      markChanged(this.groupid, questiongroupBO.getQuestiongroupid());
      this.groupid = questiongroupBO.getQuestiongroupid();
    }
    this.questiongroupBO = questiongroupBO;
  }

  public QuestiongroupBO getQuestiongroupBO() {
    return questiongroupBO;
  }

  public Double getQuestionValue() {
    return questionValue;
  }

  public void setQuestionValue(Double questionValue) {
    markChanged(this.questionValue, questionValue);
    this.questionValue = questionValue;
  }

  public Integer getQuestionOrder() {
    return questionOrder;
  }

  public void setQuestionOrder(Integer questionOrder) {
    markChanged(this.questionOrder, questionOrder);
    this.questionOrder = questionOrder;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    markChanged(this.description, description == null ? "" : description.trim());
    this.description = description == null ? "" : description.trim();
  }

  @SuppressWarnings("unused")
  private Integer getSmediafileid() {
    SMediaFileBO mediaFileBO = mediaFileHolder.get();
    if (mediaFileBO != null) {
      return mediaFileBO.getSmediafileid();
    }
    return null;
  }

  //used by ibatis
  @SuppressWarnings("unused")
  private void setSmediafileid(Integer smediafileid) {
    if (isLocked()) {
      throw new UnsupportedOperationException("You cannot set a foreign key");
    }
    SMediaFileBO mediaFileBO = mediaFileHolder.get();
    if (mediaFileBO != null) {
      mediaFileBO.setSmediafileid(smediafileid);
    } else {
      throw new IllegalArgumentException("Current MediaFileBO does not exist");
    }
  }

  public TrackableHolder<SMediaFileBO> getMediaFileHolder() {
    return mediaFileHolder;
  }

  @OneToOne(optional = true)
  @JoinColumn(name = "smediafileid")
  public SMediaFileBO getMediaFileBO() {
    return mediaFileHolder.get();
  }

  public void setMediaFileBO(SMediaFileBO mediaFileBO) {
    mediaFileHolder.set(mediaFileBO);
    markChanged(mediaFileHolder.hasChanged());
  }

  public QuestionType getQuestionTypeEnum() {
    return questionType;
  }

  public void setQuestionTypeEnum(QuestionType questionType) {
    markChanged(this.questionType, questionType);
    this.questionType = questionType;
  }

  @SuppressWarnings("unused")
  private String getQuestionType() {
    return questionType == null ? null : questionType.name();
  }

  @SuppressWarnings("unused")
  private void setQuestionType(String questionType) {
    setQuestionTypeEnum(questionType == null ? null : QuestionType.valueOf(questionType));
  }

  @Override
  public int hashCode() {
    return questionid == null ? 0 : questionid.hashCode();
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    return obj instanceof QuestionBO
            && questionid != null
            && questionid.equals(((QuestionBO) obj).getQuestionid());
  }

  public String toString() {
    return "QuestionBO{" + questionid
            + "->" + groupid + "(" + questionOrder + ")" // group and order within
            + ": '" + questionText + "' - " + questionType + " (" + questionValue + " %)" // name, type and value
            + (isNullOrEmpty(description) ? "" : ", \"" + description + "\"")
            + (answers.isEmpty() ? "" : ", " + answers.size() + " answers")
            + (mediaFileHolder.get() == null ? "" : ", " + "mediaFile = " + mediaFileHolder.get())
            + "}";
  }

  @Override
  public BaseBean newInstance() {
    return new QuestionBO();
  }

  @Override
  public List<SearchOrder> defaultSortOrder() {
    List<SearchOrder> ordering = new ArrayList<SearchOrder>();
    ordering.add(new SearchOrder(Fields.ORDER, SearchOrder.Direction.ASC));
    ordering.add(new SearchOrder(Fields.VALUE, SearchOrder.Direction.DESC));
    ordering.add(new SearchOrder(Fields.TEXT, SearchOrder.Direction.ASC));
    return ordering;
  }

}