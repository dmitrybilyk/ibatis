package com.ibatis.scorecardmodel.bo.question;



import com.ibatis.scorecardmodel.BaseBean;
import com.ibatis.scorecardmodel.bo.ChartInfo;
import com.ibatis.scorecardmodel.bo.TrackableLinkedHashSet;
import com.ibatis.search.SearchOrder;

import javax.persistence.*;
import java.util.*;

import static com.google.common.base.Strings.isNullOrEmpty;

@Entity
@Table(name="questiongroups")
public class QuestiongroupBO extends BaseBean implements ChartInfo {
  private static final long serialVersionUID = 573066651741418454L;
  /**
   * Order by {@link #getGroupOrder()}, nulls last.
   */
  public static final Comparator<QuestiongroupBO> QUESTIONGROUP_ORDER_COMPARATOR = new Comparator<QuestiongroupBO>() {
    @Override
    public int compare(QuestiongroupBO g1, QuestiongroupBO g2) {
      return QuestformBO.NULL_LAST_INTEGER_COMPARATOR.compare(g1.getGroupOrder(), g2.getGroupOrder());
    }
  };

  public enum Fields {
    QUESTIONGR_GROUPNAME("groupname"), Q_FORM("qformid"), VALUE("groupValue"), QUESTIONGR_ORDER("groupOrder"),
    QUESTIONGR_DESC("description"),QUESTIONGROUPID("questiongroupid");
    private final String fieldName;

    Fields(final String fieldName) {
      this.fieldName = fieldName;
    }
    
    public String getFieldName() {
      return fieldName;
    }
    
    public static Fields getField(String fieldName) {
      for (Fields field: Fields.values())
        if (field.fieldName.equals(fieldName))
          return field;
      return null;
    }
  }

  @Id
  @GeneratedValue
  private Integer questiongroupid;
  private String groupname;
  @Transient
  private Integer qformid;
  @ManyToOne
  @JoinColumn(name ="qformid")
  private QuestformBO questformBO;
  @Column(name ="group_value")
  private Double groupValue;
  @Column(name ="group_order")
  private Integer groupOrder;
  private String description;
  @OneToMany(mappedBy = "questiongroupBO")
  private Set<QuestionBO> questions;
  
  public QuestiongroupBO() {
    questions = new TrackableLinkedHashSet<QuestionBO>();
    groupname = "";
    description = "";
  }

  @Override
  public void lock() {
    super.lock();
    if (questions instanceof TrackableLinkedHashSet) {
      ((TrackableLinkedHashSet) questions).lock();
    }
  }


