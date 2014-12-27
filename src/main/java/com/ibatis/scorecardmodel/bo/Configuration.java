package com.ibatis.scorecardmodel.bo;


/** @author Stanislav Valenta, 20.5.2009 */
public interface Configuration {

  public String getDaoFactoryClass();
  public String getUserDaoClass();
  public String getEvaluationDaoClass();
  public String getMediaDaoClass();
  public String getQuestionDaoClass();
  public String getToolDaoClass();
  public String getAuthenticationDaoLocalClass();
  public String getAuthenticationDaoUCCClass();
  public String getAuthenticationDaoCallRecClass();
  
  public String getConfigurationServiceURL();
}
