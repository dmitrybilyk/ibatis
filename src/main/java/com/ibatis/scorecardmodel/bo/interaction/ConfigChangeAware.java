package com.ibatis.scorecardmodel.bo.interaction;

import com.ibatis.scorecardmodel.bo.Configuration;

/**
 * Stanislav Valenta, 10/2/13
 */
public interface ConfigChangeAware {
  void onConfigChanged(Configuration configuration);
}
