package com.ibatis.scorecardmodel.bo.evaluation;



import com.ibatis.scorecardmodel.BaseBean;
import com.ibatis.scorecardmodel.bo.Tools;
import com.ibatis.scorecardmodel.bo.TrackableLinkedHashSet;
import com.ibatis.scorecardmodel.bo.question.QuestionBO;
import com.ibatis.search.SearchOrder;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/** @author Stanislav Valenta, 25.6.2009 */
public class SubevaluationBO extends BaseBean {
  private static final long serialVersionUID = -4109887699570150835L;

  public enum Fields {
    CRITERIA_ID("criteriaid"), SUBEVAL_ORDERING("ordering"), TICKET_NUMBER("ticketNumber"), MAIL_NUMBER("mailNumber"), CATEGORY_ID("categoryid"),
    SUB_EVAL_TOTAL("subEvalTotal"), IMPROVE("feedbackImprove"), MAINTAIN("feedbackMaintain"), REPLACED("replaced"), SUB_EVAL_STATUS("subevalstatus"),
    INTERNAL_NOTE("internalNote"), FAST_NOTE("fastNote"), LENGTH("length"), INTERACTION_TYPE("interactionType"), LOCATION("location"),
    SUBEVAL_ID("subevaluationid");
    private final String fieldName;


    Fields(final String fieldName) {
      this.fieldName = fieldName;
    }


    public String getFieldName() {
      return fieldName;
    }


    public static Fields getField(String fieldName) {
      for (Fields field : Fields.values())
        if (field.getFieldName().equals(fieldName))
          return field;
      return null;
    }
  }

  public enum SubEvalStatus {
    CREATED, IN_PROGRESS, FINISHED
  }
  
  private Integer subevaluationid;
  private Integer criteriaid;
  private CriteriaBO criteriaBO;
  private Integer ordering;
  private String ticketNumber;
  private String mailNumber;
  private Integer categoryid;
  private CategoryBO categoryBO;
  private Double subEvalTotal;
//  private String feedbackImprove;
//  private String feedbackMaintain;
  private FeedbackBO feedbackBO;
  private String internalNote;
  private String internalNoteHtml;
  private String fastNote;
  private Integer length;
  private Integer interactionType;
  private InteractionTypeBO interactionTypeBO;
  private String location;
  private Boolean replaced;
  private SubEvalStatus subevalstatus;

  private TrackableLinkedHashSet<EvalAnswerBO> answerSet;
  private TrackableLinkedHashSet<EvalcallBO> evalCallSet;


  public SubevaluationBO() {
    subEvalTotal = 0.0;
    replaced = false;
    ordering = 0;
    feedbackBO = new FeedbackBO();
    internalNote = "";
    fastNote = "";
    ticketNumber = "";
    mailNumber = "";
    location = "";
    subevalstatus = SubEvalStatus.CREATED;
    answerSet = new TrackableLinkedHashSet<EvalAnswerBO>();
    evalCallSet = new TrackableLinkedHashSet<EvalcallBO>();
  }
  
  @Override
  public boolean hasAnythingChanged() {
    boolean changed = super.hasAnythingChanged();
    changed |= answerSet.hasAnythingChanged();
    changed |= (interactionTypeBO != null && interactionTypeBO.hasChanged());
    changed |= feedbackBO.hasAnythingChanged();
    changed |= evalCallSet.hasAnythingChanged();
    return changed;
  }

  public boolean hasEvaluationStarted() {
    boolean result = false;
    result |= !Tools.compareDouble(subEvalTotal, 0.0);
    result |= internalNote.length() > 0;
    result |= fastNote.length() > 0;
    result |= ticketNumber.length() > 0;
    result |= mailNumber.length() > 0;
    result |= location.length() > 0;
    result |= feedbackBO.getImprove().length() > 0;
    result |= feedbackBO.getMaintain().length() > 0;
    result |= !getEvalAnswers().isEmpty();
    return result;
  }


  @Override
  public void lock() {
    super.lock();
    answerSet.lock();
    evalCallSet.lock();
    feedbackBO.lock();
  }

