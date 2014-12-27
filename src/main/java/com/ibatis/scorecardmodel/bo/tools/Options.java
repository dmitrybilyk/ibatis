package com.ibatis.scorecardmodel.bo.tools;

import java.io.Serializable;

/**
 * Interface for options. Repesents a key - value pair, where the key is enum, value is string.
 *
 * Created by IntelliJ IDEA.
 * User: jelen
 * Date: 24.9.2009
 * Time: 13:21:06
 *
 * T - key type.
 */
public interface Options<T extends Enum<?>> extends Serializable {

  public T getKEYEnum();

  public String getValue();

  public void setValue(String value);

}
