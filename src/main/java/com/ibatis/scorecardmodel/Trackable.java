package com.ibatis.scorecardmodel;

/**
 * Trackable objects can track changes after they have been locked.
 * By default, they should be able to report whether they have changed after they've been locked.
 */
public interface Trackable {
  boolean hasChanged();

  boolean hasAnythingChanged();

  /**
   * Starts to keep track of changes.
   */
  void lock();

  void unlock();

  boolean isLocked();
}
