package com.ibatis.scorecardmodel.bo.dashboard;

import java.io.Serializable;

/** @author Stanislav Valenta, 20.2.2010 */
public interface WidgetConfigListener extends Serializable {
  void configChanged(WidgetConfig config);
}
