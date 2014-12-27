package com.ibatis.scorecardmodel.bo.interaction;

import com.ibatis.scorecardmodel.bo.question.QuestformBO;

/**
 * Created by IntelliJ IDEA.
 * User: juan
 * Date: 5/10/11
 * Time: 10:40 AM
 * To change this template use File | Settings | File Templates.
 */
public class InteractionExtraDataTO {
  private String coupleSid;
  private QuestformBO.ScoringSystem scoringSystem;
  private Double score;

  public String getCoupleSid() {
    return coupleSid;
  }

  public void setCoupleSid(String coupleSid) {
    this.coupleSid = coupleSid;
  }

  public QuestformBO.ScoringSystem getScoringSystem() {
    return scoringSystem;
  }

  public void setScoringSystem(QuestformBO.ScoringSystem scoringSystem) {
    this.scoringSystem = scoringSystem;
  }

  public Double getScore() {
    return score;
  }

  public void setScore(Double score) {
    this.score = score;
  }
}
