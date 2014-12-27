package com.ibatis.scorecardmodel.bo.evaluation;


import com.ibatis.scorecardmodel.BaseBean;
import com.ibatis.scorecardmodel.bo.ServerSideDate;
import com.ibatis.scorecardmodel.bo.ServerSideDateSupport;

import java.util.Date;

public class EvalcallBO extends BaseBean {
  private static final long serialVersionUID = 7832746950516056962L;

  public enum Fields {
    SID("sid"), MAIN("isMain"), FROM("fromNumber"), TO("toNumber"),
    START("startDate"), STOP("stopDate"), LENGTH("length"), DESCRIPTION("description"),
    EXTERNAL_ID_TYPE("externalIdType"), EXTERNAL_ID("externalId");

    private String fieldName;

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

  //need to be added and filled other fields
  private Integer evalcallsid;
  private String sid;
  private String externalIdType;
  private String externalId;
  private boolean isMain;
  private String fromNumber;
  private String toNumber;
  private ServerSideDate startDate;
  private ServerSideDate stopDate;
  private long length;
  private Integer interactionType;
  private InteractionTypeBO interactionTypeBO;
  private Integer subevaluationid;
  private String description;

  // transient
  private String coupleDirection;
  private InteractionTypeBO requestedInteractionType;

  private SubevaluationBO subevaluationBO;

  public EvalcallBO() {
    fromNumber = "";
    toNumber = "";
  }

  @Override
  public Integer getId() {
    return getEvalcallsid();
  }

  @Override
  public void setId(Integer value) {
    setEvalcallsid(value);
  }

  public Integer getEvalcallsid() {
    return evalcallsid;
  }

  // used by iBatis
  public void setEvalcallsid(Integer evalcallsid) {
    if (isLocked()) {
      throw new UnsupportedOperationException("You cannot set a primary key");
    }
    markChanged(this.evalcallsid, evalcallsid);
    this.evalcallsid = evalcallsid;
  }


  public String getDescription() {
    return description;
  }


  public void setDescription(String description) {
    markChanged(this.description, description);
    this.description = description;
  }


  public String getSid() {
    return sid;
  }

  public void setSid(String sid) {
    markChanged(this.sid, sid);
    this.sid = sid;
  }

  public String getExternalIdType() {
    return externalIdType;
  }

  public void setExternalIdType(String externalIdType) {
    markChanged(this.externalIdType, externalIdType);
    this.externalIdType = externalIdType;
  }

  public String getExternalId() {
    return externalId;
  }

  public void setExternalId(String externalId) {
    markChanged(this.externalId, externalId);
    this.externalId = externalId;
  }

  public boolean getIsMain() {
    return isMain;
  }

  public void setIsMain(boolean isMain) {
    markChanged(this.isMain, isMain);
    this.isMain = isMain;
  }

  public String getFromNumber() {
    return fromNumber;
  }

  public void setFromNumber(String fromNumber) {
    markChanged(this.fromNumber, fromNumber);
    this.fromNumber = fromNumber;
  }

  public String getToNumber() {
    return toNumber;
  }

  public void setToNumber(String toNumber) {
    markChanged(this.toNumber, toNumber);
    this.toNumber = toNumber;
  }

  public Date getStartDate() {
    return startDate;
  }

  public void setStartDate(Date startDate) {
    ServerSideDate serverSideDate = ServerSideDateSupport.createServerSideDate(startDate);
    markChanged(this.startDate, serverSideDate);
    this.startDate = serverSideDate;
  }

  public Date getStopDate() {
    return stopDate;
  }

  public void setStopDate(Date stopDate) {
    ServerSideDate serverSideDate = ServerSideDateSupport.createServerSideDate(stopDate);
    markChanged(this.stopDate, serverSideDate);
    this.stopDate = serverSideDate;
  }

  public long getLength() {
    return length;
  }

  public void setLength(long length) {
    markChanged(this.length, length);
    this.length = length;
  }

  public Integer getSubevaluationid() {
    return subevaluationid;
  }


  public void setSubevaluationid(Integer subevaluationid) {
    markChanged(this.subevaluationid, subevaluationid);
    this.subevaluationid = subevaluationid;
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

  public Integer getInteractionType() {
    return interactionType;
  }

  // used by iBatis
  @SuppressWarnings("unused")
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

  public InteractionTypeBO getRequestedInteractionType() {
    return requestedInteractionType;
  }


  public void setRequestedInteractionType(InteractionTypeBO requestedInteractionType) {
    this.requestedInteractionType = requestedInteractionType;
  }

  public String getCoupleDirection() {
    return coupleDirection;
  }

  public void setCoupleDirection(String coupleDirection) {
    this.coupleDirection = coupleDirection;
  }

  @Override
  public int hashCode() {
    return ((getEvalcallsid() == null) ? 0 : getEvalcallsid().hashCode());
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (!(obj instanceof EvalcallBO))
      return false;
    EvalcallBO other = (EvalcallBO) obj;
    return getEvalcallsid() != null && other.getEvalcallsid() != null && getEvalcallsid().equals(other.getEvalcallsid());
  }

  @Override
  public String toString() {
    return "EvalcallBO{" +
            "sid=" + sid + ", " +
            "isMain=" + isMain + ", " +
            "fromNumber=" + fromNumber + ", " +
            "toNumber=" + toNumber + ", " +
            "startDate=" + startDate + ", " +
            "stopDate=" + stopDate + ", " +
            "length=" + length + ", " +
            "subevalationid=" + subevaluationid + ", " +
            "subevaluation=" + subevaluationBO + ", " +
            "mediaTypes=" + interactionType + "}";
  }

  @Override
  public BaseBean newInstance() {
    return new EvalcallBO();
  }
}
