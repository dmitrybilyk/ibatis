package com.ibatis.scorecardmodel.bo.evaluation;



import com.ibatis.scorecardmodel.BaseBean;
import com.ibatis.scorecardmodel.bo.ChartInfo;
import com.ibatis.scorecardmodel.bo.ServerSideDate;
import com.ibatis.scorecardmodel.bo.ServerSideDateSupport;
import com.ibatis.scorecardmodel.bo.TrackableLinkedHashSet;
import com.ibatis.scorecardmodel.bo.question.QuestformBO;
import com.ibatis.scorecardmodel.bo.user.CompanyBO;
import com.ibatis.scorecardmodel.bo.user.UserBO;
import com.ibatis.search.SearchOrder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

/** @author Stanislav Valenta, 26.6.2009 */
public class EvaluationBO extends BaseBean implements ChartInfo {
  private static final long serialVersionUID = 1835890619213230551L;

  public enum Fields {
	  EVALUATION_ID("id"),
    EVAL_DATE("evaluationDate"), Q_FORM("qformid"), EVALUATOR("evaluatorBO"), EVALUATED_USER("evaluatedUserBO"), ALLOW_MODIFICATION("allowModification"),
    LAST_MODIFIED("lastModified"), EVAL_STATUS("evalstatus"), COMMENTS("comments"), EVALUATION_COPYOF("copyof"), READ("read"),
    FEEDBACK_IMPROVE("feedbackImprove"),FEEDBACK_MAINTAIN("feedbackMaintain"), APPLY_TO_STATISTICS("applyToStatistics"),
    COMPANY_EVALUATION("company"), SCORE("score"), VISIBLE_TO_AGENT("visibleToAgent"), CREATED_BY("createdByBO");
    private final String fieldName;

    Fields(final String fieldName) {
      this.fieldName = fieldName;
    }

    public String getFieldName() {
      return fieldName;
    }

    public static Fields getField(String fieldName) {
      for (Fields field: Fields.values())
        if (field.getFieldName().equals(fieldName))
          return field;
      return null;
    }
  }

  public enum EvalStatus {
    CREATED, IN_PROGRES, FINISHED, TEMPLATE
  }

  private Integer evaluationid;
  private ServerSideDate evaluationDate;
  private Integer qformid;
  private QuestformBO questformBO;
  private Integer evaluatorid;
  private UserBO evaluatorBO;  //todo: load as default
  private Integer evaluatedUser;
  private UserBO evaluatedUserBO; //todo: load as default
  private Boolean allowModification;
  private ServerSideDate lastModified;
  private String comments;
  private Integer copyof;
  private Boolean read;
  private FeedbackBO feedbackBO;
  private Boolean applyToStatistics;
  private Integer company;
  private CompanyBO companyBO;  //todo: load as default
  private Integer createdBy;
  private UserBO createdByBO;       //todo: load as default
  private EvalStatus evalstatus;
  private Double score;
  private TrackableLinkedHashSet<CriteriaBO> criteriaSet;
  private boolean visibleToAgent;

  public EvaluationBO() {
    allowModification = true;
    applyToStatistics = true;
    visibleToAgent = true;
    lastModified = new ServerSideDate();
    read = false;
    score = 0.0;
    evalstatus = EvalStatus.CREATED;
    feedbackBO = new FeedbackBO();
    comments = "";
    criteriaSet = new TrackableLinkedHashSet<CriteriaBO>();
  }

  @Override
  public boolean hasChanged() {
    return super.hasChanged() || feedbackBO.hasChanged();
  }

  @Override
  public boolean hasAnythingChanged() {
    boolean changed = super.hasAnythingChanged();
    for (CriteriaBO criteriaBO: criteriaSet) {
      changed |= criteriaBO.hasAnythingChanged();
    }
    changed |= feedbackBO.hasAnythingChanged();
    return changed;
  }

  public QuestformBO getQuestformBO() {
    return questformBO;
  }

  public void setQuestformBO(QuestformBO questformBO) {
    this.questformBO = questformBO;
    if (questformBO != null) {
      markChanged(qformid, questformBO.getQformid());
      qformid = questformBO.getQformid();
    } else {
      markChanged(qformid, null);
      qformid = null;
    }
  }

