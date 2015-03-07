package com.ibatis.scorecardmodel.bo.interaction;

/**
 * Stanislav Valenta, 9/30/13
 */
public interface BOValidator<Type> {
  boolean isValid(Type object);
}
