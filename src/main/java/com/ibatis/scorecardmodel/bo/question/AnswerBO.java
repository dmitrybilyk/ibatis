package com.ibatis.scorecardmodel.bo.question;



import com.ibatis.scorecardmodel.BaseBean;
import com.ibatis.search.SearchOrder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Entity
@Table(name="answers")
public class AnswerBO extends BaseBean {
  private static final long serialVersionUID = 7026769972283077199L;
  /**
   * Order by {@link #getAnswerOrder()}, nulls last.
   */
  public static final Comparator<AnswerBO> ANSWER_ORDER_COMPARATOR = new Comparator<AnswerBO>() {
    @Override
    public int compare(AnswerBO a1, AnswerBO a2) {
      return QuestformBO.NULL_LAST_INTEGER_COMPARATOR.compare(a1.getAnswerOrder(), a2.getAnswerOrder());
    }
  };

  public enum Fields {
    TEXT("answerText"), VALUE("answerValue"), KEY("answerKeyEnum"),
    QUESTION("questionid"), ORDER("answerOrder"), DESC("description"), COMPLIANCE("complianceEnum");
    private final String fieldName;

    Fields(final String fieldName) {
      this.fieldName = fieldName;
    }

    public String getFieldName() {
      return fieldName;
    }

    public static Fields getField(String fieldName) {
      for (Fields field: Fields.values()) {
        if (field.fieldName.equals(fieldName)) {
            return field;
        }
    }
      return null;
    }
  }

  public enum Compliance {
    SUCCESS_ALL(false),
    SUCCESS_GROUP(false),
    NONE(true),
    NOT_APPLICABLE(false),
    FAIL_ALL(false),
    FAIL_GROUP(false);

    private final boolean scored;

    Compliance(boolean scored) {
      this.scored = scored;
    }

    /**
     * Signifies whether the answer with this compliance is allowed to have a score.
     *
     * @return true if the answer with this compliance can have a score; false if such an answer should not have score
     */
    public boolean isScored() {
      return scored;
    }
  }

  public enum AnswerKey {
    KEY_0("0"), KEY_1("1"), KEY_2("2"), KEY_3("3"), KEY_4("4"),
    KEY_5("5"), KEY_6("6"), KEY_7("7"), KEY_8("8"), KEY_9("9"),
    NONE("NONE");

    private String key;

    AnswerKey(String key) {
      this.key = key;
    }

    /**
     * @return The key as a String, which is unique and is never null. E.g., {@code "1"} for {@link #KEY_1}
     */
    @NotNull
    public String getKey() {
      return key;
    }

    /**
     * Find the enum instance that has the specified key
     * @param key the key, as a String, to find the enum instance for
     * @return the enum instance, or null if no instance with such key exists
     */
    @Nullable
    public static AnswerKey fromKey(@Nullable String key) {
      for (AnswerKey answerKey : values()) {
        if (answerKey.getKey().equals(key)) {
          return answerKey;
        }
      }
      return null;
    }
  }

  @Id
  @GeneratedValue
  private Integer answerid;
  @Column(name = "answer_text")
  private String answerText;
  @Column(name = "answer_value")
  private Double answerValue;
  @Column(name = "answer_key")
  private AnswerKey answerKey;
  @Transient
  private Integer questionid;
  @ManyToOne
  @JoinColumn(name = "questionid")
  private QuestionBO questionBO;
  @Column(name = "answer_order")
  private Integer answerOrder;
  private String description;
  private Compliance compliance;

  public AnswerBO() {
    compliance = Compliance.NONE;
    answerKey = AnswerKey.NONE;
    answerText = "";
    description = "";
  }

  public Compliance getComplianceEnum() {
    return compliance;
  }

  public void setComplianceEnum(Compliance compliance) {
    markChanged(this.compliance, compliance);
    this.compliance = compliance;
  }

  public AnswerKey getAnswerKeyEnum() {
    return answerKey;
  }

  public void setAnswerKeyEnum(AnswerKey answerKey) {
    markChanged(this.answerKey, answerKey);
    this.answerKey = answerKey;
  }

              /***********************
               * Getters and Setters *
               ***********************/

  @Override
  public Integer getId() {
    return getAnswerid();
  }