  public void addEvalAnswer(EvalAnswerBO evalAnswerBO) {
    evalAnswerBO.setSubevaluationBO(this);
    answerSet.add(evalAnswerBO);
    // don't mark as changed. We use hasChanged() or hasAnythingChanget() method on TrackableLinkedHashSet instead.
  }


  public void removeEvalAnswer(EvalAnswerBO evalAnswerBO) {
    evalAnswerBO.setSubevaluationBO(null);
    answerSet.remove(evalAnswerBO);
    // don't mark as changed. We use hasChanged() or hasAnythingChanget() method on TrackableLinkedHashSet instead.
  }


  public void removeAllEvalAnswers() {
    answerSet.clear();
    // don't mark as changed. We use hasChanged() or hasAnythingChanget() method on TrackableLinkedHashSet instead.
  }
  
  public EvalAnswerBO getRemovedEvalAnswer(EvalAnswerBO evalAnswerBO) {
    if (evalAnswerBO == null) {
      return null;
    }
    Iterator<EvalAnswerBO> iterator = answerSet.getRemoved().iterator();
    QuestionBO questionBO = null;
    if (evalAnswerBO.getAnswerBO() != null && evalAnswerBO.getAnswerBO().getQuestionBO() != null)
      questionBO = evalAnswerBO.getAnswerBO().getQuestionBO();
    while (iterator.hasNext()) {
      EvalAnswerBO bo = iterator.next();
      if (bo.equals(evalAnswerBO))
        return bo;
      if (bo.getAnswerBO() != null && bo.getAnswerBO().getQuestionBO() != null)
        if (bo.getAnswerBO().getQuestionBO().equals(questionBO))
          return bo;
    }
    return null;
  }


  public Set<EvalAnswerBO> getEvalAnswers() {
    return answerSet;
  }

  // used by iBatis

  @SuppressWarnings("unused")
  private void setEvalAnswers(Set<EvalAnswerBO> evalAnswers) {
    if (evalAnswers != null) {
      answerSet = new TrackableLinkedHashSet<EvalAnswerBO>(evalAnswers);
    }
  }


  public Set<EvalcallBO> getEvalCalls() {
    return evalCallSet;
  }

  /**
   * Finds an evalcall within {@link #getEvalCalls()} that is considered {@link EvalcallBO#getIsMain() main}.
   * Normally the first added eval call should be main and there should be only one main eval call.
   *
   * @return first main evalcall found, or null if there's no main evalcall
   */
  @Nullable
  public EvalcallBO getMainEvalCallBO() {
    for (EvalcallBO evalCallBO : getEvalCalls()) {
      if (evalCallBO.getIsMain()) {
        return evalCallBO;
      }
    }
    return null;
  }

  
  public void addEvalCall(EvalcallBO evalcall) {
    evalcall.setSubevaluationBO(this);
    boolean changed = evalCallSet.add(evalcall);
    markChanged(changed);
  }


  public void removeEvalCall(EvalcallBO evalcall) {
    evalcall.setSubevaluationBO(null);
    boolean changed = evalCallSet.remove(evalcall);
    markChanged(changed);
  }

  private EvalcallBO getMainEvalCallIbatis() {
    if (getMainEvalCallBO() != null) {
      return getMainEvalCallBO();
    }
    if (getEvalCalls() == null) {
      evalCallSet = new TrackableLinkedHashSet<EvalcallBO>();
    }
    EvalcallBO main = new EvalcallBO();
    main.setIsMain(true);
    evalCallSet.add(main);
    return main;
  }

  private void setMainEvalCallIbatis(EvalcallBO evalcall) {
    if (getMainEvalCallBO() != null) {
      throw new UnsupportedOperationException("Cannot set a main call because there is one already");
    }
    if (getEvalCalls() == null) {
      evalCallSet = new TrackableLinkedHashSet<EvalcallBO>();
    }
    evalcall.setIsMain(true);
    evalCallSet.add(evalcall);
  }


  public CategoryBO getCategoryBO() {
    return categoryBO;
  }


