package com.ibatis.scorecardmodel.bo;

import java.io.Serializable;

/**
 * General server response to client request
 * @author Stanislav Valenta, 28.4.2010
 */
public interface Response<D, S> extends Serializable {
  /** Requested data */
  D getData();
  /** Response status */
  S getStatus();
}
