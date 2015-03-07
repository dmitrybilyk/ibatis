package com.ibatis.scorecardmodel.bo.evaluation;



import com.ibatis.scorecardmodel.BaseBean;
import com.ibatis.scorecardmodel.bo.ServerSideDate;
import com.ibatis.scorecardmodel.bo.ServerSideDateSupport;
import com.ibatis.scorecardmodel.bo.user.UserBO;
import com.ibatis.search.SearchOrder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * BO for feedbacks.
 *
 * Created by IntelliJ IDEA.
 * User: jelen
 * Date: 24.11.2009
 * Time: 16:42:27
 */
public class FeedbackBO extends BaseBean {

  private static final long serialVersionUID = 1835890654813240551L;

  private String maintain;
  private String improve;
  private ServerSideDate lastModified;
  private Integer evaluatorid;
  private UserBO evaluatorBO;

  public enum Fields {
    MAINTAIN("maintain"), IMPROVE("improve"), LAST_MODIFIED("lastModified"), EVALUATOR("evaluatorBO");
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

  public FeedbackBO() {
    maintain = "";
    improve = "";
  }

  public String getImprove() {
    return improve;
  }

  public void setImprove(String improve) {
    markChanged(this.improve, improve == null ? "" : improve.trim());
    if(improve == null){
      this.improve = "";
    } else {
      this.improve = improve.trim();
    }
  }

  public Date getLastModified() {
    return lastModified;
  }

  public String getMaintain() {
    return maintain;
  }

  public void setMaintain(String maintain) {
    markChanged(this.maintain, maintain == null ? "" : maintain.trim());
    if (maintain == null) {
      this.maintain = "";
    } else {
      this.maintain = maintain.trim();
    }
  }

  public void setLastModified(Date lastModified) {
    ServerSideDate serverSideDate = ServerSideDateSupport.createServerSideDate(lastModified);
    markChanged(this.lastModified, serverSideDate);
    this.lastModified = serverSideDate;
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

  @Override
  public int hashCode() {
    return getMaintain().hashCode() + getImprove().hashCode();
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (!(obj instanceof FeedbackBO))
      return false;
    FeedbackBO other = (FeedbackBO) obj;
    return getMaintain().compareTo(other.getMaintain()) == 0 && getImprove().compareTo(other.getImprove()) == 0;
  }

  @Override
  public BaseBean newInstance() {
    return new FeedbackBO();
  }

    @Override
  public List<SearchOrder> defaultSortOrder() {
    List<SearchOrder> ordering = new ArrayList<SearchOrder>();
    ordering.add(new SearchOrder(Fields.LAST_MODIFIED, SearchOrder.Direction.DESC));
    return ordering;
  }
}