  public Set<CriteriaBO> getCriteria() {
    return criteriaSet;
  }

  public CriteriaBO getMainCriteriaBO() {
    // should be at the first position, but just in case
    for (CriteriaBO criteriaBO: getCriteria())
      if (criteriaBO.getIsMain())
        return criteriaBO;
    return null;
  }

  // used by ibatis
  @SuppressWarnings("unused")
  private void setIbatisMainCriteriaBO(CriteriaBO criteriaBO) {
    criteriaSet.add(criteriaBO);
  }

  //used by ibatis
  @SuppressWarnings("unused")
  private CriteriaBO getIbatisMainCriteriaBO() {
    CriteriaBO criteriaBO = getMainCriteriaBO();
    if (criteriaBO == null) {
      criteriaBO = new CriteriaBO();
      criteriaBO.setIsMain(true);
    }
    criteriaSet.add(criteriaBO);
    return criteriaBO;
  }

  public void addCriteria(CriteriaBO criteriaBO) {
    criteriaBO.setEvaluationBO(this);
    criteriaSet.add(criteriaBO);
  }

  public void removeCriteria(CriteriaBO criteriaBO) {
    criteriaBO.setEvaluationBO(null);
    criteriaSet.remove(criteriaBO);
  }

  /**
   * Finds all subevaluations belonging to this evaluation.
   *
   * @return a new {@link TrackableLinkedHashSet} with subevaluations
   */
  @NotNull
  public Set<SubevaluationBO> getSubevaluations() {
    TrackableLinkedHashSet<SubevaluationBO> set = new TrackableLinkedHashSet<SubevaluationBO>();
    for (CriteriaBO criteriaBO: getCriteria()){
      if (criteriaBO.getSubevaluations() instanceof TrackableLinkedHashSet<?>) {
        TrackableLinkedHashSet<SubevaluationBO> trackableLinkedHashSet = (TrackableLinkedHashSet<SubevaluationBO>) criteriaBO.getSubevaluations();
        set.addAllStateful(trackableLinkedHashSet);
      } else {
        set.addAll(criteriaBO.getSubevaluations());
      }
    }
    return set;
  }

  public void setVisibleToAgent(boolean visibleToAgent) {
    markChanged(this.visibleToAgent, visibleToAgent);
    this.visibleToAgent = visibleToAgent;
  }

  public boolean isVisibleToAgent() {
    return visibleToAgent;
  }

  @Override
  public String getInfo() {
    return score + ", " + evaluationDate;
  }


  public EvalStatus getEvalstatusEnum() {
    return evalstatus;
  }


  public void setEvalstatusEnum(EvalStatus evalStatus) {
    markChanged(this.evalstatus, evalStatus);
    this.evalstatus = evalStatus;
  }

  public UserBO getCreatedByBO() {
    return createdByBO;
  }

  public void setCreatedByBO(UserBO creator) {
   this.createdByBO = creator;
    if (createdByBO != null) {
      markChanged(createdBy, createdByBO.getUserid());
      createdBy = createdByBO.getUserid();
    } else {
      markChanged(createdBy, null);
      createdBy = null;
    }
  }

  public CompanyBO getCompanyBO() {
    return companyBO;
  }

  public void setCompanyBO(CompanyBO companyBO) {
    this.companyBO = companyBO;
    if (companyBO != null) {
      markChanged(company, companyBO.getCompanyid());
      company = companyBO.getCompanyid();
    } else {
      markChanged(company, null);
      company = null;
    }
  }

  public UserBO getEvaluatorBO() {
    return evaluatorBO;
  }

  public void setEvaluatorBO(UserBO evaluator) {
    this.evaluatorBO = evaluator;
    if (evaluatorBO != null) {
      markChanged(evaluatorid, evaluatorBO.getUserid());
      evaluatorid = evaluatorBO.getUserid();
    } else {
      markChanged(evaluatorid, null);
      evaluatorid = null;
    }
  }

  public UserBO getEvaluatedUserBO() {
    return evaluatedUserBO;
  }

  public void setEvaluatedUserBO(UserBO evaluatedUserBO) {
    this.evaluatedUserBO = evaluatedUserBO;
    if (evaluatedUserBO != null) {
      markChanged(evaluatedUser, evaluatedUserBO.getUserid());
      evaluatedUser = evaluatedUserBO.getUserid();
    } else {
      markChanged(evaluatedUser, null);
      evaluatedUser = null;
    }
  }