  @Override
  public void setId(Integer value) {
    setAnswerid(value);
  }

  public Integer getAnswerid() {
    return answerid;
  }

  //used by ibatis
  public void setAnswerid(Integer answerid) {
    if (isLocked()) {
      throw new UnsupportedOperationException("You cannot set a primary key");
    }
    this.answerid = answerid;
  }

  public String getAnswerText() {
      return answerText;
  }

  public void setAnswerText(String answerText) {
    markChanged(this.answerText, answerText == null ? "" : answerText.trim());
    this.answerText = answerText == null ? "" : answerText.trim();
  }

  public Double getAnswerValue() {
    return answerValue;
  }

  public void setAnswerValue(Double answerValue) {
    markChanged(this.answerValue, answerValue);
    this.answerValue = answerValue;
  }

  //used by ibatis
  @SuppressWarnings("unused")
  public String getAnswerKey() {
    return answerKey == null ? null : answerKey.getKey();
  }

  //used by ibatis
  @SuppressWarnings("unused")
  public void setAnswerKey(String answerKey) {
    if (isLocked()) {
      throw new UnsupportedOperationException("this method is for iBatis. Use setAnswerKeyEnum() instead");
    }
    this.answerKey = AnswerKey.fromKey(answerKey);
  }

  public Integer getQuestionid() {
    return questionid;
  }

  //used by ibatis
  @SuppressWarnings("unused")
  private void setQuestionid(Integer questionid) {
    if (isLocked()) {
        throw new UnsupportedOperationException("You cannot set a foreign key");
    }
    this.questionid = questionid;
  }

  /**
   * this method will get filled in automatically by a manager
   */
  public void setQuestionBO(QuestionBO questionBO) {
    if (questionBO == null) {
      markChanged(this.questionid, null);
      this.questionid = null;
    } else {
      markChanged(this.questionid, questionBO.getQuestionid());
      this.questionid = questionBO.getQuestionid();
    }
    this.questionBO = questionBO;
  }

  public QuestionBO getQuestionBO() {
    return questionBO;
  }

  public Integer getAnswerOrder() {
    return answerOrder;
  }

  public void setAnswerOrder(Integer answerOrder) {
    markChanged(this.answerOrder, answerOrder);
    this.answerOrder = answerOrder;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    markChanged(this.description, description == null ? "" : description.trim());
    this.description = description == null ? "" : description.trim();
  }

  //used by ibatis
  @SuppressWarnings("unused")
  private String getCompliance() {
    return compliance.toString();
  }

  //used by ibatis
  @SuppressWarnings("unused")
  private void setCompliance(String compliance) {
    if (isLocked()) {
      throw new UnsupportedOperationException("this method is for iBatis. Use setComplianceEnum() instead");
    }
    this.compliance = Compliance.valueOf(compliance);
  }

  @Override
  public int hashCode() {
    return ((getAnswerid() == null) ? 0 : getAnswerid().hashCode());
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
        return true;
    }
    if (!(obj instanceof AnswerBO)) {
        return false;
    }
    AnswerBO other = (AnswerBO) obj;
    if ((getAnswerid() == null) || (other.getAnswerid() == null)) {
        return false;
    }
    return getAnswerid().equals(other.getAnswerid());
  }

  /**
   * Constructs a <code>String</code> with all attributes
   * in name = value format.
   *
   * @return a <code>String</code> representation
   * of this object.
   */
  @Override
  public String toString() {
    final String tab = "\n    ";
    return new StringBuilder("\nAnswerBO (")
            .append(tab).append("answerid = ").append(this.answerid)
            .append(tab).append("answerText = ").append(this.answerText)
            .append(tab).append("answerValue = ").append(this.answerValue)
            .append(tab).append("answerKey = ").append(this.answerKey)
            .append(tab).append("questionid = ").append(this.questionid)
            .append(tab).append("questionBO = ").append(this.questionBO)
            .append(tab).append("answerOrder = ").append(this.answerOrder)
            .append(tab).append("description = ").append(this.description)
            .append(tab).append("compliance = ").append(this.compliance)
            .append("\n)\n")
            .toString();
  }

  @Override
  public BaseBean newInstance() {
    return new AnswerBO();
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