  public void setCategoryBO(CategoryBO categoryBO) {
    this.categoryBO = categoryBO;
    if (categoryBO != null) {
      markChanged(categoryid, categoryBO.getCategoryid());
      categoryid = categoryBO.getCategoryid();
    } else {
      markChanged(categoryid, null);
      categoryid = null;
    }
  }

   private CategoryBO getCategoryIbatis() {
     if (categoryBO == null) {
       categoryBO = new CategoryBO();
     }
     return categoryBO;
  }

  private void setCategoryIbatis(CategoryBO category) {
     setCategoryBO(categoryBO);
  }


  public CriteriaBO getCriteriaBO() {
    return criteriaBO;
  }


  public void setCriteriaBO(CriteriaBO criteriaBO) {
    this.criteriaBO = criteriaBO;
    if (criteriaBO != null) {
      markChanged(criteriaid, criteriaBO.getCriteriaid());
      criteriaid = criteriaBO.getCriteriaid();
    }
    else {
      markChanged(criteriaid, null);
      criteriaid = null;
    }
  }

  public Integer getInteractionType() {
    return interactionType;
  }

  // used by iBatis
  @SuppressWarnings({"UnusedDeclaration"})
  private void setInteractionType(Integer interactionType) {
    if (isLocked())
      throw new UnsupportedOperationException("You cannot set a foreign key");
    this.interactionType = interactionType;
  }

  public InteractionTypeBO getInteractionTypeBO() {
    return interactionTypeBO;
  }

  public void setInteractionTypeBO(InteractionTypeBO interactionTypeBO) {
    this.interactionTypeBO = interactionTypeBO;
    if (interactionTypeBO != null) {
      markChanged(interactionType, interactionTypeBO.getInteractiontypeid());
      interactionType = interactionTypeBO.getInteractiontypeid();
    } else {
      markChanged(interactionType, null);
      interactionType = null;
    }
  }

  @SuppressWarnings("unused")
  private InteractionTypeBO getInteractionTypeIbatis() {
    if (interactionTypeBO == null) {
      interactionTypeBO = new InteractionTypeBO();
    }
    return interactionTypeBO;
  }

  @SuppressWarnings("unused")
  private void setInteractionTypeIbatis(InteractionTypeBO interaction) {
    setInteractionTypeBO(interaction);
  }

  @Override
  public Integer getId() {
    return getSubevaluationid();
  }
  
  @Override
  public void setId(Integer value) {
    setSubevaluationid(value);
  }
  // GETTERS, SETTERS ####################################################################################################


  public Integer getSubevaluationid() {
    return subevaluationid;
  }


  // used by ibatis
  public void setSubevaluationid(Integer subevaluationid) {
    if (isLocked()) {
      throw new UnsupportedOperationException("You cannot set a primary key");
    }
    this.subevaluationid = subevaluationid;
    for (EvalAnswerBO evalAnswerBO : getEvalAnswers()) {
      evalAnswerBO.setSubevaluationBO(this);
    }
    for (EvalcallBO evalcallBO : getEvalCalls()) {
      evalcallBO.setSubevaluationBO(this);
    }
  }


  public Integer getCriteriaid() {
    return criteriaid;
  }


  //used by ibatis
  @SuppressWarnings({"unused"})
  private void setCriteriaid(Integer criteriaid) {
    if (isLocked())
      throw new UnsupportedOperationException("You cannot set a foreign key");
    this.criteriaid = criteriaid;
  }


  public Integer getOrdering() {
    return ordering;
  }


  public void setOrdering(Integer ordering) {
    markChanged(this.ordering, ordering);
    this.ordering = ordering;
  }


  public String getTicketNumber() {
    return ticketNumber;
  }


  public void setTicketNumber(String ticketNumber) {
    markChanged(this.ticketNumber, ticketNumber == null ? "" : ticketNumber.trim());
    this.ticketNumber = ticketNumber == null ? "" : ticketNumber.trim();
  }


  public String getMailNumber() {
    return mailNumber;
  }


  public void setMailNumber(String mailNumber) {
    markChanged(this.mailNumber, mailNumber == null ? "" : mailNumber.trim());
    this.mailNumber = mailNumber == null ? "" : mailNumber.trim();
  }


