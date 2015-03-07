package com.ibatis.scorecardmodel.bo.evaluation;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: mensik
 * Date: 14.10.2009
 * Time: 10:23:52
 */
public class TemplateForRelationBO implements Serializable {

  private static final long serialVersionUID = 4211115332580863956L;

  private int evaluatorid;
  private int relationid;//may be userid or groupid
  private int templateid;

  @SuppressWarnings("unused")
  private TemplateForRelationBO() {
    //empty constructor
  }

  public TemplateForRelationBO(int evaluatorId,int relationid){
    this.evaluatorid=evaluatorId;
    this.relationid=relationid;
  }

  public int getEvaluatorid() {
    return evaluatorid;
  }

  public void setEvaluatorid(int evaluatorid) {
    this.evaluatorid = evaluatorid;
  }

  public int getRelationid() {
    return relationid;
  }


  public void setRelationid(int relationid) {
    this.relationid = relationid;
  }

  public int getTemplateid() {
    return templateid;
  }

  public void setTemplateid(int templateid) {
    //TODO: fix access rights
    this.templateid = templateid;
  }
}