  @Override
  public Integer getId() {
    return getEvaluationid();
  }

  @Override
  public void setId(Integer value) {
    setEvaluationid(value);
  }

  public Integer getEvaluationid() {
    return evaluationid;
  }

  //used by ibatis
  public void setEvaluationid(Integer evaluationid) {
    if (isLocked()) {
      throw new UnsupportedOperationException("You cannot set a primary key");
    }
    this.evaluationid = evaluationid;
    for (CriteriaBO criteriaBO: getCriteria()) {
      criteriaBO.setEvaluationBO(this);
    }
  }

  public Date getEvaluationDate() {
    return evaluationDate;
  }

  public void setEvaluationDate(Date evaluationDate) {
    ServerSideDate serverSideDate = ServerSideDateSupport.createServerSideDate(evaluationDate);
    markChanged(this.evaluationDate, serverSideDate);
    this.evaluationDate = serverSideDate;
  }

  public Integer getQformid() {
    return qformid;
  }

  //used by ibatis
  @SuppressWarnings({"unused"})
  private void setQformid(Integer qformid) {
    if (isLocked())
      throw new UnsupportedOperationException("You cannot set a foreign key");
    this.qformid = qformid;
  }

  public Integer getEvaluatorid() {
    return evaluatorid;
  }

  //used by ibatis
  @SuppressWarnings({"unused"})
  private void setEvaluatorid(Integer evaluatorid) {
    if (isLocked())
      throw new UnsupportedOperationException("You cannot set a foreign key");
    this.evaluatorid = evaluatorid;
  }

  public Integer getEvaluatedUser() {
    return evaluatedUser;
  }

  //used by ibatis
  @SuppressWarnings({"unused"})
  private void setEvaluatedUser(Integer evaluatedUser) {
    if (isLocked())
      throw new UnsupportedOperationException("You cannot set a foreign key");
    this.evaluatedUser = evaluatedUser;
  }

  public Boolean getAllowModification() {
    return allowModification;
  }

  public void setAllowModification(Boolean allowModification) {
    markChanged(this.allowModification, allowModification);
    this.allowModification = allowModification;
  }

  public Date getLastModified() {
    return lastModified;
  }

  public void setLastModified(Date lastModified) {
    ServerSideDate serverSideDate = ServerSideDateSupport.createServerSideDate(lastModified);
    markChanged(this.lastModified, serverSideDate);
    this.lastModified = serverSideDate;
    feedbackBO.setLastModified(serverSideDate);
  }

  public FeedbackBO getFeedbackBO() {
    return feedbackBO;
  }

  //used by ibatis
  @SuppressWarnings({"unused"})
  private void setEvalstatus(String status) {
    if (isLocked())
      throw new UnsupportedOperationException("You cannot set this field");
    evalstatus = EvalStatus.valueOf(status);
  }

  //used by ibatis
  protected String getEvalstatus() {
    return evalstatus.toString();
  }

  public String getComments() {
    return comments;
  }

  public void setComments(String comments) {
    markChanged(this.comments, comments == null ? "" : comments.trim());
    this.comments = comments == null ? "" : comments.trim();
  }

  public Integer getCopyof() {
    return copyof;
  }

  public void setCopyof(@Nullable Integer copyof) {
    markChanged(this.copyof, copyof);
    this.copyof = copyof;
  }

  public Boolean getRead() {
    return read;
  }

  public void setRead(Boolean read) {
    markChanged(this.read, read);
    this.read = read;
  }

  public String getFeedbackImprove() {
    return feedbackBO.getImprove();
  }

  public void setFeedbackImprove(String feedbackImprove) {
    this.feedbackBO.setImprove(feedbackImprove);
  }

  public String getFeedbackMaintain() {
    return feedbackBO.getMaintain();
  }

  public void setFeedbackMaintain(String feedbackMaintain) {
    this.feedbackBO.setMaintain(feedbackMaintain);
  }

  public Boolean getApplyToStatistics() {
    return applyToStatistics;
  }

  public void setApplyToStatistics(Boolean applyToStatistics) {
    markChanged(this.applyToStatistics, applyToStatistics);
    this.applyToStatistics = applyToStatistics;
  }

