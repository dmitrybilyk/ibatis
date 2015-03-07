package com.ibatis.scorecardmodel.bo.evaluation;



import com.ibatis.scorecardmodel.BaseBean;
import com.ibatis.scorecardmodel.bo.TrackableHolder;
import com.ibatis.scorecardmodel.bo.question.AnswerBO;
import com.ibatis.scorecardmodel.bo.question.SMediaFileBO;

import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

/**
 * @author Stanislav Valenta, 26.6.2009
 */
public class EvalAnswerBO extends BaseBean {
  private static final long serialVersionUID = 1111043115958825539L;

  private Integer evalanswerid;
  private Integer subevaluationid;
  private SubevaluationBO subevaluationBO;
  private Integer answerid;
  private AnswerBO answerBO;
  private Double totalAnswerValue;
  private Double groupAnswerValue;
  private Double questionAnswerValue;
  private Double answerValue;
  private String note;
  private TrackableHolder<SMediaFileBO> mediaFileHolder;

  public EvalAnswerBO() {
    note = "";
    mediaFileHolder = new TrackableHolder<SMediaFileBO>();
  }

  public SubevaluationBO getSubevaluationBO() {
    return subevaluationBO;
  }

  public void setSubevaluationBO(SubevaluationBO subevaluationBO) {
    this.subevaluationBO = subevaluationBO;
    if (subevaluationBO != null) {
      markChanged(subevaluationid, subevaluationBO.getSubevaluationid());
      subevaluationid = subevaluationBO.getSubevaluationid();
    } else {
      markChanged(subevaluationid, null);
      subevaluationid = null;
    }
  }

  public AnswerBO getAnswerBO() {
    return answerBO;
  }

  public void setAnswerBO(AnswerBO answerBO) {
    this.answerBO = answerBO;
    if (answerBO != null) {
      markChanged(answerid, answerBO.getAnswerid());
      answerid = answerBO.getAnswerid();
    } else {
      markChanged(answerid, null);
      answerid = null;
    }
  }

  public Integer getEvalanswerid() {
    return evalanswerid;
  }

  // used by ibatis
  public void setEvalanswerid(Integer evalanswerid) {
    if (isLocked()) {
      throw new UnsupportedOperationException("You cannot set a primary key");
    }
    this.evalanswerid = evalanswerid;
  }

  @Override
  public Integer getId() {
    return getEvalanswerid();
  }

  @Override
  public void setId(Integer value) {
    setEvalanswerid(value);
  }

  public Integer getSubevaluationid() {
    return subevaluationid;
  }

  //used by ibatis
  @SuppressWarnings({"unused"})
  private void setSubevaluationid(Integer subevaluationid) {
    markChanged(this.subevaluationid, subevaluationid);
    this.subevaluationid = subevaluationid;
  }

  public Integer getAnswerid() {
    return answerid;
  }

  // used by ibatis
  @SuppressWarnings({"unused"})
  private void setAnswerid(Integer answerid) {
    if (isLocked()) {
      throw new UnsupportedOperationException("You cannot set a foreign key");
    }
    this.answerid = answerid;
  }

  public Double getTotalAnswerValue() {
    return totalAnswerValue;
  }

  public void setTotalAnswerValue(Double totalAnswerValue) {
    markChanged(this.totalAnswerValue, totalAnswerValue);
    this.totalAnswerValue = totalAnswerValue;
  }

  public Double getGroupAnswerValue() {
    return groupAnswerValue;
  }

  public void setGroupAnswerValue(Double groupAnswerValue) {
    markChanged(this.groupAnswerValue, groupAnswerValue);
    this.groupAnswerValue = groupAnswerValue;
  }

  public Double getQuestionAnswerValue() {
    return questionAnswerValue;
  }

  public void setQuestionAnswerValue(Double questionAnswerValue) {
    markChanged(this.questionAnswerValue, questionAnswerValue);
    this.questionAnswerValue = questionAnswerValue;
  }

  public Double getAnswerValue() {
    return answerValue;
  }

  public void setAnswerValue(Double answerValue) {
    markChanged(this.answerValue, answerValue);
    this.answerValue = answerValue;
  }

  public String getNote() {
    return note;
  }

  public void setNote(String note) {
    markChanged(this.note, note == null ? "" : note.trim());
    this.note = note == null ? "" : note.trim();
  }

  public Integer getSmediafileid() {
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

  @Override
  public int hashCode() {
    return ((getEvalanswerid() == null) ? 0 : getEvalanswerid().hashCode());
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (!(obj instanceof EvalAnswerBO)) {
      return false;
    }
    EvalAnswerBO other = (EvalAnswerBO) obj;
    if (getEvalanswerid() == null || other.getEvalanswerid() == null) {
      return false;
    }
    return getEvalanswerid().equals(other.getEvalanswerid());
  }


  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append("EvalAnswerBO");
    sb.append("{id=").append(evalanswerid);
    sb.append('}');
    return sb.toString();
  }

  @Override
  public BaseBean newInstance() {
    return new EvalAnswerBO();
  }
}