  public double getBestAttainablePointsScore() {
    double currentMax = 0;

    for (QuestionBO questionBO : this.getQuestions()) {
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

    return currentMax;
  }


  public double getWorstAttainablePointsScore() {
    double currentMin = 0;

    for (QuestionBO questionBO : this.getQuestions()) {
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
    return currentMin;
  }
  
  @Override
  public boolean hasAnythingChanged() {
    boolean changed = super.hasAnythingChanged();
    if (questions instanceof TrackableLinkedHashSet) {
      changed |= ((TrackableLinkedHashSet) questions).hasAnythingChanged();
    }
    return changed;
  }
  
  public Set<QuestionBO> getQuestions() {
    return questions;
  }

  public boolean addQuestion(QuestionBO questionBO) {
    questionBO.setQuestiongroupBO(this);
    return questions.add(questionBO);
  }

  public void addQuestions(Set<QuestionBO> questionsBO) {
    for (QuestionBO questionBO: questionsBO)
      addQuestion(questionBO);
  }

  public boolean removeQuestion(QuestionBO questionBO) {
    questionBO.setQuestiongroupBO(null);
    return questions.remove(questionBO);
  }

  public void removeQuestions(Set<QuestionBO> questionsBO) {
    for (QuestionBO questionBO: questionsBO)
      removeQuestion(questionBO);
  }

  // userd by ibatis
  @SuppressWarnings("unused")
  private void setQuestionList(List<QuestionBO> questions) {
    if (questions == null)
      return;

    for (QuestionBO questionBO: questions) {
      addQuestion(questionBO);
    }
  }

  // userd by ibatis
  @SuppressWarnings("unused")
  private Set<QuestionBO> getQuestionList() {
    return new HashSet<QuestionBO>() {
      private static final long serialVersionUID = -3673859834170879383L;

      public boolean add(QuestionBO e) {
        return addQuestion(e);
      }
    };
  }

  @Override
  public Integer getId() {
    return getQuestiongroupid();
  }

  @Override
  public void setId(Integer value) {
    setQuestiongroupid(value);
  }

  public Integer getQuestiongroupid() {
    return questiongroupid;
  }

  //used by ibatis
  public void setQuestiongroupid(Integer questiongroupid) {
    if (isLocked()) {
      throw new UnsupportedOperationException("You cannot set a primary key");
    }
    this.questiongroupid = questiongroupid;
    for (QuestionBO questionBO: questions) {
      questionBO.setQuestiongroupBO(this);
    }
  }

  public String getGroupname() {
    return groupname;
  }

  public void setGroupname(String groupname) {
    markChanged(this.groupname, groupname == null ? "" : groupname.trim());
    this.groupname = groupname == null ? "" : groupname.trim();
  }

  public Integer getQformid() {
    return qformid;
  }

  //used by ibatis
  @SuppressWarnings("unused")
  private void setQformid(Integer qformid) {
    if (isLocked())
      throw new UnsupportedOperationException("You cannot set a foreign key");
    this.qformid = qformid;
  }

  /**
   * this method will get filled in automatically by a manager
   *
   * RPC serialization fix: group cannot be assigned to different form -> do not mark as changed.
   */
  public void setQuestformBO(QuestformBO questformBO) {
    if (questformBO == null) {
      this.qformid = null;
    } else {
      this.qformid = questformBO.getQformid();
    }
    this.questformBO = questformBO;
  }
  
  public QuestformBO getQuestformBO() {
    return questformBO;
  }

  public Double getGroupValue() {
    return groupValue;
  }

  public void setGroupValue(Double groupValue) {
    markChanged(this.groupValue, groupValue);
    this.groupValue = groupValue;
  }

  public Integer getGroupOrder() {
    return groupOrder;
  }

  public void setGroupOrder(Integer groupOrder) {
    markChanged(this.groupOrder, groupOrder);
    this.groupOrder = groupOrder;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    markChanged(this.description, description == null ? "" : description.trim());
    this.description = description == null ? "" : description.trim();
  }

  /**
   * ChartInfo implementation
   * @return legend to be displayed in chart
   */
  @Override
  public String getInfo() {
    return getGroupname();
  }

  @Override
  public int hashCode() {
    return questiongroupid == null ? 0 : questiongroupid.hashCode();
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    return obj instanceof QuestiongroupBO
            && questiongroupid != null
            && questiongroupid.equals(((QuestiongroupBO) obj).getQuestiongroupid());
  }

  public String toString() {
    return "QuestiongroupBO{" + questiongroupid // id
            + "->" + qformid + "(" + groupOrder + ")" // questform and order within it
            + ": '" + groupname + "' (" + groupValue + " %)" // name and weight
            + (isNullOrEmpty(description) ? "" : ", \"" + description + "\"")
            + (questions.isEmpty() ? "" : ", " + questions.size() + " questions")
            + "}";
  }

  @Override
  public BaseBean newInstance() {
    return new QuestiongroupBO();
  }

  @Override
  public List<SearchOrder> defaultSortOrder() {
    List<SearchOrder> ordering = new ArrayList<SearchOrder>();
    ordering.add(new SearchOrder(Fields.QUESTIONGR_ORDER, SearchOrder.Direction.ASC));
    ordering.add(new SearchOrder(Fields.QUESTIONGR_GROUPNAME, SearchOrder.Direction.ASC));
    return ordering;
  }

}