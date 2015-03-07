package com.ibatis.scorecardmodel.bo.charts;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

public class PointDateDouble implements Serializable {

  private static final long serialVersionUID = 2059801417294543216L;
  public Date date;
  public Double value;
  public String category;
  Collection<Integer> evaluationIds = new ArrayList<Integer>();

  public PointDateDouble() {
  }

  public PointDateDouble(Date date, Double value) {
    this.date = date;
    this.value = value;
  }



  /**
	 * An array of evaluation ids for represented by this instance on the graph
	 */
	public Collection<Integer> getEvaluationIds(){
		return evaluationIds;
	}

  public String getEvaluationIdsToString(final int max){
		StringBuffer sb = new StringBuffer();
    int exported = 0;
    for (Integer evaluationId : evaluationIds) {
      sb.append(evaluationId).append(",");
      if(++exported>=max){
        break;
      }

    }
    if(sb.length()>1){
      return sb.substring(0,sb.length()-1);
    }
    return "-1";


	}

	/**
	 * An array of evaluation ids for represented by this instance on the graph
	 *
	 * @param newVal
	 */
	public void setEvaluationIds(Collection<Integer> newVal){
		evaluationIds = newVal;
	}


  public void addEvaluationIds(Collection<Integer> addVal){
    evaluationIds.addAll(addVal);
  }

  public String getCategory() {
    return category;
  }

  public void setCategory(String category) {
    this.category = category;
  }
}