  public Integer getCompany() {
    return company;
  }

  //used by ibatis
  @SuppressWarnings({"unused"})
  private void setCompany(Integer company) {
    if (isLocked())
      throw new UnsupportedOperationException("You cannot set a foreign key");
    this.company = company;
  }

  public Integer getCreatedBy() {
    return createdBy;
  }

  //used by ibatis
  @SuppressWarnings({"unused"})
  private void setCreatedBy(Integer createdBy) {
    if (isLocked())
      throw new UnsupportedOperationException("You cannot set a foreign key");
    this.createdBy = createdBy;
  }

  public void setScore(Double score) {
    markChanged(this.score, score);
    this.score = score;
  }

  public Double getScore() {
    return score;
  }

  @Override
  public int hashCode() {
    return getEvaluationid() == null ? 0 : getEvaluationid().hashCode();
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (!(obj instanceof EvaluationBO)) {
      return false;
    }
    EvaluationBO other = (EvaluationBO) obj;
    return getEvaluationid() != null && getEvaluationid().equals(other.getEvaluationid());
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append("EvaluationBO");
    sb.append("{id=").append(evaluationid);
    sb.append('}');
    if(questformBO != null && questformBO.getQformname() != null) {
      sb.append("\nQuestionform: ").append(questformBO.getQformname());
    }
    if(evaluatorBO != null && evaluatorBO.getName() != null && evaluatorBO.getSurname() != null) {
      sb.append("\nEvaluator: ").append(evaluatorBO.getName()).append(" ").append(evaluatorBO.getSurname());
    }
    if(evaluatedUserBO != null && evaluatedUserBO.getName() != null && evaluatedUserBO.getSurname() != null) {
      sb.append("\nEvaluated user: ").append(evaluatedUserBO.getName()).append(" ").append(evaluatedUserBO.getSurname());
    }

    return sb.toString();
  }

  @Override
  public void lock() {
    super.lock();
    criteriaSet.lock();
    feedbackBO.lock();
  }

  @Override
  public BaseBean newInstance() {
    return new EvaluationBO();
  }

    @Override
  public List<SearchOrder> defaultSortOrder() {
    List<SearchOrder> ordering = new ArrayList<SearchOrder>();
    ordering.add(new SearchOrder(Fields.LAST_MODIFIED, SearchOrder.Direction.DESC));
    return ordering;
  }

  /**Iterates over all criteria and returns minimum date periodFrom in all criteriaSet.
   *
   * @return Earliest date in criteria period from or null if criteria is empty or all dates periodFrom are null
   */
  @Nullable
  public Date getMinimumCriteriaPeriodFrom() {
    if (criteriaSet == null) {
      return null;
    }
    Date minimum = null;
    for (CriteriaBO criteriaBO : criteriaSet) {
      if (criteriaBO.getPeriodFrom() != null) {
        if (minimum == null) {
          minimum = criteriaBO.getPeriodFrom();
        } else if (criteriaBO.getPeriodFrom().before(minimum)) {
          minimum = criteriaBO.getPeriodFrom();
        }
      }
    }
    return minimum;
  }

  /**Iterates over all criteria and returns maximum date periodTo in all criteriaSet.
   *
   * @return Latest date in criteria period to or null if criteria is empty or all dates periodTo are null
   */
  @Nullable
  public Date getMaximumCriteriaPeriodTo() {
    if (criteriaSet == null) {
      return null;
    }
    Date maximum = null;
    for (CriteriaBO criteriaBO : criteriaSet) {
      if (maximum == null || (criteriaBO.getPeriodTo() != null && criteriaBO.getPeriodTo().after(maximum))) {
        maximum = criteriaBO.getPeriodTo();
      }
    }
    return maximum;
  }

  public boolean isSelfEvaluation() {
    Integer evaluatorId = evaluatorid != null ? evaluatorid
            : evaluatorBO != null ? evaluatorBO.getId()
            : null;
    Integer evaluatedUserId = evaluatedUser != null ? evaluatedUser
            : evaluatedUserBO != null ? evaluatedUserBO.getId()
            : null;
    return evaluatorId != null && evaluatorId.equals(evaluatedUserId);
  }

}