  public Integer getCategoryid() {
    return categoryid;
  }


  // used by ibatis
  @SuppressWarnings({"unused"})
  private void setCategoryid(Integer categoryid) {
    if (isLocked())
      throw new UnsupportedOperationException("You cannot set a foreign key");
    this.categoryid = categoryid;
  }


  public Double getSubEvalTotal() {
    return subEvalTotal;
  }


  public void setSubEvalTotal(Double subEvalTotal) {
    markChanged(this.subEvalTotal, subEvalTotal);
    this.subEvalTotal = subEvalTotal;
  }


  public String getFeedbackImprove() {
    return feedbackBO.getImprove();
  }


  public void setFeedbackImprove(String feedbackImprove) {
    feedbackBO.setImprove(feedbackImprove);
  }


  public String getFeedbackMaintain() {
    return feedbackBO.getMaintain();
  }


  public void setFeedbackMaintain(String feedbackMaintain) {
    feedbackBO.setMaintain(feedbackMaintain);
  }


  public String getInternalNote() {
    return internalNote;
  }


  public void setInternalNote(String internalNote) {
    markChanged(this.internalNote, internalNote == null ? "" : internalNote.trim());
    this.internalNote = internalNote == null ? "" : internalNote.trim();
  }

  public String getInternalNoteHtml() {
    return internalNoteHtml;
  }

  public void setInternalNoteHtml(String internalNoteHtml) {
    // transient field - do not mark as changed
    this.internalNoteHtml = internalNoteHtml == null ? "" : internalNoteHtml.trim();
  }

  public String getFastNote() {
    return fastNote;
  }


  public void setFastNote(String fastNote) {
    markChanged(this.fastNote, fastNote == null ? "" : fastNote.trim());
    this.fastNote = fastNote == null ? "" : fastNote.trim();
  }


  public Integer getLength() {
    return length;
  }


  public void setLength(Integer length) {
    markChanged(this.length, length);
    this.length = length;
  }

  public String getLocation() {
    return location;
  }


  public void setLocation(String location) {
    markChanged(this.location, location == null ? "" : location.trim());
    this.location = location == null ? "" : location.trim();
  }


  public void setReplaced(Boolean replaced) {
    markChanged(this.replaced, replaced);
    this.replaced = replaced;
  }

  
  public Boolean isReplaced() {
    return replaced;
  }
  
  public SubEvalStatus getSubevalstatusEnum() {
    return subevalstatus;
  }

  public void setSubevalstatusEnum(SubEvalStatus subevalstatus) {
    markChanged(this.subevalstatus, subevalstatus);
    this.subevalstatus = subevalstatus;
  }
  
  //used by ibatis
  @SuppressWarnings({"unused"})
  private void setSubevalstatus(String status) {
    if (isLocked())
      throw new UnsupportedOperationException("You cannot set this field");
    subevalstatus = SubEvalStatus.valueOf(status);
  }

  //used by ibatis
  protected String getSubevalstatus() {
    return subevalstatus.toString();
  }


  @Override
  public int hashCode() {
    return ((getSubevaluationid() == null) ? 0 : getSubevaluationid().hashCode());
  }


  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (!(obj instanceof SubevaluationBO))
      return false;
    SubevaluationBO other = (SubevaluationBO) obj;
    if (getSubevaluationid() == null || other.getSubevaluationid() == null)
      return false;
    return getSubevaluationid().equals(other.getSubevaluationid());
  }


  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append("SubevaluationBO");
    sb.append("{id=").append(subevaluationid);
    sb.append('}');
    return sb.toString();
  }

  @Override
  public BaseBean newInstance() {
    return new SubevaluationBO();
  }

  @Override
  public boolean hasChanged() {
    return super.hasChanged() || feedbackBO.hasChanged();
  }

  @Override
  public List<SearchOrder> defaultSortOrder() {
    List<SearchOrder> ordering = new ArrayList<SearchOrder>();
    ordering.add(new SearchOrder(Fields.SUBEVAL_ORDERING, SearchOrder.Direction.ASC));
    return ordering;
  }
}