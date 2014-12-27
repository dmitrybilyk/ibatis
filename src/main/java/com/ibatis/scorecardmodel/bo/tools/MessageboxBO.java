package com.ibatis.scorecardmodel.bo.tools;



import com.ibatis.scorecardmodel.BaseBean;
import com.ibatis.scorecardmodel.bo.user.UserBO;
import com.ibatis.search.SearchOrder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MessageboxBO extends BaseBean {
  private static final long serialVersionUID = -1687616649015714002L;

  public enum Fields {
    TO_USER("toUser"), DATE_TIME("datetime"), SUBJECT("subject"), FROM_USER("fromUser"), CARBON_COPY("carbonCopy"), TEXT("text"),
    TO_STATUS("toStatus"), FROM_STATUS("fromStatus"), CC_STATUS("ccStatus");
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
  
  public enum Subject { 
    NewEvaluationCreated, EvaluationCompleted, SendEvaluationAgentFeedback, SendEvaluationFeedback, DelegationAssigned, DelegationDateChanged, DelegationChanged, DelegationRemoved, DeadlineReminder,
    PasswordRegenerated
  }

  public enum Status {
    NEW, READ, DELETED
  }
  
  private Integer messageboxid;
  private Integer toUser;
  private UserBO toUserBO;
  private Date datetime;
  private Subject subject;
  private Integer fromUser;
  private UserBO fromUserBO;
  private Integer carbonCopy;
  private UserBO carbonCopyBO;
  private String text;
  private Status toStatus;
  private Status fromStatus;
  private Status ccStatus;

  public MessageboxBO() {
    toStatus = Status.NEW;
    fromStatus = Status.NEW;
    ccStatus = Status.NEW;
    text = "";
  }

  @Override
  public Integer getId() {
    return getMessageboxid();
  }

  @Override
  public void setId(Integer value) {
    setMessageboxid(value);
  }

  public Integer getMessageboxid() {
    return messageboxid;
  }

  // used by ibatis
  public void setMessageboxid(Integer messageboxid) {
    if (isLocked()) {
      throw new UnsupportedOperationException("You cannot set a primary key");
    }
    this.messageboxid = messageboxid;
  }

  // used by ibatis
  public Integer getToUser() {
    return toUser;
  }

  // used by ibatis
  @SuppressWarnings("unused")
  private void setToUser(Integer toUser) {
    if (isLocked())
      throw new UnsupportedOperationException("You cannot set a foreign key");
    this.toUser = toUser;
  }

  public void setToUserBO(UserBO toUserBO) {
    this.toUserBO = toUserBO;
    if (toUserBO != null) {
      markChanged(toUser, toUserBO.getUserid());
      toUser = toUserBO.getUserid();
    } else {
      markChanged(toUser, null);
      toUser = null;
    }
  }

  public UserBO getToUserBO() {
    return toUserBO;
  }

  public Date getDatetime() {
    return datetime;
  }

  public void setDatetime(Date datetime) {
    markChanged(this.datetime, datetime);
    this.datetime = datetime;
  }

  // used by ibatis
  @SuppressWarnings("unused")
  private String getSubject() {
    return subject.toString();
  }

  // used by ibatis
  @SuppressWarnings("unused")
  private void setSubject(String subject) {
    if (isLocked())
      throw new UnsupportedOperationException("You cannot set this field. Use setSubjectEnum method instead.");
    this.subject = Subject.valueOf(subject);
  }
  
  public Subject getSubjectEnum() {
    return subject;
  }

  public void setSubjectEnum(Subject subject) {
    markChanged(this.subject, subject);
    this.subject = subject;
  }

  // used by ibatis
  public Integer getFromUser() {
    return fromUser;
  }

  // used by ibatis
  @SuppressWarnings("unused")
  private void setFromUser(Integer fromUser) {
    if (isLocked())
      throw new UnsupportedOperationException("You cannot set a foreign key.");
    this.fromUser = fromUser;
  }

  public void setFromUserBO(UserBO fromUserBO) {
    this.fromUserBO = fromUserBO;
    if (fromUserBO != null) {
      markChanged(fromUser, fromUserBO.getUserid());
      fromUser = fromUserBO.getUserid();
    } else {
      markChanged(fromUser, null);
      fromUser = null;
    }
  }

  public UserBO getFromUserBO() {
    return fromUserBO;
  }

  // used by ibatis
  public Integer getCarbonCopy() {
    return carbonCopy;
  }

  // used by ibatis
  @SuppressWarnings("unused")
  private void setCarbonCopy(Integer carbonCopy) {
    if (isLocked())
      throw new UnsupportedOperationException("You cannot set a foreign key.");
    this.carbonCopy = carbonCopy;
  }

  public void setCarbonCopyBO(UserBO carbonCopyBO) {
    this.carbonCopyBO = carbonCopyBO;
    if (carbonCopyBO != null) {
      markChanged(carbonCopy, carbonCopyBO.getUserid());
      carbonCopy = carbonCopyBO.getUserid();
    } else {
      markChanged(carbonCopy, null);
      carbonCopy = null;
    }
  }

  public UserBO getCarbonCopyBO() {
    return carbonCopyBO;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    markChanged(this.text, text == null ? "" : text.trim());
    this.text = text == null ? "" : text.trim();
  }

  // used by ibatis
  @SuppressWarnings("unused")
  private String getToStatus() {
    return toStatus.toString();
  }

  // used by ibatis
  @SuppressWarnings("unused")
  private void setToStatus(String toStatus) {
    if (isLocked())
      throw new UnsupportedOperationException("You cannot set this field. Use setToStatusEnum method instead.");
    this.toStatus = Status.valueOf(toStatus);
  }
  
  public Status getToStatusEnum() {
    return toStatus;
  }

  public void setToStatusEnum(Status toStatus) {
    markChanged(this.toStatus, toStatus);
    this.toStatus = toStatus;
  }
  
  // used by ibatis
  @SuppressWarnings("unused")
  private String getFromStatus() {
    return fromStatus.toString();
  }

  // used by ibatis
  @SuppressWarnings("unused")
  private void setFromStatus(String fromStatus) {
    if (isLocked())
      throw new UnsupportedOperationException("You cannot set this field. Use setFromStatusEnum method instead.");
    this.fromStatus = Status.valueOf(fromStatus);
  }
  
  public Status getFromStatusEnum() {
    return fromStatus;
  }

  public void setFromStatusEnum(Status fromStatus) {
    markChanged(this.fromStatus, fromStatus);
    this.fromStatus = fromStatus;
  }
  
  // used by ibatis
  @SuppressWarnings("unused")
  private String getCcStatus() {
    return ccStatus.toString();
  }

  // used by ibatis
  @SuppressWarnings("unused")
  private void setCcStatus(String ccStatus) {
    if (isLocked())
      throw new UnsupportedOperationException("You cannot set this field. Use setCcStatusEnum method instead.");
    this.ccStatus = Status.valueOf(ccStatus);
  }
  
  public Status getCcStatusEnum() {
    return ccStatus;
  }

  public void setCcStatusEnum(Status ccStatus) {
    markChanged(this.ccStatus, ccStatus);
    this.ccStatus = ccStatus;
  }
  
  @Override
  public int hashCode() {
    return ((getMessageboxid() == null) ? 0 : getMessageboxid().hashCode());
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (!(obj instanceof MessageboxBO))
      return false;
    MessageboxBO other = (MessageboxBO) obj;
    if (getMessageboxid() == null || other.getMessageboxid() == null)
        return false;
    return getMessageboxid().equals(other.getMessageboxid());
  }

  @Override
  public BaseBean newInstance() {
    return new MessageboxBO();
  }

  @Override
  public List<SearchOrder> defaultSortOrder() {
    List<SearchOrder> ordering = new ArrayList<SearchOrder>();
    ordering.add(new SearchOrder(Fields.DATE_TIME, SearchOrder.Direction.DESC));
    return ordering;
  }

}