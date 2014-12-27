package com.ibatis.scorecardmodel.bo.tools;

import com.ibatis.scorecardmodel.bo.ChartInfo;

/**
 * this class holds enumerations defined as value for appoptions or user settings
 * @author zdarsky
 *
 */
public interface ScorecardEnums {

  public enum NOT_APPLICABLE_ANSWER_CALCULATING_METHOD_VALUES {
    METHOD1, METHOD2, METHOD3, METHOD4
  }

  public enum QuestionThreshold implements ChartInfo {
    EXCELLENT, AVERAGE, BAD;

    @Override
    public String getInfo() {
      return name();
    }
  }

  public enum InteractionsVolumeChartType {
    VOLUME, VALUE_RATIO, CALL_LENGTH, CALL_RATIO
  }

  public enum EvaluationsVolumeChartType {
    VOLUME, VOLUME_RATIO
  }

  /* Do not change the order, it defines the order in which they are displayed */
  public enum InteractionState {
    EVALUATED, REPLACED, NOT_EVALUATED
  }

  public enum AppletServerCommunicationMode {
    NONE, SERVER_TO_CLIENT, CLIENT_TO_SERVER, BOTH, AUTODETECT
  }
}
